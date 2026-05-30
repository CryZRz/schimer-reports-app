package com.schimer.reportsapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class QualityFormRow {
    private Long id;
    private final String specification;
    private String parameter;
    private String result;
    private final String units;
    private final String methodology;
}
