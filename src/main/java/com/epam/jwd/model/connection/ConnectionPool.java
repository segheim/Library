package com.epam.jwd.model.connection;

import java.sql.Connection;

public interface ConnectionPool {

    boolean init();

    boolean shoutDown();

    Connection takeConnection();

    void returnConnection(Connection connection);

}
