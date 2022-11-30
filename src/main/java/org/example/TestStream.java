package org.example;


import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@SuppressWarnings("ALL")
public class TestStream {
    public static void main(String[] args) {
        /**
         * 1. 创建流
         * 2. 中间操作
         * 3. 终结操作
         */
//        HashMap<String, Integer> hashMap = new HashMap<>();
//        hashMap.put("tom", 12);
//        hashMap.put("jack", 16);
//        Set<Map.Entry<String, Integer>> entrySet = hashMap.entrySet();
//        Stream<Map.Entry<String, Integer>> stream = entrySet.stream();
//        stream.filter(entry -> entry.getValue() > 12)
//                .forEach(entry -> System.out.println(entry.getKey() + "---" + entry.getValue()));

        List<Author> authors = getAuthors();
        // 打印现有数据的所有分类，要求对分类去重，不能出现格式：哲学，爱情
        authors.stream()
                .flatMap(author -> {
                    return author.getBookList().stream();
                })
                .distinct()         // 书籍去重
                //"哲学，爱情" =》"哲学“ ”爱情"       数组创建流Arrays.stream()
                .flatMap(new Function<Book, Stream<String>>() {
                    @Override
                    public Stream<String> apply(Book book) {
                        return Arrays.stream(book.getCategory().split(","));    // 数组创建流Arrays.stream()
                    }
                })
                .distinct()         // 分类去重
                .forEach(category -> System.out.println(category));

        authors.stream()
                .flatMap(author -> author.getBookList().stream())
                .distinct()         // 书籍去重
                //"哲学，爱情" =》"哲学“ ”爱情"       数组创建流Arrays.stream()
                .flatMap(book -> Arrays.stream(book.getCategory().split(",")))
                .distinct()         // 分类去重
                .forEach(category -> System.out.println(category));



//        // 打印书籍名字，去重
//        authors.stream()
//                .flatMap(new Function<Author, Stream<Book>>() {     // 转成Stream<Book>
//                    @Override
//                    public Stream<Book> apply(Author author) {
//                        return author.getBookList().stream();
//                    }
//                })
//                .distinct()
//                .forEach(new Consumer<Book>() {
//                    @Override
//                    public void accept(Book book) {
//                        System.out.println(book.getName());
//                    }
//                });
//        // lambda
//        authors.stream()
//                .flatMap(author -> author.getBookList().stream())       // 转成Stream<Book>
//                .distinct()
//                .forEach(book -> System.out.println(book.getName()));

        // 打印除了年龄最大的作家外的其他作家，并且不能重复，按年龄降序
//        authors.stream()
//                .distinct()
//                .sorted(((o1, o2) -> o2.getAge() - o1.getAge()))
//                .skip(1)
//                .forEach(author -> System.out.println(author));

        // 对流中元素按年龄降序，并且不能重复，打印年龄最大的俩个作家名字
//        authors.stream()
//                .distinct()
//                .sorted(((o1, o2) -> o2.getAge() - o1.getAge()))
//                .limit(2)
//                .forEach(author -> System.out.println(author.getName()));


        //按年龄排序，不能重复
        // 1. 空参sorted()方法
//        authors.stream()
//                .distinct()
//                // 此方式需要Author类implements Comparable重写compareTo方法，否则报ClassCastException
//                // 实现降序或升序依赖compareTo方法
//                .sorted()
//                .forEach(author -> System.out.println(author.getAge()));
//        // 2. 有参sorted()方法
//        authors.stream()
//                .distinct()
//                .sorted(new Comparator<Author>() {
//                    @Override
//                    public int compare(Author o1, Author o2) {
//                        return o1.getAge() - o2.getAge();
//                    }
//                })
//                .forEach(author -> System.out.println(author.getAge()));
//
//        authors.stream()
//                .distinct()
//                .sorted((o1, o2) -> o1.getAge() - o2.getAge())
//                .forEach(author -> System.out.println(author.getAge()));

        //打印所以作家姓名,去重(只判断名字)
//        authors.stream()
//                .map(author -> author.getName())
//                .distinct()
//                .forEach(name -> System.out.println(name));

//        authors.stream()
//                .map(new Function<Author, String>() {   //Author类型转成String
//                    @Override
//                    public String apply(Author author) {
//                        return author.getName();
//                    }
//                })
//                .forEach(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) {
//                        System.out.println(s);
//                    }
//                });
//        authors.stream()
//                .map( author -> author.getName())       //Author类型转成String
//                .forEach(s -> System.out.println(s));
//
//        authors.stream()
//                .map(author -> author.getAge())
//                .map(age -> age+10)
//                .forEach(age -> System.out.println(age));
//        //打印姓名长度大于1的作家姓名
//        authors.stream()
//                .filter(new Predicate<Author>() {
//                    @Override
//                    public boolean test(Author author) {
//                        return author.getName().length() > 1;
//                    }
//                })
//                .forEach(new Consumer<Author>() {
//                    @Override
//                    public void accept(Author author) {
//                        System.out.println(author.getName());
//                    }
//                });
//
//        authors.stream()
//                .filter(author -> author.getName().length()>1)
//                .forEach(author -> System.out.println(author.getName()));


        // 1.年龄小于18岁的作家名字并且去重
//        authors.stream()    //集合转成流
//                .distinct() //去重 equals hash
//                .filter(new Predicate<Author>() {
//                    @Override
//                    public boolean test(Author author) {
//                        return author.getAge() < 18;
//                    }
//                })
//                .forEach(new Consumer<Author>() {
//                    @Override
//                    public void accept(Author author) {
//                        System.out.println(author.getName());
//                    }
//                });

//        authors.stream()
//                .distinct()
//                .filter(author -> author.getAge() < 18)
//                .forEach(author -> System.out.println(author.getName()));


        // 2.简化写法
        //test5(authors);


        // 3.数组转stream，前面是list集合
        //test4();

        // 4.map转stream 得先转换成单列集合才行
        //test3();

        //test();

        //test1();

        //test2();

//        test7();
//
//        test8();
//
//        test9();
//
//        test10();
//
//        test11();
//
//        test12();
//
//        test13();
//
//        test14();
//
//        test15();
//
//        test16();
//
//        test17();
//
//        test18();
//
//        test19();
    }

