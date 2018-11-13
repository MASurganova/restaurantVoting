package ru.voting.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.voting.service.AbstractJpaVotingServiceTest;
import ru.voting.service.AbstractVotingServiceTest;

import static ru.voting.Profiles.JPA;

@ActiveProfiles(JPA)
public class JpaVotingServiceTest extends AbstractJpaVotingServiceTest {
}
