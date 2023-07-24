package com.evoke.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@SpringBootApplication
@EnableSwagger2
public class DemoApplication {
    @FunctionalInterface
    public interface Arth {
        public int apply(int k, int h);
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        List<Integer> list = Arrays.asList(-98, 23, 33, 3, 6, 3, 5, 6, 8, 9, 9, 12, 15, -45);

        // list.stream().sorted().peek(x -> System.out.println(x));
        // list.stream().sorted(Comparator.reverseOrder()).forEach(x -> System.out.println(x));
        //System.out.println(list.stream().filter(num->33==(num)).count());
        list.stream().filter(i -> i % 2 == 1)
                .map(i -> i * 2)
                .forEach(i -> System.out.println(i));
//        Stream.iterate(15, element -> element + 1)
//                .filter(element -> element % 5 == 0)
//                .limit(5)
//                .forEach(System.out::println);
        //Stream.iterate(10,element->element+1)
        Arth a = (i, k) -> i + k;
        Arth a1 = (i, k) -> i - k;
        int sum = a.apply(5, 6);
        System.out.println(sum);
        System.out.println(a1.apply(7, 3));

        BiFunction<Integer, Integer, Integer> func = (x1, x2) -> x1 + x2;
        Function<Integer, String> func1 = (i) -> "Result : " + String.valueOf(i);

        String result = func.andThen(func1).apply(4, 5);
        System.out.println(result);
        List<Integer> intList = Arrays.asList(2, 4, 5, 6, 8);
        Map<Boolean, List<Integer>> isEven = intList.stream().collect(
                Collectors.partitioningBy(i -> i % 2 == 0));
        System.out.println("is even :" + isEven.get(true).size());
        System.out.println("is odd :" + isEven.get(false).size());
        List<String> stringList = Arrays.asList("soujanya", "satya", "bavitha", "dhanush");
        Map<Character, List<String>> getGroups = stringList.stream()
                .collect(Collectors
                        .groupingBy(e -> e.charAt(0)));
        System.out.println(getGroups.get('s').size());


        //andThen() to chain with a Function
        /* Employee[] arrayOfEmps = {
        new Employee(1, "Jeff Bezos", 100000.0),
        new Employee(2, "Bill Gates", 200000.0),
        new Employee(3, "Mark Zuckerberg", 300000.0)
    };
    List<Employee> empList = Arrays.asList(arrayOfEmps);
    */
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.evoke.example")).build();
    }
}
