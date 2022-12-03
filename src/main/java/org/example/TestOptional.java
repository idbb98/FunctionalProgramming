package org.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TestOptional {


    public static void main(String[] args) {


    }

    @Test
    public void test07() {
        Optional<Author> authorOptional = getAuthorOptional();
        authorOptional.map(author -> author.getBookList())
                .ifPresent(books -> System.out.println(books));

    }

    @Test
    public void test06() {
        Optional<Author> authorOptional = getAuthorOptional();
        if (authorOptional.isPresent()) {
            System.out.println(authorOptional.get().getName());
        }

    }

    @Test
    public void test05() {
        Optional<Author> authorOptional = getAuthorOptional();
        Optional<Author> optional = authorOptional.filter(author -> author.getAge() > 18);

    }

    @Test
    public void test04() throws Throwable {
        Optional<Author> authorOptional = getAuthorOptional();
        Author author = authorOptional.orElseThrow(new Supplier<Throwable>() {
            @Override
            public Throwable get() {
                return new RuntimeException("数据为null");
            }
        });
        System.out.println(author);

    }

    @Test
    public void test03() {
        Optional<Author> authorOptional1 = getAuthorOptional();

        Author author = authorOptional1.orElseGet(new Supplier<Author>() {
            @Override
            public Author get() {
                // 设置如果 authorOptional1为对象 null,返回的默认对象
                return new Author(1L, "default", "my introduction 1", 18, null);
            }
        });

        // lambda
//        Author author = authorOptional1.orElseGet(() -> {
//            // 设置如果 authorOptional1为对象 null,返回的默认对象
//            return new Author(1L, "default", "my introduction 1", 18, null);
//        });
        System.out.println(author.getName());
    }

    @Test
    public void test02() {
        Optional<Author> authorOptional1 = getAuthorOptional();
        authorOptional1.ifPresent(author -> author.getName());
    }

    @Test
    public void test01() {
        Author author = getAuthor();
        Optional<Author> authorOptional = Optional.ofNullable(author);
        authorOptional.ifPresent(author1 -> System.out.println(author1.getName()));

    }

    private static Optional<Author> getAuthorOptional() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(3L, "喜剧", "书名3", 83D, "这是简介哦"));
        Author author = new Author(1L, "tom", "my introduction 1", 18, books);
        return Optional.ofNullable(author);
        //return Optional.ofNullable(null);
    }

    private static Author getAuthor() {
        Author author = new Author(1L, "tom", "my introduction 1", 18, null);
        return null;
    }


}
