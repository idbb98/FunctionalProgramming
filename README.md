# 函数式编程 stream流
## 1.lambda表达式
### 1.1 概述
- lambda是JDK8中的一个语法糖，可
  以对某些匿名内部类的写法进行优化，让函数式编程只关注数据而不是对象。
- 基本格式：(参数列表)->{代码}

## 2. stream流
### 2.1 概述
- stream使用的是函数式编程模式，可以被用来对集合或数组进行链状流式的操作
- 有别于其他输入输出流，这里是针对集合操作数据的流哦

### 2.2 功能
- 流不存储元素。它只是通过计算操作的流水线从数据结构, 数组或I/O通道等源中传递元素。
- 流本质上是功能性的。对流执行的操作不会修改其源。例如, 对从集合中获取的流进行过滤会产生一个新的不带过滤元素的流, 而不是从源集合中删除元素。
- Stream是惰性的, 仅在需要时才评估代码。
- 在流的生存期内, 流的元素只能访问一次。像Iterator一样, 必须生成新的流以重新访问源中的相同元素。

### 2.3 常用操作

#### 2.3.1 创建流
单列集合: 集合对象.stream()
```java
List<Author> authors = getAuthors();
Stream<Author> stream = authors.stream();
```
数组: Arrays.stream(arr) / Stream.of(arr)
```java 
Integer[] arr = {1,2,3,4,5};
Stream<Integer> stream = Arrays.stream(arr);
Stream<Integer> stream2 = Stream.of(arr);
```
双列集合: 
```java
HashMap<String, Integer> hashMap = new HashMap<>();
hashMap.put("tom",12);
hashMap.put("jack",16);

Set<Map.Entry<String, Integer>> entrySet = hashMap.entrySet();
Stream<Map.Entry<String, Integer>> stream = entrySet.stream();

stream.filter(new Predicate<Map.Entry<String, Integer>>() {
              @Override
              public boolean test(Map.Entry<String, Integer> entry) {
                return entry.getValue() > 12;
              }
            })
        .forEach(new Consumer<Map.Entry<String, Integer>>() {
              @Override
              public void accept(Map.Entry<String, Integer> entry) {
                System.out.println(entry.getKey()+"---"+entry.getValue());
              }
        });
// lambda
stream.filter(entry -> entry.getValue() > 12)
        .forEach(entry -> System.out.println(entry.getKey() + "---" + entry.getValue()));
```

#### 2.3.2 中间操作
- filter
```java
List<Author> authors = getAuthors();
//打印姓名长度大于1的作家姓名
authors.stream()
        .filter(new Predicate<Author>() {
            @Override
            public boolean test(Author author) {
                return author.getName().length() > 1;
            }
        })
        .forEach(new Consumer<Author>() {
            @Override
            public void accept(Author author) {
                System.out.println(author.getName());
            }
        });
// lambda
authors.stream()
        .filter(author -> author.getName().length()>1)
        .forEach(author -> System.out.println(author.getName()));
```

- map
对流中的数据进行计算或转换
```java
List<Author> authors = getAuthors();
//打印所以作家姓名
authors.stream()
        .map(new Function<Author, String>() {   //Author类型姓名转成String
            @Override
            public String apply(Author author) {
                return author.getName();
            }
        })
        .forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
authors.stream()
        .map( author -> author.getName())       //Author类型姓名转成String
        .forEach(s -> System.out.println(s));
// 计算
authors.stream()
        .map(author -> author.getAge())         //Author类型年龄转成Integer
        .map(age -> age+10)
        .forEach(age -> System.out.println(age));
```

- distinct 
依赖Object的equals方法（直接==判断）,一般要重写equals方法
```java
List<Author> authors = getAuthors();
//打印所以作家姓名,去重(只判断名字)
authors.stream()
        .map(author -> author.getName())
        .distinct()
        .forEach(name -> System.out.println(name));
```

- sorted
```java
List<Author> authors = getAuthors();
//按年龄排序，不能重复
// 1. 空参sorted()方法
authors.stream()
        .distinct()
        // 此方式需要Author类implements Comparable重写compareTo方法，否则报ClassCastException
        // 实现降序或升序依赖compareTo方法
        .sorted()
        .forEach(author -> System.out.println(author.getAge()));
// 2. 有参sorted()方法
authors.stream()
        .distinct()
        .sorted(new Comparator<Author>() {
            @Override
            public int compare(Author o1, Author o2) {
                return o1.getAge() - o2.getAge();
            }
        })
        .forEach(author -> System.out.println(author.getAge()));
// lambda
authors.stream()
        .distinct()
        .sorted((o1, o2) -> o1.getAge() - o2.getAge())
        .forEach(author -> System.out.println(author.getAge()));
```

