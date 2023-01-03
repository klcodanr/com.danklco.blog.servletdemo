package com.danklco.auth;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.StreamSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.SlingException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

public interface BaseAuthServlet {

    default void handleResponse(
            final ResourceResolver resolver, final HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter()
                .write("Hello " + resolver.getUserID() + " the dam count is " + getDamCount(resolver));
    }

    static long getDamCount(ResourceResolver resolver) {
        Iterable<Resource> children = Optional.ofNullable(resolver.getResource("/content/dam"))
                .map(Resource::getChildren)
                .orElseThrow(() -> new SlingException("Failed to read /content/dam", null));
        return StreamSupport.stream(children.spliterator(), false).count();
    }
}
