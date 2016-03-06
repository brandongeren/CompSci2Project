package com.airamerica;

import java.util.Comparator;
import java.util.Iterator;

//self-sorting linked list of objects
public class SortedList<T> implements Iterable<T> {

	private SortedListNode<T> head = null;
	private SortedListNode<T> tail = null;
	private Comparator<T> comp;
	
	//constructor with head known
	public SortedList(SortedListNode<T> head, Comparator<T> comp) {
		this.head = head;
		this.comp = comp;
	}//	public SortedList(SortedListNode<T> head, Comparator<T> comp) {

	//constructor without head known
	public SortedList(Comparator<T> comp) {
		this.comp = comp;
	}//	public SortedList(Comparator<T> comp) {

	//return the first node of the list
	public SortedListNode<T> getHead() {
		return head;
	}//public SortedListNode<T> getHead() {

	//change the first node of the list
	public void setHead(SortedListNode<T> head) {
		this.head = head;
	}//	public void setHead(SortedListNode<T> head) {

	//output the last node of the list
	public SortedListNode<T> getTail() {
		return tail;
	}//	public SortedListNode<T> getEnd() {

	//change the tail
	public void setTail(SortedListNode<T> tail) {
		this.tail = tail;
	}//	public void setEnd(SortedListNode<T> end) {

	//determine which comparator is being used
	public Comparator<T> getComp() {
		return comp;
	}//	public Comparator<T> getComp() {

	//output what the sorted list would look like with a different comparator
	public SortedList<T> changeComparator(Comparator<T> comp) {
	
	SortedList<T> newList = new SortedList<T>(comp);
		
		for(T t: this) {
			newList.add(t);
		}//	for(T t: this) {
		return newList;
	}//	public SortedList<T> changeComparator(Comparator<T> comp) {
	
	//change this object's comparator
	public void setComp(Comparator<T> comp) {
		this.comp = comp;
	}//	public void setComp(Comparator<T> comp) {
	
	//allows the list to be iterated over
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			SortedListNode<T> current = head;
			
			@Override
			public boolean hasNext() {
				return current !=null;
			}//public boolean hasNext() {
			
			@Override
			public T next() {
				T t = current.getObject();
				current = current.getNext();
				
				return t;
			}//public T next() {
			
