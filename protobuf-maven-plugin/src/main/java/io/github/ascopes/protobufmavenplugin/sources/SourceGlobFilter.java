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
package io.github.ascopes.protobufmavenplugin.sources;

import io.github.ascopes.protobufmavenplugin.utils.FileUtils;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Filter for source files to allow including and excluding based on glob patterns.
 *
 * @author Ashley Scopes
 * @since 2.2.0
 */
final class SourceGlobFilter {

  private final List<PathMatcher> includes;
  private final List<PathMatcher> excludes;

  SourceGlobFilter(List<String> includes, List<String> excludes) {
    this.includes = compileMatchers(includes);
    this.excludes = compileMatchers(excludes);
  }

  boolean matches(Path relativeRoot, Path file) {
    var relativeFile = relativeRoot.relativize(file);

    if (excludes.stream().anyMatch(forPath(relativeFile))) {
      // File was explicitly excluded.
      return false;
    }

    if (!includes.isEmpty() && includes.stream().noneMatch(forPath(relativeFile))) {
      // File was not explicitly included when inclusions were present.
      return false;
    }

    return Files.isRegularFile(file)
        && FileUtils.getFileExtension(file)
            .filter(".proto"::equalsIgnoreCase)
            .isPresent();
  }

  private static List<PathMatcher> compileMatchers(List<String> patterns) {
    return patterns.stream()
        .map("glob:"::concat)
        .map(FileSystems.getDefault()::getPathMatcher)
        .collect(Collectors.toUnmodifiableList());
  }

  private static Predicate<PathMatcher> forPath(Path path) {
    return matcher -> matcher.matches(path);
  }
}
