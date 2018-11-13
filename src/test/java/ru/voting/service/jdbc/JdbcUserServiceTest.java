package ru.voting.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.voting.service.AbstractJpaUserServiceTest;
import ru.voting.service.AbstractUserServiceTest;

import static ru.voting.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {
}
