/*
 * Copyright (C) 2023 - 2024, Ashley Scopes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ascopes.protobufmavenplugin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("OptionalProtocPlugin tests")
class OptionalProtocPluginTest {

  @DisplayName("The default value for isOptional is the expected value")
  @Test
  void theDefaultValueForIsOptionalIsTheExpectedValue() {
    // Given
    var obj = mock(OptionalProtocPlugin.class);
    when(obj.isOptional()).thenCallRealMethod();

    // Then
    assertThat(obj.isOptional()).isFalse();
  }
}
