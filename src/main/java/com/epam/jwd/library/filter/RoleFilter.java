package com.epam.jwd.library.filter;

import com.epam.jwd.library.command.*;
import com.epam.jwd.library.model.Account;
import com.epam.jwd.library.model.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@WebFilter(urlPatterns = "/*")
public class RoleFilter implements Filter {

    private static final String ERROR_JSP_URL = "/controller?command=error_page";

    private final Map<Role, Set<Command>> commandsWithRole = new ConcurrentHashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {

        Set<Command> commandsForAdmin = new HashSet<>();
        commandsForAdmin.add(ShowMainPageCommand.getInstance());
        commandsForAdmin.add(ShowErrorPageCommand.getInstance());
        commandsForAdmin.add(ShowAuthorPageCommand.getInstance());
        commandsForAdmin.add(ShowCatalogPageCommand.getInstance());
        commandsForAdmin.add(ShowRegistrationPageCommand.getInstance());
        commandsForAdmin.add(CreateAuthorCommand.getInstance());
        commandsForAdmin.add(CreateBookCommand.getInstance());
        commandsForAdmin.add(DeleteAccountCommand.getInstance());
        commandsForAdmin.add(DeleteAuthorCommand.getInstance());
        commandsForAdmin.add(DeleteBookCommand.getInstance());
        commandsForAdmin.add(ShowAccountPageCommand.getInstance());
        commandsForAdmin.add(ShowAccountsPageCommand.getInstance());
        commandsForAdmin.add(ShowBookPageCommand.getInstance());
        commandsForAdmin.add(ShowCreateAuthorPageCommand.getInstance());
        commandsForAdmin.add(ShowCreateBookPageCommand.getInstance());
        commandsForAdmin.add(ShowUpdateAuthorPageCommand.getInstance());
        commandsForAdmin.add(ShowUpdateBookPageCommand.getInstance());
        commandsForAdmin.add(UpdateAuthorCommand.getInstance());
        commandsForAdmin.add(UpdateBookCommand.getInstance());
        commandsForAdmin.add(ChangeAccountRoleCommand.getInstance());
        commandsForAdmin.add(LoginCommand.getInstance());
        commandsForAdmin.add(LogoutCommand.getInstance());

        Set<Command> commandsForLibrarian = new HashSet<>();
        commandsForLibrarian.add(ShowMainPageCommand.getInstance());
        commandsForLibrarian.add(ShowErrorPageCommand.getInstance());
        commandsForLibrarian.add(ShowAuthorPageCommand.getInstance());
        commandsForLibrarian.add(ShowCatalogPageCommand.getInstance());
        commandsForLibrarian.add(DeleteBookOrderCommand.getInstance());
        commandsForLibrarian.add(ShowLibrarianBookOrderPageCommand.getInstance());
        commandsForLibrarian.add(IssueBookCommand.getInstance());
        commandsForLibrarian.add(EndBookOrderCommand.getInstance());
        commandsForLibrarian.add(LogoutCommand.getInstance());

        Set<Command> commandsForReader = new HashSet<>();
        commandsForReader.add(ShowMainPageCommand.getInstance());
        commandsForReader.add(ShowErrorPageCommand.getInstance());
        commandsForReader.add(ShowAuthorPageCommand.getInstance());
        commandsForReader.add(ShowCatalogPageCommand.getInstance());
        commandsForReader.add(ShowBookPageCommand.getInstance());
        commandsForReader.add(ShowCreateBookOrderPageCommand.getInstance());
        commandsForReader.add(ShowReaderBookOrderPageCommand.getInstance());
        commandsForReader.add(ShowErrorPageCommand.getInstance());
        commandsForReader.add(ShowAccountPageCommand.getInstance());
        commandsForReader.add(DeleteBookOrderCommand.getInstance());
        commandsForReader.add(CreateBookOrderCommand.getInstance());
        commandsForReader.add(LogoutCommand.getInstance());

        Set<Command> commandsForGuest = new HashSet<>();
        commandsForGuest.add(ShowMainPageCommand.getInstance());
        commandsForGuest.add(ShowLoginPageCommand.getInstance());
        commandsForGuest.add(ShowErrorPageCommand.getInstance());
        commandsForGuest.add(ShowCatalogPageCommand.getInstance());
        commandsForGuest.add(ShowAuthorPageCommand.getInstance());
        commandsForGuest.add(ShowRegistrationPageCommand.getInstance());
        commandsForGuest.add(RegistrationCommand.getInstance());
        commandsForGuest.add(LoginCommand.getInstance());

        commandsWithRole.put(Role.ADMIN, commandsForAdmin);
        commandsWithRole.put(Role.LIBRARIAN, commandsForLibrarian);
        commandsWithRole.put(Role.READER, commandsForReader);
        commandsWithRole.put(Role.GUEST, commandsForGuest);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final String commandName = request.getParameter("command");
        final Command command = Command.of(commandName);
        if (isCurrentAccountRoleEvokeCommand(command, request)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            final HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect(ERROR_JSP_URL);
        }
    }


    private boolean isCurrentAccountRoleEvokeCommand(Command command, HttpServletRequest request) {
        final Role role = retrieveCurrentAccountRole(request);
        final Set<Command> commands = commandsWithRole.get(role);
        return commands.contains(command);
    }

    private Role retrieveCurrentAccountRole(HttpServletRequest request) {
        return Optional.ofNullable(request.getSession(false))
                .map(s -> (Account) s.getAttribute("account"))
                .map(Account::getRole)
                .orElse(Role.GUEST);
    }
}


