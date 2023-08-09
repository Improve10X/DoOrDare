package com.improve10x.doordare;

import java.util.ArrayList;

public class CountryCode {

    private String countryName;
    private String countryCode;

    public CountryCode(String countryName, String countryCode) {
        this.countryName = countryName;
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return countryName + " (" + countryCode + ")";
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
