package com.schimer.reportsapp.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "certificates_quality")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PtQualityCertificateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_finished_id")
    private ProductFinishedEntity productFinished;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @OneToMany(mappedBy = "qualityCertificate")
    private List<PtQualityCertificateDetailEntity> qualityDetails;

    private Integer amount;
}
