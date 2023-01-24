package com.elsoft.assessment.jurlshortener.controllers;

import com.elsoft.assessment.jurlshortener.dto.UrlRequest;
import com.elsoft.assessment.jurlshortener.service.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;



    @ApiOperation(value = "Generates new and shorter url", notes = "Converts a long url to short url")
    @PostMapping()
    public String convertToShortUrl(@RequestBody UrlRequest request) {
        return urlShortenerService.generateShortLink(request);
    }

    @ApiOperation(value = "Retrieves and Redirect", notes = "Finds original url from short url and redirects")
    @GetMapping()
    @Cacheable(value = "urls", key = "#shortUrl", sync = true)
    public ResponseEntity<Void> getAndRedirect(@PathVariable String shortUrl) {
        var url = urlShortenerService.getOriginalFull(shortUrl);
        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create(url))
                .build();
    }
}
