package org.moshun.library.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.moshun.library.model.Books;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String name;
    private List<Books> borrowedBookList;
    private LocalDateTime membershipDate;
}
