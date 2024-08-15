package org.moshun.library.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BorrowedBookResponseDto {
    private Long memberId;
    private String memberName;
    private String bookTitle;
    private String bookAuthor;
    private Long bookId;

}