- limit 
设置流的最大长度，超出部分将被抛弃
```java
List<Author> authors = getAuthors();
// 对流中元素按年龄降序，并且不能重复，打印年龄最大的俩个作家名字
authors.stream()
        .distinct()
        .sorted(((o1, o2) -> o2.getAge() - o1.getAge()))
        .limit(2)
        .forEach(author -> System.out.println(author.getName()));
```

- skip 
跳过流中的前n个元素，返回剩下的元素
```java
List<Author> authors = getAuthors();
// 打印除了年龄最大的作家外的其他作家，并且不能重复，按年龄降序
authors.stream()
        .distinct()
        .sorted(((o1, o2) -> o2.getAge() - o1.getAge()))
        .skip(1)
        .forEach(author -> System.out.println(author));
```

- flatMap
map能把一个对象转换成另外一个对象来作为流中的元素，而flatMap可以把一个对象转换成多个对象作为流中的元素
```java
List<Author> authors = getAuthors();
// 打印书籍名字，去重
authors.stream()
        .flatMap(new Function<Author, Stream<Book>>() {     // 转成Stream<Book>
            @Override
            public Stream<Book> apply(Author author) {
                    return author.getBookList().stream();
                    }
        })
        .distinct()
        .forEach(new Consumer<Book>() {
            @Override
            public void accept(Book book) {
                    System.out.println(book.getName());
                    }
        });
// lambda
authors.stream()
        .flatMap(author -> author.getBookList().stream())       // 转成Stream<Book>
        .distinct()
        .forEach(book -> System.out.println(book.getName()));
```

```java
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
```


#### 2.3.2 常用方法说明 TestStream
- map:相当于对数据进行一个操作，可以自定义返回值等
- distinct:可以去除流中的相同元素，注意（*该方法依赖的Object的equals方法来判断是否是相同对象，所以要重写equals方法，否则只有对象地址一样时才会被认为是重复*）
- sorted:可以对流中的元素进行排序，传入空参时使用的是实体类的比较方法
- limit:设置流的最大长度，超出部分将被抛弃
- skip:跳过流中的前n个元素，返回剩下的元素
- **flatMap**:map能把一个对象转换成另外一个对象来作为流中的元素，而flatMap可以把一个对象转换成多个对象作为流中的元素
- 中间操作（filter,map,distinct,sorted,limit,skip,flatMap）
- 终结操作（forEach,collect,count,max,min,reduce归并,查找与匹配）
- forEach:遍历所有元素
- count:计算元素数量
- min&max:返回的是option对象，这里和sorted一样，得指定比较规则
- collect:把当前流转换成一个集合（list, set, map）
    - Collectors.toList()
    - Collectors.toSet()
    - Collectors.toMap(key, value)
- anyMatch:可以用来判断是否有任意符合匹配条件的元素，结果为boolean类型
- allMatch:可以用来判断是否都匹配条件，结果也是boolean类型，都符合则为true
- noneMatch:是否都不符合，都不符合则为true
- findAny:获取流中的任意一个元素，该方法无法保证获取的是流中的第一个元素，只是匹配到
- findFirst:获取流中的第一个元素
- reduce:对流中的数据按照你制定的计算方式计算出一个结果，并返回一个Optional描述归约值（如果有）
    ```java
    T result = identity;
    for(T element : this stream) {
        result = accumulator.apply(result, element); // 执行具体数据操作
    }
    return result;
    // 还有一种三个方法的重载方法，后面还需要补充
  ```

#### 2.3 参考资料


#### 2.5 注意事项
- 惰性求值，如果没有终结操作是不会执行的
- 流是一次性的，经过终结操作之后就不能再被使用
- 不会影响元数据


### 3.Optional
#### 3.1 概述
很多情况下代码容易出现空指针异常，尤其对象的属性是另外一个对象的时候，
判断十分麻烦，代码也会很臃肿，这种情况下Java 8 引入了optional来避免空指针异常，
并且很多函数式编程也会用到API也都用到
#### 3.2 使用
1. 创建对象
- optional就像是包装类，可以把我们的具体数据封装Optional对象内部，
  然后我们去使用它内部封装好的方法操作封装进去的数据就可以很好的避免空指针异常
- 一般我们使用Optional.ofNullable来把数据封装成一个optional对象，无论传入的参数是否为null都不会出现问题
  `Author author = getAuthor();  Optional<Author> author = Optional.ofNullable(author);`
- 如果你确定一个对象不是空的话就可以用Optional.of这个静态方法来把数据封装成Optional对象
  `Optional.of(author);`这里一定不能是null值传入，可以试试会出现空指针
