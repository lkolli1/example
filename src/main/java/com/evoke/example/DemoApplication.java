package com.evoke.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;


@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        List<Integer> list = Arrays.asList(-98, 23, 33, 3, 6, 3, 5, 6, 8, 9, 9, 12, 15, -45);

        list.stream().sorted().forEach(x -> System.out.println(x));
        list.stream().sorted(Comparator.reverseOrder()).forEach(x -> System.out.println(x));
		System.out.println(list.stream().filter(num->33==(num)).count());
        Stream.iterate(15, element -> element + 1)
                .filter(element -> element % 5 == 0)
                .limit(5)
                .forEach(System.out::println);
        //Stream.iterate(10,element->element+1)
    }

}
