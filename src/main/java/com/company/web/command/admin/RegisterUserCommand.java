package com.company.web.command.admin;

import com.company.Paths;
import com.company.db.constant.Language;
import com.company.db.constant.Role;
import com.company.db.dao.DaoFactory;
import com.company.db.dao.TariffDao;
import com.company.db.dao.UserDao;
import com.company.db.dao.mysql.MySqlDaoFactory;
import com.company.db.entity.Tariff;
import com.company.db.entity.User;
import com.company.web.command.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterUserCommand extends Command {

    private static final Logger logger = LogManager.getLogger(RegisterUserCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Starting service command");
        UserDao userDao = MySqlDaoFactory.getInstance().getUserDao();

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String patronymicName = request.getParameter("patronymicName");
        String password = request.getParameter("password");

        User user = new User();

        try {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPatronymicName(patronymicName);
            user.setPassword(password);
            user.setRoleId(Role.USER.ordinal());
            user.setLanguageId(Language.UKRAINIAN.ordinal());
            userDao.insert(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect(Paths.COMMAND_USER_MANAGER);
        return null;
    }
}
