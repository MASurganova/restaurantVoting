package ru.voting.repository.mock;

import org.springframework.stereotype.Repository;
import ru.voting.model.Restaurant;
import ru.voting.repository.HistoryRepository;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryHistoryRepository implements HistoryRepository {

    private Map<LocalDate, Restaurant> repository;

    public InMemoryHistoryRepository() {
        this.repository = new ConcurrentHashMap<>();
    }

    @Override
    public void addInHistory(LocalDate date, Restaurant restaurant) {
        repository.put(date, restaurant);
    }

    @Override
    public Map<LocalDate, Restaurant> getHistory() {
        return repository;
    }
}

