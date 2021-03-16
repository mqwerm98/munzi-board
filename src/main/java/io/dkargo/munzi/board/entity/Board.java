package io.dkargo.munzi.board.entity;

import io.dkargo.munzi.board.dto.request.ReqCreateBoardDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Board(ReqCreateBoardDto dto, User user) {
        this.user = user;
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }

    public void changeTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeContent(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }
}
