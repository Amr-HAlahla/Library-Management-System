package com.amr.training.securitydemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String isbn;
    private LocalDate publishedDate;
    private Integer pages;
    private AuthorDTO author;
    private PublisherDTO publisher;
}
