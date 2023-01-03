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
package com.danklco.hello;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

import com.danklco.TestHttpContext;

@Component(service = { Servlet.class }, property = {
        HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN + "=/hello/*",
        HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT + "=("
                + HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME + "=" + TestHttpContext.CONTEXT_NAME + ")" })
public class HelloWorldOsgiServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().write("Hello World");
    }

}
