package com.schimer.reportsapp.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "certificates_quality_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PtQualityCertificateDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "certificate_quality_id")
    private PtQualityCertificateEntity qualityCertificate;

    @Column(name = "specification_name")
    private String specificationName;

    @Column(name = "parameter_value")
    private String parameterValue;

    @Column(name = "result_value")
    private String resultValue;

    @Column(name = "units_value")
    private String unitsValue;

    @Column(name = "methodology_value")
    private String methodologyValue;
}
