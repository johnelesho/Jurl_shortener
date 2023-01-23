package com.elsoft.assessment.jurlshortener.utils;

import org.apache.commons.validator.routines.UrlValidator;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Helpers {
   public static boolean isUrlValid(String urlString) throws MalformedURLException, URISyntaxException {
        try {
            new URL(urlString).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
   }
    public static boolean isUrlValid2(String urlString) throws MalformedURLException {
        UrlValidator validator = new UrlValidator();
        return validator.isValid(urlString);
    }
}
