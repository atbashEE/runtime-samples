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

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Path("/config")
public class ConfigPropertiesResource {

    @Inject
    @ConfigProperties
    private Details serverDetails;

    @Inject
    @ConfigProperties(prefix = "client")
    private Details clientDetails;

    @Inject
    @ConfigProperty(name = "asInstance")
    private Instance<String> instanceString;

    @Inject
    @ConfigProperty(name = "asProvider")
    private Provider<String> providerString;

    @Inject
    @ConfigProperties(prefix = "") // This effectively uses no prefix at all.
    private Details baseDetails;

    @GET
    @Path("/server")
    public String getServerDetails() {
        return serverDetails.toString();
    }

    @GET
    @Path("/client")
    public String getClientDetails() {
        return clientDetails.toString();
    }

    @GET
    @Path("/base")
    public String getBaseDetails() {
        return baseDetails.toString();
    }

    @GET
    @Path("/programmatic1")
    public String getDetails1() {
        // NO_PREFIX -> default on Class -> prefix = server
        Details details = CDI.current().select(Details.class, ConfigProperties.Literal.NO_PREFIX).get();
        return details.toString();
    }

    @GET
    @Path("/programmatic2")
    public String getDetails2() {
        // "" -> no prefix
        Details details = CDI.current().select(Details.class, ConfigProperties.Literal.of("")).get();
        return details.toString();
    }

    // FIXME Move
    // Some additional tests.
    @GET
    @Path("/asinstance")
    public String instance() {
        return instanceString.get();
    }

    @GET
    @Path("/asprovider")
    public String provider() {
        return providerString.get();
    }
}
