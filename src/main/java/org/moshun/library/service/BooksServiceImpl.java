package org.moshun.library.service;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.moshun.library.dto.BookRequestDto;
import org.moshun.library.dto.BookResponseDto;
import org.moshun.library.mapper.BookMapper;
import org.moshun.library.mapper.MembersMapper;
import org.moshun.library.model.Books;
import org.moshun.library.model.BorrowedBook;
import org.moshun.library.model.Members;
import org.moshun.library.repository.BooksRepository;
import org.moshun.library.repository.BorrowedBookRepository;
import org.moshun.library.repository.MembersRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {
    private final BooksRepository booksRepository;
    private final BookMapper bookMapper;
    private final BorrowedBookRepository borrowedBookRepository;
    private final MembersMapper membersMapper;
    private final MembersRepository membersRepository;

    public BookResponseDto createBook(BookRequestDto requestDto) {
        Optional<Books> byTitleAndAuthor = booksRepository.findByTitleAndAuthor(
                requestDto.getTitle(), requestDto.getAuthor());

        Books book;
        if (byTitleAndAuthor.isPresent()) {
            book = byTitleAndAuthor.get();
            book.setAmount(book.getAmount() + 1);
        } else {
            book = new Books();
            book.setTitle(requestDto.getTitle());
            book.setAuthor(requestDto.getAuthor());
            book.setAmount(requestDto.getAmount());
        }
        booksRepository.save(book);
        return bookMapper.toDto(book);
    }

    public List<BookResponseDto> getAllBooks(Pageable pageable) {
        return booksRepository.findAll().stream().map(bookMapper::toDto).toList();
    }

    public BookResponseDto updateBook(BookRequestDto requestDto, Long id) {
        Optional<Books> byId = booksRepository.findById(id);
        if (byId.isPresent()) {
            Books books = byId.get();
            books.setTitle(requestDto.getTitle());
            books.setAuthor(requestDto.getAuthor());
            books.setAmount(requestDto.getAmount());
            booksRepository.save(books);
        }
        return bookMapper.toDto(byId.orElseThrow(
                () -> new NoSuchElementException("No such element")));
    }

    public void deleteById(Long id) {
        booksRepository.findById(id).ifPresent(booksRepository::delete);
    }

    @Value("${book.limit}")
    private int borrowLimit;

    public void borrowBook(Long memberId, Long bookId) {
        Optional<Books> bookById = booksRepository.findById(bookId);
        Optional<Members> membersById = membersRepository.findById(memberId);

        if (membersById.isPresent() && bookById.isPresent()) {
            Books book = bookById.get();
            Members member = membersById.get();

            if (borrowedBookRepository.countBorrowedBookByMembersId(memberId).size() >= borrowLimit) {
                throw new IllegalStateException("Member has already borrowed the maximum number of books.");
            }

            if (book.getAmount() <= 0) {
                throw new IllegalStateException("No available copies of this book to borrow.");
            }

            BorrowedBook borrowedBook = new BorrowedBook();
            borrowedBook.setBooks(book);
            borrowedBook.setMembers(member);
            borrowedBookRepository.save(borrowedBook);

            book.setAmount(book.getAmount() - 1);
            booksRepository.save(book);
        } else {
            throw new IllegalArgumentException("Member or Book not found.");
        }
    }

    public void returnBook(Long memberId, Long bookId) {
        Optional<Books> bookById = booksRepository.findById(bookId);
        Optional<Members> membersById = membersRepository.findById(memberId);

        if (membersById.isPresent() && bookById.isPresent()) {
            Books book = bookById.get();
            Members member = membersById.get();
            book.setAmount(book.getAmount() + 1);
            List<Books> borrowedBookList = member.getBorrowedBookList();
            borrowedBookList.remove(book);
            booksRepository.save(book);
            membersRepository.save(member);
        }
    }
}

