/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.danklco;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.stream.Stream;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.testing.mock.sling.junit5.SlingContext;
import org.apache.sling.testing.mock.sling.junit5.SlingContextExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.danklco.hello.HelloWorldOsgiServlet;
import com.danklco.hello.HelloWorldPathServlet;
import com.danklco.hello.HelloWorldResourceServlet;

@ExtendWith(SlingContextExtension.class)
class HelloServletsTest {

    private final SlingContext context = new SlingContext();

    private static Stream<Arguments> servlets() {
        return Stream.of(
                Arguments.of(new HelloWorldOsgiServlet()),
                Arguments.of(new HelloWorldPathServlet()),
                Arguments.of(new HelloWorldResourceServlet()));
    }

    @ParameterizedTest
    @MethodSource("servlets")
    void testGet(Servlet servlet) throws ServletException, IOException {
        servlet.service(context.request(), context.response());
        assertEquals("Hello World", context.response().getOutputAsString());
    }

}
