package com.company.db.entity;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * User entity
 */
public class User implements Serializable {

    private static final long serialVersionUID = 290724913968202592L;


    private int agreementNumber;
    private String firstName;
    private String lastName;
    private String patronymicName;
    private String password;
    private double balance;
    private boolean blocked;
    private int roleId;
    private int languageId;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return agreementNumber == user.agreementNumber &&
                blocked == user.blocked &&
                roleId == user.roleId &&
                languageId == user.languageId &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                Objects.equals(patronymicName, user.patronymicName) &&
                password.equals(user.password) &&
                balance == user.balance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(agreementNumber, firstName, lastName, patronymicName, password, balance, blocked, roleId, languageId);
    }


    public int getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(int agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymicName() {
        return patronymicName;
    }

    public void setPatronymicName(String patronymicName) {
        this.patronymicName = patronymicName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public User(String firstName, String lastName, String patronymicName, String password, double balance, boolean blocked, int roleId, int languageId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymicName = patronymicName;
        this.password = password;
        this.balance = balance;
        this.blocked = blocked;
        this.roleId = roleId;
        this.languageId = languageId;
    }

    public User(int agreementNumber, String firstName, String lastName, String patronymicName, String password, double balance, boolean blocked, int roleId, int languageId) {
        this.agreementNumber = agreementNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymicName = patronymicName;
        this.password = password;
        this.balance = balance;
        this.blocked = blocked;
        this.roleId = roleId;
        this.languageId = languageId;
    }

    public User() {
    }


}

