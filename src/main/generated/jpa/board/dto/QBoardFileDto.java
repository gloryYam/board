package jpa.board.dto;

import com.querydsl.core.types.Expression;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.dsl.NumberPath;

import javax.annotation.processing.Generated;

/**
 * jpa.board.dto.QBoardFileDto is a Querydsl Projection type for BoardFileDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QBoardFileDto extends ConstructorExpression<BoardFileDto> {

    private static final long serialVersionUID = -2039614905L;

    public QBoardFileDto(Expression<Long> boardFileId, NumberPath<Long> id, Expression<String> originFileName, Expression<Long> size, Expression<String> extension) {
        super(BoardFileDto.class, new Class<?>[]{long.class, long.class, String.class, long.class, String.class}, boardFileId, originFileName, size, extension);
    }

}

