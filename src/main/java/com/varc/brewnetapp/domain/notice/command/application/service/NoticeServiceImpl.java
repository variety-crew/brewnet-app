package com.varc.brewnetapp.domain.notice.command.application.service;

import com.varc.brewnetapp.common.S3ImageService;
import com.varc.brewnetapp.domain.member.command.domain.repository.MemberRepository;
import com.varc.brewnetapp.domain.notice.command.application.dto.CreateNoticeRequestDTO;
import com.varc.brewnetapp.domain.notice.command.domain.aggregate.entity.Notice;
import com.varc.brewnetapp.domain.notice.command.domain.aggregate.entity.NoticeImage;
import com.varc.brewnetapp.domain.notice.command.domain.repository.NoticeImageRepository;
import com.varc.brewnetapp.domain.notice.command.domain.repository.NoticeRepositiory;
import com.varc.brewnetapp.exception.InvalidDataException;
import com.varc.brewnetapp.exception.MemberNotFoundException;
import com.varc.brewnetapp.security.utility.JwtUtil;
import java.time.LocalDateTime;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service(value = "commandNoticeService")
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepositiory noticeRepositiory;
    private final NoticeImageRepository noticeImageRepository;
    private final JwtUtil jwtUtil;
    private final S3ImageService s3ImageService;
    private final MemberRepository memberRepository;

    @Autowired
    public NoticeServiceImpl(NoticeRepositiory noticeRepositiory,
        NoticeImageRepository noticeImageRepository, JwtUtil jwtUtil, S3ImageService s3ImageService,
        MemberRepository memberRepository) {
        this.noticeRepositiory = noticeRepositiory;
        this.noticeImageRepository = noticeImageRepository;
        this.jwtUtil = jwtUtil;
        this.s3ImageService = s3ImageService;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public void createNotice(CreateNoticeRequestDTO createNoticeRequestDTO,
        List<MultipartFile> image, String accessToken) {

        String loginId = jwtUtil.getLoginId(accessToken.replace("Bearer ", ""));

        Integer memberCode = memberRepository.findById(loginId)
            .orElseThrow(() -> new MemberNotFoundException("공지 작성자의 회원 정보가 없습니다.")).getMemberCode();

        Notice notice = Notice.builder()
            .title(createNoticeRequestDTO.getTitle())
            .content(createNoticeRequestDTO.getContent())
            .createdAt(LocalDateTime.now())
            .memberCode(memberCode)
            .active(true)
            .build();

        notice = noticeRepositiory.save(notice);

        for(MultipartFile file : image) {

            String s3Url = null;
            try {
                s3Url = s3ImageService.upload(file);
            }catch (InvalidDataException e) {
                throw new InvalidDataException("이미지를 저장할 수 없습니다");
            }

            if(s3Url == null)
                throw new InvalidDataException("이미지가 저장되지 않았습니다");

            NoticeImage noticeImage = NoticeImage.builder()
                .noticeCode(notice.getNoticeCode())
                .imageUrl(s3Url)
                .build();

            noticeImageRepository.save(noticeImage);
        }
    }


}
