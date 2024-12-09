//package com.varc.brewnetapp.domain.statistics.query.controller;
//
//import static org.awaitility.Awaitility.given;
//
//import com.varc.brewnetapp.domain.statistics.query.dto.NewOrderDTO;
//import com.varc.brewnetapp.domain.statistics.query.service.StatisticsService;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.test.web.servlet.MockMvc;
//
//@WebMvcTest(StatisticsController.class)
//@MockBean(JpaMetamodelMappingContext.class)
//class StatisticsControllerTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private StatisticsService statisticsService;
//
//    @Test
//    @DisplayName("신규 주문 목록 조회 로직 테스트")
//    public void findNewOrderTest() throws Exception {
//
//        // Given
//        MemberDto.Post post = new MemberDto.Post("hgd@gmail.com", "홍길동", "010-1234-5678");
//        String content = gson.toJson(post);
//
//        MemberDto.response responseDto =
//            new MemberDto.response(1L,
//                "hgd@gmail.com",
//                "홍길동",
//                "010-1234-5678",
//                Member.MemberStatus.MEMBER_ACTIVE,
//                new Stamp());
//
//        Pageable page = PageRequest.of(0, 10);
//        List<NewOrderDTO> newOrderList = new ArrayList<>();
//        newOrderList.add(new NewOrderDTO(1, 1, "테스트 프랜차이즈", "고구마", 1000, "2024-12-12"));
//        Page<NewOrderDTO> orderPage = new PageImpl<>(newOrderList, page, 1);
//
//
//        given(statisticsService.findNewOrder(Mockito.any(Pageable.class))).willReturn(orderPage);
//        given(mapper.memberPostToMember(Mockito.any(MemberDto.Post.class))).willReturn(new Member());
//
//        given(memberService.createMember(Mockito.any(Member.class))).willReturn(new Member());
//
//        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(responseDto);
//
//        // When
//        ResultActions actions =
//            mockMvc.perform(
//                post("/members")
//                    .accept(MediaType.APPLICATION_JSON)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(content)
//            );
//
//        // Then
//        actions
//            .andExpect(status().isCreated())
//            .andExpect(jsonPath("$.data.email").value(post.getEmail()))
//            .andExpect(jsonPath("$.data.name").value(post.getName()))
//            .andExpect(jsonPath("$.data.phone").value(post.getPhone()));
//    }
//
//}