package ru.voting.repository.repositoryImpl;

import org.springframework.stereotype.Repository;
import ru.voting.model.Restaurant;
import ru.voting.repository.HistoryRepository;

import java.time.LocalDate;
import java.util.Map;

@Repository
public class HistoryrepositoryImpl implements HistoryRepository {

    public HistoryrepositoryImpl() {
    }

    @Override
    public void addInHistory(LocalDate date, Restaurant restaurant) {

    }

    @Override
    public Map<LocalDate, Restaurant> getHistory() {
        return null;
    }
}
