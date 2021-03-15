package io.dkargo.munzi.board.dto.response;

import io.dkargo.munzi.board.entity.Gender;
import io.dkargo.munzi.board.entity.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetUserDetailDto {

    private String email;

    private String nickname;

    private Gender gender;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public ResGetUserDetailDto(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.gender = user.getGender();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}
