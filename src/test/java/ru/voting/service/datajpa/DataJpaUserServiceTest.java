package ru.voting.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.voting.service.AbstractUserServiceTest;

import static ru.voting.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
}
