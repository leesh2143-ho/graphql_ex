package com.example.board.controller;

import com.example.board.dto.BoardDto;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardGraphQlController {

    private final BoardService boardService;

    @QueryMapping
    public Page<BoardDto> boards(
            @Argument String keyword,
            @Argument int page,
            @Argument int size,
            @Argument String sort,
            @Argument Sort.Direction dir) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort));
        return boardService.findAll(keyword, pageable);
    }

    @QueryMapping
    public Optional<BoardDto> board(@Argument Long id) {
        return boardService.findById(id);
    }
}