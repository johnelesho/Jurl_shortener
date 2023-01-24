package com.elsoft.assessment.jurlshortener.service.impl;


import com.elsoft.assessment.jurlshortener.exceptions.BadRequestException;
import com.elsoft.assessment.jurlshortener.service.UrlShortenerUtilsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
@Slf4j
public class UrlShortenerUtilsServiceImpl implements UrlShortenerUtilsService {

    private  final String possibleCharacters = "vwAMheo9PI2qNs5Zpf8FjkDc0TBn7lmRbtQ4YKXHEWxuzdra316OJigGLSVUCy";
    private  final char[] possibleCharactersArray = possibleCharacters.toCharArray();
    private  final int base = possibleCharactersArray.length;

    private final String urlBase = "jurl.el/";
    @Override
    public  String retrieveStringFromId(long id) {
        var shortenedUrl = new StringBuilder();

        if (id == 0) {
            return String.valueOf(possibleCharactersArray[0]);
        }

        while (id > 0) {
            shortenedUrl.append(possibleCharactersArray[(int) (id % base)]);
            id = id / base;
        }

        return urlBase.concat(String.valueOf(shortenedUrl.reverse()));
    }

    @Override
    public long retrieveIdFromString(String input) {
        if(!input.startsWith(urlBase))
                throw new BadRequestException("Invalid Short Url");

        int beginIndex = urlBase.length();
        String shortLink = input.substring(beginIndex);
        var characters = shortLink.toCharArray();
        var length = characters.length;
        log.info("String to decode {} short Link {}", input, shortLink);
        var decoded = 0;

        var counter = 1;
        for (char character : characters) {
            decoded += possibleCharacters.indexOf(character) * Math.pow(base, length - counter);
            counter++;
        }
        log.info("Decoded String {}", decoded);
        return decoded;
    }

    @Override
    public String getBaseUrl(String url) throws MalformedURLException, URISyntaxException {
        URL reqUrl = new URL(url).toURI().toURL();
        String protocol = reqUrl.getProtocol();
        String host = reqUrl.getHost();
        int port = reqUrl.getPort();

        if (port == -1) {
            return String.format("%s://%s/", protocol, host);
        } else {
            return String.format("%s://%s:%d/", protocol, host, port);
        }

    }
}
