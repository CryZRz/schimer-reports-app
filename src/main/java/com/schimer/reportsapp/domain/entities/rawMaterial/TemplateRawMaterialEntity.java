package com.schimer.reportsapp.domain.entities.rawMaterial;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "templates_raw_material")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemplateRawMaterialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String path;
}
