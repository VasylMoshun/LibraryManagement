package org.moshun.library.repository;

import org.moshun.library.model.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembersRepository extends JpaRepository<Members, Long> {
    Optional<Members> findByName(String name);
}
