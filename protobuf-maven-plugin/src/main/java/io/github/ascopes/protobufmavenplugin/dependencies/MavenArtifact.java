/*
 * Copyright (C) 2023 - 2025, Ashley Scopes.
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
package io.github.ascopes.protobufmavenplugin.dependencies;

import java.util.Set;
import org.jspecify.annotations.Nullable;


/**
 * Base interface for a parameter that consumes Maven artifact details.
 *
 * @author Ashley Scopes
 * @since 1.2.0
 */
public interface MavenArtifact {

  String getGroupId();

  String getArtifactId();

  String getVersion();

  @Nullable String getType();

  @Nullable String getClassifier();

  @Nullable DependencyResolutionDepth getDependencyResolutionDepth();

  Set<MavenExclusionBean> getExclusions();
}
