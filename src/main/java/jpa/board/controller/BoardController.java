package jpa.board.controller;

import jakarta.validation.Valid;
import jpa.board.dto.BoardDto;
import jpa.board.entity.Board;
import jpa.board.repository.CustomBoardRepository;
import jpa.board.service.BoardService;
import jpa.board.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final CustomBoardRepository customBoardRepository;
    private final BoardService boardService;
    private final FileService fileService;

    /**
     * 목록 화면
     */
    @GetMapping("/")
    public String list(String searchVal, Pageable pageable, Model model){
        Page<BoardDto> results = customBoardRepository.selectBoardList(searchVal, pageable);

        model.addAttribute("list", results);
        model.addAttribute("maxPage", 5);
        model.addAttribute("searchVal", searchVal);

        pageModelPut(results, model);
        return "board/list";
    }

    /**
     * PageNation 관련 값 model 에 넣기
     */
    private void pageModelPut(Page<BoardDto> results, Model model){
        model.addAttribute("totalCount", results.getTotalElements());
        model.addAttribute("size",  results.getPageable().getPageSize());
        model.addAttribute("number",  results.getPageable().getPageNumber());
    }

    /**
     * 글쓰기 화면
     */
    @GetMapping("/write")
    public String write(Model model) {

        model.addAttribute("boardDto", new BoardDto());

        return "board/write";
    }

    @PostMapping("/write")
    public String save(@Valid BoardDto boardDto, BindingResult result) throws Exception {

        if(result.hasErrors()) {
            return "board/write";
        }

        boardService.saveBoard(boardDto);
        return "redirect:/";
    }

    /**
     * 게시판 수정화면
     */
    @GetMapping("/update/{boardId}")
    public String detail(@PathVariable Long boardId, Model model){
        Board board = boardService.selectBoardDetail(boardId);

        BoardDto boardDto = BoardDto.builder()
                .id(boardId)
                .title(board.getTitle())
                .content(board.getContent())
                .build();
        model.addAttribute("boardDto", boardDto);
        model.addAttribute("boardFile", customBoardRepository.selectBoardFileDetail(boardId));

        return "board/update";
    }

    /**
     * 게시판 수정
     */
    @PutMapping("/update/{boardId}")
    public String update(@Valid BoardDto boardDto, BindingResult result) throws Exception {
        //유효성검사 걸릴시
        if(result.hasErrors()){
            return "board/update";
        }

        boardService.saveBoard(boardDto);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam List<String> boardIds) {

        for(int i = 0; i < boardIds.size(); i++) {
            Long id = Long.valueOf(boardIds.get(i));
            boardService.deleteBoard(id);
        }

        return "redirect:/";
    }

    @PostMapping("/boardFileDelete")
    public String boardFileDelete(@RequestParam Long fileId, @RequestParam Long boardId) {

        // 게시판 파일 삭제
        fileService.deleteBoardFile(fileId);

        return "redirect:/update/"+boardId;
    }

}
