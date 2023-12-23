package jpa.board.repository;

import jpa.board.dto.BoardDto;
import jpa.board.entity.Board;
import jpa.board.entity.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 게시판_등록(){

        //등록된글 다지우기
        boardRepository.deleteAll();

        //given
        String title = "타이틀1";
        String content = "내용1";

        List<Member> memberList = memberRepository.findAll();
        Member member = memberList.get(0);

        BoardDto boardDto = BoardDto.builder()
                            .title(title)
                            .content(content)
                            .build();
        Board board = boardDto.toEntity(member);
        boardRepository.save(board);

        //when
        List<Board> boardList = boardRepository.findAll();

        //then
        Board boards = boardList.get(0);
        assertThat(boards.getTitle()).isEqualTo(title);
        assertThat(boards.getContent()).isEqualTo(content);
    }

}