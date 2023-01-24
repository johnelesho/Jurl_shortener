package com.elsoft.assessment.jurlshortener.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "Request object for create a short link")
@NoArgsConstructor
@AllArgsConstructor
public class UrlRequest {
    @ApiModelProperty(required = true, notes = "The full Url to convert to short link")
    @NotBlank(message="Please provide the full url to be converted")
    private String url;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty(required = false, notes = "Please indicate when you want it to expire")

    private LocalDateTime expiryDate ;

    @ApiModelProperty(required = false, notes = "Please indicate how long (days) you want it to be active")

    private Long activeHours;

    public UrlRequest(String url )
    {
       this.url = url;
    }
}
