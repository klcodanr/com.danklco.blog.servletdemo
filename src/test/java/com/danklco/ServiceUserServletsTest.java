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
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.stream.Stream;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.testing.mock.sling.junit5.SlingContext;
import org.apache.sling.testing.mock.sling.junit5.SlingContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.danklco.serviceuser.ServiceUserOsgiServlet;
import com.danklco.serviceuser.ServiceUserPathServlet;
import com.danklco.serviceuser.ServiceUserResourceServlet;

@ExtendWith(SlingContextExtension.class)
class ServiceUserServletsTest {

    private SlingContext context = new SlingContext();
    private ResourceResolverFactory factory;

    static Stream<Arguments> servlets() {
        return Stream.of(
                Arguments.of(ServiceUserOsgiServlet.class),
                Arguments.of(ServiceUserPathServlet.class),
                Arguments.of(ServiceUserResourceServlet.class));
    }

    @BeforeEach
    void init() throws LoginException {
        factory = mock(ResourceResolverFactory.class);
        when(factory.getServiceResourceResolver(anyMap())).then(inv -> context.resourceResolver());
        context.create().resource("/var/test/serviceuser", Collections.singletonMap("message", "Hello World"));
    }

    @ParameterizedTest
    @MethodSource("servlets")
    void testGet(Class<Servlet> servletClass)
            throws ServletException, IOException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Servlet servlet = servletClass.getConstructor(ResourceResolverFactory.class).newInstance(factory);
        servlet.service(context.request(), context.response());
        assertEquals("Hello null the message is Hello World", context.response().getOutputAsString());
    }

}
