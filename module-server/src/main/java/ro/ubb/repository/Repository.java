package ro.ubb.repository;

import ro.ubb.model.Entity;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T extends Entity<ID>, ID> {
    ID save(T entity) throws SQLException;
    List<T> findAll() throws SQLException;
}
