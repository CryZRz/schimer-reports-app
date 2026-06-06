package com.schimer.reportsapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Lo pongo en español para mapearlo al template
@AllArgsConstructor
@Setter
@NoArgsConstructor
@Getter
public class QualityFormRowTemplate {
    private Long id;
    private String especificacion;
    private String parametro;
    private String resultado;
    private String unidades;
    private String metodologia;

    public Long getId() {
        return id;
    }

    public String getEspecificacion() {
        return especificacion;
    }

    public String getParametro() {
        return parametro;
    }

    public String getResultado() {
        return resultado;
    }

    public String getUnidades() {
        return unidades;
    }

    public String getMetodologia() {
        return metodologia;
    }
}
