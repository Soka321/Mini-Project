package csc2b.DataStructure;

import java.util.Collection;


import java.util.Iterator;
import java.util.*;
import java.util.ListIterator;

/**
 * 
 * @author 219001589 S. Sithebe
 * Class used to create the doublylinked list data structure to store blocks of the blockchain
 *
 * @param <T>
 */
public class DoublyLinkedList<T> extends LinkedList<T>  {
    
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Class to create Nodes for the list
	 */
	@SuppressWarnings("hiding")
	public class Node<T>
	{
		private T Element;
	    private Node<T> next;
	    private Node<T> prev;
	    
	    
	     //initializing variables
		public Node(Node<T> next,  Node<T> prev,
				T elem) {
			this.Element=elem;
			this.next=next;
			this.prev=prev;
		}
		
		public Node<T> getNext()
		{
			return next;
		}
		
		public Node<T> getPrev()
		{
			return prev;
		}
		
		public T getElement()
		{
			return Element;
		}
		
		public void setElement(T elem)
		{
			this.Element=elem;
		}
		
		public void setNext(Node<T> next)
		{
	this.next=next;	}
		
		public void setPrev(Node<T> prev)
		{
			this.prev=prev;
		}
	}
	
	private int size=0;
    private Node<T> header;
    private Node<T> trailer;
    
   public DoublyLinkedList()
   {
	   header= new Node<>(null,null,null);
	   trailer= new Node<>(null,header,null);
	   header.setNext(trailer);
   }
   
   public int Size()
   {
	   return size;
   }
    
 public boolean IsEmpty()
 {
	 return size==0;
 }
 
 public T first()
 {
	 if(IsEmpty())
	 {
		 System.err.print("Error, List is Empty");
	 }
	 
	 return header.getNext().getElement();
 }
 
 public T last()
 {
	 if(IsEmpty())
	 {
		 System.err.print("Error, List is Empty");
	 }
	 
	 return trailer.getPrev().getElement();
 }
 
 public void addBetween(Node<T> next,  Node<T> prev,
				T elem)
 {
	 Node<T> node = new Node<T>(next, prev, elem);  //create a new node
	 next.setPrev(node);  //put before header
	 prev.setNext(node);// put after the previous node
	size++;
		
 }
 
public void AddFirst(T e)
{
	addBetween(header.getNext(),header,  e);
	
}
public void AddLast(T e)
{
	addBetween( trailer,trailer.prev, e);
	
}
 
 public T RemoveFirst()
 {
	 if(IsEmpty()) 
		{
			return null;
		}
	return remove(header.getNext());
 }
 
 public T RemoveLast()
 {
	if(IsEmpty()) 
	{
		return null;
	}
return remove(trailer.getPrev());
 } 
 
 public T remove(Node<T> node)
 {
	 Node<T> pre= node.getPrev();
	 Node<T> access=node.getNext();
	 pre.setNext(access);
	 access.setPrev(pre);
	 size--;
	 
	 
	 return node.getElement();
 }


}
 

