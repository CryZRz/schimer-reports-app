package com.schimer.reportsapp.domain.entities.rawMaterial;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "quality_parameters_raw_material")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QualityParameterRawMaterialRelease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String parameter;
    private String specification;
    private String result;

    @ManyToOne
    @JoinColumn(name = "raw_material_release_id")
    private RawMaterialReleaseEntity rawMaterialReleases;
}
