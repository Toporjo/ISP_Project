package com.company.web;

import com.company.db.Fields;

import java.lang.reflect.Field;

public class TariffSortingParameters {
    String order;
    String orderBy;

    public TariffSortingParameters(String order, String orderBy) {
        this.order = order;
        this.orderBy = orderBy;
    }

    public TariffSortingParameters(){
        this.order = "asc";
        this.orderBy = Fields.TARIFF_NAME;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
