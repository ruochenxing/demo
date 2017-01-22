package com.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamDemo {

	public static void main(String[] args) {
		test13();
	}

	// 第一个方法使用流中的第一个值作为初始值，后面两个方法则使用一个提供的初始值。
	public static void test14() {
		Optional<Integer> total = Stream.of(1, 2, 3, 4, 5).reduce((x, y) -> x + y);
		Integer total2 = Stream.of(1, 2, 3, 4, 5).reduce(0, (x, y) -> x + y);
		System.out.println((!total.isPresent()?0:total.get())+"\t"+total2);
	}

	// count方法返回流中的元素的数量。
	// collect
	public static void test13() {
		String[] arr = new String[] { "b_123", "c+342", "b#632", "d_123" };
		List<String> asList = Arrays.stream(arr).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		String concat = Arrays.stream(arr).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
				.toString();
		System.out.println(concat);
		asList.stream().forEach(System.out::println);
	}

	/**************** 终点操作 *******************/
	/**
	 * 这一组方法用来检查流中的元素是否满足断言。 allMatch只有在所有的元素都满足断言时才返回true,否则flase,流为空时总是返回true
	 * anyMatch只有在任意一个元素满足断言时就返回true,否则flase,
	 * noneMatch只有在所有的元素都不满足断言时才返回true,否则flase,
	 */
	public static void test12() {
		System.out.println(Stream.of(1, 2, 3, 4, 5).allMatch(i -> i > 0)); // true
		System.out.println(Stream.of(1, 2, 3, 4, 5).anyMatch(i -> i > 0)); // true
		System.out.println(Stream.of(1, 2, 3, 4, 5).noneMatch(i -> i > 0)); // false
		System.out.println(Stream.<Integer> empty().allMatch(i -> i > 0)); // true
		System.out.println(Stream.<Integer> empty().anyMatch(i -> i > 0)); // false
		System.out.println(Stream.<Integer> empty().noneMatch(i -> i > 0)); // true
	}

	/**************** 终点操作 *******************/

	/**************** 中间操作 *******************/
	// skip返回丢弃了前n个元素的流，如果流中的元素小于或者等于n，则返回空的流。
	public static void test11() {

	}

	// sorted()将流中的元素按照自然排序方式进行排序，如果元素没有实现Comparable，则终点操作执行时会抛出java.lang.ClassCastException异常。
	// sorted(Comparator<? super T> comparator)可以指定排序的方式。
	public static void test10() {
		String[] arr = new String[] { "b_123", "c+342", "b#632", "d_123" };
		List<String> l = Arrays.stream(arr).sorted((s1, s2) -> {
			if (s1.charAt(0) == s2.charAt(0))
				return s1.substring(2).compareTo(s2.substring(2));
			else
				return s1.charAt(0) - s2.charAt(0);
		}).collect(Collectors.toList());
		System.out.println(l); // [b_123, b#632, c+342, d_123]
	}

	// peek方法方法会使用一个Consumer消费流中的元素，但是返回的流还是包含原来的流中的元素。
	public static void test9() {
		String[] arr = new String[] { "a", "b", "c", "d" };
		Arrays.stream(arr).peek(System.out::println); // a,b,c,d .count();
	}

	// limit方法指定数量的元素的流。对于串行流，这个方法是有效的，这是因为它只需返回前n个元素即可，但是对于有序的并行流，它可能花费相对较长的时间，如果你不在意有序，可以将有序并行流转换为无序的，可以提高性能。
	public static void test8() {
		List<Integer> l = IntStream.range(1, 100).limit(5).boxed().collect(Collectors.toList());
		System.out.println(l);// [1, 2, 3, 4, 5]
	}

	// flatmap方法混合了map+flattern的功能，它将映射后的流的元素全部放入到一个新的流中。它的方法定义如下：
	// 可以看到mapper函数会将每一个元素转换成一个流对象，而flatMap方法返回的流包含的元素为mapper生成的流中的元素。
	public static void test7() {
		String poetry = "Where, before me, are the ages that have gone?/n"
				+ "And where, behind me, are the coming generations?/n"
				+ "I think of heaven and earth, without limit, without end,/n"
				+ "And I am all alone and my tears fall down.";
		Stream<String> lines = Arrays.stream(poetry.split("/n"));
		Stream<String> words = lines.flatMap(line -> Arrays.stream(line.split(" ")));
		List<String> l = words.map(w -> {
			if (w.endsWith(",") || w.endsWith(".") || w.endsWith("?"))
				return w.substring(0, w.length() - 1).trim().toLowerCase();
			else
				return w.trim().toLowerCase();
		}).distinct().sorted().collect(Collectors.toList());
		System.out.println(l); // [ages, all, alone, am, and, are, before,
								// behind, coming, down, earth, end, fall,
								// generations, gone, have, heaven, i, limit,
								// me, my, of, tears, that, the, think, where,
								// without]
	}

	// map方法将流中的元素映射成另外的值，新的值类型可以和原来的元素的类型不同。
	public static void test6() {
		List<Integer> l = Stream.of('a', 'b', 'c').map(c -> c.hashCode()).collect(Collectors.toList());
		System.out.println(l); // [97, 98, 99]
	}

	// filter返回的流中只包含满足断言(predicate)的数据。
	public static void test5() {
		List<Integer> l = IntStream.range(1, 10).filter(i -> i % 2 == 0).boxed().collect(Collectors.toList());
		System.out.println(l); // [2, 4, 6, 8]
	}

	// distinct保证输出的流中包含唯一的元素，它是通过Object.equals(Object)来检查是否包含相同的元素。
	public static void test4() {
		List<String> l = Stream.of("a", "b", "c", "b").distinct().collect(Collectors.toList());
		System.out.println(l); // [a, b, c]
	}

	/**************** 中间操作 *******************/
	public static void test3() {
		List<String> l = new ArrayList<>(Arrays.asList("zero", "one", "two", "three", "four", "five"));
		class State {
			boolean s;
		}
		final State state = new State();
		// 上面的代码在并行执行时多次的执行结果可能是不同的。这是因为这个lambda表达式是有状态的。
		Stream<String> sl = l.parallelStream().map(e -> {
			if (state.s)
				return "OK";
			else {
				state.s = true;
				return e;
			}
		});
		sl.forEach(System.out::println);
	}

	public static void test2() {
		List<String> l = new CopyOnWriteArrayList<>(Arrays.asList("one", "two"));
		Stream<String> sl = l.stream();
		sl.forEach(s -> l.add("three"));
		l.stream().forEach(System.out::println);
	}

	public static void test0() {
		List<String> l = new ArrayList<>(Arrays.asList("one", "two"));
		Stream<String> sl = l.stream();
		sl.forEach(s -> l.add("three"));
	}

	public static void test1() {
		List<String> l = new ArrayList<>(Arrays.asList("one", "two"));
		Stream<String> sl = l.stream();
		l.add("three");
		sl.forEach(System.out::println);
	}
}
