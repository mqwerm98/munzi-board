package io.dkargo.munzi.board.repository;

import io.dkargo.munzi.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
