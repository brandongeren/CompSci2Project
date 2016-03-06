package com.airamerica;

import java.util.Comparator;

public class Tester {
	public static void main(String args[]) {
		
		Comparator<Integer> compInteger = new Comparator<Integer>() {
			@Override
			public int compare(Integer a, Integer b) {
				return a.compareTo(b);
			}
		};
		
		Comparator<Integer> compIntegerReverse = new Comparator<Integer>() {
			@Override
			public int compare(Integer a, Integer b) {
				return b.compareTo(a);
			}
		};
		
		Comparator<String> compString = new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				return a.compareTo(b);
			}
		};
		
		SortedList<Integer> numbers = new SortedList<Integer>(compInteger);
		
		numbers.add(1);
		numbers.add(-1);
		numbers.add(2);
		numbers.add(3);
		numbers.add(0);
		numbers.add(5);
		numbers.add(4);
		numbers.add(10);
		numbers.add(6);
		numbers.add(2);
		
		System.out.println(numbers.toString());
		
	}//	public static void main(String args[]) {

}//public class Tester {

