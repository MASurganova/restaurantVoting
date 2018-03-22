package ru.voting.repository.mock;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.voting.TestData;
import ru.voting.model.Restaurant;
import ru.voting.model.User;
import ru.voting.repository.UserRepository;

import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryUserRepository extends AbstractInMemoryRepository<User> implements UserRepository {

    private static final Logger log = getLogger(InMemoryUserRepository.class);

    public InMemoryUserRepository() {
        super(log);
        init();
        inMemoryCount = new AtomicInteger(2);
    }

    public void init() {
        inMemoryRepository.clear();
        inMemoryRepository.put(TestData.USER_ID, new User(TestData.USER));
        inMemoryRepository.put(TestData.ADMIN_ID, new User(TestData.ADMIN));
        inMemoryCount = new AtomicInteger(2);
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
