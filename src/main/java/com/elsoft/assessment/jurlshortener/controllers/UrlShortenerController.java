package com.elsoft.assessment.jurlshortener.controllers;

import com.elsoft.assessment.jurlshortener.dto.UrlRequest;
import com.elsoft.assessment.jurlshortener.service.UrlShortenerService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;



    @ApiOperation(value = "Generates new and shorter url", notes = "Converts a long url to short url")
    @PostMapping()
    public ResponseEntity<String> convertToShortUrl(@RequestBody UrlRequest request) {
        String shortLink = urlShortenerService.generateShortLink(request);
        return new ResponseEntity<>(shortLink, HttpStatus.CREATED);
    }
    @ApiOperation(value = "Retrieves all url", notes = "Finds original urls stored in the database")
    @GetMapping("all")
    @Cacheable(value = "allUrls", sync = true   )
    public ResponseEntity<?> getAll(@RequestParam(value = "search", required = false ) String search,@RequestParam(value = "createdDate", required = false ) LocalDateTime createdDate, @RequestParam(value = "expiryDate", required = false ) LocalDateTime expiryDate) {

        var urls = urlShortenerService.getAll(search,createdDate, expiryDate);
        return ResponseEntity.status(HttpStatus.OK)
                .body(urls);
    }

    @ApiOperation(value = "Retrieves and Redirect", notes = "Finds original url from short url and redirects")
    @GetMapping()
    @Cacheable(value = "urls", key = "#shortUrl", sync = true)
    public ResponseEntity<Void> getAndRedirect(@RequestParam("url") String shortUrl) {
        var url = urlShortenerService.getOriginalFull(shortUrl);
        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create(url))
                .build();
    }


}
