/*
 * Copyright (c) 2019 Santanu Sinha <santanu.sinha@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.appform.dropwizard.discovery.bundle;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Ranger configuration.
 */
@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class ServiceDiscoveryConfiguration {
    @NotNull
    @NotEmpty
    private String namespace = Constants.DEFAULT_NAMESPACE;

    @NotNull
    @NotEmpty
    private String environment;

    @NotNull
    @NotEmpty
    private String zookeeper;

    @Min(1000)
    @Max(60000)
    private int connectionRetryIntervalMillis = 5000;

    @NotNull
    @NotEmpty
    private String publishedHost = Constants.DEFAULT_HOST;

    @NotNull
    @Min(-1)
    @Max(65535)
    private int publishedPort = Constants.DEFAULT_PORT;

    private int refreshTimeMs;

    private boolean disableWatchers;

    @Min(0)
    @Max(600)
    private long initialDelaySeconds;

    private boolean initialRotationStatus = true;

    @Builder
    private ServiceDiscoveryConfiguration(String namespace,
                                         String environment,
                                         String zookeeper,
                                         int connectionRetryIntervalMillis,
                                         String publishedHost,
                                         int publishedPort,
                                         boolean initialRotationStatus,
                                         int refreshTimeMs,
                                         boolean disableWatchers,
                                         long initialDelaySeconds) {
        this.namespace = namespace;
        this.environment = environment;
        this.zookeeper = zookeeper;
        this.connectionRetryIntervalMillis = connectionRetryIntervalMillis;
        this.publishedHost = publishedHost;
        this.publishedPort = publishedPort;
        this.initialRotationStatus = initialRotationStatus;
        this.refreshTimeMs = refreshTimeMs;
        this.disableWatchers = disableWatchers;
        this.initialDelaySeconds = initialDelaySeconds;
    }
}
