package com.example.demoapi.repository;

import com.example.demoapi.model.DemoItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implémentation en mémoire du repository.
 *
 * @Repository indique à Spring que cette classe est un composant de persistance.
 * Elle sera automatiquement injectée dans le service.
 *
 * Ce choix évite volontairement la base de données pour garder la démo centrée sur les tests.
 */
@Repository
public class InMemoryDemoItemRepository implements DemoItemRepository {

    private final AtomicLong sequence = new AtomicLong(0);
    private final Map<Long, DemoItem> items = new ConcurrentHashMap<>();

    @Override
    public DemoItem save(String label) {
        Long id = sequence.incrementAndGet();
        DemoItem item = new DemoItem(id, label);
        items.put(id, item);
        return item;
    }

    @Override
    public Optional<DemoItem> findById(Long id) {
        return Optional.ofNullable(items.get(id));
    }

    @Override
    public List<DemoItem> findAll() {
        return new ArrayList<>(items.values())
                .stream()
                .sorted(Comparator.comparing(DemoItem::id))
                .toList();
    }

    @Override
    public void deleteAll() {
        items.clear();
        sequence.set(0);
    }
}
