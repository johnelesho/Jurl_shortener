package com.elsoft.assessment.jurlshortener.service.impl;

import com.elsoft.assessment.jurlshortener.BadRequestException;
import com.elsoft.assessment.jurlshortener.dto.UrlRequest;
import com.elsoft.assessment.jurlshortener.entity.UrlEntity;
import com.elsoft.assessment.jurlshortener.repository.UrlRepository;
import com.elsoft.assessment.jurlshortener.service.UrlShortenerService;
import com.elsoft.assessment.jurlshortener.service.UrlShortenerUtilsService;
import com.elsoft.assessment.jurlshortener.utils.Helpers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
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
        else if(request.getExpiryDate() == null)
            url.setExpiryDate(LocalDateTime.now().plusHours(request.getActiveHours()));
        else url.setExpiryDate(request.getExpiryDate());

        try {
            if(Helpers.isUrlValid2(request.getUrl())){
                var entity = urlRepository.save(url);
                return urlShortenerUtilsService.convertToShortUrl(entity.getId());
            }
        } catch (MalformedURLException e) {
            throw new BadRequestException("Invalid Url");
        }
        throw new BadRequestException();

    }

    @Override
    public String getOriginalFull(String shortUrl) {
        var id = urlShortenerUtilsService.retrieveLongUrl(shortUrl);
        var entity = urlRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("There is no entity with " + shortUrl));

        if (entity.getExpiryDate() != null && entity.getExpiryDate().isBefore(LocalDateTime.now())){
            urlRepository.delete(entity);
            throw new BadRequestException("Link expired!");
        }

        return entity.getUrl();
    }


}
