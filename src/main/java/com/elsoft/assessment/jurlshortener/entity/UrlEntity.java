package com.elsoft.assessment.jurlshortener.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_urls")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UrlEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(columnDefinition = "Text", nullable = false)
    private String url;

    @Column(name = "date_created", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime createdDate;
      @Column(name = "date_to_expire", columnDefinition = "TIMESTAMP")
    private LocalDateTime expiryDate;


    public UrlEntity(String s) {
        this.url = s;
    }
}