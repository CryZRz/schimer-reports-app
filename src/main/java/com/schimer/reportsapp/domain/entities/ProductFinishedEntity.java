package com.schimer.reportsapp.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products_finished")
public class ProductFinishedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String batch;
    private String product;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private TemplateEntity template;
}
