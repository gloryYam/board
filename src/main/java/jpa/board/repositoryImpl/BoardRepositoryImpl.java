package jpa.board.repositoryImpl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpa.board.dto.BoardDto;
import jpa.board.dto.BoardFileDto;
import jpa.board.dto.QBoardDto;
import jpa.board.dto.QBoardFileDto;
import jpa.board.entity.QBoardFile;
import jpa.board.repository.CustomBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static jpa.board.entity.QBoard.board;
import static jpa.board.entity.QBoardFile.boardFile;
import static jpa.board.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements CustomBoardRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<BoardDto> selectBoardList(String searchVal, Pageable pageable) {
        List<BoardDto> content = getBoardMemberDtos(searchVal, pageable);
        Long count = getCount(searchVal);
        return new PageImpl<>(content, pageable, count);
    }

    @Override
    public List<BoardFileDto> selectBoardFileDetail(Long boardId) {
        List<BoardFileDto> content = jpaQueryFactory
                .select(new QBoardFileDto(
                        boardFile.id
                        ,boardFile.file.id
                        ,boardFile.file.originFileName
                        ,boardFile.file.size
                        ,boardFile.file.extension
                ))
                .from(boardFile)
                .leftJoin(boardFile.file)
                .where(boardFile.boardId.eq(boardId))
                .where(boardFile.delYn.eq("N"))
                .fetch();
        return content;
    }

    private Long getCount(String searchVal){
        Long count = jpaQueryFactory
                .select(board.count())
                .from(board)
                .where(containsSearch(searchVal))
                //.leftJoin(board.member, member)   //검색조건 최적화
                .fetchOne();
        return count;
    }

    private List<BoardDto> getBoardMemberDtos(String searchVal, Pageable pageable){
        List<BoardDto> content = jpaQueryFactory
                .select(new QBoardDto(
                        board.id
                        ,board.title
                        ,board.content
                        ,board.regDate
                        ,board.uptDate
                        ,board.viewCount
                        ,member.username))
                .from(board)
                .leftJoin(board.member, member)
                .where(containsSearch(searchVal))
                .where(board.delYn.eq("N"))
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return content;
    }

    private BooleanExpression containsSearch(String searchVal) {
        return searchVal != null ? board.title.contains(searchVal) : null;
    }
}





























