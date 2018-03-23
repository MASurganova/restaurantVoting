package ru.voting.repository;

import ru.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.Map;

public interface HistoryRepository {

    void addInHistory(LocalDate date, Restaurant restaurant);

    Map<LocalDate, Restaurant> getHistory();
}