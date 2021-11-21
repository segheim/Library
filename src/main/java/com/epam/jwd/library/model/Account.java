package com.epam.jwd.library.model;

import java.util.Objects;

public class Account implements Entity{

    private final Long id;
    private final String login;
    private final String password;
    private final Role role;

    public Account(Long id, String login, String password, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Account(String login, String password) {
        this(null, login, password, Role.READER);
    }

    public Account(Long id, String login, String password) {
        this(id, login, password, Role.READER);
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public Account withPassword(String password) {
        return new Account(id, login, password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(login, account.login) && Objects.equals(password, account.password) && Objects.equals(role, account.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, role);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
