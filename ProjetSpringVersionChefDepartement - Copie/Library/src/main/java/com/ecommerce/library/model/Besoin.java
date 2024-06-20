package com.ecommerce.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Besoin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateDemande;
    private Date dateAffectation;
    private boolean isAffected = false;
    private String idMembreDepartement;
    private Long idDepartement;
    private boolean isBesoinInAppelOffre = false;
    private boolean isBesoinSeenByChef = false;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Ordinateur> ordinateurs = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Imprimante> imprimantes = new ArrayList<>();

    public void setBesoinSeenByChef(boolean besoinSeenByChef) {
        isBesoinSeenByChef = besoinSeenByChef;
    }


}
