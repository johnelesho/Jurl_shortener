package com.elsoft.assessment.jurlshortener.service.impl;


import com.elsoft.assessment.jurlshortener.dto.UrlRequest;
import com.elsoft.assessment.jurlshortener.dto.UrlResponse;
import com.elsoft.assessment.jurlshortener.entity.UrlEntity;
import com.elsoft.assessment.jurlshortener.exceptions.BadRequestException;
import com.elsoft.assessment.jurlshortener.repository.UrlRepository;
import com.elsoft.assessment.jurlshortener.service.UrlShortenerService;
import com.elsoft.assessment.jurlshortener.service.UrlShortenerUtilsService;
import com.elsoft.assessment.jurlshortener.utils.Helpers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlShortenerServiceImpl implements UrlShortenerService {
    final UrlRepository urlRepository;
    final UrlShortenerUtilsService urlShortenerUtilsService;
    @Override
    public String generateShortLink(UrlRequest request) {
        var url = new UrlEntity();
        url.setUrl(request.getUrl());

        if(request.getExpiryDate() != null && request.getActiveHours() != null) {
            long diff = Helpers.getDateDiff(request.getExpiryDate());
            if(diff != request.getActiveHours())
                 throw new BadRequestException("Enter either an expiry date or active hours (default is 48 hours)");
        }
        else if(request.getExpiryDate() == null && request.getActiveHours() != null)
            url.setExpiryDate(LocalDateTime.now().plusHours(request.getActiveHours()));
        else if(request.getExpiryDate() != null)
            url.setExpiryDate(request.getExpiryDate());
        else
            url.setExpiryDate(LocalDateTime.now().plusHours(48));

        try {
            if(Helpers.isUrlValid2(request.getUrl())){
                UrlEntity entity = getUrlFromDb(request.getUrl());
                if(entity == null)
                    entity = urlRepository.save(url);

//               String shortUrl = urlShortenerUtilsService.getBaseUrl(request.getUrl());
             String   shortUrl = urlShortenerUtilsService.retrieveStringFromId(entity.getId());
                log.info("Long Url : {}  ShortUrl {}", request.getUrl(), shortUrl);

                return shortUrl;
            }
        } catch (MalformedURLException e) {
            throw new BadRequestException("Invalid Url " + e.getMessage());
        }
        throw new BadRequestException();

    }

    @Override
    public String getOriginalFull(String shortUrl) {
        var id = 0L;
        try {
            id = urlShortenerUtilsService.retrieveIdFromString(shortUrl);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
        log.info("Decoded Id: {}", id);
        var entity = urlRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Invalid Url " + shortUrl));

        if (entity.getExpiryDate() != null && entity.getExpiryDate().isBefore(LocalDateTime.now())) {
            urlRepository.delete(entity);
            throw new BadRequestException("Link expired!");
        }

        return entity.getUrl();
    }

    @Override
    public List<UrlResponse> getAll(String search, LocalDateTime createdDate, LocalDateTime expDate) {
        log.info("Getting all urls search  {} CreatedDate {} expDate {} ", search, createdDate, expDate);
        var result = urlRepository.findAllByQuery(search, createdDate, expDate);
        return result.stream().map(res ->{
            return new UrlResponse(res.getUrl(), res.getCreatedDate(), res.getExpiryDate());
        }).collect(Collectors.toList());
    }
    private UrlEntity getUrlFromDb(String fullUrl) {
        return urlRepository.findByUrl(fullUrl);
    }
}
