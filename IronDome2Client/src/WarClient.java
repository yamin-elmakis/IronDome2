import java.io.IOException;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
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
	ChoiceBox<String> launchers, destinations;
	private int width, height;
	private double headerHeight;
	private VBox headerPane, launcherPane, missilePane;
	private boolean threadRead;

	private void init(Stage primaryStage) {

		Group root = new Group();
		width = 700; 
		height = 400;
		Scene scene = new Scene(root);
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
    	HBox hbLauncherTitle = new HBox();
    	hbLauncherTitle.setAlignment(Pos.CENTER);
    	Label lblTitle = new Label("Add Launcher");
        lblTitle.setId("title");
        hbLauncherTitle.getChildren().addAll(lblTitle);
        
        HBox hbLauncherState = new HBox();
        hbLauncherState.setAlignment(Pos.CENTER);
        hbLauncherState.setSpacing(10);
        bAddLauncher = new Button("Add Launcher");
        bAddLauncher.setOnAction(new AddLauncherPressed());
        hbLauncherState.getChildren().addAll(bAddLauncher);
        
        VBox vbLauncher = new VBox();
        vbLauncher.setId("vb_launcher");
        vbLauncher.setSpacing(20);
        vbLauncher.getChildren().addAll(hbLauncherTitle, hbLauncherState);
 
    	return vbLauncher;
    }
    
    public VBox addLaunchMissilePane(){
    	HBox hbTitle = new HBox();
    	hbTitle.setAlignment(Pos.CENTER);
		Label lblTitle = new Label("Launch Missile");
	    lblTitle.setId("title");
	    hbTitle.getChildren().addAll(lblTitle);
	    
	    HBox hbChooseLauncher = new HBox();
	    hbChooseLauncher.setAlignment(Pos.CENTER);
	    hbChooseLauncher.setSpacing(10);
	    Label lblState = new Label("Launcher: ");
	    launchers = new ChoiceBox<String>();
	    hbChooseLauncher.getChildren().addAll(lblState, launchers);
	    
	    HBox hbChooseDestination = new HBox();
	    hbChooseDestination.setAlignment(Pos.CENTER);
	    hbChooseDestination.setSpacing(10);
	    Label lblDest = new Label("Destination: ");
	    destinations = new ChoiceBox<String>();
	    hbChooseDestination.getChildren().addAll(lblDest, destinations);
	    
	    
	    HBox hbButton = new HBox();
	    hbButton.setAlignment(Pos.CENTER);
    	bShootMissile = new Button("Launch");
	    bShootMissile.setOnAction(new ShootMissilePressed());
	    hbButton.getChildren().addAll(bShootMissile);
	    
	    VBox vbMissile = new VBox();
	    vbMissile.setId("vb_missile");
	    vbMissile.setSpacing(20);
	    vbMissile.getChildren().addAll(hbTitle, hbChooseLauncher, hbChooseDestination, hbButton);
	
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
		if (launchers.getItems().size() > 0 || disable)
			bShootMissile.setDisable(disable);
	}
	
	class ConnectToServerPressed implements EventHandler<ActionEvent> {

		private boolean connected = false;
		
		@Override
		public void handle(ActionEvent event) {
			if (connected){
				disableButtons(true);
				threadRead = false;
				console.getItems().add(0,"Dis-Connecting from Server...");
				SocketObject obj = new SocketObject(ObjType.disConnect, "bye from client"); 
				handleSend(obj);
				bConnect.setDisable(true);
			}else{
				console.getItems().add(0,"Connecting to Server...");
				bConnect.setText("Dis-Connect from Server");
				try {
					disableButtons(false);
					socket = new SocketData(new Socket("localhost", 7000));
					handleRead();
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
			handleSend(obj);
		}
	}
	
	class ShootMissilePressed implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			if (launchers.getItems().size() == 0 || destinations.getItems().size() == 0)
				return;
			SocketObject obj = new SocketObject(ObjType.shootMissile, "shoot missile"); 
			obj.setLauncherId(launchers.getSelectionModel().getSelectedItem().toString());
			obj.setDestination(destinations.getSelectionModel().getSelectedItem().toString());
			handleSend(obj);
		}
	}

	private void handleRead(){
		threadRead = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (threadRead) {
					synchronized (socket.getInputStream()) {
						SocketObject obj = socket.readData();
						showMessage(obj.getMessage());
						switch (obj.getType()) {
						case newLauncher:
							addLauncherID(obj.getLauncherId());
							break;
						case launcherDestroied:
							subLauncherID(obj.getLauncherId());
							break;
						case disConnect:
							socket.closeConnection();
							break;
						case connect:
							addCityNames(obj.getCityNames());
							SocketObject conObj = new SocketObject(ObjType.connect, "Hello from Client");
							handleSend(conObj);
							break;	
						}
					}//synchronized
				}// while
			}
		}).start();; // Thread
	}
	
	public synchronized void handleSend(SocketObject obj){
		if (socket.getSocket().isConnected()){
			socket.sendData(obj);
		}
	}
	
    private void showMessage(String text) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				console.getItems().add(0,text);
			}
		});// Platform
	}

	private void addCityNames(String[] cityNames) {
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
				destinations.getItems().addAll(cityNames);
				destinations.getSelectionModel().selectFirst();
			}
		});// Platform
	}
    
    private void subLauncherID(String launcherId) {
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
				launchers.getItems().remove(launcherId);
				if (launchers.getItems().size() == 0 && socket.getSocket().isConnected())
					bShootMissile.setDisable(true);
				if (launchers.getItems().size() == 1)
					launchers.getSelectionModel().selectFirst();
			}
		});// Platform
	}
    
    private void addLauncherID(String launcherID) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				launchers.getItems().add(launcherID);
				if (launchers.getItems().size() > 0 && socket.getSocket().isConnected())
					bShootMissile.setDisable(false);
				if (launchers.getItems().size() == 1)
					launchers.getSelectionModel().selectFirst();
			}
		});// Platform
	}

	public static void main(String[] args) { 
		launch(args); 
	}

	@Override 
	public void start(Stage primaryStage) {
	    init(primaryStage);
	    primaryStage.show();
	    headerHeight = headerPane.getHeight();
	    console.setMaxHeight(height - headerHeight);
	}
    
}