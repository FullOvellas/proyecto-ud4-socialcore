package com.aad.proyectoud4socialcore.endpoints;

import com.aad.proyectoud4socialcore.model.entity.Meeting;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.repository.MeetingRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;

import java.util.List;

@Endpoint
@AnonymousAllowed
public class MeetingEndpoint {

    private final MeetingRepository repository;

    public MeetingEndpoint(MeetingRepository repository) {
        this.repository = repository;
    }

    public List<Meeting> findAll() {
        return repository.findAll();
    }

    public List<Meeting> findSocialUserMeetings(SocialUser user) {

        return repository.findMeetingWithParticipant(user);

    }

    public List<Meeting> findSocialUsersMeetings(List<SocialUser> users) {

        return repository.findMeetingWithParticipants(users);

    }

    public Meeting add(Meeting meeting) {
        return repository.save(meeting);
    }

}
