package com.example.board.service;

import com.example.board.dto.BoardDto;
import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public Page<BoardDto> findAll(String keyword, Pageable pageable) {
        if (StringUtils.hasText(keyword)) {
            return boardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable).map(BoardDto::from);
        }
        return boardRepository.findAll(pageable).map(BoardDto::from);
    }

    public Optional<BoardDto> findById(Long id) {
        return boardRepository.findById(id).map(BoardDto::from);
    }
}