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

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TariffManagerCommand extends Command {

    private static final String SERVICE_PARAMETER = "service";
    private static final String REQUEST_ATTRIBUTE_ERROR = "errorMessage";
    private static final String REQUEST_ATTRIBUTE_TARIFFS = "tariffs";
    private static final int PAGE_SIZE = 9;

    private static final Logger logger = LogManager.getLogger(TariffManagerCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Starting service command");
        TariffDao tariffDao = DaoFactory.getDaoFactory().getTariffDao();
        String pageParam = request.getParameter("page");
        HttpSession session = request.getSession();

        String forward = Paths.PAGE_ERROR_PAGE;
        List<Tariff> tariffs;

        try {
            int page = Integer.parseInt(Optional.ofNullable(pageParam).orElse("1"));
            int tariffsAmount = tariffDao.getTariffsNumber();
            if (--page * PAGE_SIZE > tariffsAmount)  {
                throw new IllegalArgumentException();
            }
            tariffs = tariffDao.getAllTariffs(Util.determineLocale(session, request), page, PAGE_SIZE);

            request.setAttribute(REQUEST_ATTRIBUTE_TARIFFS, tariffs);
            request.setAttribute("page",page+1);
            request.setAttribute("pages",(int)Math.ceil((double)tariffsAmount/PAGE_SIZE));
            request.setAttribute("amount",tariffsAmount);
            request.setAttribute("size",PAGE_SIZE);

        } catch (NumberFormatException e) {
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR, Paths.ERROR_UNKNOWN_SERVICE);
            return forward;
        } catch (SQLException s) {
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR, Paths.ERROR_FAILED_TO_RETRIEVE_DATA);
            return forward;
        } catch (Exception x) {
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR, Paths.ERROR_SOMETHING_WRONG);
        }

        forward = Paths.PAGE_TARIFF_MANAGER;
        return forward;
    }
}
