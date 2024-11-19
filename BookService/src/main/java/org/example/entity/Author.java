package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PUBLIC)
@Table(name = Author.TABLE_NAME)
public class Author {
    public static final String TABLE_NAME = "authors";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "author_id", updatable = false, nullable = false)
    private UUID authorId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "pen_name")
    private String penName;
}
