package com.aad.proyectoud4socialcore.endpoints;

import com.aad.proyectoud4socialcore.exception.ForbidenAccessException;
import com.aad.proyectoud4socialcore.model.entity.Meeting;
import com.aad.proyectoud4socialcore.model.entity.PointOfInterest;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import com.aad.proyectoud4socialcore.model.entity.UserGroup;
import com.aad.proyectoud4socialcore.model.repository.MeetingRepository;
import com.aad.proyectoud4socialcore.service.MeetingService;
import com.aad.proyectoud4socialcore.service.UserAuthService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import org.joda.time.DateTime;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Endpoint
@RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
public class MeetingEndpoint {

    private final MeetingRepository repository;
    private final MeetingService meetingService;
    private final UserAuthService userAuthService;


    public MeetingEndpoint(MeetingRepository repository, UserAuthService userAuthService, MeetingService meetingService) {
        this.repository = repository;
        this.userAuthService = userAuthService;
        this.meetingService = meetingService;
    }

    public List<Meeting> findAll() {
        return repository.findAll();
    }

    /**
     * Permite a los usuarios que se encuentran en una quedada borrar un punto de interés. Solo se puede realizar si
     * la quedada aún no se realizó
     * @param meeting la quedada
     * @param poi el punto de interés que borrar
     * @return true si se pudo borrar
     * @throws ForbidenAccessException si el usuario no pertenece a la quedada
     */
    public boolean removePointFromMeeting(Meeting meeting, PointOfInterest poi) throws ForbidenAccessException {

        SocialUser user = userAuthService.getContextUser();

        if(!meeting.getAttendants().contains(user)) {
            throw new ForbidenAccessException("Forbbiden access");
        }

        if(meeting.getPlannedTime().isBeforeNow()) {
            return false;
        }

        return meetingService.removePointFromMeeting(meeting, poi);
    }

    /**
     * Permite crear una nueva quedada
     * @param group el grupo que crea la quedada
     * @return la nueva quedada
     * @throws ForbidenAccessException si el usuario no pertenece al grupo que crea la quedada
     */
    public Meeting createNewMeeting(UserGroup group ) throws ForbidenAccessException {

        SocialUser user = userAuthService.getContextUser();

        if(!group.getParticipants().contains(user)) {
            throw new ForbidenAccessException("Forbbiden access");
        }

        return meetingService.createNewMeeting(group, DateTime.now());
    }

    /**
     * Permite a los usuarios que se encuentran en una quedada añadir un punto de interés. Solo se puede realizar si
     * la quedada aún no se realizó
     * @param meeting la quedada
     * @param poi el punto de interés que añadir
     * @return true si se pudo añadir
     * @throws ForbidenAccessException si el usuario no pertenece a la quedada
     */
    public boolean addPointToMeeting(Meeting meeting, PointOfInterest poi) throws ForbidenAccessException {

        SocialUser user = userAuthService.getContextUser();

        if(!meeting.getAttendants().contains(user)) {
            throw new ForbidenAccessException("Forbbiden access");
        }

        if(meeting.getPlannedTime().isBeforeNow()) {
            return false;
        }

        return meetingService.addPointToMeeting(meeting, poi);
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
