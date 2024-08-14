package org.moshun.library.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private Long amount;
}
