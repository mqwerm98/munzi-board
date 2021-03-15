package io.dkargo.munzi.board.dto.response;

import io.dkargo.munzi.board.entity.Board;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResGetBoardDetailDto {

    private String title;

    private String content;

    private String writer;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public ResGetBoardDetailDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getUser().getNickname();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
    }
}
