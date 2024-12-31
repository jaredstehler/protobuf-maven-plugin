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

import io.github.ascopes.protobufmavenplugin.utils.HostSystem;
import io.github.ascopes.protobufmavenplugin.utils.ResolutionException;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Factory that can produce classifiers for dependencies based on the current platform.
 *
 * @author Ashley Scopes
 */
@Named
public final class PlatformClassifierFactory {

  private static final Map<String, String> LINUX_MAPPING = Map.of(
      "aarch64", "linux-aarch_64",
      "amd64", "linux-x86_64",
      "ppc64", "linux-ppcle_64",
      "ppc64le", "linux-ppcle_64",
      "s390x", "linux-s390_64",
      "zarch_64", "linux-s390_64"
  );

  private static final Map<String, String> MAC_OS_MAPPING = Map.of(
      "aarch64", "osx-aarch_64",
      "amd64", "osx-x86_64",
      "x86_64", "osx-x86_64"
  );

  private static final Map<String, String> WINDOWS_MAPPING = Map.of(
      "amd64", "windows-x86_64",
      "x86", "windows-x86_32",
      "x86_32", "windows-x86_32",
      "x86_64", "windows-x86_64"
  );

  private static final Map<String, String> FALLBACK_MAPPING = Map.of();

  private final HostSystem hostSystem;

  @Inject
  public PlatformClassifierFactory(HostSystem hostSystem) {
    this.hostSystem = hostSystem;
  }

  public String getClassifier(String binaryName) throws ResolutionException {
    Map<String, String> osMapping;

    if (hostSystem.isProbablyLinux()) {
      osMapping = LINUX_MAPPING;
  
    } else if (hostSystem.isProbablyMacOs()) {
      osMapping = MAC_OS_MAPPING;
  
    } else if (hostSystem.isProbablyWindows()) {
      osMapping = WINDOWS_MAPPING;
  
    } else {
      osMapping = FALLBACK_MAPPING;
    }

    var rawArch = hostSystem.getCpuArchitecture();
    var classifier = osMapping.get(rawArch);

    if (classifier != null) {
      return classifier;
    }

    var rawOs = hostSystem.getOperatingSystem();
    throw new ResolutionException(
        "No '" + binaryName + "' binary is available for reported OS '"
            + rawOs + "' and CPU architecture '" + rawArch + "'"
    );
  }
}
