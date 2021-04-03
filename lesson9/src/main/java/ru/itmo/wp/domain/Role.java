package ru.itmo.wp.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
@Data
public class Role {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Name name;

    /**
     * @noinspection unused
     */
    public Role() {
    }

    public Role(@NotNull Name name) {
        this.name = name;
    }

    public enum Name {
        WRITER,
        ADMIN
    }
}
