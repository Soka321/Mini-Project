package csc2b.client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringJoiner;

import acsse.csc03a3.Block;
import acsse.csc03a3.Transaction;
import csc2b.DataStructure.DoublyLinkedList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ShipmentPane extends BorderPane {

	PrintWriter pw= null;
	Socket socket = null; 
	BufferedReader br = null;
	Block<String> block;
	//DataInputStream Din= null;
	//ObjectOutputStream Oin=null;
	
	Button btnConnect = new Button("Connect");
	Button btnDownload = new Button("Download");
	TextField txtHost = new TextField();
	TextField txtPort = new TextField();
	Button btnLogin = new Button("Login");
	TextField txtpassword = new TextField();
	TextField txtUsername = new TextField();
	TextField txtStatus = new TextField();
	TextArea ta= new TextArea(); //Text area used to respond
	GridPane GPane = new GridPane();
	
	//for ordering
		GridPane OrderPane = new GridPane(); //used for login
		Button btnAdd = new Button("Add Order");
		Button btnConfirm = new Button("Confirm Order");
		Button btnDisConnect = new Button("Disconnect");
		TextField txtProductname = new TextField();
		TextField txtKg = new TextField();
		TextField txtQuantity = new TextField();
		TextField txtPrice = new TextField();
		TextField txtProductId = new TextField();
		TextField txtType = new TextField();
		VBox Orderbox = new VBox();
		VBox vorder = new VBox();
		HBox thbox = new HBox();
		
		//for inbox
		TitledPane Infotpane= new TitledPane();
		
		
		
		TextArea inbox= new TextArea(); //Text area used to respond
		VBox hinbox = new VBox();
		
		
		
		//for block viweing
				TitledPane blocktpane= new TitledPane();
				TextArea txtBlock= new TextArea(); //Text area used to respond
		
		// for adding list to block
		//Transaction<String> trans = new Transaction<String>("Admin","Manufacturer",ta.getText());
		
		//for Block chain usage
		//CreateChain chain = new CreateChain();
		Button btnRemove = new Button("Remove Data"); //button used to remove data in the supply chain
		DoublyLinkedList<Transaction<String>> list = new DoublyLinkedList<Transaction<String>>();
		Transaction<String> trans=null;
		//Block<Transaction<String>> block = new Block<Transaction<String>>("Admin",list);
		TitledPane Blocktpane = new TitledPane();
		Button btnSend = new  Button("Send Transaction");
		Button btnBlock = new  Button("Get Blocks");
		Button btntrans = new  Button("Get My transactions");
		
		HBox blockBox = new HBox();
		
		TextField txtIndex = new TextField();

		Button btnClear = new Button("Clear Text");
		
		//blockchain.addBlock(admin.list);
		
		BorderPane Bpane = new BorderPane();
		VBox vbox = new VBox();
		HBox Connectbox = new HBox();
		HBox Confirmbox = new HBox();
		
	public ShipmentPane()
	{
		
		//for connecting
		 Connectbox.getChildren().addAll(btnConnect,btnDisConnect);
		GPane.add(btnConnect/*new Label("Host Name:")*/, 0, 0);
		GPane.add(btnDisConnect, 0, 1);
		GPane.add(new Label("Status:"), 1, 0);
		GPane.add(txtPort, 1, 1);
		//GPane.add(Connectbox, 0, 2);
		//GPane.add(btnConnectMan, 2, 1);
	
		
		//for ordering implementation
		OrderPane.add(new Label("Order here:"), 0, 0);
		OrderPane.add(new Label("Product ID:"), 0, 1);
		OrderPane.add(new Label("Product Name:"), 0, 2);
		OrderPane.add(new Label("Type:"), 0, 3);
		OrderPane.add(new Label("Quantity:"), 0, 4);
		OrderPane.add(new Label("Price:"), 0, 5);
		OrderPane.add(new Label("Kg:"), 0, 6);
		OrderPane.add(txtProductId, 1, 1);
		OrderPane.add(txtProductname, 1, 2);
		OrderPane.add(txtType, 1, 3);
		OrderPane.add(txtQuantity, 1, 4);
		OrderPane.add(txtPrice, 1, 5);
		OrderPane.add(txtKg, 1, 6);
		OrderPane.add(btnAdd, 1, 7);
		vorder.setMaxHeight(getMaxHeight());
		vorder.setMaxWidth(2000);
		Orderbox.getChildren().addAll(OrderPane);
		Orderbox.setAlignment(Pos.TOP_RIGHT);
		Confirmbox.getChildren().addAll(btnClear,btnConfirm,btnSend);
		vorder.getChildren().addAll(new Text("Enter Text & Edit here:"),ta,Confirmbox);
		vorder.setAlignment(Pos.TOP_RIGHT);
		//setLeft(Orderbox);
		vorder.setMaxWidth(3000);
		ta.setPrefWidth(4000);;
		ta.setMaxWidth(3000);
		//for viewing blocks
		blocktpane.setText("View Blocks");
		blocktpane.setContent(txtBlock);		
		//for inbox implementation
		Infotpane.setText("View Inbox Here");
		Infotpane.setContent(inbox);
		blockBox.getChildren().addAll(btnBlock,btntrans);
		
			hinbox.getChildren().addAll(Infotpane,blocktpane,blockBox);
		hinbox.setAlignment(Pos.CENTER_RIGHT);
		setRight(hinbox);
		setBottom(Connectbox);
		///for login
		ta.setText("Product ID:"+ '\t'+ "Product Correct:"+'\t'+"Product Condition:" +'\t' +"Any Issues?:");
		setStyle("-fx-background-color: grey");
		/*LoginPane.add(new Label("User:"), 0, 0);
		LoginPane.add(txtUsername, 0, 1);
		LoginPane.add(new Label("Password:"), 1, 0);
		LoginPane.add(txtpassword, 1, 1);
		LoginPane.add(new Label("Status:"), 0, 0);
		LoginPane.add(txtStatus, 0, 1);
		LoginPane.add(btnLogin, 0, 2);*/
		vbox.setAlignment(Pos.TOP_RIGHT);
		vbox.getChildren().addAll(GPane);
		
		btnConfirm.setOnAction(e->{ NewTransaction();
			
			
		});
		
		btnDisConnect.setOnAction(e-> Disconnect());
		btnAdd.setOnAction(e-> AddOrder());
		btnConnect.setOnAction(e-> Connect());
		
		btnClear.setOnAction(e-> ta.clear());
		btnBlock.setOnAction(e-> getMyTrans());
		btnSend.setOnAction(e->sendOrder());
		//setBottom(btnDownload);
		setTop(vbox);
		setCenter(vorder);
	}

	
	public void AddOrder()
	{
		DoublyLinkedList<String> ll = new DoublyLinkedList<String>();
		
		
		ll.addLast("Product ID:"+'\s' +txtProductId.getText());
		ll.addLast('\s' +"Product name:"+'\s' +txtProductname.getText());
		ll.addLast('\s' +"Type:"+'\s' +txtType.getText());
		ll.addLast('\s' +"Quantity:"+'\s' +txtQuantity.getText());
		ll.addLast('\s' +"Price:"+'\s' +txtPrice.getText());
		ll.addLast('\s' +"Kgs:" +'\s' + txtKg.getText());
		ll.addLast('\s' +"Total cost:" +'\s' + (Double.parseDouble(txtPrice.getText())*Integer.parseInt(txtQuantity.getText())));
		
		//ta.setText("Transaction added");
		for(String t : ll)
		{
			//join.add( t);
			ta.appendText(t);
			
		}
		
		
	}
	// for connecting
	public void Connect()
	{
		//int port = Integer.parseInt(txtPort.getText());
	try {
		socket=new Socket("localhost",2024);
		pw= new PrintWriter(socket.getOutputStream(),true);
		br= new BufferedReader( new InputStreamReader(socket.getInputStream()));
		//Oin=new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		txtPort.setText("Connected!");
		txtPort.setStyle("-fx-background-color: green");
		System.out.println("Connected");
		
		//String str = br.readLine();
		//ta.setText(str);
	} catch (IOException e) {
		
		txtPort.setText("Not Connected!");
		txtPort.setStyle("-fx-background-color: red");
		System.err.println("Could NOT Connect");
	}
	
	}
	
	//creates a new Transaction
			public void NewTransaction()
			{
				 trans = new Transaction<String>("Shipment","Client",ta.getText());
				
				
				//setting the data in the transaction
				trans.setData(ta.getText()); 
				trans.setSender("Shipment");
				trans.setReceiver("client");
				//ll.add(trans);
				list.AddFirst(trans);
				//txtBlock.setText(getBlockTrans());
				
				ta.setText("Transaction added");
				txtBlock.setText(trans.toString());
					
				
				//txtBlock.appendText("From :"+ '\s'+ trans.getSender()+'\s'+ "To:"+ trans.getReceiver());
				//getBlockTrans();
			}
			
			
			public void sendOrder()
			{
				pw.println("SEND"+'\s'+"Shipment");//+trans.toString());//+txtBlock.getText());
				pw.flush();
				AddBlock();
				/*try {
					Oin.writeUTF(trans.toString());
				} catch (IOException e1) {
					System.err.println("Could not send object");
				}*/
				inbox.setText("Message sent");
				
				String response;
				try {
					response = br.readLine();
					inbox.appendText('\s'+'\s'+response);
				} catch (IOException e) {
					inbox.appendText('\s'+'\s'+"Error could not read response from server");
					System.err.println("Error could not read response from server");
				}
				
			
		
			}
			public  DoublyLinkedList<Transaction<String>> getList()
			{
				return list;
			}
			
			//helps to remove a transaction
		public void RemoveData()
		{
			//this text box helps to find the index of data needed to be edited or removed from chain
			String num = txtIndex.getText();
			
			//Sends it to the server
			pw.println("Remove"+'\s'+num);
			pw.flush();
			
			//getting the response
			String response;
			try {
				response=br.readLine();
				inbox.setText(response);
			} catch (IOException e) {
				System.err.println("Error, could not read response");
			
				inbox.setStyle("-fx-text-color: red");
				inbox.setText("response network error, please try again..");
			}
			
		}
		
		public void getMyTrans()
		{
			pw.println("GET"+'\s'+"Shipment"+'\s'+list.toString());
			pw.flush();
			inbox.appendText("Getting transactions from block");
			
			try {
				String mytrans =br.readLine();
				txtBlock.setText(mytrans);//all transactions will be sent and displayed here
			} catch (IOException e) {
				
			txtBlock.setText("Network error, could not read transactions");
			}
		}

		public void Disconnect()
		{
			pw.println("QUIT");
			pw.flush();
			 String str;
			try {
				str = br.readLine();
				inbox.appendText(str);
				pw.close();
				br.close();
				socket.close();
				
				System.err.println("Disconnected");
			} catch (IOException e) {
				System.err.println("Could not disconnect");
			}
			txtPort.setText("DisConnected!");
			txtPort.setStyle("-fx-background-color: red");
			
		}
		//Edits the data in the Blocks
		public Block<String> AddBlock()
		{
			//creating a block for client
			block = new Block<String>("Shipment",list);
				txtBlock.setText("Block for transactions successfully created");
				return block;
				
		}
			
		

}
