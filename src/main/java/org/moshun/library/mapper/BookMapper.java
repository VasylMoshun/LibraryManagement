package org.moshun.library.mapper;

import org.mapstruct.Mapper;
import org.moshun.library.config.MapperConfig;
import org.moshun.library.dto.BookRequestDto;
import org.moshun.library.dto.BookResponseDto;
import org.moshun.library.model.Books;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookResponseDto toDto(Books books);

    Books toModel(BookRequestDto requestDto);
}
