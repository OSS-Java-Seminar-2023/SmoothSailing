package com.SmoothSailing.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Table(name="crewcompany")
public class CrewCompanyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "crew_id")
    private CrewModel crew_id;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyModel company_id;
}
