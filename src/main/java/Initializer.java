import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import socialcore.model.User;
import socialcore.model.repository.UserRepository;

import java.util.stream.Stream;

@Component
public class Initializer implements CommandLineRunner {

    private final UserRepository repository;

    public Initializer(UserRepository repository) {

        this.repository = repository;

    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of("FullOvellas", "margb7", "lucascabaleiro")
                .forEach(username -> repository.save(new User(username)));
    }
}
