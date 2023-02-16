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
    public Meeting createNewMeeting(@NotNull UserGroup group, PointOfInterest destination ,Date dateTime) {

        Meeting meeting = new Meeting();

        meeting.setAttendants(group.getParticipants());
        meeting.setPlannedTime(dateTime);
        meeting.setDestination(destination);

        return meetingRepository.save(meeting);
    }

    public void removeMeeting(Meeting meeting) {
        meetingRepository.delete(meeting);
    }

}
