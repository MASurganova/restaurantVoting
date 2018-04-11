package ru.voting.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.voting.model.Restaurant;
import ru.voting.repository.HistoryRepository;

import java.time.LocalDate;
import java.util.Map;

@Repository
public class DataJpaHistoryRepositoryImpl implements HistoryRepository{
    @Override
    public void addInHistory(LocalDate date, Restaurant restaurant) {

    }

    @Override
    public Map<LocalDate, Restaurant> getHistory() {
        return null;
    }
}
