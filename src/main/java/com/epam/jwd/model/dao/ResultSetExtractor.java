package com.epam.jwd.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ResultSetExtractor<T> {

    T extract(ResultSet resultSet);

    default List<T> extractAll(ResultSet resultSet) throws SQLException {
        List<T> entities = new ArrayList<>();
        while (resultSet.next()) {
            final T entity = this.extract(resultSet);
            entities.add(entity);
        }
        return entities;
    }
}
