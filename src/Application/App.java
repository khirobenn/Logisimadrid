package Application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import Circuit.Circuit;
import Circuit.CircuitSaver;
import Circuit.Unity;
import Circuit.NouveauComposant ;
import Circuit.ComposantLoad ;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class App extends Application{
    Scene scene;
    private double widthOfButton = Unity.x*5;
    private double widthOfShape = Unity.x*10;
    // private final int height = Unity.height;
    private final int height = 1080;
    // private final int width = Unity.width;
    private final int width = 1920;
    private final NouveauComposant cmp = ComposantLoad.chargerComp("./Composants_Json/Ajout_Comp.json") ; // on a le chemin de fichier
    String name;

    // -----------

    // List<Circuit> myCircuits = new ArrayList<Circuit>();

    
    public static void main(String[] args) throws Exception {
        launch(args);
    }
    
    @Override
    public void init() throws Exception{
        super.init();
    }

    private void AfficherFenetreNom ( CircuitSaver saver ) {
        Stage pop = new Stage() ;
        pop.initModality(Modality.APPLICATION_MODAL);
        pop.setTitle ("File Name");
        Label l = new Label("Give a Name :") ;
        TextField n = new TextField();
        Button OK = new Button("OK") ;
        OK.setOnAction( e-> {
          String nF = n.getText().trim() ;
          if ( ! nF.isEmpty()  )  {
  
              if ( !nF.endsWith(".json")) {
                  nF += ".json" ;
              }
              saver.saveCircuit(nF);
              System.out.println("Fichier sauvegarde :"+nF );
              pop.close(); 
          }
          else {
              Alert a = new Alert(Alert.AlertType.WARNING , "You didn't give a name !!") ;
              a.showAndWait();
          }
  
        }); 
  
        VBox ly = new VBox(10,l,n,OK) ;
        ly.setPadding(new Insets(15));
        Scene scene2 = new Scene(ly,200 , 200 ) ;
  
        pop.setScene(scene2);
        pop.showAndWait();
  
       }
       @Override
    public void start(Stage window) throws Exception {
        // Ajouter une icone
        Image icon = new Image(getClass().getResourceAsStream("/pictures/icon.png"));
        window.getIcons().add(icon);
    
        
        Image logoImage = new Image(getClass().getResourceAsStream("/pictures/logo.jpeg"));
        ImageView logoView = new ImageView(logoImage);
        logoView.setFitHeight(300); 
        logoView.setPreserveRatio(true);
        Pane logoPane = new Pane(logoView);
        logoPane.setStyle("-fx-background-color: white;");
        logoView.setLayoutX(Unity.width/2 - logoView.getLayoutBounds().getMaxX()/2); 
        logoView.setLayoutY(Unity.height/2 - logoView.getLayoutBounds().getMaxY()/2);

        Scene splashScene = new Scene(logoPane, Unity.width, Unity.height); 
        window.setScene(splashScene);
        window.setTitle("LOGISIM");
        window.setResizable(false);
        window.show();        
        
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> {
            try {
                window.close();
                window.setResizable(true);
                window.show();
                window.setMaximized(true);
                launchMainApp(window); 
            } catch (Exception e) {
                e.printStackTrace();
            }
            });
        delay.play();
    }

    
    @SuppressWarnings("unchecked")
    private void launchMainApp(Stage window) throws Exception {
        Pane sp = new Pane();

        Pane pane2 = new Pane();
        pane2.setMaxWidth(width);
        pane2.setMaxHeight(height);
        pane2.getChildren().add(sp);

        ScrollPane scroll = new ScrollPane();
        scroll.setMinHeight(Unity.height/2);
        scroll.setMinWidth(Unity.width/2);
        
        scroll.setMaxWidth(width);
        scroll.setMaxHeight(height);
        scroll.setContent(pane2);
        
        sp.setMinSize(width - widthOfButton - widthOfShape, height);
        sp.setMaxWidth(Region.USE_PREF_SIZE);
        sp.setMaxHeight(Region.USE_PREF_SIZE);

        sp.setPrefWidth(width*4);
        sp.setPrefHeight(height*4);

        Circuit circuit = new Circuit(sp);
        sp.setOnMouseClicked(e -> addItem(e, circuit, sp));


        // Tree Items
        TreeItem<Label> allTree = new TreeItem<Label>();
        allTree.getChildren().addAll(Unity.portesTree(), Unity.varAndOutTree(), Unity.bitABitTree(), Unity.basculesTree());

        TreeView<Label> rootTree = new TreeView<Label>(allTree);
        rootTree.setShowRoot(false);
        rootTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setGateName(newValue.getValue().getText());
        });


        CircuitSaver saver = new CircuitSaver(circuit);

        // -----------

        MenuBar menu = createMenu(circuit, saver);

        // -----------

        VBox vb = new VBox();
        Label label = new Label("Ajout/Suppression d'entrées");
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setFont(Unity.fontBold);

        HBox hb = new HBox();
        
        Button decreaseInput = new Button("-");
        decreaseInput.setOnMouseClicked(e -> circuit.decreaseInputs());
        
        Button increaseInput = new Button("+");
        increaseInput.setOnMouseClicked(e -> circuit.increaseInputs());
        
        hb.getChildren().addAll(decreaseInput, increaseInput);
        vb.getChildren().addAll(label, hb);

        Slider sliderZoom = new Slider(Unity.minZoom, Unity.maxZoom, 1.);
        sliderZoom.setShowTickLabels(true);
        sliderZoom.setShowTickMarks(true);
        sliderZoom.setBlockIncrement(10.f);
        sliderZoom.valueProperty().addListener((observable, oldValue, newValue) -> {
            circuit.setZoom(newValue.doubleValue());
        });




        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.getItems().addAll(rootTree, vb, sliderZoom);
        splitPane.setPrefWidth(widthOfButton + widthOfShape);
        splitPane.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            BorderWidths.DEFAULT)));


        BorderPane border = new BorderPane();
        border.setLeft(splitPane);
        border.setCenter(scroll);
        border.setTop(menu);

        BorderPane.setMargin(scroll, new Insets(Unity.x));

        scene = new Scene(border, Unity.width, Unity.height);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent key) {
                if(key.getCode() == KeyCode.DELETE){
                    circuit.removeSelectedGate();
                } else if (key.isControlDown() && key.getCode() == KeyCode.S) {
                    AfficherFenetreNom(saver);
                } else if (key.isControlDown() && key.getCode() == KeyCode.EQUALS) {
                    circuit.zoom(); 
                } else if (key.isControlDown() && key.getCode() == KeyCode.MINUS) {
                    circuit.unzoom();  
                } else if (key.isControlDown() && key.getCode() == KeyCode.L){
                    AfficherTelechargement(saver);
                }else if (key.isControlDown() && key.getCode() == KeyCode.C){
                    circuit.clearAll();
                }    
            }
            
        });
        window.setScene(scene);
    }

    private void setGateName(String name){
        this.name = name;
    }

    private void addItem(MouseEvent e, Circuit circuit, Pane pane){
        double x = Unity.tranformDoubleToInt(e.getX());
        double y = Unity.tranformDoubleToInt(e.getY());
        if(name != null){
            circuit.createGate(name.toUpperCase(), 2, x, y);
        }
        name = null;
    }

    private void AfficherTelechargement ( CircuitSaver saver ) {
        Stage pop = new Stage() ;
        pop.initModality(Modality.APPLICATION_MODAL);
        pop.setTitle ("File Name");
        Label l = new Label("Give a Name :") ;
        TextField n = new TextField();
        Button OK = new Button("OK") ;
        OK.setOnAction( e-> {
          String nF = n.getText().trim() ;
          if ( ! nF.isEmpty()  )  {
  
              if ( !nF.endsWith(".json")) {
                  nF += ".json" ;
              }
              //saver.saveCircuit(nF);
              File fichier = new File(nF) ;
              if ( fichier.exists()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION,"LE FICHIER EXISTE:"+nF) ;
                try {
                    saver.loadCircuit(nF);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                a.showAndWait();
              }
              else {
                Alert a = new Alert(Alert.AlertType.INFORMATION,"LE FICHIER N EXISTE PAS: "+nF) ;
                a.showAndWait() ;
              }
           
              pop.close(); 
          }
          else {
              Alert a = new Alert(Alert.AlertType.WARNING , "You didn't give a name !!") ;
              a.showAndWait();
          }
  
        }); 
  
        VBox ly = new VBox(10,l,n,OK) ;
        ly.setPadding(new Insets(15));
        Scene scene2 = new Scene(ly,200 , 200 ) ;
  
        pop.setScene(scene2);
        pop.showAndWait();
  
       }

    private MenuBar createMenu(Circuit circuit, CircuitSaver saver){
        MenuBar menu = new MenuBar(); 

        Menu file = new Menu("File");
        MenuItem save = new MenuItem("Save");
        MenuItem load = new MenuItem("Load");
        MenuItem clear = new MenuItem("Clear");

        file.getItems().addAll(save, load, clear);
        save.setOnAction(e -> AfficherFenetreNom(saver));
        load.setOnAction(e -> AfficherTelechargement(saver));
        clear.setOnAction(e -> circuit.clearAll());

        // ---

        Menu view = new Menu("View");

        MenuItem zoom = new MenuItem("Zoom in");
        MenuItem zoomOut = new MenuItem("Zoom out");

        view.getItems().addAll(zoom, zoomOut);
        zoom.setOnAction(e -> circuit.zoom());
        zoomOut.setOnAction(e -> circuit.unzoom());


        menu.getMenus().addAll(file, view);
        return menu;
    }

}



