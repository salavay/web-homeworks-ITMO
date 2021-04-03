package ru.itmo.wp.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(
    indexes = @Index(columnList =  "creationTime")
)

public class Notice {
    @Id
    @GeneratedValue
    private long id;

    @Lob
    @NotNull
    @NotEmpty
    private String content;

    @CreationTimestamp
    private Date creationTime;

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getCreationTime() {
        return creationTime;
    }
}
