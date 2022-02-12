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

import be.atbash.runtime.testing.AbstractAtbashTest;
import be.atbash.runtime.testing.jupiter.AtbashContainerTest;
import jakarta.ws.rs.client.WebTarget;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@AtbashContainerTest
public class ConfigPropertiesResourceIT extends AbstractAtbashTest {

    @Test
    public void testServer() {

        WebTarget path = getClientWebTargetApplication(atbash).path("/rest/config/server");
        String result = path.request().get(String.class);
        assertThat(result).isEqualTo("Details{host='localhost', port=9080, endpoint='query', location='London'}");

    }

    @Test
    public void testClient() {

        WebTarget path = getClientWebTargetApplication(atbash).path("/rest/config/client");
        String result = path.request().get(String.class);
        assertThat(result).isEqualTo("Details{host='myHost', port=9081, endpoint='shelf', location='Dublin'}");

    }

    @Test
    public void testBase() {

        WebTarget path = getClientWebTargetApplication(atbash).path("/rest/config/base");
        String result = path.request().get(String.class);
        assertThat(result).isEqualTo("Details{host='anotherHost', port=9082, endpoint='book', location='Berlin'}");

    }

    @Test
    public void testProgrammatic1() {

        WebTarget path = getClientWebTargetApplication(atbash).path("/rest/config/programmatic1");
        String result = path.request().get(String.class);
        assertThat(result).isEqualTo("Details{host='localhost', port=9080, endpoint='query', location='London'}");

    }

    @Test
    public void testProgrammatic2() {

        WebTarget path = getClientWebTargetApplication(atbash).path("/rest/config/programmatic2");
        String result = path.request().get(String.class);
        assertThat(result).isEqualTo("Details{host='anotherHost', port=9082, endpoint='book', location='Berlin'}");

    }

    @Test
    public void testInstance() {

        WebTarget path = getClientWebTargetApplication(atbash).path("/rest/config/asinstance");
        String result = path.request().get(String.class);
        assertThat(result).isEqualTo("Instance value");

    }

    @Test
    public void testProvider() {

        WebTarget path = getClientWebTargetApplication(atbash).path("/rest/config/asprovider");
        String result = path.request().get(String.class);
        assertThat(result).isEqualTo("Provider value");

    }

}
