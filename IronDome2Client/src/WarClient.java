import java.io.IOException;
import java.net.Socket;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import TzukEitan.clientServer.SocketData;
import TzukEitan.clientServer.SocketData.ObjType;
import TzukEitan.clientServer.SocketObject;

public class WarClient extends Application {

	private SocketData socket;
	
	private ListView<String> console;
	private Button bConnect, bAddLauncher, bShootMissile;
	private int width, height;
	private double headerHeight;
	private VBox headerPane, launcherPane, missilePane;

	private void init(Stage primaryStage) {

		Group root = new Group();
		width = 700; 
		height = 400;
		Scene scene = new Scene(root, width, height);
		scene.getStylesheets().add(WarClient.class.getResource("ClientCSS.css").toExternalForm());

		primaryStage.setResizable(false);
		primaryStage.setScene(scene);

		BorderPane borderPane = new BorderPane();

		// top content
		headerPane = addHaederPane();
		borderPane.setTop(headerPane);
		
		// Left content
		launcherPane = addLauncherPane();
		borderPane.setLeft(launcherPane);

		// Right content
		missilePane = addLaunchMissilePane();
		borderPane.setRight(missilePane);

		// Center content
		console = addTextConsole();
		borderPane.setCenter(console);

		root.getChildren().add(borderPane);
		
		disableButtons(true);
	}

    public VBox addHaederPane(){
    	Label mainLable = new Label("Hamas Controll Center");
		mainLable.setId("main_lable");
		HBox hbTitle = new HBox();
		hbTitle.setAlignment(Pos.CENTER);
		hbTitle.getChildren().add(mainLable);
		
		bConnect = new Button("Connect to Server");
		bConnect.setOnAction(new ConnectToServerPressed());
		HBox hbConnect = new HBox();
		hbConnect.setAlignment(Pos.CENTER);
		hbConnect.getChildren().add(bConnect);
		
    	VBox vbHeader = new VBox();
    	vbHeader.setSpacing(20);
    	vbHeader.getChildren().addAll(hbTitle, hbConnect);
    	headerHeight = vbHeader.getHeight();
    	return vbHeader;
    }
    
    public VBox addLauncherPane(){
    	Label lblTitle = new Label("Add Launcher");
        lblTitle.setId("title");
        
        HBox hbLauncherState = new HBox();
        hbLauncherState.setAlignment(Pos.CENTER);
        hbLauncherState.setSpacing(10);
        Label lblState = new Label("Launcher State: ");
        CheckBox state = new CheckBox("Visible");
        state.setSelected(true);
        
        
        bAddLauncher = new Button("Add Launcher");
        bAddLauncher.setOnAction(new AddLauncherPressed());
        hbLauncherState.getChildren().addAll(bAddLauncher);
        
        VBox vbLauncher = new VBox();
        vbLauncher.setId("vb_launcher");
        vbLauncher.setSpacing(20);
        vbLauncher.getChildren().addAll(lblTitle, hbLauncherState);
 
    	return vbLauncher;
    }
    
    public VBox addLaunchMissilePane(){
		Label lblTitle = new Label("Launch Missile");
	    lblTitle.setId("title");
	    
	    HBox hbChooseLauncher = new HBox();
	    hbChooseLauncher.setAlignment(Pos.CENTER);
	    hbChooseLauncher.setSpacing(10);
	    Label lblState = new Label("Launcher: ");
	    ChoiceBox<String> launchers = new ChoiceBox<String>();
	    launchers.getItems().addAll("l1", "l2", "l3");
	    launchers.getSelectionModel().selectFirst();
	    hbChooseLauncher.getChildren().addAll(lblState, launchers);
	    
	    HBox hbChooseDestination = new HBox();
	    hbChooseDestination.setAlignment(Pos.CENTER);
	    hbChooseDestination.setSpacing(10);
	    Label lblDest = new Label("Destination: ");
	    ChoiceBox<String> destinations = new ChoiceBox<String>();
	    destinations.getItems().addAll("city1", "city2", "city3");
	    destinations.getSelectionModel().selectFirst();
	    hbChooseDestination.getChildren().addAll(lblDest, destinations);
	    
	    bShootMissile = new Button("Launch");
	    bShootMissile.setOnAction(new ShootMissilePressed());
	    
	    VBox vbMissile = new VBox();
	    vbMissile.setId("vb_missile");
	    vbMissile.setSpacing(20);
	    vbMissile.getChildren().addAll(lblTitle, hbChooseLauncher, hbChooseDestination, bShootMissile);
	
		return vbMissile;
	}

	public ListView<String> addTextConsole() {
		ListView<String> consoleList = new ListView<String>(FXCollections.<String> observableArrayList());
		// listen on the console items and remove old ones when we get over 30 items
		consoleList.getItems().addListener(new ListChangeListener<String>() {
			@Override
			public void onChanged(Change<? extends String> change) {
				while (change.next()) {
					if (change.getList().size() > 30)
						change.getList().remove(0);
				}
			}
		});
		return consoleList;
	}
    
	private void disableButtons(boolean disable){
		bAddLauncher.setDisable(disable);
		bShootMissile.setDisable(disable);
	}
	
	class ConnectToServerPressed implements EventHandler<ActionEvent> {

		private boolean connected = false;
		
		@Override
		public void handle(ActionEvent event) {
			if (connected){
				disableButtons(true);
				console.getItems().add(0,"Dis-Connecting from Server...");
				SocketObject obj = new SocketObject(); 
				obj.setType(ObjType.disConnect);
				obj.setMessage("bye from client");
				socket.sendData(obj);
				obj = socket.readData();
				if (obj.getType() == ObjType.disConnect){
					console.getItems().add(0,obj.getMessage());
					socket.closeConnection();
					console.getItems().add(0,"disconnected.");
				}
				bConnect.setText("Connect to Server");
			}else{
				console.getItems().add(0,"Connecting to Server...");
				bConnect.setText("Dis-Connect from Server");
				try {
					disableButtons(false);
					socket = new SocketData(new Socket("localhost", 7000));
					SocketObject obj = new SocketObject();
					obj = new SocketObject();
					obj = socket.readData();
					if (obj.getType() == ObjType.connect)
						console.getItems().add(0,obj.getMessage());
					obj.setType(ObjType.connect);
					obj.setMessage("Hello from client");
					socket.sendData(obj);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			connected = !connected;
		}
	}
	
    class AddLauncherPressed implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			SocketObject obj = new SocketObject(); 
			obj.setType(ObjType.newLauncher);
			obj.setMessage("add launcher");
			socket.sendData(obj);
			obj = socket.readData();
			if (obj.getType() == ObjType.newLauncher)
				console.getItems().add(0,obj.getMessage());
		}
	}
	
	class ShootMissilePressed implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			
			SocketObject obj = new SocketObject(); 
			obj.setType(ObjType.shootMissile);
			obj.setMessage("shoot missile");
			socket.sendData(obj);
			obj = socket.readData();
			if (obj.getType() == ObjType.shootMissile)
				console.getItems().add(0,obj.getMessage());
		}
	}

    @Override public void start(Stage primaryStage) {

        init(primaryStage);
        primaryStage.show();
        headerHeight = headerPane.getHeight();
        console.setMaxHeight(height - headerHeight);
    }

    public static void main(String[] args) { 
    	launch(args); 
    }
}