package com.varc.brewnetapp.domain.notice.command.application.service;

import com.varc.brewnetapp.domain.notice.command.application.dto.CreateNoticeRequestDTO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface NoticeService {

    void createNotice(CreateNoticeRequestDTO createNoticeRequestDTO, List<MultipartFile> image, String accessToken);
}
