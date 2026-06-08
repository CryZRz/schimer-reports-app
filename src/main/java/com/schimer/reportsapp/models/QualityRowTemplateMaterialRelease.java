package com.schimer.reportsapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//en español para hacer bind con el word xd
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QualityRowTemplateMaterialRelease {
    private Long id;
    private String parametro;
    private String especificacion;
    private String resultado;
}
