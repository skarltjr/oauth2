package com.example.oauth2.events;


import com.example.oauth2.account.Account;
import com.example.oauth2.account.AccountRepository;
import com.example.oauth2.account.LoginUser;
import com.example.oauth2.account.SessionAccount;
import com.example.oauth2.common.ErrorResource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventValidator eventValidator;
    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;
    private final EventRepository eventRepository;

    @PostMapping
    public ResponseEntity createEvent(@LoginUser SessionAccount account, @RequestBody @Valid  EventDto eventDto
            ,Errors errors) {
        if (errors.hasErrors()) {
            EntityModel<Errors> error1 = ErrorResource.modelOf(errors);
            return ResponseEntity.badRequest().body(error1);
        }
        eventValidator.validate(eventDto,errors);
        if (errors.hasErrors()) {
            EntityModel<Errors> error2 = ErrorResource.modelOf(errors);
            return ResponseEntity.badRequest().body(error2);
        }
        Account person = accountRepository.findByEmail(account.getEmail());
        Event map = modelMapper.map(eventDto, Event.class);
        map.update();
        map.setManager(person);
        Event saved = eventRepository.save(map);

        URI createdUri = linkTo(EventController.class).slash(saved.getId()).toUri();
        EntityModel<Event> eventResource = EventResource.modelOf(saved);
        eventResource.add(linkTo(EventController.class).withRel("query-events"));
        return ResponseEntity.created(createdUri).body(eventResource);
    }
    @GetMapping("/{id}")
    public ResponseEntity updateEvent(@PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity queryEvents(@LoginUser SessionAccount account, Pageable pageable,
                                      PagedResourcesAssembler<Event> assembler) {
        Page<Event> paged = eventRepository.findAll(pageable);
        PagedModel<EntityModel<Event>> eventResources = assembler.toModel(paged, p -> EventResource.modelOf(p));
        if (account != null) {
            eventResources.add(linkTo(EventController.class).withRel("create-event"));
        }
        System.out.println(accountRepository.count());
        return ResponseEntity.ok(eventResources);
    }
    @PostMapping("/input")
    public ResponseEntity queryEvents(@LoginUser SessionAccount account,@RequestParam String hello) {
        System.out.println(hello);
        return ResponseEntity.ok().build();
    }
}
