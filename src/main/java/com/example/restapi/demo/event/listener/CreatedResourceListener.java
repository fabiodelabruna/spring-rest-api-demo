package com.example.restapi.demo.event.listener;

import com.example.restapi.demo.event.CreatedResourceEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class CreatedResourceListener implements ApplicationListener<CreatedResourceEvent> {

    @Override
    public void onApplicationEvent(CreatedResourceEvent createdResourceEvent) {
        Long resourceId = createdResourceEvent.getResourceId();
        HttpServletResponse response = createdResourceEvent.getResponse();

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(resourceId).toUri();

        response.setHeader("Location", uri.toASCIIString());
    }
}
