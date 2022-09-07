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

import be.rubus.runtime.examples.rest.json.model.Book;
import be.rubus.runtime.examples.rest.json.model.CreateUser;
import be.rubus.runtime.examples.rest.json.model.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("/user")
public class UserResource {

    private static Logger logger = Logger.getLogger(UserResource.class.getName());

    private static final List<User> users;

    static {
        users = new ArrayList<>();
        User user = new User("Rudy", "rdebusscher@gmail.com");
        Book book = new Book("9780140707342", "Hamlet, Prince of Denmark", "Shakespeare", 1603);
        user.addBook(book);
        users.add(user);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUser() {
        return users;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(CreateUser user, @Context UriInfo uriInfo) {
        User savedUser = new User(user.getName(), user.getEmail());
        users.add(savedUser);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(savedUser.getId());
        return Response.created(uriBuilder.build()).entity(savedUser).build();
    }
}
