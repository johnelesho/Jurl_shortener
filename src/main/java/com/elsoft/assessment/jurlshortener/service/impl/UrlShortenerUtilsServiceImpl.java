package com.elsoft.assessment.jurlshortener.service.impl;


import com.elsoft.assessment.jurlshortener.service.UrlShortenerUtilsService;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerUtilsServiceImpl implements UrlShortenerUtilsService {

    private  final String possibleCharacters = "vwAMheo9PI2qNs5Zpf8FjkDc0TBn7lmRbtQ4YKXHEWxuzdra316OJigGLSVUCy";
    private  final char[] possibleCharactersArray = possibleCharacters.toCharArray();
    private  final int base = possibleCharactersArray.length;

    @Override
    public  String convertToShortUrl(long id) {
        var shortenedUrl = new StringBuilder();

        if (id == 0) {
            return String.valueOf(possibleCharactersArray[0]);
        }

        while (id > 0) {
            shortenedUrl.append(possibleCharactersArray[(int) (id % base)]);
            id = id / base;
        }

        return shortenedUrl.reverse().toString();
    }

    @Override
    public long retrieveLongUrl(String input) {
        var characters = input.toCharArray();
        var length = characters.length;

        var decoded = 0;

        var counter = 1;
        for (char character : characters) {
            decoded += possibleCharacters.indexOf(character) * Math.pow(base, length - counter);
            counter++;
        }
        return decoded;
    }
}
