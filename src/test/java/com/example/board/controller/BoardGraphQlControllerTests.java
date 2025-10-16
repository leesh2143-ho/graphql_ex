package com.example.board.controller;

import com.example.board.dto.BoardDto;
import com.example.board.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@GraphQlTest(BoardGraphQlController.class)
public class BoardGraphQlControllerTests {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private BoardService boardService;

    @BeforeEach
    void setUp() {
        BoardDto boardDto = BoardDto.builder()
                .id(1L)
                .title("Test Title")
                .content("Test Content")
                .author("Test Author")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(boardService.findAll(any(), any()))
                .thenReturn(new PageImpl<>(Collections.singletonList(boardDto), PageRequest.of(0, 10), 1));
        when(boardService.findById(1L)).thenReturn(Optional.of(boardDto));
    }

    @Test
    void testBoardsQuery() {
        String query = """
                query {
                  boards(page: 0, size: 10, sort: "createdAt", dir: DESC) {
                    content {
                      id
                      title
                    }
                  }
                }
                """;

        graphQlTester.document(query)
                .execute()
                .path("boards.content")
                .entityList(Object.class)
                .hasSize(1);
    }

    @Test
    void testBoardQuery() {
        String query = """
                query($id: ID!) {
                  board(id: $id) {
                    id
                    title
                  }
                }
                """;

        graphQlTester.document(query)
                .variable("id", 1)
                .execute()
                .path("board.title")
                .entity(String.class)
                .isEqualTo("Test Title");
    }
}