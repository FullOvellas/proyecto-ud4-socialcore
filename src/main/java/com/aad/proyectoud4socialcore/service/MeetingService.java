package com.aad.proyectoud4socialcore.service;

import com.aad.proyectoud4socialcore.model.entity.Meeting;
import com.aad.proyectoud4socialcore.model.entity.PointOfInterest;
import com.aad.proyectoud4socialcore.model.entity.UserGroup;
import com.aad.proyectoud4socialcore.model.repository.MeetingRepository;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    public Meeting createNewMeeting(@NotNull UserGroup group, DateTime dateTime) {

        Meeting meeting = new Meeting();

        // TODO: temporal -> cambiar attendants por grupo de usuarios
        meeting.setAttendants(group.getParticipants());
        meeting.setPlannedTime(dateTime);

        return meetingRepository.save(meeting);
    }

    public boolean addPointToMeeting(Meeting meeting, PointOfInterest poi) {

        if(meeting == null || poi == null) {
            return false;
        }

        meeting.getPointsOfInterest().add(poi);
        meetingRepository.save(meeting);

        return true;
    }

    public boolean removePointFromMeeting(Meeting meeting, PointOfInterest poi ) {

        if(meeting == null || poi == null || !meeting.getPointsOfInterest().contains(poi) ) {
            return false;
        }

        meeting.getPointsOfInterest().remove(poi);
        meetingRepository.save(meeting);

        return true;
    }

}
