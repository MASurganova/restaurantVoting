package ru.voting.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.voting.service.AbstractVotingServiceTest;

import static ru.voting.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
public class DataJpaVotingServiceTest extends AbstractVotingServiceTest {
}
