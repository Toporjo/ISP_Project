package com.company.jstl;

import com.company.Paths;

import java.util.HashMap;
import java.util.Map;

public class PrintLanguage {

    private static final Map<Integer,String> services;

    static {
        int counter= 0;
        services = new HashMap<>();

        services.put(counter++, Paths.RESOURCE_LANGUAGE_UKRAINIAN);
        services.put(counter++, Paths.RESOURCE_LANGUAGE_ENGLISH);
        services.put(counter, Paths.RESOURCE_LANGUAGE_RUSSIAN);
    }


    public static String print(Integer id) {
        return services.get(id);
    }
}
