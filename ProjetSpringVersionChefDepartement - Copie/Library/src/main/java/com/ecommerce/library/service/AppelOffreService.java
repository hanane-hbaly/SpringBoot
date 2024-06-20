package com.ecommerce.library.service;

import com.ecommerce.library.model.AppelOffre;

import java.util.List;
import java.util.Optional;

public interface AppelOffreService  {

    Optional<AppelOffre> getAppelOffreById(Long id);
    List<AppelOffre> getAllAppelOffre();
    void publierAppelOffre(AppelOffre appelOffre);
    void deleteAppelOffre(Long id);

}
