package io.dkargo.munzi.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dkargo.munzi.board.dto.request.ReqCreateUserDto;
import io.dkargo.munzi.board.dto.request.ReqUpdateUserDto;
import io.dkargo.munzi.board.dto.response.ResCreateUserDto;
import io.dkargo.munzi.board.dto.response.ResGetUserDetailDto;
import io.dkargo.munzi.board.dto.response.ResGetUserListDto;
import io.dkargo.munzi.board.entity.Gender;
import io.dkargo.munzi.board.entity.User;
import io.dkargo.munzi.board.error.ErrorCode;
import io.dkargo.munzi.board.repository.UserRepository;
import io.dkargo.munzi.board.service.TestService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {


    private final String URL = "/user";

    private final String email = "munzi@deleo.co.kr";
    private final String nickname = "munzi";
    private final Gender gender = Gender.F;

    private Long userId = null;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestService testService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;


    @Test
    @Order(1)
    @DisplayName("유저 생성 - 성공")
    void createUser_success() throws Exception {
        ReqCreateUserDto dto = new ReqCreateUserDto(email, nickname, gender);
        String json = objectMapper.writeValueAsString(dto);

        MvcResult result = this.mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(testService.isSuccess(result));

        ResCreateUserDto response = objectMapper.readValue(result.getResponse().getContentAsString(), ResCreateUserDto.class);
        userId = response.getUserId();
        assertNotNull(userId);

        Optional<User> user = userRepository.findById(userId);
        assertTrue(user.isPresent());
        assertEquals(user.get().getEmail(), email);
        assertEquals(user.get().getNickname(), nickname);
    }

    @Test
    @Order(2)
    @DisplayName("유저 생성 - 실패 (EMAIL_DUPLICATED)")
    void createUser_error_EMAIL_DUPLICATED() throws Exception {
        createUser_success();

        ReqCreateUserDto dto = new ReqCreateUserDto(email, nickname, gender);
        String json = objectMapper.writeValueAsString(dto);

        MvcResult result = this.mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(ErrorCode.EMAIL_DUPLICATED.getStatus()))
                .andReturn();

        assertTrue(testService.isErrorCode(result, ErrorCode.EMAIL_DUPLICATED));
    }


    @Test
    @Order(3)
    @DisplayName("유저 목록 조회 - 성공")
    void getUserList_success() throws Exception {
        createUser_success();

        MvcResult result = this.mockMvc.perform(get(URL)
                .param("page", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(testService.isSuccess(result));

        ResGetUserListDto response = objectMapper.readValue(result.getResponse().getContentAsString(), ResGetUserListDto.class);
        assertEquals(response.getPage(), 1);
        assertEquals(response.getSize(), 10);

        assertEquals(response.getList().get(0).getEmail(), email);
        assertEquals(response.getList().get(0).getNickname(), nickname);
    }

    @Test
    @Order(4)
    @DisplayName("유저 목록 조회 - 실패 (BAD_REQUEST)")
    void getUserList_error_BAD_REQUEST() throws Exception {
        MvcResult result = this.mockMvc.perform(get(URL)
                .param("page", "BAD")
                .param("size", "REQUEST"))
                .andExpect(status().is(ErrorCode.INVALID_FORMAT.getStatus()))
                .andReturn();

        assertTrue(testService.isBadRequest(result));
    }

    @Test
    @Order(5)
    @DisplayName("유저 상세 조회 - 성공")
    void getUserDetail_success() throws Exception {
        createUser_success();

        MvcResult result = this.mockMvc.perform(get(URL + "/" + userId))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(testService.isSuccess(result));

        ResGetUserDetailDto response = objectMapper.readValue(result.getResponse().getContentAsString(), ResGetUserDetailDto.class);
        assertEquals(response.getEmail(), email);
        assertEquals(response.getNickname(), nickname);
    }

    @Test
    @Order(6)
    @DisplayName("유저 상세 조회 - 실패 (USER_NOT_FOUND)")
    void getUserDetail_error_USER_NOT_FOUND() throws Exception {
        MvcResult result = this.mockMvc.perform(get(URL + "/" + "0"))
                .andExpect(status().is(ErrorCode.USER_NOT_FOUND.getStatus()))
                .andReturn();

        assertTrue(testService.isErrorCode(result, ErrorCode.USER_NOT_FOUND));
    }

    @Test
    @Order(7)
    @DisplayName("유저 닉네임 변경 - 성공")
    void updateUser_success() throws Exception {
        createUser_success();

        ReqUpdateUserDto dto = new ReqUpdateUserDto(userId, nickname + "-changed");
        String json = objectMapper.writeValueAsString(dto);

        MvcResult result = this.mockMvc.perform(patch(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(testService.isSuccess(result));

        Boolean response = objectMapper.readValue(result.getResponse().getContentAsString(), Boolean.class);
        assertTrue(response);

        Optional<User> user = userRepository.findById(userId);
        assertTrue(user.isPresent());
        assertEquals(user.get().getNickname(), nickname + "-changed");
    }

    @Test
    @Order(8)
    @DisplayName("유저 닉네임 변경 - 실패 (USER_NOT_FOUND)")
    void updateUser_error_USER_NOT_FOUND() throws Exception {
        ReqUpdateUserDto dto = new ReqUpdateUserDto(0, nickname + "-changed");
        String json = objectMapper.writeValueAsString(dto);

        MvcResult result = this.mockMvc.perform(patch(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(ErrorCode.USER_NOT_FOUND.getStatus()))
                .andReturn();

        assertTrue(testService.isErrorCode(result, ErrorCode.USER_NOT_FOUND));
    }
}