/*
 * Copyright 2022 Rudy De Busscher (https://www.atbash.be)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.rubus.runtime.examples.mpconfig.properties;

import jakarta.enterprise.context.Dependent;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ConfigProperties(prefix = "server")
@Dependent
public class Details {
    public String host; // the value of the configuration property server.host

    public int port;   // the value of the configuration property server.port

    private String endpoint; //the value of the configuration property server.endpoint

    @ConfigProperty(name = "old.location")
    public String location; //the value of the configuration property server.old.location

    public String getEndpoint() {
        return endpoint;
    }

    @Override
    public String toString() {
        return "Details{" + "host='" + host + '\'' +
                ", port=" + port +
                ", endpoint='" + endpoint + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}