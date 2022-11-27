package com.example.blogapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blogPosts")
public class BlogPosts implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "seq_blogPosts", allocationSize = 1)
    @GeneratedValue(generator = "seq_blogPosts", strategy = GenerationType.SEQUENCE)
    @Column(name = "blogPosts_id")
    private long blogId;

    @NotEmpty(message = "blog title cannot be empty")
    @Column(name = "blogPosts_title", nullable = false, columnDefinition = "TEXT")
    private String blogTitle;

    @NotEmpty(message = "blog content cannot be empty")
    @Column(name = "blogPosts_content", nullable = false, columnDefinition = "TEXT")
    private String blogContent;

    @Lob
    @Column(name = "blogImage", columnDefinition = "LONGBLOB")
    private byte[] blogImage;

    @Column(name = "blogPosts_CreatedDate", nullable = false)
    private LocalDate blogCreatedDate = LocalDate.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blogUser_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    BlogUsers blogUsers;
}
