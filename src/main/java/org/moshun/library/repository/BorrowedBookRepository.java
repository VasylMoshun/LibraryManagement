package org.moshun.library.repository;

import org.moshun.library.model.Books;
import org.moshun.library.model.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    List<Books> countBorrowedBookByMembersId(Long memberId);

    BorrowedBook findByMembersIdAndBooksId(Long memberId, Long bookId);

    int countByMembersId(Long memberId);

    List<BorrowedBook> findByMembersId(Long memberId);

    int countByMembersIdAndBooksId(Long memberId, Long bookId);

    void deleteBorrowedBookByMembersIdAndBooksId(Long memberId, Long bookId);

    @Query(value = "SELECT bb.books FROM BorrowedBook bb "
            + "JOIN Books b ON bb.books.id = b.id "
            + "JOIN Members m ON bb.members.id = m.id  WHERE bb.members.name = :memberName")
    List<Books> findBorrowedBookByMembers_Name(String memberName);

    int countByBooksId(Long bookId);

}
