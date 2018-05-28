package com.example.restapi.demo.event;

import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

public class CreatedResourceEvent extends ApplicationEvent {

    private Long resourceId;

    private HttpServletResponse response;

    public CreatedResourceEvent(Object source, HttpServletResponse response, Long resourceId) {
        super(source);
        this.response = response;
        this.resourceId = resourceId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
}
