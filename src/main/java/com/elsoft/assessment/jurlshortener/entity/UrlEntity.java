package com.elsoft.assessment.jurlshortener.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_urls")
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)
public class UrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(columnDefinition = "Text")
    private String longUrl;

    @Column(name = "date_created")
    @CreatedDate
    private LocalDateTime createdDate;
      @Column(name = "date_created")

    private LocalDateTime expiryDate;




}