package io.dkargo.munzi.board.dto.request;

import lombok.Data;

@Data
public class ReqUpdateBoardDto {

    private long boardId;

    private String title;

    private String content;

}
