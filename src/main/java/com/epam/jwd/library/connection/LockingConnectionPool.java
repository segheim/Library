package com.epam.jwd.library.connection;

import com.epam.jwd.library.exception.InitializeConnectionPoolError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockingConnectionPool implements ConnectionPool{

    private static final Logger LOG = LogManager.getLogger(LockingConnectionPool.class);

    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    public static final int DEFAULT_POOL_SIZE = 8;

    private static LockingConnectionPool instance;

    private final Queue<ProxyConnection> availableConnections;
    private final List<ProxyConnection> givenAwayConnections;

    private final AtomicBoolean initialize = new AtomicBoolean();

    private final static Lock locker = new ReentrantLock();
    private final Condition condition = locker.newCondition();

    public LockingConnectionPool() {
        this.availableConnections = new ArrayDeque<>();
        this.givenAwayConnections = new ArrayList<>();
    }

    public static LockingConnectionPool getInstance() {
        if (instance == null){
            locker.lock();
            try {
                if (instance == null) {
                    instance = new LockingConnectionPool();
                }
            } finally {
                locker.unlock();
            }
        }
        LOG.info("create instance: {}", instance);
        return instance;
    }

    @Override
    public boolean init() {
        if (initialize.compareAndSet(false, true)) {
            registerDrivers();
            initializeConnections(DEFAULT_POOL_SIZE);
            return true;
        }
        return false;
    }

    @Override
    public boolean shoutDown() {
        if (initialize.compareAndSet(true, false)) {
            closeConnections();
            deregisterDrivers();
            return false;
        }
        return false;
    }

    private void closeConnections() {
        locker.lock();
        try {
            closeCollectionConnections(availableConnections);
            closeCollectionConnections(givenAwayConnections);
        } finally {
            locker.unlock();
        }
    }

    private void closeCollectionConnections(Collection<ProxyConnection> collection) {
        for (ProxyConnection proxyConnection : collection) {
            try {
                proxyConnection.realClose();
                LOG.info("connection closed: {}", proxyConnection);
            } catch (SQLException e) {
                LOG.error("could not close connection", e);
            }
        }
    }

    @Override
    public Connection takeConnection() {
        ProxyConnection proxyConnection = null;
        locker.lock();
        try {
            while(availableConnections.isEmpty()) {
                condition.await();
            }
            proxyConnection = availableConnections.poll();
            givenAwayConnections.add(proxyConnection);
        } catch (InterruptedException e) {
            LOG.error("interrupted when waiting empty availableConnections", e);
        } finally {
            locker.unlock();
        }
        return proxyConnection;
    }

    @Override
    public void returnConnection(Connection connection) {
        locker.lock();
        try {
            if (connection instanceof ProxyConnection && givenAwayConnections.remove(connection)) {
                availableConnections.add((ProxyConnection) connection);
                condition.signalAll();
            }
        } finally {
            locker.unlock();
        }
    }

    private void initializeConnections(int amount) {
        locker.lock();
        try {
            for (int i = 0; i < amount; i++) {
                final Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                final ProxyConnection proxyConnection = new ProxyConnection(connection);
                availableConnections.add(proxyConnection);
            }
        } catch (SQLException e) {
            LOG.fatal("error occurred creating Connection", e);
            throw new InitializeConnectionPoolError("failed creating Connection", e);
        } finally {
            locker.unlock();
        }
    }

    private void registerDrivers() {
        LOG.trace("registering sql drivers");
        try {
            DriverManager.registerDriver(DriverManager.getDriver(DB_URL));
        } catch (SQLException e) {
            LOG.info("could not register drivers");
            throw new InitializeConnectionPoolError("sql drivers are not initialize", e);
        }
    }

    private void deregisterDrivers() {
        LOG.trace("unregistering sql drivers");
        final Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            try {
                DriverManager.deregisterDriver(drivers.nextElement());
            } catch (SQLException e) {
                LOG.error("could not deregister drivers", e);
            }
        }
    }
}
