package io.dkargo.munzi.board.service;

import io.dkargo.munzi.board.dto.request.ReqCreateBoardDto;
import io.dkargo.munzi.board.dto.request.ReqUpdateBoardDto;
import io.dkargo.munzi.board.dto.response.*;
import io.dkargo.munzi.board.entity.Board;
import io.dkargo.munzi.board.entity.PageRequest;
import io.dkargo.munzi.board.entity.User;
import io.dkargo.munzi.board.error.DkargoException;
import io.dkargo.munzi.board.error.ErrorCode;
import io.dkargo.munzi.board.repository.BoardRepository;
import io.dkargo.munzi.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    
    public ResCreateBoardDto createBoard(ReqCreateBoardDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new DkargoException(ErrorCode.USER_NOT_FOUND));

        Board board = new Board(dto, user);
        boardRepository.save(board);

        return new ResCreateBoardDto(board.getId());
    }

    public ResGetBoardListDto getBoardList(int page, int size) {
        PageRequest pr = new PageRequest(page, size, Sort.Direction.DESC);
        List<Board> boardList = boardRepository.findAll(pr.of()).getContent();

        long count = boardRepository.count();
        int totalPage = (int) ((count + size - 1) / size);
        return new ResGetBoardListDto(boardList, page, size, totalPage);
    }

    public ResGetBoardDetailDto getBoardDetail(long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new DkargoException(ErrorCode.BOARD_NOT_FOUND));

        return new ResGetBoardDetailDto(board);
        
    }

    public boolean updateBoard(ReqUpdateBoardDto dto) {
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new DkargoException(ErrorCode.BOARD_NOT_FOUND));

        board.changeTitle(dto.getTitle());

        return true;
    }
}
