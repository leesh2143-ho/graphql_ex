package com.example.board.config;

import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BoardRepository boardRepository;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 1; i <= 20; i++) {
            boardRepository.save(Board.builder()
                    .title("Title " + i)
                    .content("Content " + i)
                    .author("Author " + i)
                    .build());
        }
    }
}