package com.schimer.reportsapp.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "job_position")
    private String jobPosition;

    private String signature;

    private String password;

    private String email;

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dropbox_account_id")
    private DropboxAccountEntity dropboxAccount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email_account_id")
    private EmailAccountEntity emailAccount;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<QuestionResponseEntity> questions;
}
