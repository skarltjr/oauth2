package com.example.oauth2.events;


import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class EventResource extends EntityModel<Event> {
    public static EntityModel<Event> modelOf(Event event) {
        EntityModel<Event> eventResource = EntityModel.of(event);
        eventResource.add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
        return eventResource;
    }
}
