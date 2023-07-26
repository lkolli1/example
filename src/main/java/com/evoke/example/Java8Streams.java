package com.evoke.example;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Java8Streams {
    public static void main(String[] args) {
        List<Integer> listInt = Arrays.asList(23, 24, 25, 25, 26, 27, 32, 32, 33, 34);

        log.info(listInt.stream().max(Comparator.comparing(Integer::intValue)).get().toString());
        listInt.stream().map(i -> i * 3).forEach(i->log.info(String.valueOf(i)));

        int[] nums = {12, 13, 14, 12};
        Arrays.sort(nums);
        Arrays.stream(nums).forEach(System.out::println);
        List<Integer> listInt1 = Arrays.stream(nums).boxed().toList();
                Stream.concat(listInt.stream(), listInt1.stream()).forEach(System.out::println);
        Set<Integer> setInts = new HashSet<>(listInt1);
        if (listInt1.size() == setInts.size()) {
            System.out.println("false");
        } else {
            System.out.println("true"+listInt1.size() +"hai set: "+setInts.size());
        }
        String[] strs = {"AA", "BB", "CC", "AA", "abdd"};

        List<String> listString = Arrays.asList(strs);
        listString.stream().filter(i -> i.length() > 3).forEach(System.out::println);
        Map<String, Long> count = listString.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<String, Long> dupCount = listString.stream()
                .filter(e -> Collections.frequency(listString, e) > 1)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        listString.stream().map(String::toLowerCase)
                .forEach(System.out::println);
        listInt.stream().filter(i->i%2>0).forEach(System.out::println);
        listInt.stream().map(e->e+"")
                .filter(e->e.startsWith("3"))
                .forEach(System.out::println);//starts with

        Set<Integer> setInt = new HashSet<>();
        listInt.stream().filter(e -> !setInt.add(e))
                .forEach(System.out::println);
         listInt.stream().findFirst().ifPresent(System.out::println);
        Long ct=listInt.stream().count();
         System.out.println(ct);
        int maxNum=listInt.stream().max(Integer::compare).get();
        System.out.println(maxNum);
        String input = "Java articles are Awesome";

        Character result = input.chars()
                .mapToObj(s -> Character.toLowerCase(Character.valueOf((char) s)))
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == 1L) //>1L for Duplicate ==1L for
                .map(e -> e.getKey())
                .findFirst()
                .get();
        System.out.println(result);

        List<Emp> empList = new ArrayList<>();

        empList.add(new Emp(101, "Sharanya", 26, "IT", 30000, "Female", "active"));
        empList.add(new Emp(102, "Mounika", 24, "Non-IT", 20000, "Female", "active"));
        empList.add(new Emp(103, "Ram", 27, "IT", 35000, "Male", "active"));
        empList.add(new Emp(104, "Laxman", 29, "IT", 65000, "Male", "active"));
        empList.add(new Emp(105, "Meghana", 26, "Non-IT", 15000, "female", "inactive"));
        empList.add(new Emp(106, "Hanuman", 28, "Non-IT", 30000, "Male", "active"));
        empList.add(new Emp(107, "Ravi", 30, "IT", 60000, "Male", "active"));
        empList.add(new Emp(108, "Shiva", 29, "IT", 75000, "Male", "active"));
        empList.add(new Emp(109, "Krishna", 23, "Non-IT", 24000, "Male", "inactive"));
        empList.add(new Emp(110, "Ramesh", 27, "IT", 80000, "Male", "active"));
        System.out.println("employee details by dept");
        Map<String, List<Emp>> empsMap = empList.stream()
                .collect(Collectors
                        .groupingBy(Emp::getDept, Collectors.toList()));
        empsMap.forEach((K, V) -> System.out.println("Dept=" + K + ",EMP=" + V));

        empList.stream().filter(e -> e.getDept().equals("IT")).forEach(System.out::println);

        empList.stream().collect(Collectors.groupingBy(Emp::getDept, Collectors.counting()))
                .forEach((K, V) -> System.out.println("Dept=" + K + " Count =" + V));
        empList.stream().max(Comparator.comparing(Emp::getSalary)).ifPresent(System.out::println);
        empList.stream()
                .collect(Collectors
                        .groupingBy(Emp::getDept, Collectors.averagingInt(Emp::getSalary)))
                .forEach((K, V) -> System.out.println("Dept=" + K + ",Avg Sal=" + V));
        empList.stream().sorted(Comparator.comparing(Emp::getSalary).reversed()).forEach(System.out::println);

        empList.stream().filter(e -> e.getAge() > 25)
                .forEach(e -> {
                    e.setSalary(e.getSalary() * 3);
                    System.out.println(e);
                });
        Consumer<String> str = System.out::println;
        str.accept("Sairam");
        Supplier<String> str1 = () -> "Sairam";
        System.out.println(str1.get());
        List<List<String>> names = Arrays.asList(Arrays.asList("ss", "ll"), Arrays.asList("kk", "ssr"));
        names.stream().flatMap(name -> name.stream()).filter(s -> s.startsWith("s")).forEach(System.out::println);
        Stream.iterate(2, cnt -> cnt + 1).filter(i -> i % 2 == 0).limit(5).forEach(System.out::println);
    }

}
