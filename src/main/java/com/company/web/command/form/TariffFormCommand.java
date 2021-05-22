package com.company.web.command.form;

import com.company.Paths;
import com.company.db.constant.Language;
import com.company.db.dao.TariffDao;
import com.company.db.dao.mysql.MySqlDaoFactory;
import com.company.db.entity.Tariff;
import com.company.web.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class TariffFormCommand extends Command {

    private static final Logger logger = Logger.getLogger(TariffFormCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Starting command");
        String forward = Paths.PAGE_TARIFF_FORM;
        TariffDao tariffDao = MySqlDaoFactory.getInstance().getTariffDao();
        String mode = request.getParameter("mode");
        String id = request.getParameter("id");
        String errorMessage;

        if ("new".equals(mode)){
            request.setAttribute("languages",Language.values());
        }

        if ("edit".equals(mode)){
            try {
                Tariff tariff = tariffDao.getTariffById(Integer.parseInt(id));
                Map<Language,Tariff> tariffInfo = tariffDao.getTariffInfoByTariffId(tariff.getId());

                request.setAttribute("tariff",tariff);
                request.setAttribute("tariffInfo",tariffInfo);
                request.setAttribute("languages",Language.values());

            } catch (SQLException e) {
                logger.trace("Login failed. Data doesn't matches");
                errorMessage = Paths.ERROR_NO_SUCH_USER;
                request.setAttribute("errorMessage", errorMessage);
                return forward;
            } catch (NumberFormatException nfe){
                logger.trace("Login failed. Data doesn't matches");
                errorMessage = Paths.ERROR_INVALID_DATA;
                request.setAttribute("errorMessage", errorMessage);
                return forward;
            }
        }



        logger.debug("Done. Sending forward");
        return forward;
    }
}
