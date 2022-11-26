package com.example.blogapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blogUsers")
public class BlogUsers implements Serializable {
    @Id
    @SequenceGenerator(name = "seq_blogUsers", allocationSize = 1)
    @GeneratedValue(generator = "seq_blogUsers", strategy = GenerationType.SEQUENCE)
    @Column(name = "blogUser_id")
    private long userId;

    @Column(name = "blogUser_name", nullable = false, columnDefinition = "TEXT")
    private String userName;
}
