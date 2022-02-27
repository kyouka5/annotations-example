package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import service.JsonInclude;

import java.util.List;

@Data
@AllArgsConstructor
public class Book {
    @JsonInclude
    private String author;

    @JsonInclude
    private String title;

    @JsonInclude("year")
    private Integer publicationYear;

    private List<String> genres;
}
