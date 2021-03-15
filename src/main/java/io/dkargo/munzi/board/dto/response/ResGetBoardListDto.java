package io.dkargo.munzi.board.dto.response;

import io.dkargo.munzi.board.entity.Board;
import io.dkargo.munzi.board.entity.Gender;
import io.dkargo.munzi.board.entity.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ResGetBoardListDto {

    private int page;

    private int size;

    private int totalPage;

    private List<GetBoard> list = new ArrayList<>();

    public ResGetBoardListDto(List<Board> list, int page, int size, int totalPage) {
        this.list = list.stream().map(u -> new GetBoard(u)).collect(Collectors.toList());
        this.page = page;
        this.size = size;
        this.totalPage = totalPage;
    }

    public static class GetBoard {

        private String title;

        private String content;

        private String writer;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;

        public GetBoard(Board board) {
            this.title = board.getTitle();
            this.content = board.getContent();
            this.writer = board.getUser().getNickname();
            this.createdAt = board.getCreatedAt();
            this.updatedAt = board.getUpdatedAt();
        }
    }
}
