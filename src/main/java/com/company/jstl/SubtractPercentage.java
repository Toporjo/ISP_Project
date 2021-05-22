package com.company.jstl;

import com.company.Util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOError;
import java.io.IOException;
import java.math.BigDecimal;

public class SubtractPercentage extends SimpleTagSupport {

    private Double price;
    private Integer discount;

    public void setPrice(Double price){
        this.price = price;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public void doTag() throws JspException, IOException{

        JspWriter out = getJspContext().getOut();
        out.println(Util.calculateDiscount(price,discount)+"0");
    }
}
