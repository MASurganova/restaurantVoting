package ru.voting.repository.mock;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.voting.model.Restaurant;
import ru.voting.repository.HistoryRepository;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.voting.TestData.MY;
import static ru.voting.TestData.OTHER;

@Repository
public class InMemoryHistoryRepository implements HistoryRepository {

    public static final Logger log = getLogger(InMemoryHistoryRepository.class);

    private Map<LocalDate, Restaurant> repository;

    public InMemoryHistoryRepository() {
        this.repository = new ConcurrentHashMap<>();
        init();
    }

    public void init() {
        repository.clear();
        repository.put(LocalDate.of(2018, 3, 20), MY);
        repository.put(LocalDate.of(2018, 3, 19), OTHER);
    }

    @Override
    public void addInHistory(LocalDate date, Restaurant restaurant) {
        log.info("add in history {} - {}", date, restaurant);
        repository.put(date, restaurant);
    }

    @Override
    public Map<LocalDate, Restaurant> getHistory() {
        log.info("get history");
        return repository;
    }
}

