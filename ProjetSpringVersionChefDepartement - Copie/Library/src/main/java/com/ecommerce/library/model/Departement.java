package com.ecommerce.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;


@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Departement", uniqueConstraints = @UniqueConstraint(columnNames = "nomDepartement"))
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomDepartement;

    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<MembreDepartement> membreDepartements;
    @Override
    public String toString() {
        return "MembreDepartement{" +
                // Include fields here, excluding departement to avoid circular reference
                '}';
    }
}
