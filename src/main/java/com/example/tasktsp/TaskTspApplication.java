package com.example.tasktsp;

import com.example.tasktsp.repository.exception.ReadFileException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
@RequiredArgsConstructor
public class TaskTspApplication implements CommandLineRunner {
    private final JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(TaskTspApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            String sqlScript = StreamUtils.copyToString(
                    new ClassPathResource("sql-init.sql").getInputStream(), StandardCharsets.UTF_8);

            jdbcTemplate.execute(sqlScript);
        } catch (IOException e) {
            throw new ReadFileException("Could not read file");
        }
    }
}
