package org.example.Repositoriy;

import java.util.List;

public interface BaseRepository <T>{

    public T add (T element);
    public boolean delete (T element);
    public T update (T element);
    public T findById (int id);
    public List<T> findAll ();
}
