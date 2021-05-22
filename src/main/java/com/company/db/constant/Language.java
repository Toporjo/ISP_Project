package com.company.db.constant;

import com.company.db.entity.User;
import com.sun.deploy.net.HttpRequest;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.stream.Stream;

/**
 * Enum for supported languages
 */
public enum Language {
    UKRAINIAN("uk"),ENGLISH("en"),RUSSIAN("ru");


    Language(String isoCode){
        this.isoCode = isoCode;
    }

    private String isoCode;




    public static Language getLanguageByCode(String isoCode){

        return Stream.of(Language.values())
                .filter(x->x.isoCode.equals(isoCode))
                .findFirst()
                .orElse(UKRAINIAN);
    }



    public int getId(){
        Language[]languages = Language.values();
        for (int i=0;i<languages.length;i++){
            if(this.equals(languages[i])){
                return i;
            }
        }
        return -1;
    }

    public String getName() {
        return StringUtils.capitalize(name().toLowerCase());
    }

    public String getIsoCode() {
        return isoCode;
    }


}
