package ru.voting.repository.MockRepositories;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.voting.model.Restaurant;
import ru.voting.model.Role;
import ru.voting.model.User;
import ru.voting.repository.UserRepository;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryUserRepository extends AbstractInMemoryRepository<User> implements UserRepository {

    private static final Logger log = getLogger(InMemoryUserRepository.class);

    public InMemoryUserRepository() {
        super();
        save (new User(null, "USER", "user@mail.ru",
                "password", Role.ROLE_USER));
        save(new User(null, "ADMIN", "admin@mail.ru",
                "password", Role.ROLE_ADMIN, Role.ROLE_USER));
    }

    @Override
    public void setChoice(User user, Restaurant restaurant) {
        log.info("set choice {} by {}", restaurant, user);
        user.setChoice(restaurant);
        save(user);
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return inMemoryRepository.values().stream().filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }
}
