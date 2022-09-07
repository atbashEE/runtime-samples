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
package be.rubus.runtime.examples.rest.json.model;

import jakarta.json.bind.annotation.JsonbTransient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User extends CreateUser {

    private final String id;

    @JsonbTransient
    private final List<Book> books = new ArrayList<>();

    public User(String name, String email) {
        super(name, email);
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }


    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public void addBook(Book book) {
        books.add(book);
    }
}
