package org.moshun.library.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.moshun.library.dto.BookRequestDto;
import org.moshun.library.dto.BookResponseDto;
import org.moshun.library.dto.BorrowedBookRequestDto;
import org.moshun.library.dto.BorrowedBookResponseDto;
import org.moshun.library.mapper.BookMapper;
import org.moshun.library.service.BooksService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final BookMapper bookMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Tag(name = "Get all books", description = "Controller to get all books")
    public List<BookResponseDto> getAllBooks(Pageable pageable) {
        return booksService.getAllBooks(pageable);
    }

    @PostMapping
    @Tag(name = "Create book", description = "Controller to create new book")
    public BookResponseDto createBook(@RequestBody @Valid BookRequestDto requestDto) {
        return booksService.createBook(requestDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Tag(name = "book update",description = "Controller for updating books")
    public BookResponseDto updateBook(
            @RequestBody @Valid BookRequestDto requestDto, @PathVariable Long id) {
        return booksService.updateBook(requestDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Tag(name = "delete book", description = "Controller to delet book")
    public void deleteBook(@PathVariable Long id) {
        booksService.deleteById(id);
    }

    @PostMapping("/borrowed")
    @ResponseStatus(HttpStatus.OK)
    @Tag(name = "Borrow book", description = "Member can borrow book")
    public ResponseEntity<BorrowedBookResponseDto> borrowBook
            (@RequestBody @Valid BorrowedBookRequestDto requestDto) {
        return ResponseEntity.ok(booksService.borrowBook(requestDto));
    }

    @PostMapping("/return")
    @Tag(name = "Return borrowed book", description = "Controller to return book")
    public void returnBook(@RequestBody @Valid BorrowedBookRequestDto requestDto) {
        booksService.returnBook(requestDto);
    }
}
