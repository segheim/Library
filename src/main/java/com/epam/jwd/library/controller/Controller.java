package com.epam.jwd.library.controller;

import com.epam.jwd.library.command.Command;
import com.epam.jwd.library.command.CommandRegistry;
import com.epam.jwd.library.command.CommandResponse;
import com.epam.jwd.library.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(Controller.class);

    public void init() {
        ConnectionPool.lockingPool().init();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        LOG.trace("doGet method");
        final String commandName = request.getParameter("command");
        final Command command = Command.of(commandName);
        final CommandResponse commandResponse = command.execute(null);
        forwardOrRedirectToCommandResponseLocation(request, response, commandResponse);
    }

    private void forwardOrRedirectToCommandResponseLocation(HttpServletRequest request, HttpServletResponse response, CommandResponse commandResponse) {
        try {
            if (commandResponse.isRedirect()) {
                response.sendRedirect(commandResponse.getPath());
            } else {
                final String pathForDispatcher = commandResponse.getPath();
                final RequestDispatcher dispatcher = request.getRequestDispatcher(pathForDispatcher);
                dispatcher.forward(request, response);
            }
        } catch (ServletException e) {
            LOG.error("servlet exception occurred", e);
        } catch (IOException e) {
            LOG.error("IO exception occurred", e);
        }
    }


    public void destroy() {
    }
}