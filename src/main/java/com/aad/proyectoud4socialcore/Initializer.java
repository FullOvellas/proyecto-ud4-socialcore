package com.aad.proyectoud4socialcore;

import com.aad.proyectoud4socialcore.model.entity.*;
import com.aad.proyectoud4socialcore.model.repository.*;
import com.aad.proyectoud4socialcore.service.UserGroupService;
import com.google.maps.model.LatLng;
import com.google.maps.model.OpeningHours;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class Initializer implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserGroupService userGroupService;

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    PasswordEncoder encoder;
    private final UserGroupRepository userGroupRepository;

    public Initializer(UserRepository userRepository,
                       UserGroupRepository userGroupRepository) {

        this.userRepository = userRepository;

        this.userGroupRepository = userGroupRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        SocialUser user1 = new SocialUser();
        List<Role> roles2 = new ArrayList<>();
        Meeting meeting1 = new Meeting();

        PointOfInterest point1 = new PointOfInterest("a", "a", new LatLng(42.2513809, -8.6900709), new OpeningHours(), "a", new ArrayList<>(), 1.0f);
        PointOfInterest point2 = new PointOfInterest("a", "a", new LatLng(42.3407844, -8.6048713), new OpeningHours(), "a", new ArrayList<>(), 1.0f);

        meeting1.setDestination(point1);

        roles2.add(roleRepository.findByName("ROLE_ADMIN"));

        user1.setEmail("margb7");
        user1.setFullName("margb7");
        user1.setPassword(encoder.encode("1234"));
        user1.setRoles(roles2);
        user1.setResidence(new Residence(new LatLng(42.2404590, -8.6932391)));

        meeting1.getAttendants().add(user1);

        userRepository.save(user1);
        userGroupService.createUserGroup(user1, "Grupo margb7");

        Stream.of("lucascabaleiro", "FullOvellas")
                .forEach(name -> {

                    SocialUser user = new SocialUser();
                    List<Role> roles = new ArrayList<>();

                    roles.add(roleRepository.findByName("ROLE_ADMIN"));

                    user.setEmail(name);
                    user.setFullName(name);
                    user.setPassword(encoder.encode("1234"));
                    user.setRoles(roles);

                    meeting1.getAttendants().add(user);

                    userRepository.save(user);
                });

        System.out.println("ID: " + meetingRepository.save(meeting1).getId());

    }
}
