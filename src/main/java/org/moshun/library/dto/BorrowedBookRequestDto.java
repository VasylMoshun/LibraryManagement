package org.moshun.library.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BorrowedBookRequestDto {

    @NotNull
    @Positive
    private Long membersId;

    @NotNull
    @Positive
    private Long BooksId;
}
