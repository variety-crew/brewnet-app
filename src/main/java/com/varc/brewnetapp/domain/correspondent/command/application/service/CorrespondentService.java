package com.varc.brewnetapp.domain.correspondent.command.application.service;

import com.varc.brewnetapp.domain.correspondent.command.application.dto.CorrespondentRequestDTO;

public interface CorrespondentService {

    void createCorrespondent(String loginId, CorrespondentRequestDTO newCorrespondent);
}
