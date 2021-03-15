package io.dkargo.munzi.board.service;

import io.dkargo.munzi.board.dto.request.ReqCreateUserDto;
import io.dkargo.munzi.board.dto.request.ReqUpdateUserDto;
import io.dkargo.munzi.board.dto.response.ResCreateUserDto;
import io.dkargo.munzi.board.dto.response.ResGetUserDetailDto;
import io.dkargo.munzi.board.dto.response.ResGetUserListDto;
import io.dkargo.munzi.board.entity.PageRequest;
import io.dkargo.munzi.board.entity.User;
import io.dkargo.munzi.board.error.DkargoException;
import io.dkargo.munzi.board.error.ErrorCode;
import io.dkargo.munzi.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResCreateUserDto createUser(ReqCreateUserDto dto) {

        if (checkEmailDuplicate(dto.getEmail())) {
            throw new DkargoException(ErrorCode.EMAIL_DUPLICATED);
        }

        User user = new User(dto);
        userRepository.save(user);

        return new ResCreateUserDto(user.getId());
    }

    public ResGetUserListDto getUserList(int page, int size) {
        PageRequest pr = new PageRequest(page, size, Sort.Direction.DESC);
        List<User> userList = userRepository.findAll(pr.of()).getContent();

        long count = userRepository.count();
        int totalPage = (int) ((count + size - 1) / size);
        return new ResGetUserListDto(userList, page, size, totalPage);
    }

    public ResGetUserDetailDto getUserDetail(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DkargoException(ErrorCode.USER_NOT_FOUND));

        return new ResGetUserDetailDto(user);
    }

    public boolean updateUser(ReqUpdateUserDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new DkargoException(ErrorCode.USER_NOT_FOUND));

        user.changeNickname(dto.getNickname());

        return true;
    }

    private boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }
}
