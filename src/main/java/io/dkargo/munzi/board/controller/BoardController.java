package io.dkargo.munzi.board.controller;

import io.dkargo.munzi.board.dto.request.ReqCreateBoardDto;
import io.dkargo.munzi.board.dto.request.ReqCreateUserDto;
import io.dkargo.munzi.board.dto.request.ReqUpdateBoardDto;
import io.dkargo.munzi.board.dto.request.ReqUpdateUserDto;
import io.dkargo.munzi.board.dto.response.*;
import io.dkargo.munzi.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResCreateBoardDto createBoard(@Validated @RequestBody ReqCreateBoardDto dto) {
        return boardService.createBoard(dto);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResGetBoardListDto getBoardList(@RequestParam("page") int page, @RequestParam("size") int size) {
        return boardService.getBoardList(page, size);
    }

    @GetMapping("/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public ResGetBoardDetailDto getBoardDetail(@PathVariable("boardId") long boardId) {
        return boardService.getBoardDetail(boardId);
    }

    @PatchMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Boolean updateBoard(@Validated @RequestBody ReqUpdateBoardDto dto) {
        return boardService.updateBoard(dto);
    }

}
