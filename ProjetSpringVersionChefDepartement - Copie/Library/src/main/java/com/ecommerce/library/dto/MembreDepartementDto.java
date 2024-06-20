package com.ecommerce.library.dto;

import com.ecommerce.library.model.Departement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembreDepartementDto {
    private Long id;
    private String domaineExpertise;
    private String laboratoire;
    private Departement departement;
}
