package com.company.web.command.common;

import com.company.Paths;
import com.company.db.Fields;
import com.company.db.constant.Language;
import com.company.db.dao.DaoFactory;
import com.company.db.dao.TariffDao;
import com.company.db.entity.Tariff;
import com.company.web.TariffSortingParameters;
import com.company.web.command.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

public class ServiceCommand extends Command {


    private static final String SERVICE_PARAMETER = "service";
    private static final String REQUEST_ATTRIBUTE_ERROR = "errorMessage";
    private static final String REQUEST_ATTRIBUTE_TARIFFS = "tariffs";

    private static final Logger logger = LogManager.getLogger(ServiceCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Starting service command");
        TariffDao tariffDao = DaoFactory.getDaoFactory().getTariffDao();

        HttpSession session = request.getSession();

        String order =request.getParameter("order");
        String orderBy =request.getParameter("orderBy");

        TariffSortingParameters sortingParams =
                (TariffSortingParameters) session.getAttribute("tariffSortParams");

        if(sortingParams == null){
            sortingParams = new TariffSortingParameters();
        }
        if(order != null){
            sortingParams.setOrder(order);
        }
        if (orderBy != null){
            sortingParams.setOrderBy(orderBy);
        }



        int serviceId;
        String forward = Paths.PAGE_ERROR_PAGE;
        List<Tariff> tariffs;

        try {
           serviceId = Integer.parseInt(request.getParameter(SERVICE_PARAMETER));

           Language userLocale = (Language) session.getAttribute("locale");
//get all tariffs
//           tariffs = tariffDao.getTariffsByServiceId(serviceId,
//                   userLocale != null ? userLocale : Language.getLanguageByCode(request.getLocale().toString()));
//get all tariffs with sorting
            tariffs = tariffDao.getTariffsByServiceId(serviceId,
                    userLocale != null ? userLocale : Language.getLanguageByCode(request.getLocale().toString()),
                    sortingParams.getOrder(),
                    sortingParams.getOrderBy());

           request.setAttribute(REQUEST_ATTRIBUTE_TARIFFS,tariffs);
           forward = Paths.PAGE_SERVICE;

        } catch (NumberFormatException e){
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR,Paths.ERROR_UNKNOWN_SERVICE);
            return forward;
        } catch (SQLException s){
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR,Paths.ERROR_FAILED_TO_RETRIEVE_DATA);
            return forward;
        } catch (Exception x){
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR,Paths.ERROR_SOMETHING_WRONG);
        }

        session.setAttribute("tariffSortParams",sortingParams);
        request.setAttribute("successMessage","Test success message");





        return forward;
    }



}
