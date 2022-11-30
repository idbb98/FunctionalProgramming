package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Book {
    private Long id;
    private String category;    //"哲学"，"哲学，爱情"
    private String name;
    private Double score;
    private String introduction;

}
