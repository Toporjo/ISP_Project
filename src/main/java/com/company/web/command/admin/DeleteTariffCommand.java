package com.company.web.command.admin;

import com.company.Paths;
import com.company.db.constant.Language;
import com.company.db.dao.DaoFactory;
import com.company.db.dao.TariffDao;
import com.company.db.entity.Tariff;
import com.company.web.command.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DeleteTariffCommand extends Command {

    private static final Logger logger = LogManager.getLogger(DeleteTariffCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Starting service command");
        TariffDao tariffDao = DaoFactory.getDaoFactory().getTariffDao();
        HttpSession session = request.getSession();
        String forward = Paths.PAGE_ERROR_PAGE;
        String id = request.getParameter("id");

        try {
            tariffDao.deleteTariff(Integer.parseInt(id));
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("Failed to delete tariff",Paths.ERROR_UNKNOWN_SERVICE);
            return forward;
        }


        forward = Paths.PAGE_TARIFF_MANAGER;
        response.sendRedirect(Paths.COMMAND_TARIFF_MANAGER);
        return null;
    }

}
