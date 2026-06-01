package com.schimer.reportsapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QualityFormRowMaterialRelease {
    private Long id;
    private String parameter;
    private String specification;
    private String result;
}
