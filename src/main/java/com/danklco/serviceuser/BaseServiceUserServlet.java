package com.danklco.serviceuser;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.SlingException;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

public interface BaseServiceUserServlet {

    ResourceResolverFactory getResourceResolverFactory();

    default void handleResponse(
            final ResourceResolverFactory resourceResolverFactory, final HttpServletResponse response)
            throws ServletException, IOException {
        try (ResourceResolver resolver = safeGetServiceUserResolver(resourceResolverFactory)) {
            response.getWriter()
                    .write("Hello " + resolver.getUserID() + " the message is " + getMessage(resolver));
        }
    }

    static ResourceResolver safeGetServiceUserResolver(ResourceResolverFactory resourceResolverFactory) {
        try {
            return resourceResolverFactory.getServiceResourceResolver(
                    Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, "servlettest"));
        } catch (LoginException e) {
            throw new SlingException("Failed to get resource resolver", e);
        }
    }

    static String getMessage(ResourceResolver resolver) {
        return Optional.ofNullable(resolver.getResource("/var/test/serviceuser")).map(Resource::getValueMap)
                .map(vm -> vm.get("message", String.class))
                .orElseThrow(() -> new SlingException("Failed to read message", null));
    }
}
