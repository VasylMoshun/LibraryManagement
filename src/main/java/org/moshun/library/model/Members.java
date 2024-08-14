package org.moshun.library.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE members SET is_deleted = true WHERE id=?")
public class Members {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "membership_date")
    private  LocalDateTime membershipDate;

    @OneToMany
    private List<Books> borrowedBookList;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @PrePersist
    protected void setDate() {
        if (this.membershipDate == null) {
            this.membershipDate = LocalDateTime.now();
        }
    }
}
