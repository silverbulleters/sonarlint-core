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

import org.sonar.api.CoreProperties;
import org.sonar.api.config.PropertyDefinitions;
import org.sonar.api.config.Settings;

public class GlobalSettings extends Settings {

  private final GlobalProperties bootstrapProps;

  public GlobalSettings(GlobalProperties bootstrapProps, PropertyDefinitions propertyDefinitions) {
    super(propertyDefinitions);
    getEncryption().setPathToSecretKey(bootstrapProps.property(CoreProperties.ENCRYPTION_SECRET_KEY_PATH));
    this.bootstrapProps = bootstrapProps;
    init();
  }

  private void init() {
    addProperties(bootstrapProps.properties());
  }

}