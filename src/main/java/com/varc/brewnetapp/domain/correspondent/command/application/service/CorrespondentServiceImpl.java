package com.varc.brewnetapp.domain.correspondent.command.application.service;

import com.varc.brewnetapp.domain.correspondent.command.domain.repository.CorrespondentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CorrespondentServiceCommand")
public class CorrespondentServiceImpl implements CorrespondentService{

    private final CorrespondentRepository correspondentRepository;

    @Autowired
    public CorrespondentServiceImpl(CorrespondentRepository correspondentRepository) {
        this.correspondentRepository = correspondentRepository;
    }
}
