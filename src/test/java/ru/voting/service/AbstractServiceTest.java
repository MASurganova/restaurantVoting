package ru.voting.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.voting.ActiveDbProfileResolver;
import ru.voting.Profiles;
import ru.voting.TimingRules;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.slf4j.LoggerFactory.getLogger;
import static ru.voting.util.ValidationUtil.getRootCause;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
public abstract class AbstractServiceTest {
    private static final Logger log = getLogger("result");

    @Autowired
    public Environment env;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("users").clear();
    }


    @ClassRule
    public static ExternalResource summary = TimingRules.SUMMARY;

    @Rule
    public Stopwatch stopwatch = TimingRules.STOPWATCH;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    static {
        // needed only for java.util.logging (postgres driver)
        SLF4JBridgeHandler.install();
    }

    public boolean isJpaBased() {
        return env.acceptsProfiles(Profiles.JPA, Profiles.DATAJPA);
    }

    //  Check root cause in JUnit: https://github.com/junit-team/junit4/pull/778
    public <T extends Throwable> void validateRootCause(Runnable runnable, Class<T> exceptionClass) {
        try {
            runnable.run();
            Assert.fail("Expected " + exceptionClass.getName());
        } catch (Exception e) {
            Assert.assertThat(getRootCause(e), instanceOf(exceptionClass));
        }
    }
}
