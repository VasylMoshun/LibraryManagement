package org.moshun.library.service;

import lombok.RequiredArgsConstructor;
import org.moshun.library.dto.BookRequestDto;
import org.moshun.library.dto.BookResponseDto;
import org.moshun.library.dto.BorrowedBookRequestDto;
import org.moshun.library.dto.BorrowedBookResponseDto;
import org.moshun.library.mapper.BookMapper;
import org.moshun.library.mapper.BorrowedMapper;
import org.moshun.library.mapper.MembersMapper;
import org.moshun.library.model.Books;
import org.moshun.library.model.BorrowedBook;
import org.moshun.library.model.Members;
import org.moshun.library.repository.BooksRepository;
import org.moshun.library.repository.BorrowedBookRepository;
import org.moshun.library.repository.MembersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {
    private final BooksRepository booksRepository;
    private final BookMapper bookMapper;
    private final BorrowedBookRepository borrowedBookRepository;
    private final MembersMapper membersMapper;
    private final MembersRepository membersRepository;
    private final BorrowedMapper borrowedMapper;

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

    public BorrowedBookResponseDto borrowBook(BorrowedBookRequestDto requestDto) {
        checkBorrowLimit(requestDto.getMembersId());

        Optional<Books> bookById = booksRepository.findById(requestDto.getBooksId());
        Optional<Members> membersById = membersRepository.findById(requestDto.getMembersId());

        if (membersById.isPresent() && bookById.isPresent()) {
            checkBookAmount(bookById.get());
        }

        Books book = bookById.get();
        Members member = membersById.get();

        checkBookAmount(bookById.get());

        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setBooks(book);
        borrowedBook.setMembers(member);
        book.setAmount(book.getAmount() - 1);
        booksRepository.save(book);
        borrowedBookRepository.save(borrowedBook);
        return borrowedMapper.toDto(borrowedBook);
    }

    @Transactional
    public void returnBook(BorrowedBookRequestDto requestDto) {
        BorrowedBook byMembersIdAndBooksId = borrowedBookRepository
                .findByMembersIdAndBooksId(requestDto.getMembersId(), requestDto.getBooksId());
        Optional<Books> bookById = booksRepository.findById(requestDto.getBooksId());
        Optional<Members> membersById = membersRepository.findById(requestDto.getMembersId());

        if (membersById.isPresent() && bookById.isPresent()) {
            Books book = bookById.get();
            Members member = membersById.get();
            book.setAmount(book.getAmount() + 1);
            booksRepository.save(book);
            membersRepository.save(member);
            borrowedBookRepository.delete(byMembersIdAndBooksId);
        }
    }

    private void checkBorrowLimit(Long memberId) {
        if (borrowedBookRepository.countByMembersId(memberId) >= borrowLimit) {
            throw new IllegalStateException("Member with ID: " + memberId + " exceeded borrow limit");
        }
    }

    private void checkBookAmount(Books book) {
        if (book.getAmount() == 0) {
            throw new IllegalStateException("No available copies of this book to borrow"
                    + book.getId());
        }
    }
}