			@Override 
			public void remove() {
				
			}//public void remove() {
		};//new Iterator<T>() {
	}//public Iterator<T> iterator() {
	
	//add an element to the start of the linked list (as the head)
	private void addToStart(T t) {
		if(head == null) {
			SortedListNode<T> newHead = new SortedListNode<T>(t, null, null);
			this.setHead(newHead);
		}//	if(head == null) {
		else {
			if(head.getNext() == null) {
				SortedListNode<T> oldHead = new SortedListNode<T>(head.getObject(), 
											head.getNext(), head.getPrevious());
				SortedListNode<T> newHead = new SortedListNode<T>(t, oldHead, null);
				oldHead.setPrevious(newHead);
				this.setHead(newHead);
				this.setTail(oldHead);
			}//	if(head.getNext() == null) {
			else {
				SortedListNode<T> oldHead = new SortedListNode<T>(head.getObject(), 
											head.getNext(), head.getPrevious());
				SortedListNode<T> newHead = new SortedListNode<T>(t, oldHead, null);
				oldHead.setPrevious(newHead);
				this.setHead(newHead);
			}// if the head is not the only element in the list
		}//if head is not null;
	}//	private void addToStart(T t) {
	
	//add an element to the end of the linked list (as the tail)
	private void addToEnd(T t) {
		if(tail == null) {
			if(head == null) {
				addToStart(t);
			}//if(head == null) {
			else {
				SortedListNode<T> newTail = new SortedListNode<T>(t);
				newTail.setPrevious(head);
				head.setNext(newTail);
				this.setTail(newTail);
			}//if head is NOT null
		}//	if(tail == null) {
		else {
			SortedListNode<T> newTail = new SortedListNode<T>(t, null, null);
			tail.setNext(newTail);
			newTail.setPrevious(tail);
			this.setTail(newTail);
		}// if tail not null
	}//	private void addToEnd(T t) {-
	
	//add a node sorted into the list
		//determine where it should go, then put it there
	public void add(T t) {
		//create the new node
		SortedListNode<T> node = new SortedListNode<T>(t);
		
		//empty list case
		if(head == null) {
			addToStart(t);
		}//if(head == null) {
		else {
			//insert at the head if the new node belongs there
			if(this.comp.compare(head.getObject(), node.getObject()) > 0) {
				addToStart(t);
			}//	if(this.comp.compare(head.getObject(), node.getObject()) < 0) {
			else {
				SortedListNode<T> parentNode = head;
				SortedListNode<T> childNode = head.getNext();
				//loop through the list to see where the new node should go
				while(childNode != null) {
					//if the new node belongs between the parent and the child, then put it there
					if(this.comp.compare(childNode.getObject(), node.getObject()) > 0) {
						/**
						 * to put the node between the parent and the child:
						 * put the parent as the previous node to the new one and the new one as the next of the parent
						 * put the child as the next node to the new one and the new one as the previous of the child
						 */
						node.setPrevious(parentNode);
						node.setNext(childNode);
						parentNode.setNext(node);
						childNode.setPrevious(node);
						break;
					}//	if(this.comp.compare(newNode.getObject(), node.getObject()) < 0) {
					else {
						//if the new node does not belong between the parent and the child, then:
							//check if it belongs between the next pair of nodes in the list
						parentNode = childNode;
						childNode = childNode.getNext();
					}//NOT if(this.comp.compare(newNode.getObject(), node.getObject()) < 0) 
				}//	while(newNode != null) {
				
				if(childNode == null) {
					addToEnd(t);
				}//	if(newNode == null) {
			}//if not this.comp.compare(head.getObject(), node.getObject()) < 0) 
		}//if head not null
	}//	public void add(T t) {
	
	//returns the node at the given position, assumes that head is position 0
	private SortedListNode<T> getNode(int position) {
		
		if(head == null) {
			return head;
		}//if(head == null) {
		else {
			SortedListNode<T> node = this.head;
			
			for(int i = 0; i < position; i++) {
				node = node.getNext();
			}//for(int i = 0; i < position; i++) {
			
			return node;
		}//if head isn't null
	}//	private SortedListNode<T> getNode(int position) {
	
	//iterate through the list until the object at the correct position is found, then output it 
	public T getObject(int position) {
		return getNode(position).getObject();
	}//	public T getObject(int position) {
	
	//get rid of the object at the correct position, but link the list back together in its absence
	public void remove(int position) {
		//add in corner cases
		
		SortedListNode<T> node = getNode(position);
		node.getPrevious().setNext(node.getNext());
		node.getNext().setPrevious(node.getPrevious());
	}//	public void remove(int position) {
	
	//generate a string representation of the objects stored in this list
	@Override
	public String toString() {
		SortedListNode<T> current = this.head;
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		while(current != null) {
			if(current.getNext() == null) {
				sb.append(current.getObject().toString());
			}//	if(current.getNext() == null) {
			else {
				sb.append(current.getObject().toString());
				sb.append(", ");
			}//if the next is NOT null
			current = current.getNext();
		}//	while(current != null) {
		sb.append("]");
		return sb.toString();
	}//	public String toString() {
	
	//search through the linked list for the first object similar to the one given
	public SortedListNode<T> searchFirstObjectLike(T t) {
		SortedListNode<T> current = head;
		while(head != t && head != null) {
			if(current.getObject() == t) {
				return current;
			}//	if(current.getObject() == t) {
			current = current.getNext();
		}//		while(head != t && head != null) {
		return current;
	}//	public T searchObject(T t) {
	
}//public class SortedListOfInvoices {