package com.schimer.reportsapp.domain.entities.rawMaterial;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "raw_material_releases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterialReleaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    private Integer amount;
    private String note;

    @Column(name = "is_accepted")
    private boolean isAccepted;

    private String certificate;

    @OneToOne
    @JoinColumn(name = "raw_material_id")
    private RawMaterialEntity rawMaterial;

    @OneToMany(
            mappedBy = "rawMaterialReleases",
            cascade = CascadeType.ALL,
            fetch =  FetchType.EAGER,
            orphanRemoval = true
    )
    private List<QualityParameterRawMaterialRelease> qualityParameters;
}
