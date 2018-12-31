package ru.voting.repository.mock;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.voting.TestData;
import ru.voting.model.VotingEvent;
import ru.voting.repository.HistoryRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryHistoryRepository implements HistoryRepository {

    public static final Logger log = getLogger(InMemoryHistoryRepository.class);

    private Map<LocalDate, VotingEvent> inMemoryRepository;

    public InMemoryHistoryRepository() {
        inMemoryRepository  = new ConcurrentHashMap<>();
        init();
    }

    public void init() {
        inMemoryRepository.clear();
        inMemoryRepository.put(TestData.EVENT_1.getDate(), TestData.EVENT_1);
        inMemoryRepository.put(TestData.EVENT_2.getDate(), TestData.EVENT_2);
    }


    @Override
    public boolean delete(LocalDate date) {
        log.info("delete {}", date);
        return inMemoryRepository.remove(date) != null;
    }

    @Override
    public VotingEvent save(VotingEvent event) {
        log.info("create {}", event);
        return inMemoryRepository.put(event.getDate(), event);
    }

    @Override
    public VotingEvent get(LocalDate date) {
        log.info("get {}", date);
        return inMemoryRepository.get(date);
    }

    @Override
    public List<VotingEvent> getAll() {
        log.info("getAll");
        return new ArrayList<>(inMemoryRepository.values());
    }
}

