//package com.varc.brewnetapp.domain.statistics.query.controller;
//
//import static org.awaitility.Awaitility.given;
//import static org.mockito.ArgumentMatchers.any;
//
//import com.varc.brewnetapp.domain.statistics.query.dto.NewOrderDTO;
//import com.varc.brewnetapp.domain.statistics.query.service.StatisticsService;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.BDDMockito;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//class StatisticsControllerTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private StatisticsService statisticsService;
//
//
//    @Test
//    @DisplayName("신규 주문 목록 조회 로직 테스트")
//    @WithMockUser(username = "wnstj", roles = "MASTER") // Mock 인증 추가
//    public void findNewOrderTest() throws Exception {
//
//        //given
//        Pageable page = PageRequest.of(0, 10);
//        List<NewOrderDTO> newOrderList = new ArrayList<>();
//        newOrderList.add(new NewOrderDTO(1, 1, "테스트 프랜차이즈", "고구마", 1000, "2024-12-12"));
//
//        Page<NewOrderDTO> orderPage = new PageImpl<>(newOrderList, page, newOrderList.size());
//
//        Mockito.when(statisticsService.findNewOrder(page)).thenReturn(orderPage);
//
//        // When
//        ResultActions actions =
//            mockMvc.perform(get("/api/v1/hq/statistics/new-order")
//                .param("page", "0") // 페이지 번호
//                .param("size", "10") // 페이지 크기
//                .accept(MediaType.APPLICATION_JSON)
//            );
//
//        // Then
//        actions
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.result.content[0].franchiseName").value(newOrderList.get(0).getFranchiseName()));
//    }
//
//
//}