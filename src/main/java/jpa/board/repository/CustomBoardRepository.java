package jpa.board.repository;

import jpa.board.dto.BoardDto;
import jpa.board.dto.BoardFileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomBoardRepository {
    Page<BoardDto> selectBoardList(String searchVal, Pageable pageable);

    List<BoardFileDto> selectBoardFileDetail(Long boardId);
}
