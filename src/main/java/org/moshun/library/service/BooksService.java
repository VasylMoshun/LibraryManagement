package org.moshun.library.service;

import org.moshun.library.dto.BookRequestDto;
import org.moshun.library.dto.BookResponseDto;
import org.moshun.library.dto.BorrowedBookRequestDto;
import org.moshun.library.dto.BorrowedBookResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BooksService {
    BookResponseDto createBook(BookRequestDto requestDto);

    List<BookResponseDto> getAllBooks(Pageable pageable);

    BookResponseDto updateBook(BookRequestDto requestDto, Long id);

    void deleteById(Long id);

    BorrowedBookResponseDto borrowBook(BorrowedBookRequestDto requestDto);

    void returnBook(BorrowedBookRequestDto requestDto);
}
