package com.elsoft.assessment.jurlshortener.service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public interface UrlShortenerUtilsService {

    String retrieveStringFromId(long id);

    long retrieveIdFromString(String input);

    String getBaseUrl(String url) throws MalformedURLException, URISyntaxException;
}
