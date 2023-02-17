package com.aad.proyectoud4socialcore;

import com.aad.proyectoud4socialcore.model.entity.*;
import com.aad.proyectoud4socialcore.model.enums.SocialPlaceType;
import com.aad.proyectoud4socialcore.model.repository.*;
import com.aad.proyectoud4socialcore.service.PointOfInterestService;
import com.aad.proyectoud4socialcore.service.UserGroupService;
import com.google.maps.model.LatLng;
import com.google.maps.model.OpeningHours;
import com.google.maps.model.PlaceType;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

@Component
public class Initializer implements CommandLineRunner {

    PointOfInterestService pointOfInterestService;
    @Autowired
    CommentRepository commentRepository;

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
    private final PointOfInterestRepository pointOfInterestRepository;

    public Initializer(UserRepository userRepository,
                       UserGroupRepository userGroupRepository,
                       PointOfInterestService pointOfInterestService,
                       PointOfInterestRepository pointOfInterestRepository) {
        this.userRepository = userRepository;
        this.pointOfInterestService = pointOfInterestService;
        this.userGroupRepository = userGroupRepository;
        this.pointOfInterestRepository = pointOfInterestRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        /*
        SocialUser user1 = new SocialUser();
        List<Role> roles2 = new ArrayList<>();
        Meeting meeting1 = new Meeting();

        roles2.add(roleRepository.findByName("ROLE_ADMIN"));

        user1.setEmail("margb7");
        user1.setFullName("margb7");
        user1.setPassword(encoder.encode("1234"));
        user1.setRoles(roles2);
        user1.setResidence(new Residence(new LatLng(41.9469759,-8.7745392)));

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

        SocialUser user2 = userRepository.findSocialUserByEmail("FullOvellas");

        user2.setResidence(new Residence(new LatLng(42.3406437,-8.6077173)));
        userRepository.save(user2);

        SocialUser user3 = userRepository.findSocialUserByEmail("lucascabaleiro");

        user3.setResidence(new Residence(new LatLng(42.2238929,-8.7304424)));
        userRepository.save(user3);

        // ==============================================
        // ===========$$$$$ DANGER ZONE $$$$$$===========
        // user1.getResidence().getNearbyPointsOfInterest().addAll(pointOfInterestService.getNearbyPointsOfInterestForUser(user1, PlaceType.CAFE.name()));
        // user2.getResidence().getNearbyPointsOfInterest().addAll(pointOfInterestService.getNearbyPointsOfInterestForUser(user2, PlaceType.CAFE.name()));
        // user3.getResidence().getNearbyPointsOfInterest().addAll(pointOfInterestService.getNearbyPointsOfInterestForUser(user3, PlaceType.CAFE.name()));
        // ===============================================

        Set<SocialPlaceType> types= new HashSet<>();

        types.add(SocialPlaceType.CAFE);

        PointOfInterest point1 = new PointOfInterest("IES TEIS", "a ", new LatLng(42.2513809, -8.6900709), new OpeningHours(), "a", 1.0f, types);
        PointOfInterest point2 = new PointOfInterest("a", "a", new LatLng(42.3407844, -8.6048713), new OpeningHours(), "a",  1.0f, types);

        user2.getResidence().getNearbyPointsOfInterest().addAll(new ArrayList<>(Arrays.asList(point1, point2)));
        userRepository.save(user2);

        Comment comment = new Comment();
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();

        comment.setText("Comentario de ejemplo 1");
        comment1.setText("Comentario de ejemplo 2");
        comment2.setText("Comentario de ejemplo 3");

        comment.setUser(user1);
        comment1.setUser(user1);
        comment2.setUser(user1);

        comment.setRating(1f);

        comment1.setRating(2f);

        comment2.setRating(3f);

        comment.setPointOfInterest(point1);
        comment1.setPointOfInterest(point1);
        comment2.setPointOfInterest(point1);

        pointOfInterestRepository.save(point1);
        pointOfInterestRepository.save(point2);

        commentRepository.save(comment);
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        */

    }
}
