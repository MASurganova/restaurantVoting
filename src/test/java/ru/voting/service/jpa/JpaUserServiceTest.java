package ru.voting.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.voting.service.AbstractJpaUserServiceTest;
import ru.voting.service.AbstractUserServiceTest;

import static ru.voting.Profiles.JPA;

@ActiveProfiles(JPA)
public class JpaUserServiceTest extends AbstractJpaUserServiceTest {
}
