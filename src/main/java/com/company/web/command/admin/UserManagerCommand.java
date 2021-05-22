package com.company.web.command.admin;

import com.company.Paths;
import com.company.Util;
import com.company.db.dao.DaoFactory;
import com.company.db.dao.TariffDao;
import com.company.db.dao.UserDao;
import com.company.db.dao.mysql.MySqlDaoFactory;
import com.company.db.dao.mysql.MySqlUserDao;
import com.company.db.entity.Tariff;
import com.company.db.entity.User;
import com.company.web.command.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserManagerCommand extends Command {

    private static final Logger logger = LogManager.getLogger(UserManagerCommand.class);
    private static final int PAGE_SIZE = 9;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Starting service command");
        UserDao userDao = MySqlDaoFactory.getInstance().getUserDao();

        HttpSession session = request.getSession();
        String pageParam = request.getParameter("page");

        String forward = Paths.PAGE_ERROR_PAGE;
        List<User> users;

        try {
            int page = Integer.parseInt(Optional.ofNullable(pageParam).orElse("1"));
            int usersAmount = userDao.getUsersNumber();
            if (--page * PAGE_SIZE > usersAmount)  {
                throw new IllegalArgumentException();
            }
            users = userDao.getAllUsers(page, PAGE_SIZE);


            request.setAttribute("page",page+1);
            request.setAttribute("pages",(int)Math.ceil((double)usersAmount/PAGE_SIZE));
            request.setAttribute("amount",usersAmount);
            request.setAttribute("size",PAGE_SIZE);
            request.setAttribute("users",users);
        } catch (SQLException e) {
            e.printStackTrace();
        }



        forward = Paths.PAGE_USER_MANAGER;
        return forward;
    }
}
