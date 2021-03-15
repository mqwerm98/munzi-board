package io.dkargo.munzi.board.dto.response;

import io.dkargo.munzi.board.entity.Gender;
import io.dkargo.munzi.board.entity.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetUserListDto {

    private int page;

    private int size;

    private int totalPage;

    private List<GetUser> list = new ArrayList<>();

    public ResGetUserListDto(List<User> list, int page, int size, int totalPage) {
        this.list = list.stream().map(u -> new GetUser(u)).collect(Collectors.toList());
        this.page = page;
        this.size = size;
        this.totalPage = totalPage;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetUser {

        private String email;

        private String nickname;

        private Gender gender;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;

        public GetUser(User user) {
            this.email = user.getEmail();
            this.nickname = user.getNickname();
            this.gender = user.getGender();
            this.createdAt = user.getCreatedAt();
            this.updatedAt = user.getUpdatedAt();
        }
    }
}
