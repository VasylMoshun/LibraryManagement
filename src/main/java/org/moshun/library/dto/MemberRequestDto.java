package org.moshun.library.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class MemberRequestDto {

    @Column(nullable = false, name = "name")
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "membership_date")
    private LocalDateTime membershipDate;
}
