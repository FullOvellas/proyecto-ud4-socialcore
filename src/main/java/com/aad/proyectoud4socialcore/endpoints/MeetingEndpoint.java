package com.aad.proyectoud4socialcore.endpoints;

import com.aad.proyectoud4socialcore.exception.ForbidenAccessException;
import com.aad.proyectoud4socialcore.model.entity.Meeting;
import com.aad.proyectoud4socialcore.model.entity.PointOfInterest;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.entity.UserGroup;
import com.aad.proyectoud4socialcore.model.repository.MeetingRepository;
import com.aad.proyectoud4socialcore.service.MeetingService;
import com.aad.proyectoud4socialcore.service.PointOfInterestService;
import com.aad.proyectoud4socialcore.service.UserAuthService;
import com.google.maps.model.LatLng;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import org.joda.time.DateTime;

import javax.annotation.security.RolesAllowed;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Endpoint
@RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
public class MeetingEndpoint {

    private final MeetingRepository repository;
    private final MeetingService meetingService;
    private final PointOfInterestService pointOfInterestService;
    private final UserAuthService userAuthService;


    public MeetingEndpoint(MeetingRepository repository, UserAuthService userAuthService, MeetingService meetingService, PointOfInterestService pointOfInterestService) {
        this.repository = repository;
        this.userAuthService = userAuthService;
        this.meetingService = meetingService;
        this.pointOfInterestService = pointOfInterestService;
    }

    public List<Meeting> findAll() {
        return repository.findAll();
    }

    /**
     * Permite crear una nueva quedada
     * @param group el grupo que crea la quedada
     * @return la nueva quedada
     * @throws ForbidenAccessException si el usuario no pertenece al grupo que crea la quedada
     */
    public Meeting createNewMeeting(UserGroup group, PointOfInterest destination, String name ) throws ForbidenAccessException {

        SocialUser user = userAuthService.getContextUser();
        Meeting meeting;

        if(!group.getParticipants().contains(user)) {
            throw new ForbidenAccessException("Forbbiden access");
        }

        return meetingService.createNewMeeting(group, destination, name, Date.from(Instant.now()));
    }

    /**
     * Devuelve un meeting a partir de su id
     * @param id el id del meeting
     * @return el meeting
     * @throws ForbidenAccessException si el usuario no participa en el meeting
     */
    public Meeting getMeetingFromId(long id) throws ForbidenAccessException {

        SocialUser user = userAuthService.getContextUser();
        Meeting meeting = repository.findMeetingById(id);

        if(!meeting.getAttendants().contains(user)) {
            throw new ForbidenAccessException("");
        }

        return meeting;
    }

    public LatLng calculateCentroid(SocialUser[] users) {
        return pointOfInterestService.calculateCentroid(users);
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
