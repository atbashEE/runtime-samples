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
import be.rubus.runtime.examples.rest.json.model.CreateUser;
import be.rubus.runtime.examples.rest.json.model.Person;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@AtbashContainerTest
public class JaxrsJsonIT extends AbstractAtbashTest {


    @Test
    public void testAtbash() {

        String result = getClientWebTargetApplication(atbash).path("/api/person").request().get(String.class);
        assertThat(result).isEqualTo("{\"age\":42,\"name\":\"Rudy\"}");

    }

    @Test
    public void testAtbash_json_transient() {

        String result = getClientWebTargetApplication(atbash).path("/api/user").request().get(String.class);
        assertThat(result).startsWith("[{\"email\":\"rdebusscher@gmail.com\",\"name\":\"Rudy\",\"id\":\"");
        assertThat(result).doesNotContain("\"books\":");

    }

    @Test
    public void testAtbash_json_Creator() {

        CreateUser user = new CreateUser("John Doe", "john.doe@acme.org");
        Entity<?> entity = Entity.entity(user, MediaType.APPLICATION_JSON_TYPE);
        Response response = getClientWebTargetApplication(atbash).path("/api/user").request().post(entity);
        Assertions.assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
        Assertions.assertThat(response.getLocation().toString()).contains("/test/api/user/");
        String data = response.readEntity(String.class);
        Assertions.assertThat(data).startsWith("{\"email\":\"john.doe@acme.org\",\"name\":\"John Doe\",\"id\":");
        response.close();

        String result = getClientWebTargetApplication(atbash).path("/api/user").request().get(String.class);
        assertThat(result).contains("{\"email\":\"rdebusscher@gmail.com\",\"name\":\"Rudy\",\"id\":\"");
        assertThat(result).contains("{\"email\":\"john.doe@acme.org\",\"name\":\"John Doe\",\"id\":");
    }

    @Test
    public void testAtbash_jsonp() {

        String result = getClientWebTargetApplication(atbash).path("/api/person/jsonp").request().get(String.class);
        assertThat(result).isEqualTo("{\"type\":\"jsonp\",\"name\":\"Rudy\",\"age\":42}");

    }

    @Test
    public void testAtbash_post() {

        Person person = new Person();
        person.setName("Rudy");
        person.setAge(42);
        Entity<?> personPayload = Entity.json(person);
        String result = getClientWebTargetApplication(atbash).path("/api/person").request().post(personPayload).readEntity(String.class);
        assertThat(result).isEqualTo("Person{name='Rudy', age=42}");

    }

}