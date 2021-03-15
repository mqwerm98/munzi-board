package io.dkargo.munzi.board.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ReqCreateBoardDto {

    @NotNull
    private long userId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

}
