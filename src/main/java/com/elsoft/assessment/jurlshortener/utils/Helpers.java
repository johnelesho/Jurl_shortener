package com.elsoft.assessment.jurlshortener.utils;

import org.apache.commons.validator.routines.UrlValidator;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helpers {



    public static boolean urlValidator(String url)
    {final String URL_REGEX =
                "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))" +
                        "(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)" +
                        "([).!';/?:,][[:blank:]])?$";

       final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);
        if (url == null) {
            return false;
        }

        Matcher matcher = URL_PATTERN.matcher(url);
        return matcher.matches();
    }

   public static boolean isUrlValid(String urlString) throws MalformedURLException, URISyntaxException {
        try {
            new URL(urlString).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
   }
    public static boolean isUrlValid2(String urlString) throws MalformedURLException {
        UrlValidator validator = new UrlValidator(UrlValidator.ALLOW_ALL_SCHEMES);
        return validator.isValid(urlString);
    }

    public static long getDateDiff(LocalDateTime expiryDate) {
        return ChronoUnit.HOURS.between(LocalDateTime.now(), expiryDate);
    }
}
