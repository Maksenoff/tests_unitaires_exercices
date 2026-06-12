package com.example.roomapi.service;

import com.example.roomapi.exception.RoomNotFoundException;
import com.example.roomapi.model.Room;
import com.example.roomapi.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public Room create(String name, int capacity) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Le nom est obligatoire");
        }
        if (capacity < 1) {
            throw new IllegalArgumentException("La capacité doit être au moins 1");
        }
        return repository.save(name.trim(), capacity);
    }

    public Room getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
    }

    public List<Room> findAll() {
        return repository.findAll();
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
