package org.example.Repositoriy;

import org.example.Model.Command;

import java.util.List;

public class CommandRepository implements BaseRepository<Command> {
    @Override
    public Command add(Command element) {
        return null;
    }

    @Override
    public boolean delete(Command element) {
        return false;
    }

    @Override
    public Command update(Command element) {
        return null;
    }

    @Override
    public Command findById(int id) {
        return null;
    }

    @Override
    public List<Command> findAll() {
        return null;
    }
}
