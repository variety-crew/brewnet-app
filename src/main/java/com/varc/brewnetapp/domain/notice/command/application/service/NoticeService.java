package com.varc.brewnetapp.domain.notice.command.application.service;

import com.varc.brewnetapp.domain.notice.command.application.dto.CreateNoticeRequestDTO;
import com.varc.brewnetapp.domain.notice.command.application.dto.DeleteNoticeRequestDTO;
import com.varc.brewnetapp.domain.notice.command.application.dto.UpdateNoticeRequestDTO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface NoticeService {

    void createNotice(CreateNoticeRequestDTO createNoticeRequestDTO, List<MultipartFile> image, String accessToken);

    void updateNotice(UpdateNoticeRequestDTO updateNoticeRequestDTO, List<MultipartFile> image, String accessToken);

    void deleteNotice(DeleteNoticeRequestDTO deleteNoticeRequestDTO, String accessToken);
}
