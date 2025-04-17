package Application;

import Gates.*;

import java.util.Arrays;
import java.util.List;

import org.json.simple.parser.ParseException;

import Circuit.Circuit;
import Circuit.CircuitSaver;
import Circuit.Gate;
import Circuit.Unity;
import Circuit.OddParityGate;
import Circuit.EvenParityGate;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
    private double widthOfShape = Unity.x*2;
    // private final int height = Unity.height;
    private final int height = 1080;
    // private final int width = Unity.width;
    private final int width = 1920;
    private final int x = Unity.x;
    private final int y = Unity.y;

    private int nbOfButtonSelected = -1;
    private List <Button> buttons = Arrays.asList(new Button[]{ 
        createButton("AND"),
        createButton("OR"),
        createButton("XOR"),
        createButton("NAND"),
        createButton("NOR"),
        createButton("XNOR"),
        createButton("NOT"),
        createButton("VARIABLE"),
        createButton("OUTPUT"),
        createButton("ADDER"),
        createButton("MULTIPLIER"),
        createButton("ODDPARITY"),
        createButton("EVENPARITY"),
        createButton("BASCULE RS")
    });

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
    // Étape 1 : Créer la scène avec le logo
    Image logoImage = new Image(getClass().getResourceAsStream("/pictures/logo.jpeg"));
    ImageView logoView = new ImageView(logoImage);
    logoView.setFitHeight(300); // adapte la taille
    logoView.setPreserveRatio(true);
    Pane logoPane = new Pane(logoView);
    logoPane.setStyle("-fx-background-color: white;");
    logoView.setLayoutX(350); // tu peux ajuster le centrage
    logoView.setLayoutY(150);

    Scene splashScene = new Scene(logoPane, 1200, 600); // taille de l'écran d'accueil
    window.setScene(splashScene);
    window.setTitle("LOGISIM");
    window.show();

    // Étape 2 : Attendre 3 secondes
    PauseTransition delay = new PauseTransition(Duration.seconds(3));
    delay.setOnFinished(event -> {
        try {
            // Étape 3 : lancer l'app principale
            launchMainApp(window); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        });
    delay.play();

    }

    
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
        

        // String enteredByUser = "abcdef";
        // sp.setStyle("-fx-background-color: #" + enteredByUser);

        // sp.setMinSize(Unity.width - widthOfButton - widthOfShape, Unity.height);
        sp.setMinSize(width - widthOfButton - widthOfShape, height);
        sp.setMaxWidth(Region.USE_PREF_SIZE);
        sp.setMaxHeight(Region.USE_PREF_SIZE);

        sp.setPrefWidth(width*4);
        sp.setPrefHeight(height*4);

        Circuit circuit = new Circuit(sp);
        
        sp.setOnMouseClicked(e -> addItem(e, circuit, sp));

        VBox hb = new VBox();
        // hb.setStyle("-fx-background-color: black");
        hb.setPrefWidth(widthOfButton + widthOfShape);
        hb.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            BorderWidths.DEFAULT)));

        for(Button btn : buttons){
            btn.setOnMouseClicked(e -> setNbOfGateSelected(btn));
            // btn.setPrefWidth(widthOfButton);
            hb.getChildren().add(btn);
        }

        Button rotate = new Button("Rotate Item");
        hb.getChildren().add(rotate);
        rotate.setOnMouseClicked(e -> circuit.rotateSelectedEement());

        Button scale = new Button("Zoom");
        hb.getChildren().add(scale);
        scale.setOnMouseClicked(e -> circuit.zoom());

        Button unScale = new Button("Unzoom");
        hb.getChildren().add(unScale);
        unScale.setOnMouseClicked(e -> circuit.unzoom());


        CircuitSaver saver = new CircuitSaver(circuit);

        Button save = new Button("Save");
        hb.getChildren().add(save);
        save.setOnMouseClicked(e -> AfficherFenetreNom(saver));


        Button load = new Button("Load");
        hb.getChildren().add(load);
        load.setOnMouseClicked(e -> {
            try {
                saver.loadCircuit("liti.json");
                System.out.println("Ficher Télecharger");
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        });

        BorderPane border = new BorderPane();
        border.setLeft(hb);
        border.setCenter(scroll);

        BorderPane.setMargin(scroll, new Insets(Unity.x));

        scene = new Scene(border, Unity.width, Unity.height);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent key) {
                if(key.getCode() == KeyCode.DELETE){
                    circuit.removeSelectedGate();
                } else if (key.isControlDown() && key.getCode() == KeyCode.S) {
                    AfficherFenetreNom(saver);
                }
            }
            
        });
        window.setScene(scene);
        window.show();
    }

    private void addItem(MouseEvent e, Circuit circuit, Pane pane){
        double x = Unity.tranformDoubleToInt(e.getX());
        double y = Unity.tranformDoubleToInt(e.getY());
        Gate gate;
        switch (nbOfButtonSelected) {
            case 0:
                gate = new And(2, circuit, pane, x, y);
                circuit.addGate(gate);
                break;
    
            case 1:
                gate = new Or(2, circuit, pane, x, y);
                circuit.addGate(gate);
                break;

            case 2:
                gate = new Xor(2, circuit, pane, x, y);
                circuit.addGate(gate);
                break;
            
            case 3:
                gate = new Nand(2, circuit, pane, x, y);
                circuit.addGate(gate);
                break;
            
            case 4:
                gate = new Nor(2, circuit, pane, x, y);
                circuit.addGate(gate);
                break;
            
            case 5:
                gate = new Xnor(2, circuit, pane, x, y);
                circuit.addGate(gate);
                break;
            
            case 6:
                gate = new Not(circuit, pane, x, y);
                circuit.addGate(gate);
                break;

            case 7:
                gate = new Variable(circuit, pane, x, y);
                circuit.addVariable(gate);
                break;
            
            case 8:
                gate = new OutputGate(circuit, pane, x, y);
                circuit.addGate(gate);
                break;

            case 9:
                gate = new Adder(circuit, pane, x, y);
                circuit.addGate(gate);
	        	break;

            case 11:
                gate = new OddParityGate(circuit, pane,x,y,3);
                circuit.addGate(gate);
                break;

            case 10:
                gate = new Multiplier(circuit, pane,x,y);
                circuit.addGate(gate);
                break;

            case 12:
                gate = new EvenParityGate(circuit, pane,x,y,3);
                circuit.addGate(gate);
                break;
                
            case 13:
                gate = new Bascule_RS(circuit, pane,x,y);
                circuit.addGate(gate);
                break;
            default:
                break;

            }

        if(nbOfButtonSelected >= 0){
            buttons.get(nbOfButtonSelected).setStyle("-fx-background-color : white;");
        }
        nbOfButtonSelected = -1;
    }

    private void setNbOfGateSelected(Button i){
        int tmp = buttons.indexOf(i);
        if(tmp == nbOfButtonSelected){
            nbOfButtonSelected = -1;
            i.setStyle("-fx-background-color : white;");

        }
        else{
            if(nbOfButtonSelected >= 0){
                buttons.get(nbOfButtonSelected).setStyle("-fx-background-color : white;");
            }
            nbOfButtonSelected = tmp;
            i.setStyle("-fx-background-color : rgb(219, 219, 219);");
        }
    }

    private Button createButton(String str){
        Button btn = new Button(str);

        if(!str.equals("VARIABLE") && !!str.equals("MULTIPLIER") && str.equals("EvenParityGate") && !str.equals("ADDER") && !str.equals("EVENPARITY")){
            Image img = new Image(getClass().getResourceAsStream("/pictures/"  + str.toLowerCase() + ".png"));
            ImageView view = new ImageView(img);
            view.setFitHeight(widthOfShape);
            view.setPreserveRatio(true);
    
            btn.setGraphic(view);
        }
        btn.setStyle("-fx-background-color: white");
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setMaxHeight(Double.MAX_VALUE);
        btn.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            BorderWidths.DEFAULT)));
        return btn;
    }

}

