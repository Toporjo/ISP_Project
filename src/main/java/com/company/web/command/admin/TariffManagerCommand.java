package com.company.web.command.admin;

import com.company.Paths;
import com.company.Util;
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
import java.util.List;

public class TariffManagerCommand extends Command {

    private static final String SERVICE_PARAMETER = "service";
    private static final String REQUEST_ATTRIBUTE_ERROR = "errorMessage";
    private static final String REQUEST_ATTRIBUTE_TARIFFS = "tariffs";

    private static final Logger logger = LogManager.getLogger(TariffManagerCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Starting service command");
        TariffDao tariffDao = DaoFactory.getDaoFactory().getTariffDao();

        HttpSession session = request.getSession();

        String forward = Paths.PAGE_ERROR_PAGE;
        List<Tariff> tariffs;

        try {
            tariffs = tariffDao.getAllTariffs(Util.determineLocale(session,request));
            request.setAttribute(REQUEST_ATTRIBUTE_TARIFFS,tariffs);

        } catch (NumberFormatException e){
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR,Paths.ERROR_UNKNOWN_SERVICE);
            return forward;
        } catch (SQLException s){
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR,Paths.ERROR_FAILED_TO_RETRIEVE_DATA);
            return forward;
        } catch (Exception x){
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR,Paths.ERROR_SOMETHING_WRONG);
        }

        forward = Paths.PAGE_TARIFF_MANAGER;
        return forward;
    }
}
