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
package be.rubus.runtime.examples.rest.json;

import be.atbash.runtime.testing.AbstractAtbashTest;
import be.atbash.runtime.testing.jupiter.AtbashContainerTest;
import jakarta.ws.rs.client.Entity;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@AtbashContainerTest
public class JaxrsJsonIT extends AbstractAtbashTest {
    

    @Test
    public void testAtbash() throws IOException {

        String result = getClientWebTargetApplication(atbash).path("/api/person").request().get(String.class);
        assertThat(result).isEqualTo("{\"name\":\"Rudy\",\"age\":42}");

    }

    @Test
    public void testAtbash_jsonp() throws IOException {

        String  result = getClientWebTargetApplication(atbash).path("/api/person/jsonp").request().get(String.class);
        assertThat(result).isEqualTo("{\"type\":\"jsonp\",\"name\":\"Rudy\",\"age\":42}");

    }

    @Test
    public void testAtbash_post() throws IOException {

        Person person = new Person();
        person.setName("Rudy");
        person.setAge(42);
        Entity<?> personPayload = Entity.json(person);
        String  result = getClientWebTargetApplication(atbash).path("/api/person").request().post(personPayload).readEntity(String.class);
        assertThat(result).isEqualTo("Person{name='Rudy', age=42}");

    }

}