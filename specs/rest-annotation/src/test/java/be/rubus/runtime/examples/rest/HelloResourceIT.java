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
package be.rubus.runtime.examples.rest;

import be.atbash.runtime.testing.AbstractAtbashTest;
import be.atbash.runtime.testing.jupiter.AtbashContainerTest;
import jakarta.ws.rs.client.WebTarget;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@AtbashContainerTest
public class HelloResourceIT extends AbstractAtbashTest {

    @Test
    public void testGET() {

        WebTarget path = getClientWebTargetApplication(atbash).path("/rest/hello/Atbash");
        String result = path.request().get(String.class);
        assertThat(result).isEqualTo("Hello Atbash");

    }

}
