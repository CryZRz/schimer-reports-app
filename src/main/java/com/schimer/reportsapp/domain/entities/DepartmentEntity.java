package com.schimer.reportsapp.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "departments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Override
    public String toString() {
        return name != null ? name : "";
    }
}
