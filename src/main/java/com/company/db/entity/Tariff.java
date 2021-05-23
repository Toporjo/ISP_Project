package com.company.db.entity;

import com.company.db.constant.Language;

import java.util.Map;
import java.util.Objects;

public class Tariff {

    private int id;
    private String tariffName;
    private double price;
    private String description;
    private int discount;
    private int serviceId;


    @Override
    public String toString() {
        return "Tariff{" +
                "id=" + id +
                ", tariffName='" + tariffName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", discount=" + discount +
                ", serviceId=" + serviceId +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tariff tariff = (Tariff) o;
        return id == tariff.id &&
                Double.compare(tariff.price, price) == 0 &&
                discount == tariff.discount &&
                serviceId == tariff.serviceId &&
                tariffName.equals(tariff.tariffName) &&
                description.equals(tariff.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tariffName, price, description, discount, serviceId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
