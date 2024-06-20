package com.ecommerce.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MembreDepartement  extends Users{


    private String domaineExpertise;
    private String laboratoire;

    @ManyToOne
    private Departement departement;
    @Override
    public String toString() {
        return "Departement{" +
                // Include fields here, excluding membreDepartements to avoid circular reference
                '}';
    }
}
