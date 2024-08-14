package org.moshun.library.service;

import org.moshun.library.dto.BookRequestDto;
import org.moshun.library.dto.BookResponseDto;
import org.moshun.library.model.Books;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BooksService {
    public BookResponseDto createBook(BookRequestDto requestDto);

    public List<BookResponseDto> getAllBooks(Pageable pageable);

    public BookResponseDto updateBook(BookRequestDto requestDto, Long id);

    public void deleteById(Long id);

    public void borrowBook(Long memberId, Long bookId);

    public void returnBook(Long memberId, Long bookId);
}
