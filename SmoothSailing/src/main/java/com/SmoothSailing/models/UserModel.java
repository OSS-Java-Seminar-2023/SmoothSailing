package com.SmoothSailing.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name="User")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String license;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String gender;
    @Column
    private String birthday;

}
