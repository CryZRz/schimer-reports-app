package com.schimer.reportsapp.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "quality_solids_liquids")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PtQualitySolidLiquidEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal solids;
    private BigDecimal ph;

    @Column(name = "apparent_density")
    private BigDecimal apparentDensity;

    private String appearance;

    @Column(name = "zinc_oxide_percentage")
    private BigDecimal zincOxidePercentage;

    private BigDecimal kilograms;

    @Column(name = "identification_review")
    private boolean identificationReview;

    @Column(name = "packaging_review")
    private boolean packagingReview;

    private Integer certificate;

    @OneToOne
    @JoinColumn(name = "product_finished_id")
    private ProductFinishedEntity productFinished;
}
