package com.aad.proyectoud4socialcore.endpoint;

import com.aad.proyectoud4socialcore.model.entity.Meeting;
import com.aad.proyectoud4socialcore.model.repository.MeetingRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;

import java.util.List;

@Endpoint
@AnonymousAllowed
public class MeetingEndpoint {

    private MeetingRepository repository;

    public MeetingEndpoint(MeetingRepository repository) {
        this.repository = repository;
    }

    public List<Meeting> findAll() {
        return repository.findAll();
    }

    public Meeting add(Meeting meeting) {
        return repository.save(meeting);
    }

}
