package com.elsoft.assessment.jurlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@Data
@ApiModel(description = "Request object for create a short link")
public class UrlRequest {
    @ApiModelProperty(required = true, notes = "The full Url to convert to short link")
    @NotBlank("Please provide the full url to be converted")
    private String url;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty(required = false, notes = "Please indicate when you want it to expire")

    private LocalDateTime expiryDate ;

    @ApiModelProperty(required = false, notes = "Please indicate how long (hours) you want it to be active")

    private Long activeHours;
}
