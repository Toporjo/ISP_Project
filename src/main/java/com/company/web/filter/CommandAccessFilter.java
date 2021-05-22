package com.company.web.filter;


import com.company.Paths;
import com.company.db.constant.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static com.company.Util.asList;

/**
 * Security servlet filter. Controls command access.
 */


public class CommandAccessFilter implements Filter {

    private static final Logger logger = Logger.getLogger(CommandAccessFilter.class);

    private static Map<Role, List<String>> accessMap = new HashMap<>();
    private static List<String> commons = new ArrayList<>();
    private static List<String> outOfControl = new ArrayList<>();


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        logger.debug("Calling access filter");
        if (accessAllowed(request)) {
            logger.trace("Access allowed");
            chain.doFilter(request, response);
        } else {
            logger.trace("Access denied");

            String errorMessage = Paths.ERROR_ACCESS_DENIED;

            request.setAttribute("errorMessage", errorMessage);

            request.getRequestDispatcher(Paths.PAGE_ERROR_PAGE)
                    .forward(request, response);
        }
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("command");
        if (commandName == null || commandName.isEmpty()) {
            return false;
        }

        if(!isCommandExist(commandName)){
            return true;
        }

        if (outOfControl.contains(commandName)) {
            return true;
        }

        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return false;
        }

        Role userRole = (Role) session.getAttribute("userRole");
        if (userRole == null) {
            return false;
        }

        if (accessMap.get(userRole).contains(commandName) || commons.contains(commandName)) {
            return true;
        }

        return false;

//		return accessMap.get(userRole).contains(commandName)
//				|| commons.contains(commandName);
//		return accessMap.get(userRole).contains(commandName)
//				|| commons.contains(commandName);
    }

    public void init(FilterConfig fConfig) throws ServletException {
        logger.debug("Initializing command access filter ");
        // roles
        accessMap.put(Role.ADMIN, asList(fConfig.getInitParameter("admin")));
        logger.trace("ADMIN roles -" + accessMap.get(Role.ADMIN));
        accessMap.put(Role.USER, asList(fConfig.getInitParameter("user")));
        logger.trace("User roles -" + accessMap.get(Role.USER));



        // commons
        commons = asList(fConfig.getInitParameter("common"));

        // out of control
        outOfControl = asList(fConfig.getInitParameter("out-of-control"));

        logger.debug("Initialization done");
    }

    private boolean isCommandExist(String command) {
        if (outOfControl.contains(command)) {
            return true;
        }
        if (commons.contains(command)) {
            return true;
        }
        for (Role role : Role.values()) {
            if (accessMap.get(role).contains(command)) {
                return true;
            }
        }
        return false;
    }




}