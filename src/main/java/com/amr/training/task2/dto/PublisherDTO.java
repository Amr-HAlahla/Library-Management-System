package com.amr.training.task2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDTO {
    private Long id;
    private String name;
    private LocalDate establishedDate;
}
