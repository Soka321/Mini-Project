package csc2b.Server;

import csc2b.DataStructure.DoublyLinkedList;

import csc2b.client.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import java.util.StringTokenizer;

import acsse.csc03a3.Block;
import acsse.csc03a3.Blockchain;
import acsse.csc03a3.Transaction;

public class ChainHandler implements Runnable {
	private Socket socket= null;
	PrintWriter pw = null;
	BufferedReader br=null;
//ObjectInputStream Objin = null;
	Blockchain<String> chain = null;
	Block<String> block = null; 
	Block<String> block1 = null;
	Block<String> block2 = null;
	Block<String> block3 = null;
	DoublyLinkedList<Transaction<String>> list=null;
	//List<Transaction<String>> ll=null;
	//ManAdmin admin = new ManAdmin();
	public ChainHandler(Socket socket)
	{
		this.socket= socket;
		try {
			pw= new PrintWriter(socket.getOutputStream(),true);
			br= new BufferedReader( new InputStreamReader(socket.getInputStream()));
			//Objin= new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			list = new DoublyLinkedList<Transaction<String>>();
			// ll= new LinkedList<Transaction<String>>();
			chain=new Blockchain<String>();
		} catch (IOException e) {
			
			System.err.println("could not read request");
		}
		
	
	}
	@Override
	public void run() {
		try {
			boolean process = true;
			/*pw.println("Welcome!!!");
			pw.flush();*/
		while(process)	
		{
		
			String RequestFromClient =br.readLine(); //reading request from client
			System.out.println(RequestFromClient);
			StringTokenizer st= new StringTokenizer(RequestFromClient);
			String command = st.nextToken().toUpperCase();
			
			
		    if(command.equals("SEND"))				
			{
		    	String sender = st.nextToken().toUpperCase();
		    	String transactions = st.nextToken();
		    	
		    	
		    	AddCreateBlocks(sender);//varifies client and adds block to block chain
		    	SendTransaction(transactions);//sends transactions to next client
		    	System.out.println("Transaction Successfully added inside block and blockchain:");
		    	pw.println("Transaction Successfully added inside block and blockchain:");
				pw.flush();
		    	
				
				
				
			}
			else if(command.equals("REMOVE"))				
			{
				//this enables admin to remove data from the list of transactions if an error occured
				ShipmentPane ship = new ShipmentPane();
				int index=Integer.parseInt(st.nextToken());//index from textbox
				ship.getList().remove(index);
				//ll.remove(index);
				
				pw.println("Transaction has been removed, transaction no:"+ '\s'+ index );
				pw.flush();
			}
			else if(command.equals("GET"))				
			{
				
				String sender = st.nextToken();
				
				
				//ll.add(admin.getTrans());
				//get the transactions of that sender
			//	block=new Block<String>(sender,list);
			
				
				pw.println("List of transactions:"+ '\s'+ getBlockTrans());
				pw.flush();
			}
			else if(command.equals("QUIT"))
				{
					try {
						System.out.print("Connection Closed!!");
					pw.println("Connection closed!!");
					pw.flush();
					socket.close();
					process=false;
					//Objin.close();
					br.close();
					pw.close();
					process=false;
					}
		
				catch(IOException e)
				{
					System.err.println("Error Unable to Disconnect");
					process=false;
				}
				
			}
			else {
				System.err.println("Could not proccess request/command");
				pw.println("Could not proccess request/command");
				pw.flush();
			}
		}
			
			
			
		} catch (IOException e) {
			System.err.println("could not read request");
		}
		
		

	}
	
	//sends transaction to another client
	public void SendTransaction(String msg)
	{
 		pw.println(msg);
 		pw.flush();
	}
	// varifies client and creates block of transactions and puts them into the chain
	public void AddCreateBlocks(String send)
	{
		
		if(send.equals("SHIPMENT"))
		{
			ShipmentPane ship = new ShipmentPane();
		chain.addBlock(ship.AddBlock().getTransactions());//adds the list of transactions
		System.out.println("Transaction Successfully added inside block and blockchain:");
		//if thechain is valid notify the client
		if(chain.isChainValid())
		{
		pw.println("Transaction Successfully added inside block and blockchain:");
	pw.flush();
		}
		else
		{
			pw.println("blockchain is not valid");
			pw.flush();
		}
		}
		
		 if(send.equals("Client"))
		{
			//creates a new block and adds the list of its transactions
			ClientPane client = new ClientPane();
			chain.addBlock(client.AddBlock().getTransactions());//adds the list of transactions
			System.out.println("Transaction Successfully added inside block and blockchain:");
			//if thechain is valid notify the client
			if(chain.isChainValid())
			{
			pw.println("Transaction Successfully added inside block and blockchain:");
		pw.flush();
			}
			else
			{
				pw.println("blockchain is not valid");
				pw.flush();
			}
			
			}
		else if(send.equals("Admin"))
		{
			//creates a new block and adds the list of its transactions
			ManAdmin admin = new ManAdmin();
			chain.addBlock(admin.AddBlock().getTransactions());//adds the list of transactions
			System.out.println("Transaction Successfully added inside block and blockchain:");
			//if thechain is valid notify the client
			if(chain.isChainValid())
			{
			pw.println("Transaction Successfully added inside block and blockchain:");
		pw.flush();
			}
			else
			{
				pw.println("blockchain is not valid");
				pw.flush();
			}
			
			}
		else if(send.equals("Manufacture"))
		{
			//creates a new block and adds the list of its transactions
			ManufacturePane man = new ManufacturePane();
			//block2 =new Block<String>(send,man.getList());
			//chain.addBlock(man.block2.getTransactions());//adds the list of transactions
			System.out.println("Transaction Successfully added inside block and blockchain:");
			pw.println("Transaction Successfully added inside block and blockchain:");
		pw.flush();
			}
	}
	//getting data from the block
		public String getBlockTrans()
		{
			String transSet = null ;
			for(Transaction<String> t : block.getTransactions())
			{
				//txtBlock.setText((String) t.getData());
				if(t==null)
				{
					pw.println("No transactions available");
					pw.flush();
				}
			transSet=  t.toString();
				
			}
			
			return transSet;
		}

}
