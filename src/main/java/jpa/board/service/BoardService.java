package jpa.board.service;

import jpa.board.dto.BoardDto;
import jpa.board.entity.Board;
import jpa.board.entity.Member;
import jpa.board.repository.BoardRepository;
import jpa.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    private final FileService fileService;

    @Transactional
    public Long saveBoard(BoardDto boardDto) throws Exception {
        List<Member> memberList = memberRepository.findAll();
        Member member = memberList.get(0);
        Board board = null;

        //insert
        if(boardDto.getId() == null){
            board = boardDto.toEntity(member);
            boardRepository.save(board);
        }

        //update
        else{
            board = boardRepository.findById(boardDto.getId()).get();
            board.update(boardDto.getTitle(), boardDto.getContent());
        }

        //파일 저장
        fileService.saveFile(boardDto, board.getId());

        return board.getId();
    }

    @Transactional
    public Board deleteBoard(Long id){
        Board board = boardRepository.findById(id).get();

        //플래그값이 Y이면 논리삭제
        board.delete("Y");
        return board;
    }

    @Transactional
    public Board selectBoardDetail(Long id){
        Board board = boardRepository.findById(id).get();
        board.updateViewCount(board.getViewCount());
        return board;
    }
}
