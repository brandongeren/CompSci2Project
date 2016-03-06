package com.airamerica;

public class SortedListNode<T> {

	private T object;
	private SortedListNode<T> next;
	private SortedListNode<T> previous;

	public SortedListNode(T object, SortedListNode<T> next, SortedListNode<T> previous) {
		this.object = object;
		this.next = next;
		this.previous = previous;
	}//	public SortedListNode(T object, SortedListNode<T> next) {

	public SortedListNode(T t) {
		this.next = null;
		this.previous = null;
		this.object = t;
	}//	public SortedListNode(T t) {

	public SortedListNode<T> getPrevious() {
		return previous;
	}//	public SortedListNode<T> getPrevious() {

	public void setPrevious(SortedListNode<T> previous) {
		this.previous = previous;
	}//	public void setPrevious(SortedListNode<T> previous) {

	public void setObject(T object) {
		this.object = object;
	}//	public void setObject(T object) {

	public void setNext(SortedListNode<T> next) {
		this.next = next;
	}//	public void setNext(SortedListNode<T> next) {

	public T getObject() {
		return this.object;
	}//	public T getObject() {

	public SortedListNode<T> getNext() {
		return this.next;
	}//public SortedListNode<T> getNext() {

}//public class SortedListNode<T> {
