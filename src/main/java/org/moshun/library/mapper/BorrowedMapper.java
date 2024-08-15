package org.moshun.library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.moshun.library.config.MapperConfig;
import org.moshun.library.dto.BorrowedBookResponseDto;
import org.moshun.library.model.BorrowedBook;

@Mapper(config = MapperConfig.class)
public interface BorrowedMapper {
    @Mapping(target = "bookId", source = "books.id")
    @Mapping(target = "bookTitle", source = "books.title")
    @Mapping(target = "bookAuthor", source = "books.author")
    @Mapping(target = "memberId", source = "members.id")
    @Mapping(target = "memberName", source = "members.name")
    BorrowedBookResponseDto toDto(BorrowedBook borrowedBook);
}

