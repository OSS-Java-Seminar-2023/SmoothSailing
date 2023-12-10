package com.SmoothSailing.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Data
@Entity
@Setter
@Getter
@Table(name="crew")
public class CrewModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String position;
    @Column
    private String price;
    @Column
    private String review;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @Convert(converter = CompanyModelConverter.class)
    private CompanyModel company_id;
}