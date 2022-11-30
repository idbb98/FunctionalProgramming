package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Author implements Comparable<Author>{
    private Long id;
    private String name;
    private String introduction;
    private Integer age;
    private List<Book> bookList;

    @Override
    public int compareTo(Author o) {
        return this.getAge()-o.getAge();        //更换前后位置实现降序或升序
    }
}
