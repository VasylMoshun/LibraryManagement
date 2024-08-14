package org.moshun.library.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;

import org.moshun.library.dto.BookRequestDto;
import org.moshun.library.dto.BookResponseDto;
import org.moshun.library.mapper.BookMapper;
import org.moshun.library.service.BooksService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final BookMapper bookMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponseDto> getAllBooks(Pageable pageable) {
        return booksService.getAllBooks(pageable);
    }

    @PostMapping
    public BookResponseDto createBook(@RequestBody @Valid BookRequestDto requestDto) {
        return booksService.createBook(requestDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponseDto updateBook(
            @RequestBody @Valid BookRequestDto requestDto, @PathVariable Long id) {
        return booksService.updateBook(requestDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        booksService.deleteById(id);
    }

    @PostMapping("/borrowed")
    @ResponseStatus(HttpStatus.OK)
    public void borrowBook(@RequestParam Long bookId, @RequestParam Long memberId) {
        booksService.borrowBook(memberId, bookId);
    }
}
