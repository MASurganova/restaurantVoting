package ru.voting.repository;

import org.slf4j.Logger;
import ru.voting.model.AbstractBaseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractInMemoryRepository <T extends AbstractBaseEntity> {
    protected static final Logger log = getLogger(AbstractInMemoryRepository.class);

    protected Map<Integer, T> inMemoryRepository;
    protected AtomicInteger inMemoryCount = new AtomicInteger(0);

    public AbstractInMemoryRepository() {
        inMemoryRepository = new HashMap<>();
    }

    public boolean delete(int id) {
        log.info("delete {}", id);
        inMemoryRepository.remove(id);
        return true;
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
