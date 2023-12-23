package jpa.board.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * jpa.board.dto.QBoardDto is a Querydsl Projection type for BoardDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QBoardDto extends ConstructorExpression<BoardDto> {

    private static final long serialVersionUID = 669549411L;

    public QBoardDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<java.time.LocalDateTime> regDate, com.querydsl.core.types.Expression<java.time.LocalDateTime> uptDate, com.querydsl.core.types.Expression<Long> viewCount, com.querydsl.core.types.Expression<String> username) {
        super(BoardDto.class, new Class<?>[]{long.class, String.class, String.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, long.class, String.class}, id, title, content, regDate, uptDate, viewCount, username);
    }

}

