package com.elsoft.assessment.jurlshortener;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class UrlRequest {
    private String url;
    @Builder.Default
    private LocalDateTime expiryDate = LocalDateTime.now().plusHours(48);
}
