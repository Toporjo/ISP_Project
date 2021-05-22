package com.company.web.command.admin;

import com.company.Paths;
import com.company.db.dao.DaoFactory;
import com.company.db.dao.UserDao;
import com.company.web.command.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

public class HandleUserCommand extends Command {



    private static final Logger logger = LogManager.getLogger(HandleUserCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Starting service command");
        UserDao userDao = DaoFactory.getDaoFactory().getUserDao();
        String forward = Paths.PAGE_ERROR_PAGE;
        String id = request.getParameter("id");
        boolean block = Boolean.parseBoolean(request.getParameter("block"));

        try {
            userDao.changeUserBlock(Integer.parseInt(id), block);
            Set<Integer> idsList  = (Set<Integer>) request.getServletContext().getAttribute("updatedIds");
            idsList.add(Integer.parseInt(id));
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("Failed to delete tariff",Paths.ERROR_UNKNOWN_SERVICE);
            return forward;
        }


        forward = Paths.PAGE_TARIFF_MANAGER;
        response.sendRedirect(Paths.COMMAND_USER_MANAGER);
        return null;
    }
}
