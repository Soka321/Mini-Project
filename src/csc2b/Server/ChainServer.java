package csc2b.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class ChainServer {


	/**ShipmentPane ship = new ShipmentPane();
	ManAdmin admin = new ManAdmin();
	public Blockchain<Transaction<String>> blockchain = new Blockchain<>();
	public List<Transaction<String>> list = new ArrayList<Transaction<String>>();
	public Block<String> Adblock = new Block<String>("Admin",list);*/
	

	//Create Server
	public static void main(String[] args)
	{
	
	ServerSocket ss= null;
	Socket Clientsocket = null;
	Socket Adminsocket = null;
	Socket Shipsocket = null;
	Socket ManSocket = null;
	
	try {
		System.out.println("Wating for connection from client...");
		ss = new ServerSocket(2024);
		Clientsocket= ss.accept();
		System.out.println("Connected to a client 1!" + new Date());
		
		Request(Clientsocket);
		
		Adminsocket = ss.accept();
		System.out.println("Connected to a client 2!" + new Date());
		Request(Adminsocket);
		Shipsocket = ss.accept();
		System.out.println("Connected to a client 3!" + new Date());
		Request(ManSocket);
		ManSocket = ss.accept();
		
		
	//	Request(Adminsocket);
		//Request(Shipsocket);
		//Request(ManSocket);
		System.out.println("Connected to a client 4!" + new Date());
		Request(Shipsocket);
	}
		
		catch(UnknownHostException e)
	{
		System.err.print("Could not find correct host");
	}
		catch(IOException ex)
		{
			System.err.print("Could not connect");
		}	
	}
	


	public static void Request(Socket socket)
	{
		boolean start=true;
		while(start)
		{
			Thread thread = new Thread(new  ChainHandler(socket));
			Thread thread1 = new Thread(new  ChainHandler(socket));
			Thread thread2 = new Thread(new  ChainHandler(socket));
			Thread thread3 = new Thread(new  ChainHandler(socket));
			thread.start();
			thread1.start();
			thread2.start();
			thread3.start();
			
			//start=false;
		}
	}
}

