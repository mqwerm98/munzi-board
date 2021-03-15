package io.dkargo.munzi.board.controller;

import io.dkargo.munzi.board.dto.request.ReqCreateBoardDto;
import io.dkargo.munzi.board.dto.request.ReqCreateUserDto;
import io.dkargo.munzi.board.dto.request.ReqUpdateBoardDto;
import io.dkargo.munzi.board.dto.request.ReqUpdateUserDto;
import io.dkargo.munzi.board.dto.response.*;
import io.dkargo.munzi.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("")
    public ResponseEntity<ResCreateBoardDto> createBoard(@Validated @RequestBody ReqCreateBoardDto dto) {
        ResCreateBoardDto result = boardService.createBoard(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public ResponseEntity<ResGetBoardListDto> getBoardList(@RequestParam("page") int page, @RequestParam("size") int size) {
        ResGetBoardListDto result = boardService.getBoardList(page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ResGetBoardDetailDto> getBoardDetail(@PathVariable("boardId") long userId) {
        ResGetBoardDetailDto result = boardService.getBoardDetail(userId);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("")
    public ResponseEntity<Boolean> updateBoard(@Validated @RequestBody ReqUpdateBoardDto dto) {
        boolean result = boardService.updateBoard(dto);
        return ResponseEntity.ok(result);
    }

}