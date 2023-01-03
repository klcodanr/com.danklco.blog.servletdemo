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

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.auth.core.AuthenticationSupport;
import org.apache.sling.commons.mime.MimeTypeService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * Context for "/test"
 */
@Component(service = ServletContextHelper.class, property = {
        "osgi.http.whiteboard.context.name=" + TestHttpContext.CONTEXT_NAME,
        "osgi.http.whiteboard.context.path=/test"
})
public class TestHttpContext extends ServletContextHelper {

    public static final String CONTEXT_NAME = "com.danklco.blog.test";

    private final MimeTypeService mimeTypeService;

    private final AuthenticationSupport authenticationSupport;

    /**
     * Constructs a new context using contructor injection
     *
     * @param mimeTypeService       Used when providing mime type of requests
     * @param authenticationSupport Used to authenticate requests with sling
     */
    @Activate
    public TestHttpContext(@Reference final MimeTypeService mimeTypeService,
            @Reference final AuthenticationSupport authenticationSupport) {
        this.mimeTypeService = mimeTypeService;
        this.authenticationSupport = authenticationSupport;
    }

    /**
     * Returns the MIME type as resolved by the MimeTypeService
     */
    @Override
    public String getMimeType(String name) {
        return mimeTypeService.getMimeType(name);
    }

    /**
     * Always returns <code>null</code> because resources are all provided
     * through individual endpoint implementations.
     */
    @Override
    public URL getResource(String name) {
        return null;
    }

    /**
     * Only attempts to authenticate requests to /test/auth
     */
    @Override
    public boolean handleSecurity(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        if (request.getRequestURI().equals("/test/auth")) {
            return this.authenticationSupport.handleSecurity(request, response);
        } else {
            return true;
        }
    }
}
