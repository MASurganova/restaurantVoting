package ru.voting.repository;

import org.springframework.stereotype.Repository;
import ru.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class HistoryRepository {
    private Map<LocalDate, Restaurant> repository;

    public HistoryRepository() {
        this.repository = new ConcurrentHashMap<>();
    }

    public void addInHistory(LocalDate date, Restaurant restaurant) {
        repository.put(date, restaurant);
    }

    public Map<LocalDate, Restaurant> getHistory() {
        return repository;
    }
}

