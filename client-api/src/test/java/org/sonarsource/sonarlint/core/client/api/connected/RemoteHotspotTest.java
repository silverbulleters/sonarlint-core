/*
 * SonarLint Core - Client API
 * Copyright (C) 2016-2020 SonarSource SA
 * mailto:info AT sonarsource DOT com
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
package org.sonarsource.sonarlint.core.client.api.connected;

import org.junit.jupiter.api.Test;
import org.sonarsource.sonarlint.core.client.api.common.TextRange;

import static org.assertj.core.api.Assertions.assertThat;

class RemoteHotspotTest {
  @Test
  void it_should_populate_fields_with_constructor_parameters() {
    RemoteHotspot hotspot = new RemoteHotspot("message",
      "path",
      new TextRange(0, 1, 2, 3),
      "author",
      RemoteHotspot.Status.TO_REVIEW,
      RemoteHotspot.Resolution.FIXED, new RemoteHotspot.Rule(
      "key",
      "name",
      "category",
      RemoteHotspot.Rule.Probability.HIGH,
      "risk",
      "vulnerability",
      "fix"
    ));

    assertThat(hotspot.message).isEqualTo("message");
    assertThat(hotspot.filePath).isEqualTo("path");
    assertThat(hotspot.textRange.getStartLine()).isZero();
    assertThat(hotspot.textRange.getStartLineOffset()).isEqualTo(1);
    assertThat(hotspot.textRange.getEndLine()).isEqualTo(2);
    assertThat(hotspot.textRange.getEndLineOffset()).isEqualTo(3);
    assertThat(hotspot.author).isEqualTo("author");
    assertThat(hotspot.status).isEqualTo(RemoteHotspot.Status.TO_REVIEW);
    assertThat(hotspot.resolution).isEqualTo(RemoteHotspot.Resolution.FIXED);
    assertThat(hotspot.rule.key).isEqualTo("key");
    assertThat(hotspot.rule.name).isEqualTo("name");
    assertThat(hotspot.rule.securityCategory).isEqualTo("category");
    assertThat(hotspot.rule.vulnerabilityProbability).isEqualTo(RemoteHotspot.Rule.Probability.HIGH);
    assertThat(hotspot.rule.riskDescription).isEqualTo("risk");
    assertThat(hotspot.rule.vulnerabilityDescription).isEqualTo("vulnerability");
    assertThat(hotspot.rule.fixRecommendations).isEqualTo("fix");

  }

}
