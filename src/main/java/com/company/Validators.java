package com.company;

public class Validators {


    public static boolean validateEmail(String email){
        return !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    public static boolean validateAgreementNumber(String number){
        return !number.matches("^[0-9]{9}$");
    }




}
