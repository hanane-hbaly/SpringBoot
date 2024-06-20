package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.AppelOffre;
import com.ecommerce.library.repository.AppelOffreRepository;
import com.ecommerce.library.repository.BesoinRepository;
import com.ecommerce.library.service.AppelOffreService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
public class AppelOffreImpl implements AppelOffreService {
    private final AppelOffreRepository appelOffreRepository;
    private final BesoinRepository besoinRepository;

    public AppelOffreImpl(AppelOffreRepository appelOffreRepository,
                          BesoinRepository besoinRepository) {
        this.appelOffreRepository = appelOffreRepository;
        this.besoinRepository = besoinRepository;
    }


    @Override
    public Optional<AppelOffre> getAppelOffreById(Long id) {
        Optional<AppelOffre> appelOffre = appelOffreRepository.findById(id);


        return appelOffre;
    }

    @Override
    public List<AppelOffre> getAllAppelOffre() {
        return appelOffreRepository.findAll();
    }



    @Override
    public void publierAppelOffre(AppelOffre appelOffre) {
        Optional.ofNullable(appelOffre.getBesoins())
                .stream()
                .flatMap(Collection::stream)
                .peek(b -> b.setBesoinInAppelOffre(true))
                .forEach(besoinRepository::save);
        appelOffre.setDatePub(Date.valueOf(LocalDate.now()));
        appelOffreRepository.save(appelOffre);
    }

    @Override
    public void deleteAppelOffre(Long id) {
        Optional<AppelOffre> appelOffre = appelOffreRepository.findAppelOffreById(id);





        appelOffreRepository.deleteById(id);
    }

}