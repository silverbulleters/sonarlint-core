/*
 * SonarLint Core Library
 * Copyright (C) 2009-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonarsource.sonarlint.core.container.global;

import java.util.List;
import javax.annotation.Nullable;
import org.sonar.api.ExtensionProvider;
import org.sonar.api.SonarPlugin;
import org.sonarsource.sonarlint.core.container.ComponentContainer;
import org.sonarsource.sonarlint.core.container.PluginInfo;
import org.sonarsource.sonarlint.core.plugin.DefaultPluginRepository;

public class ExtensionInstaller {

  private final DefaultPluginRepository pluginRepository;

  public ExtensionInstaller(DefaultPluginRepository pluginRepository) {
    this.pluginRepository = pluginRepository;
  }

  public ExtensionInstaller install(ComponentContainer container, ExtensionMatcher matcher) {

    // plugin extensions
    for (PluginInfo pluginInfo : pluginRepository.getPluginInfos()) {
      SonarPlugin plugin = pluginRepository.getPluginInstance(pluginInfo.getKey());
      for (Object extension : plugin.getExtensions()) {
        doInstall(container, matcher, pluginInfo, extension);
      }
    }
    List<ExtensionProvider> providers = container.getComponentsByType(ExtensionProvider.class);
    for (ExtensionProvider provider : providers) {
      Object object = provider.provide();
      if (object instanceof Iterable) {
        for (Object extension : (Iterable) object) {
          doInstall(container, matcher, null, extension);
        }
      } else {
        doInstall(container, matcher, null, object);
      }
    }
    return this;
  }

  private static void doInstall(ComponentContainer container, ExtensionMatcher matcher, @Nullable PluginInfo pluginInfo, Object extension) {
    if (matcher.accept(extension)) {
      container.addExtension(pluginInfo, extension);
    } else {
      container.declareExtension(pluginInfo, extension);
    }
  }

}