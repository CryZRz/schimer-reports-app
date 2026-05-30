package com.schimer.reportsapp.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products_finished")
public class ProductFinishedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String batch;
    private String folio;
    private String product;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserEntity user;

    @Column(name="created_at")
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private TemplateEntity template;

    @OneToOne(mappedBy = "productFinished", cascade = CascadeType.ALL)
    private PtQualityCertificateEntity qualityCertificate;

    @OneToOne(mappedBy = "productFinished", cascade = CascadeType.ALL)
    private PtQualityIndicatorsEntity qualityIndicators;

    @OneToOne(mappedBy = "productFinished", cascade = CascadeType.ALL)
    private PtQualitySolidLiquidEntity qualitySolidLiquid;
}
