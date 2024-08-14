package org.moshun.library.repository;

import org.moshun.library.model.BorrowedBook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    List<BorrowedBook> countBorrowedBookByMembersId(Long id);
}
