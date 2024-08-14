package org.moshun.library.repository;

import org.moshun.library.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {
    Optional<Books> findByTitleAndAuthor(String title, String author);
    Books findBooksByTitle(String title);
    Books findBooksByAuthor(String author);
}
