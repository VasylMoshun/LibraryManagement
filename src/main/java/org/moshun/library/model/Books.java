package org.moshun.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;

@Data
@Entity
@Table(name = "books")
@SQLDelete(sql = "UPDATE books SET is_deleted = true WHERE id=?")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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


    @Column(name = "is_deleted")
    private boolean isDeleted;
}
