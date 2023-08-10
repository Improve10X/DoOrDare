package com.improve10x.doordare.connectguesttomobilelogin;

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

    public String getCountryCode() {
        return countryCode;
    }
}