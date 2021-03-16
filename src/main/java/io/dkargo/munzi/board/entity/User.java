package io.dkargo.munzi.board.entity;

import io.dkargo.munzi.board.dto.request.ReqCreateUserDto;
import io.dkargo.munzi.board.entity.converter.GenderConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue
    private Long id;

    private String email;

    private String nickname;

//    @Enumerated(EnumType.STRING)
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @OneToMany(mappedBy = "user")
    private List<Board> boardList = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public User(ReqCreateUserDto dto) {
        this.email = dto.getEmail();
        this.nickname = dto.getNickname();
        this.gender = dto.getGender();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
        this.updatedAt = LocalDateTime.now();
    }
}
