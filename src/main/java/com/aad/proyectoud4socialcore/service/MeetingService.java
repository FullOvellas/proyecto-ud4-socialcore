package com.aad.proyectoud4socialcore.service;

import com.aad.proyectoud4socialcore.model.entity.Meeting;
import com.aad.proyectoud4socialcore.model.entity.PointOfInterest;
import com.aad.proyectoud4socialcore.model.entity.UserGroup;
import com.aad.proyectoud4socialcore.model.repository.MeetingRepository;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;
    public Meeting createNewMeeting(@NotNull UserGroup group, PointOfInterest destination, String name, LocalDateTime dateTime) {

        Meeting meeting = new Meeting();

        System.out.println(dateTime);

        meeting.setAttendants(group.getParticipants());
        meeting.setPlannedTime(Timestamp.valueOf(dateTime));
        meeting.setDestination(destination);
        meeting.setName(name);

        return meetingRepository.save(meeting);
    }

    public void removeMeeting(Meeting meeting) {
        meetingRepository.delete(meeting);
    }

}