    /**
     * reduce的一个参数使用
     * {
     * boolean foundAny = false;
     * T result = null;
     * for (T element : this stream) {
     * // 相比于两个参数的重载形式，这里把第一个元素赋值给result
     * if (!foundAny) {
     * foundAny = true;
     * result = element;
     * }
     * else
     * // 第二个元素开始就开始数据操作
     * result = accumulator.apply(result, element);
     * }
     * // 如果没有一个元素就返回空
     * return foundAny ? Optional.of(result) : Optional.empty();
     * }
     * 同样求作者中的最小年龄
     */
    private static void test19() {
        Optional<Integer> reduce = getAuthors().stream()
                .distinct()
                .map(author -> author.getAge())
                .reduce((result, element) -> result < element ? result : element);
        System.out.println("单个参数的reduce方法使用，作者中年龄最小的是：" + reduce.get());
    }

    /**
     * 使用reduce求所有作者中年龄的最大值 最小值  两个参数
     */
    private static void test18() {
        Integer maxValue = getAuthors().stream()
                .distinct()
                .map(author -> author.getAge())
                .reduce(Integer.MIN_VALUE, new BinaryOperator<Integer>() {
                    @Override
                    public Integer apply(Integer result, Integer element) {
                        return result > element ? result : element;
                    }
                });
        System.out.println("利用reduce计算出来的作者年龄最大值是：" + maxValue);

        Integer minValue = getAuthors().stream()
                .distinct()
                .map(author -> author.getAge())
                // 这个Integer.MAX_VALUE就是result的初始赋值吧
                .reduce(Integer.MAX_VALUE, (result, element) -> result > element ? element : result);
        System.out.println("利用reduce计算出来的作者年龄最小值是：" + minValue);
    }

