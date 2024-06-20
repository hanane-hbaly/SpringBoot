package com.ecommerce.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Optional;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PanneAvecRessource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPanne;
    private Long idRessource;
    private String idMembreDepartement;
    private String codeBarre;
    private String type;
    private String marque;
    private Date dateApparition;


}