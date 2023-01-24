package com.elsoft.assessment.jurlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlResponse implements Serializable {



    private String url;


    private LocalDateTime createdDate;

    private LocalDateTime expiryDate;


}