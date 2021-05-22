package com.company.jstl;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.util.List;

/**
 * Custom jstl tag
 */
public class Contains extends SimpleTagSupport {

    /**
     * Checks if list contains an object
     * @param list list to be checked
     * @param o Object
     * @return
     */
    public static boolean contains(List list, Object o) {
        if(list == null) {
            return false;
        }
        return list.contains(o);
    }
}
