package org.moshun.library.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BookRequestDto {
    @Column(nullable = false, name = "title")
    @Size(min = 3)
    @Pattern(regexp = "^[A-Z].*$", message = "Title should start with a capital letter")
    private String title;

    @Pattern(regexp = "^[A-Z][a-z]+ [A-Z][a-z]+$",
            message = "Author should contain name and surname with capital letters")
    @Column(nullable = false, name = "author")
    private String author;

    @Min(0)
    @Column(nullable = false, name = "amount")
    private Long amount;
}
