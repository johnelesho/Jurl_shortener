package com.elsoft.assessment.jurlshortener.service;

import com.elsoft.assessment.jurlshortener.dto.UrlRequest;
import com.elsoft.assessment.jurlshortener.dto.UrlResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface UrlShortenerService {


    String generateShortLink(UrlRequest request);

    String getOriginalFull(String shortUrl);

     List<UrlResponse> getAll(String search , LocalDateTime createdDate, LocalDateTime expDate);

}
