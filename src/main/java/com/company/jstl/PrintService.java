package com.company.jstl;

import com.company.Paths;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrintService  extends SimpleTagSupport {
    private static final Map<Integer,String> services;

    static {
        int counter= 0;
        services = new HashMap<>();

        services.put(counter++, Paths.RESOURCE_SERVICE_NAME_TELEPHONE);
        services.put(counter++, Paths.RESOURCE_SERVICE_NAME_INTERNET);
        services.put(counter++, Paths.RESOURCE_SERVICE_NAME_CABLE_TV);
        services.put(counter, Paths.RESOURCE_SERVICE_NAME_IP_TV);
    }


    public static String print(Integer id) {
        return services.get(id);
    }

}
