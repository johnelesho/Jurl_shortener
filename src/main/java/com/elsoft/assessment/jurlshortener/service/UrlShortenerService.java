package com.elsoft.assessment.jurlshortener.service;

import com.elsoft.assessment.jurlshortener.dto.UrlRequest;

public interface UrlShortenerService {


    String generateShortLink(UrlRequest request);

    String getOriginalFull(String shortUrl);
}
