package com.aad.proyectoud4socialcore.model.repository;

import com.aad.proyectoud4socialcore.model.entity.Meeting;
import com.aad.proyectoud4socialcore.model.entity.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    Meeting findMeetingById(Long id);

    default List<Meeting> findMeetingWithParticipant(SocialUser user) {

        return findAll()
                .stream()
                .filter(meeting -> meeting.getAttendants().contains(user))
                .collect(Collectors.toList());

    }

    default List<Meeting> findMeetingWithParticipants(List<SocialUser> users) {

        return findAll()
                .stream()
                .filter(meeting -> meeting.getAttendants().containsAll(users))
                .collect(Collectors.toList());

    }
}
