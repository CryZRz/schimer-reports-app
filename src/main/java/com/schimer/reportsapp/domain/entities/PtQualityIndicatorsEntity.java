package com.schimer.reportsapp.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Table(name = "indicators_quality")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PtQualityIndicatorsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receipt_date")
    private LocalDate reciptDate;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    private boolean status;

    @OneToOne
    @JoinColumn(name = "product_finished_id")
    private ProductFinishedEntity productFinished;
}
