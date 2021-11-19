package com.epam.jwd.library.filter;

import com.epam.jwd.library.command.*;
import com.epam.jwd.library.model.Account;
import com.epam.jwd.library.model.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@WebFilter(urlPatterns = "/*")
public class RoleFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(RoleFilter.class);

    private static final String ERROR_JSP_URL = "/controller?command=error_page";

    private final Map<Role, Set<Command>> commandsWithRole = new ConcurrentHashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {
        Set<Command> commandsForReader = new HashSet<>();
        commandsForReader.add(ShowMainPageCommand.getInstance());
        commandsForReader.add(ShowErrorPageCommand.getInstance());
        commandsForReader.add(ShowAuthorPageCommand.getInstance());
        commandsForReader.add(ShowCatalogPageCommand.getInstance());
        commandsForReader.add(LogoutCommand.getInstance());
        Collections.unmodifiableSet(commandsForReader);

        Set<Command> commandsForAdmin = new HashSet<>();
        commandsForAdmin.add(ShowMainPageCommand.getInstance());
        commandsForAdmin.add(ShowErrorPageCommand.getInstance());
        commandsForAdmin.add(ShowAuthorPageCommand.getInstance());
        commandsForAdmin.add(ShowCatalogPageCommand.getInstance());
        commandsForAdmin.add(ShowAccountPageCommand.getInstance());
        commandsForAdmin.add(LogoutCommand.getInstance());
        Collections.unmodifiableSet(commandsForAdmin);

        Set<Command> commandsForGuest = new HashSet<>();
        commandsForGuest.add(ShowMainPageCommand.getInstance());
        commandsForGuest.add(ShowLoginPageCommand.getInstance());
        commandsForGuest.add(ShowErrorPageCommand.getInstance());
        commandsForGuest.add(ShowAuthorPageCommand.getInstance());
        commandsForGuest.add(ShowCatalogPageCommand.getInstance());
        commandsForGuest.add(LoginCommand.getInstance());
        Collections.unmodifiableSet(commandsForGuest);

        commandsWithRole.put(Role.READER, commandsForReader);
        commandsWithRole.put(Role.ADMIN, commandsForAdmin);
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
        final Role role = Optional.ofNullable(request.getSession(false))
                .map(s -> (Account) s.getAttribute("account"))
                .map(a -> a.getRole())
                .orElse(Role.GUEST);
        return role;
    }
}


