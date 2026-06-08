package com.schimer.reportsapp.domain.entities.rawMaterial;

import com.schimer.reportsapp.domain.entities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "raw_materials")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RawMaterialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    private String batch;
    private String product;
    private String folio;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private TemplateRawMaterialEntity template;

    @Column(name = "report_path")
    private String reportPath;

    @OneToOne(
            mappedBy = "rawMaterial",
            cascade = CascadeType.ALL
    )
    private RawMaterialReleaseEntity rawMaterialRelease;
}