    /**
     * 最难的 reduce 归并
     * 对流中的数据按照你制定的计算方式计算出一个结果
     * reduce的作用是把stream的元素给组合起来，
     * 可以传一个初始值，他会按照我们的计算方式依次拿流中的元素和在初始值的基础上
     * 进行计算，计算结果再和后面的元素计算
     * <p>
     * 使用reduce求所有作者的年龄和
     */
    private static void test17() {
        Integer reduce = getAuthors().stream()
                .map(author -> author.getAge())
                // .reduce(0, (result, element) -> result + element)
                .reduce(0, new BinaryOperator<Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) {
                        return integer + integer2;
                    }
                });

        System.out.println("用reduce求得所有作者年龄和是:" + reduce);
    }

    /**
     * findFirst 获取一个年龄最小的作家，并输出他的名字
     */
    private static void test16() {
        Optional<Author> first = getAuthors().stream().sorted((o1, o2) -> (o1.getAge() - o2.getAge()))
                .findFirst();
        System.out.println("年龄最小的一个作家：" + first.get().getName());
    }

    /**
     * findAny 获取任意一个年龄大于18的作家，如果存在就输出他的名字
     */
    private static void test15() {
        Optional<Author> any = getAuthors().stream()
                .distinct()
                .filter(author -> author.getAge() > 18)
                .findAny();
        // any是一个对象，任意匹配到的一个对象
        System.out.println("任意一个大于18岁作家的名字：" + any.get().getName());
        System.out.println("我比较好奇的是，好像每次输出的都是同一个对象值");
    }

    /**
     * noneMatch 全都不符合
     */
    private static void test14() {
        boolean noneMatch = getAuthors().stream()
                .noneMatch(author -> author.getAge() > 60);
        System.out.println("所有作家都是退休的了:" + (noneMatch ? "是" : "否"));
    }

    /**
     * allMatch 判断所有作家都是成年人
     */
    private static void test13() {
        boolean allMatch = getAuthors().stream()
                .allMatch(author -> author.getAge() > 18);
        System.out.println("所有作家都是成年人:" + (allMatch ? "是" : "否"));
    }

    /**
     * anyMatch 判断是否有年龄在29以上的作家
     */
    private static void test12() {
        boolean anyMatch = getAuthors().stream()
                .anyMatch(author -> author.getAge() > 29);
        System.out.println("是否有年龄在29以上的作家:" + (anyMatch ? "是" : "否"));
    }

    /**
     * 获取所有作者名字合集list, set, map
     */
    private static void test11() {
        System.out.println("转换成list集合");
        List<Book> bookList = getAuthors().stream().flatMap(author -> author.getBookList().stream())
                .collect(Collectors.toList());
        for (Book book : bookList) {
            System.out.println(book.toString());
        }

        System.out.println("转换成set");
        Set<Book> bookSet = getAuthors().stream()
                .flatMap(author -> author.getBookList().stream())
                .collect(Collectors.toSet());
        for (Book book : bookSet) {
            System.out.println(book.toString());
        }

        System.out.println("转换成map");
        Map<String, Book> map = getAuthors().stream()
                .flatMap(author -> author.getBookList().stream())
                .distinct()
                .collect(Collectors.toMap(book -> book.getName(), book -> book)); // 第一个是key,第二个是value
        map.entrySet().stream()
                .forEach(new Consumer<Map.Entry<String, Book>>() {
                    @Override
                    public void accept(Map.Entry<String, Book> stringBookEntry) {
                        System.out.println(stringBookEntry.getKey() + ":" + stringBookEntry.getValue());
                    }
                });

        System.out.println("list的大小是：" + bookList.size());
        System.out.println("set的大小是：" + bookSet.size());
        System.out.println("map的大小是：" + map.size());
    }

    /**
     * 分别获取这些作家的所出书籍的最高分和最低分并打印
     */
    private static void test10() {
        Optional<Double> max = getAuthors().stream().distinct().flatMap(author -> author.getBookList().stream())
                .map(book -> book.getScore())
                .max(new Comparator<Double>() {
                    @Override
                    public int compare(Double o1, Double o2) {
                        return (int) (o1 - o2);
                    }
                });
        System.out.println(" 所有作家书籍中分数最高的是： " + max.get());


        Optional<Double> min = getAuthors().stream()
                .flatMap(author -> author.getBookList().stream())
                .map(book -> book.getScore())
                .distinct()
                .min((s1, s2) -> (int) (s1 - s2));
        System.out.println(" 所有作家书籍中分数最低的是： " + min.get());


    }

    /**
     * 打印现有数据的所有分类，去重，不能出现格式 哲学,爱情 这种拼接的
     */
    private static void test9() {
        System.out.println("flatmap again");
        getAuthors().stream().flatMap(author -> author.getBookList().stream())
                .flatMap(book -> Arrays.stream(book.getCategory().split(",")))
                .distinct().forEach(System.out::println);
    }

    /**
     * 打印所有书籍的名字，去重
     */
    private static void test8() {
        System.out.println("test flatMap");
        getAuthors().stream()
                .flatMap(new Function<Author, Stream<Book>>() {
                    @Override
                    public Stream<Book> apply(Author author) {
                        return author.getBookList().stream();
                    }
                })
                .distinct() // 去掉相同的书籍
                .forEach(book -> System.out.println(book.getName()));
    }

    /**
     * 年龄最大的作家外，不能重复，年龄降序
     */
    private static void test7() {
        List<Author> authors = getAuthors();
        authors.stream().sorted().skip(1).forEach(author -> System.out.println(author.getAge()));
    }

    private static void test6(List<Author> authors) {
        authors.stream().filter(new Predicate<Author>() {
            @Override
            public boolean test(Author author) {
                return author.getAge() < 18;
            }
        }).map(new Function<Author, String>() {
            @Override
            public String apply(Author author) {
                return author.getName();
            }
        }).distinct().forEach(new Consumer<String>() {
            @Override
            public void accept(String authorName) {
                System.out.println(authorName);
            }
        });
    }

    private static void test5(List<Author> authors) {
        authors.stream() // 返回值是stream对象，也就是集合转流
                .filter(author -> author.getAge() < 18) // 筛选条件
                .map(Author::getName) // 只要名字
                .distinct() // 去重，这里不能放在前面，不然比较的是author元素不是名字
                .forEach(System.out::println); // 遍历输出
    }

    private static void test4() {
        System.out.println("========");
        Integer[] arr = {1, 2, 3, 4};
        Stream<Integer> arr1 = Stream.of(arr); // 利用Stream的静态方法
        arr1.forEach(System.out::println);
        Stream<Integer> arr2 = Arrays.stream(arr);
    }

    private static void test3() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("波吉", 15);
        map.put("卡克", 14);
        map.put("波斯", 13);
        map.entrySet().forEach(new Consumer<Map.Entry<String, Integer>>() {
            @Override
            public void accept(Map.Entry<String, Integer> stringIntegerEntry) {
                System.out.println(stringIntegerEntry.getKey());
                System.out.println(stringIntegerEntry.getValue());
            }
        });
    }

    /**
     * 年龄降序，不能重复，打印年龄最大的两个作家名字
     */
    private static void test2() {
        List<Author> authors = getAuthors();
        authors.stream().distinct().sorted().limit(2).forEach(author -> System.out.println(author.getName()));
    }

    // 对流中的元素按照年龄进行降序排序，并且要求不能有重复元素
    private static void test1() {
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                //.map(author -> author.getAge()) // 这个是比较年龄
                .sorted() // 可以注释这一行看看什么情况
                .forEach(author -> System.out.println(author.getAge()));

        System.out.println("==========");

        authors.stream().distinct().sorted(new Comparator<Author>() {
            @Override
            public int compare(Author o1, Author o2) {
                return o2.getAge() - o1.getAge();
            }
        }).forEach(author -> System.out.println(author.getAge()));
    }

    /**
     * 初始化一些数据
     */
    private static List<Author> getAuthors() {
        Author author1 = new Author(1L, "tom", "my introduction 1", 18, null);
        Author author2 = new Author(2L, "jack", "my introduction 2", 19, null);
        Author author3 = new Author(3L, "jerry", "my introduction 3", 14, null);
        Author author4 = new Author(4L, "timi", "my introduction 4", 29, null);
        Author author5 = new Author(2L, "jack", "my introduction 2", 19, null);

        List<Book> books1 = new ArrayList<>();
        List<Book> books2 = new ArrayList<>();
        List<Book> books3 = new ArrayList<>();

        // 上面是作者和书
        books1.add(new Book(1L, "类别,分类啊", "书名1", 45D, "这是简介哦"));
        books1.add(new Book(2L, "高效", "书名2", 84D, "这是简介哦"));
        books1.add(new Book(3L, "喜剧", "书名3", 83D, "这是简介哦"));

        books2.add(new Book(5L, "天啊", "书名4", 65D, "这是简介哦"));
        books2.add(new Book(6L, "高效", "书名5", 89D, "这是简介哦"));

        books3.add(new Book(7L, "久啊", "书名6", 45D, "这是简介哦"));
        books3.add(new Book(8L, "高效", "书名7", 44D, "这是简介哦"));
        books3.add(new Book(9L, "喜剧", "书名8", 81D, "这是简介哦"));

        author1.setBookList(books1);
        author2.setBookList(books2);
        author3.setBookList(books3);
        author4.setBookList(books3);
        author5.setBookList(books2);

        return new ArrayList<>(Arrays.asList(author1, author2, author3, author4, author5));
    }

