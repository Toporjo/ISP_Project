package com.company;

/**
 * String constants for pages,commands and resources bundles properties
 */
public class Paths {

    //pages
    public static final String PAGE_HOME = "WEB-INF/jsp/common/home.jsp";
    public static final String PAGE_LOGIN = "WEB-INF/jsp/common/login.jsp";
    public static final String PAGE_REGISTRATION = "WEB-INF/jsp/common/registration.jsp";
    public static final String PAGE_SETTINGS = "WEB-INF/jsp/common/settings.jsp";
    public static final String PAGE_EVENT_FORM = "WEB-INF/jsp/from/event_form.jsp";
    public static final String PAGE_REPORT_FORM = "WEB-INF/jsp/from/report_form.jsp";
    public static final String PAGE_THEME_FORM = "WEB-INF/jsp/from/theme_form.jsp";
    public static final String PAGE_MODERATION = "WEB-INF/jsp/moderator/moderation.jsp";
    public static final String PAGE_REPORT_SUGGESTIONS = "WEB-INF/jsp/moderator/report_suggestions.jsp";
    public static final String PAGE_SPEAKER_CABINET = "WEB-INF/jsp/speaker/speaker_cabinet.jsp";
    public static final String PAGE_USER_EVENTS = "WEB-INF/jsp/user/user_events.jsp";
    public static final String PAGE_ERROR_PAGE = "WEB-INF/jsp/error_page.jsp";
    public static final String PAGE_SERVICE = "WEB-INF/jsp/common/service.jsp";
    public static final String PAGE_TARIFF_MANAGER = "WEB-INF/jsp/admin/tariff_manager.jsp";
    public static final String PAGE_USER_MANAGER = "WEB-INF/jsp/admin/user_manager.jsp";
    public static final String PAGE_TARIFF_FORM = "WEB-INF/jsp/form/tariff_from.jsp";
    public static final String PAGE_REGISTRATION_FORM = "WEB-INF/jsp/form/registration_form.jsp";
    public static final String PAGE_FUND = "WEB-INF/jsp/form/fund_form.jsp";


    //commands
    public static final String COMMAND_HOME = "/home?command=home";
    public static final String COMMAND_LOGIN_FORM = "/home?command=loginForm";
    public static final String COMMAND_MODERATE = "/home?command=moderate";
    public static final String COMMAND_SHOW_EVENT = "/home?command=showEvent&id=";
    public static final String COMMAND_VIEW_REPORT_SUGGESTIONS = "/home?command=viewReportSuggestions";
    public static final String COMMAND_SHOW_USER_EVENTS = "/home?command=showUserEvents";
    public static final String COMMAND_TARIFF_MANAGER = "/home?command=tariffManager";
    public static final String COMMAND_USER_MANAGER = "/home?command=userManager";

    //errors
    public static final String ERROR_NO_SUCH_COMMAND = "error.no_such_command";
    public static final String ERROR_ACCESS_DENIED = "error.access_denied";
    public static final String ERROR_NO_SUCH_USER = "error.no_user_with_such_email_or_password" ;
    public static final String ERROR_NO_SUCH_AGREEMENT_NUMBER = "error.no_user_with_such_email_or_password" ;
    public static final String ERROR_REGISTRATION_VALIDATION = "error.registration_validation" ;
    public static final String ERROR_USER_ALREADY_EXISTS = "error.user_already_exists" ;
    public static final String ERROR_SOMETHING_WRONG = "error.something_wrong" ;
    public static final String ERROR_WRONG_DATE_FORMAT = "error.wrong_date_format" ;
    public static final String ERROR_ALREADY_SIGNED_UP_FOR_EVENT = "error.already_signed_up_for_event" ;
    public static final String ERROR_ALREADY_MARKED_AS_VISITED = "error.already_marked_as_visited" ;
    public static final String ERROR_NO_EVENT_FOUND = "error.no_event_found" ;
    public static final String ERROR_INVALID_DATA = "error.valid_data" ;
    public static final String ERROR_UNKNOWN_SERVICE = "error.unknown_service" ;

    //services
    public static final String RESOURCE_SERVICE_NAME_TELEPHONE = "header_jspf.anchor.telephone";
    public static final String RESOURCE_SERVICE_NAME_INTERNET = "header_jspf.anchor.internet";
    public static final String RESOURCE_SERVICE_NAME_CABLE_TV = "header_jspf.anchor.cable_tv";
    public static final String RESOURCE_SERVICE_NAME_IP_TV = "header_jspf.anchor.ip_tv";

    public static final String RESOURCE_TARIFFS_FILE_NAME_FRAGMENT = "tariffs_file_name_fragment";

    //languages
    public static final String RESOURCE_LANGUAGE_UKRAINIAN = "language_uk";
    public static final String RESOURCE_LANGUAGE_ENGLISH = "language_en";
    public static final String RESOURCE_LANGUAGE_RUSSIAN = "language_ru";


    public static final String ERROR_FAILED_TO_RETRIEVE_DATA = "error.failed_to_retrieve_data";
}
