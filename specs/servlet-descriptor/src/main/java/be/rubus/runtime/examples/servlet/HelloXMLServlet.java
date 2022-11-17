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
package be.rubus.runtime.examples.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class HelloXMLServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getOutputStream().println("Hello World from XML");
        Map<String, String[]> parameterMap = req.getParameterMap();
        if (!parameterMap.isEmpty()) {
            resp.getOutputStream().println("Request parameter values");
        }
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            resp.getOutputStream().println(String.format("key -> %s, value -> %s"
                    , entry.getKey()
                    , String.join(",", entry.getValue())));
        }

    }
}
