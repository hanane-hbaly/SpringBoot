package com.ecommerce.library.repository;

import com.ecommerce.library.model.NotifFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotifFournisseurRepository extends JpaRepository<NotifFournisseur, Long> {

    List<NotifFournisseur> getNotifFournisseurByIdFournisseurAndIsSeenIsFalse(String idFournisseur);
    List<NotifFournisseur> findAllByAndIsSeenIsFalse();
    List<NotifFournisseur>findNotifFournisseurByIdFournisseur(String idFournisseur);

}
