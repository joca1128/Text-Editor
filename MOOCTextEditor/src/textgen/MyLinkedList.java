package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		tail.prev = head;
		head.next = tail;
		size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		if(element == null) {
			throw new NullPointerException("element null");
		}
		LLNode<E> temp = new LLNode<E>(element);
		temp.next = tail;
		temp.prev = tail.prev;
		tail.prev = temp;
		temp.prev.next= temp;
		size ++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		E data;
		try {
			data = this.getNode(index).data;
		}
		catch(NullPointerException ex) {
			throw new IndexOutOfBoundsException("index out  of bound");
		}
		return data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		if (index == 0 && size==0 || element==null) this.add(element);
		else {
			LLNode <E> oldNode = this.getNode(index);
			LLNode <E> newNode;
			try {
				newNode = new LLNode<E>(element);
				newNode.prev = oldNode.prev;
				oldNode.prev = newNode;
				newNode.next = oldNode;
				newNode.prev.next = newNode;
			}
			catch(NullPointerException ex) {
				throw new IndexOutOfBoundsException("index out  of bound");
			}
			
			size ++;
		}
		
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		LLNode <E> nodeToRemove;
		E data;
		try {
			nodeToRemove = this.getNode(index);
			nodeToRemove.prev.next = nodeToRemove.next;
			nodeToRemove.next.prev = nodeToRemove.prev;
			data = nodeToRemove.data;
			nodeToRemove = null;
		}
		catch(NullPointerException ex) {
			throw new IndexOutOfBoundsException("index out  of bound");
		}
		size--;
		return data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		LLNode <E> node;
		E data;
		if(element == null) {
			throw new NullPointerException("element null");
		}
		try{
			node = this.getNode(index);
			data = node.data;
			node.data = element;
		}
		catch(NullPointerException ex) {
			throw new IndexOutOfBoundsException("index out  of bound");
		}
		return data;
	}   
	
	private LLNode<E> getNode(int index) {
		if(index>=size || index<0) {
			return null;
		}
		LLNode<E> currentNode ;
		if(index<=size/2) {
			int i = 0;
			currentNode = head.next;
			while(true) {
				if(i==index) {
					return currentNode;
				}
				i++;
				currentNode = currentNode.next;
			}
		}
		else if(index>size/2) {
			int i = size-1;
			currentNode = tail.prev;
			while(true) {
				if(i==index) {
					return currentNode;
				}
				i--;
				currentNode = currentNode.prev;
			}
		}
		System.out.println("");
		return null;
	}
//	public static void main(String [] args) {
//		MyLinkedList <String> list = new MyLinkedList<String>();
//		list.add("Jorge");
//		list.remove(0);
//		list.add(0,"something");
//		for(String s : list) {
//			System.out.print(s+" ");
//		}
//		list.add(0,null);
//		
//	}

}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;
	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
		if(data != null) {
			
		}
	}

}