- 如果返回的是null，这时可以使用Optional.empty()来进行封装

2. 安全消费值
- 当我们获取到一个Optional对象的时候，可以用ifPresent方法来去消费其中的值，
  这个方法会先去判断是否为空，不为空才会去执行消费代码，优雅避免空指针
  `OptionalObject.ifPresent()`

3. 获取值
- Optional.get() 这种方法不推荐，当Optional的get方法为空时会出现异常

3.1 安全获取值
- orElseGet:获取数据并且设置数据为空时的默认值，如果数据不为空就获取该数据，为空则获取默认值
- orElseThrow

4. 过滤
- 我们可以使用filter方法对数据进行过滤，如果原来是有数据的，但是不符合判断，也会变成一个无数据的Optional对象
- Optional.filter()

5. 判断
- Optional.isPresent() 判断数据是否存在，空则返回false，否则true，这种方式不是最好的，推荐使用Optional.ifPresent()
- Optional.ifPresent()，上面isPresent不能体现Optional的优点
- 使用的时候可以先判断，相当于先判空，再去get，这样就不会空指针了

6. 数据转换
- Optional还提供map可以对数据进行转换，并且转换得到的数据还是Optional包装好的，保证安全使用

### 5.函数式接口
#### 5.1 概述
1. 只有一个抽象方法的接口就是函数式接口
2. JDK的函数式接口都加上了@FunctionalInterface注解进行标识，但是无论加不加该注解，只要接口中只有一个抽象方法，都是函数式接口
3. 常见的函数式接口
- Consumer 消费接口：可以对传入的参数进行消费
- Function 计算转换接口：根据其中抽象方法的参数列表和返回值类型可以看到，可以在方法中对传入的参数计算或转换，把结果返回
- Predicate 判断接口：可以在方法对传入的参数条件进行判断，返回判断结果
- Supplier 生产型接口：可以在方法中创建对象，把创建好的对象返回

4. 常用的默认方法
- and ：我们在使用Predicate接口的时候可能需要进行判断条件的拼接，而and方法相当于使用&&来拼接两个判断条件
- or

### 6.方法引用
- 我们在使用lambda时，如果方法体中只有一个方法的时候，包括构造方法，我们可以用方法引用进一步简化代码
#### 6.1用法及基本格式
- 方法体中只有一个方法时
- 类名或者对象名::方法名
#### 6.2语法了解
- 6.2.1 引用类静态方法 类名::方法名
  **使用前提：如果我们在重写方法的时候，方法体中只有一行代码，
  并且这行代码是调用了某个类的静态方法，并且我们把要重写的抽象方法中所有参数都按照顺序传入了这个静态方法中，
  这个时候我们就可以引用类的静态方法。**

- 6.2.2 引用对象的实例方法 对象名::方法名
  **使用前提：如果我们在重写方法的时候，方法体只有一行代码，并且这行代码是调用了某个对象的成员方法，
  并且我们把要重写的抽象方法里面中所有的参数都按照顺序传入了这个成员方法(就是类的方法)中，这个时候我们就可以引用对象的实例方法。**

- 6.2.3 引用类的实例方法 类名::方法名
  **使用前提：如果我们在重写方法的时候，方法体中只有一行代码，并且这行代码是调用了第一个参数的成员方法，
  并且我们把要重写的抽象方法中剩余的所有的参数都按照顺序传入了这个成员方法中，这个时候我们就可以引用类的实例方法。**

- 6.2.4 构造器引用 类名::new StringBuilder::new

### 7.高级用法
基本数据类型优化：很多stream方法由于都使用了泛型，所以涉及到的参数和返回值都是引用数据类型，即使我们操作的是
整数小数，实际使用还是他们的包装类，JDK5中引入的自动装箱和自动拆箱让我们在使用对应的包装类时就好像使用基本数据类型一样方便，
但是你一定要知道装箱拆箱也是需要一定的时间的，虽然这个时间消耗很小，但是在大量数据的不断重复的情况下，就不能忽视这个时间损耗了，
stream对这块内容进行了优化，提供很多针对基本数据类型的方法。
例如：mapToInt,mapToLong,mapToDouble,flatMapToInt....
比如前面我们用的map()，返回的是Stream<Integer>，如果你用.mapToInt()，最后返回的就是int值

### 8.并行流
当流中有大量元素时，我们可以使用并行流去提高操作的效率，其实并行流就是把任务分配给多个线程去完成，如果我们自己去用代码取实现的话
其实会非常复杂，并且要求你对并发编程有足够的理解和认识，而且如果我们使用stream的话，我们只需要修改一个方法的调用就可以使用并行流来帮我们实现，从而提高效率
