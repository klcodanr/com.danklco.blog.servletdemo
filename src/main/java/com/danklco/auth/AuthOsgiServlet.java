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
package com.danklco.auth;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.SlingException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.auth.core.AuthenticationSupport;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

import com.danklco.TestHttpContext;

@Component(service = { Servlet.class }, property = {
        HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN + "=/auth/*",
        HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT + "=("
                + HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME + "=" + TestHttpContext.CONTEXT_NAME + ")" })
public class AuthOsgiServlet extends HttpServlet implements BaseAuthServlet {

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        this.handleResponse(getResourceResolver(request), response);
    }

    public ResourceResolver getResourceResolver(final HttpServletRequest request) {
        Object resolverAttribute = request.getAttribute(AuthenticationSupport.REQUEST_ATTRIBUTE_RESOLVER);
        if (resolverAttribute instanceof ResourceResolver) {
            return (ResourceResolver) resolverAttribute;
        }
        throw new SlingException("Could not get ResourceResolver from request", null);
    }
}
