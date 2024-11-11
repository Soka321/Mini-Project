import csc2b.client.ClientPane;
import csc2b.client.ManAdmin;
import csc2b.client.ManufacturePane;
import csc2b.client.ShipmentPane;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SuppyManufactuer extends Application {

	public static void main(String[] args) {
		launch(args);

	}
	

	BorderPane Bpane = new BorderPane();
	GridPane LoginPane =new GridPane();
	
	TextField txtpassword = new TextField();
	TextField txtUsername = new TextField();
	TextArea txtStatus = new TextArea();
	GridPane StatusPane =new GridPane();
	
	
	Button btnLogin = new Button("Login");
	HBox hbox = new HBox();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//set up for login scene
				LoginPane.add(new Label("User:"), 0, 0);
				LoginPane.add(txtUsername, 0, 1);
				LoginPane.add(new Label("Password:"), 1, 0);
				LoginPane.add(txtpassword, 1, 1);
				StatusPane.add(new Label(" Connection Status:"), 0, 0);
				StatusPane.add(txtStatus, 0, 1);
				LoginPane.add(btnLogin, 0, 2);
				hbox.getChildren().add(new Label("Welcome, Please login below"));
				hbox.setAlignment(Pos.CENTER);
				LoginPane.setAlignment(Pos.CENTER);
				StatusPane.setAlignment(Pos.CENTER);
				Bpane.setTop(hbox);
				Bpane.setCenter(LoginPane);

				
				//back.se
				//ChainServer server= new ChainServer();
		         ClientPane CPane = new ClientPane();//initializing the clientpane
		         ManAdmin admin = new ManAdmin();
		         ShipmentPane ship = new ShipmentPane();
		         ManufacturePane man = new ManufacturePane();
		         
		         HBox cbox = new HBox();
		         cbox.getChildren().addAll(new Label("Welcome, Please login below"),CPane);
				String strPassword = txtpassword.getText();
				String strUsername = txtUsername.getText();
				Scene scene = new Scene(Bpane,1000,800);
				Scene scene1 = new Scene(cbox,1000,800);
				Scene Manscene= new Scene(man,1000,800);
		            
				
				//Changing scenes for different usersGates
				if(txtUsername.getText().equals("Manufacturer") && txtpassword.getText().equals("321"))
				{
					btnLogin.setOnAction(e -> primaryStage.setScene(Manscene) ); //System.out.print("Go//txtStatus.setText(strUsername + strPassword) );
					
					//primaryStage.setScene(scene1)
					
				}
					
				btnLogin.setOnAction(e -> primaryStage.setScene(Manscene) );
				primaryStage.setTitle("SITHEBE SUPPLY CHAIN: Manufacturer");
				primaryStage.setScene(scene);
				primaryStage.show();
				//Scene scene = new Scene(CPane,800,800);
				
			/**	primaryStage.setTitle("SITHEBE SUPPLY CHAIN");
				primaryStage.setScene(scene);
				primaryStage.show(); */
				
			}
		

}