//    private static void test() {
//        List<Product> productsList = new ArrayList<>();
//        //Adding Products
//        productsList.add(new Product(1, "HP Laptop", 25000d));
//        productsList.add(new Product(2, "Dell Laptop", 30000d));
//        productsList.add(new Product(3, "Lenovo Laptop", 28000d));
//        productsList.add(new Product(4, "Sony Laptop", 28000d));
//        productsList.add(new Product(5, "Apple Laptop", 90000d));
//        // This is more compact approach for filtering data
//        Double totalPrice = productsList.stream()
//                .map(product->product.getPrice())
//                // 第一个参数是累加类型 第二是累加表达式 累加到sum
//                .reduce(0.0D, new BinaryOperator<Double>() {
//                    @Override
//                    public Double apply(Double total, Double price) {
//                        return total + price;
//                    }
//                });
//        System.out.println(totalPrice);
//        // More precise code
//        Double totalPrice2 = productsList.stream()
//                .map(product -> product.getPrice())
//                // 简单理解就是上一个map方法之后只拿到price值，这里全加起来
//                .reduce(0.0D, Double::sum);
//        System.out.println(totalPrice2);
//
//        System.out.println("======");
//        // 过滤，获取数据，收集成list
//        List<Double> productPriceList2 =productsList.stream()
//                .filter(p -> p.getPrice() > 30000)
//                .map(p->p.getPrice())
//                .collect(Collectors.toList());
//        System.out.println(productPriceList2);
//
//        // 常规操作
//        long count = productsList.stream().filter(p -> p.getPrice() < 30000)
//                .map(p -> p.getName())
//                .distinct()
//                .count();
//        System.out.println(count);
//
//        Set<Double> productPriceListSet = productsList.stream().filter(p -> p.getPrice() > 30000)
//                .map(p -> p.getPrice())
//                .distinct().collect(Collectors.toSet());
//        System.out.println(productPriceListSet);
//
//        productsList.stream().filter(p -> p.getPrice() < 30000)
//                .map(p -> p.getName())
//                .distinct().forEach(System.out::println);
//    }
}
