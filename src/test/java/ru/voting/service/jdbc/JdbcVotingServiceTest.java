package ru.voting.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.voting.service.AbstractVotingServiceTest;

import static ru.voting.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcVotingServiceTest extends AbstractVotingServiceTest {
}
