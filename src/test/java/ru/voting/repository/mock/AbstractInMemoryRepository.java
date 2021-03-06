package ru.voting.repository.mock;

import org.slf4j.Logger;
import ru.voting.model.AbstractBaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractInMemoryRepository<T extends AbstractBaseEntity> {
    private final Logger log;

    protected Map<Integer, T> inMemoryRepository;
    protected AtomicInteger inMemoryCount = new AtomicInteger(0);

    public AbstractInMemoryRepository(Logger logger) {
        log = logger;
        inMemoryRepository = new ConcurrentHashMap<>();
    }

    public boolean delete(int id) {
        log.info("delete {}", id);
        return inMemoryRepository.remove(id) != null;
    }

    public T save(T t) {
        log.info(t.isNew() ? "create {}" : "update {}", t);
        if (t.isNew()) t.setId(inMemoryCount.incrementAndGet());
        inMemoryRepository.put(t.getId(), t);
        return t;
    }

    public T get(int id) {
        log.info("get {}", id);
        return inMemoryRepository.get(id);
    }

    public List<T> getAll() {
        log.info("getAll");
        return new ArrayList<>(inMemoryRepository.values());
    }
}
