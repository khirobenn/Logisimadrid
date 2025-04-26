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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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

    private int circuitNb = 1;
    private final NouveauComposant cmp = ComposantLoad.chargerComp("./Composants_Json/Ajout_Comp.json") ; // on a le chemin de fichier
    String name;

    // -----------

    private List<Circuit> myCircuits = new ArrayList<Circuit>();
    private List<Pane> myPanes = new ArrayList<Pane>();
    private List<CircuitSaver> myCircuitSavers = new ArrayList<CircuitSaver>();

    private Circuit currentCircuit;
    private Pane currentPane;
    private CircuitSaver currentCircuitSaver;

    private VBox vb;
    private HBox hb;
    private Label label;


    
    public static void main(String[] args) throws Exception {
        launch(args);
    }
    
    @Override
    public void init() throws Exception{
        super.init();
    }

    private void AfficherFenetreNom () {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sauvegarder votre Circuit :");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("json Files", "*.json"));
        File nf = fileChooser.showSaveDialog(new Stage());
        if(nf == null) return;
        String nF = nf.getAbsolutePath().trim() ;
        if ( ! nF.isEmpty()  )  {
            if ( !nF.endsWith(".json")) {
                nF += ".json" ;
            }
            currentCircuitSaver.saveCircuit(nF);
        }
        else {
            Alert a = new Alert(Alert.AlertType.WARNING , "You didn't give a name !!") ;
            a.showAndWait();
        }
  
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
                launchMainApp(window); 
                window.setResizable(true);
                window.show();
                window.setMaximized(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            });
        delay.play();
    }

    private void initCircuitAndPane(){
        currentPane = new Pane();
        currentCircuit = new Circuit(currentPane);

        myPanes.add(currentPane);
        myCircuits.add(currentCircuit);

        currentPane.setMinSize(width - widthOfButton - widthOfShape, height);
        currentPane.setMaxWidth(Region.USE_PREF_SIZE);
        currentPane.setMaxHeight(Region.USE_PREF_SIZE);

        currentPane.setPrefWidth(width*4);
        currentPane.setPrefHeight(height*4);

        currentPane.setOnMouseClicked(e -> addItem(e, currentCircuit, currentPane));

        currentCircuitSaver = new CircuitSaver(currentCircuit);

        myCircuitSavers.add(currentCircuitSaver);

        currentCircuit.getSelectedGateProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue == null){
                if(vb.getChildren().contains(label) && vb.getChildren().contains(hb)){
                    vb.getChildren().removeAll(label, hb);
                }
            }
            else{
                if(!vb.getChildren().contains(label)){
                    vb.getChildren().add(label);
                }

                if(!vb.getChildren().contains(hb)){
                    vb.getChildren().add(hb);
                }
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    private void launchMainApp(Stage window) throws Exception {
        vb = new VBox();
        label = new Label("Ajout/Suppression d'entrées");
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setFont(Unity.fontBold);
        label.setTextFill(Color.WHITE);
        
        hb = new HBox();
        
        Button decreaseInput = new Button("-");
        decreaseInput.setOnMouseClicked(e -> currentCircuit.decreaseInputs());
        
        Button increaseInput = new Button("+");
        increaseInput.setOnMouseClicked(e -> currentCircuit.increaseInputs());
        
        hb.getChildren().addAll(decreaseInput, increaseInput);

        initCircuitAndPane();

        VBox sp = new VBox();

        ScrollPane scroll = new ScrollPane();
        scroll.setMinHeight(Unity.height/2);
        scroll.setMinWidth(Unity.width/2);
        
        scroll.setMaxWidth(width);
        scroll.setMaxHeight(height);
        scroll.setContent(currentPane);
        
    
        // Tree Items
        TreeItem<Label> allTree = new TreeItem<Label>();
        allTree.getChildren().addAll(Unity.portesTree(), Unity.varAndOutTree(), Unity.bitABitTree(), Unity.basculesTree());

        TreeView<Label> rootTree = new TreeView<Label>(allTree);
        rootTree.setShowRoot(false);
        rootTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setGateName(newValue.getValue().getText());
        });
        
        

        
        
        Slider sliderZoom = new Slider(Unity.minZoom, Unity.maxZoom, 1.);
        sliderZoom.setShowTickLabels(true);
        sliderZoom.setShowTickMarks(true);
        sliderZoom.setBlockIncrement(10.f);
        sliderZoom.valueProperty().addListener((observable, oldValue, newValue) -> {
            currentCircuit.setZoom(newValue.doubleValue());
        });
        
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.getItems().addAll(rootTree, vb, sliderZoom);
        splitPane.setPrefWidth(widthOfButton + widthOfShape);
        splitPane.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID,
        CornerRadii.EMPTY,
        BorderWidths.DEFAULT)));
        
        HBox bottom = new HBox();
        bottom.setSpacing(Unity.x);
        
        
        Label firstLabel = new Label("Untitled " + myCircuits.size());
        firstLabel.setTextFill(Color.WHITE);
        firstLabel.setStyle("-fx-background-color: black");
        bottom.getChildren().add(firstLabel);
        
        firstLabel.setOnMouseClicked(m -> setSelectedCircuit(bottom, firstLabel, scroll));
        
        firstLabel.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID,
        CornerRadii.EMPTY,
        BorderWidths.DEFAULT)));
        firstLabel.setPadding(new Insets(4));
        firstLabel.setFont(Unity.font);
        
        Label deleteButtonFirst = new Label(" x ");
        deleteButtonFirst.setStyle("-fx-background-color: red");
        bottom.getChildren().add(deleteButtonFirst);
        
        deleteButtonFirst.setOnMouseClicked(m -> deleteSetSelectedCircuit(bottom, firstLabel, scroll));
        
        deleteButtonFirst.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID,
        CornerRadii.EMPTY,
        BorderWidths.DEFAULT)));
        deleteButtonFirst.setPadding(new Insets(4));
        deleteButtonFirst.setFont(Unity.font);
        
        
        
        Label plus = new Label("+");
        plus.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID,
        CornerRadii.EMPTY,
        BorderWidths.DEFAULT)));
        plus.setPadding(new Insets(4));
        plus.setFont(Unity.font);
        
        plus.setOnMouseClicked(e -> circuitsButtons(bottom, plus, scroll, "Untitled" + ++circuitNb));
        bottom.getChildren().add(plus);
        
        MenuBar menu = createMenu(bottom, plus, scroll);

        
        sp.getChildren().addAll(scroll, bottom);
        BorderPane border = new BorderPane();

        Image coverImage = new Image("./pictures/cover.jpg");
        vb.setBackground(new Background(new BackgroundImage(coverImage, null, null, null, null)));

        border.setLeft(splitPane);
        border.setCenter(sp);
        border.setTop(menu);
        
        BorderPane.setMargin(scroll, new Insets(Unity.x));
        
        scene = new Scene(border, Unity.width, Unity.height);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent key) {
                if(key.getCode() == KeyCode.DELETE){
                    currentCircuit.removeSelectedGate();
                } else if (key.isControlDown() && key.getCode() == KeyCode.S) {
                    AfficherFenetreNom();
                } else if (key.isControlDown() && key.getCode() == KeyCode.EQUALS) {
                    currentCircuit.zoom(); 
                } else if (key.isControlDown() && key.getCode() == KeyCode.MINUS) {
                    currentCircuit.unzoom();  
                } else if (key.isControlDown() && key.getCode() == KeyCode.L){
                    AfficherTelechargement(bottom, plus, scroll);
                }else if (key.isControlDown() && key.getCode() == KeyCode.C){
                    currentCircuit.clearAll();
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

    private void AfficherTelechargement (HBox hb, Label plus, ScrollPane scroll) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Séléctionner le fichier du Circuit :");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("json Files", "*.json"));
        File nf = fileChooser.showOpenDialog(new Stage());
        if(nf == null) return;

        String nF = nf.getAbsolutePath() ;
        if ( ! nF.isEmpty()  )  {

            if ( !nF.endsWith(".json")) {
                nF += ".json" ;
            }
            //saver.saveCircuit(nF);
            File fichier = new File(nF) ;
            if ( fichier.exists()) {
            try {
                circuitsButtons(hb, plus, scroll, nf.getName().split(".json")[0]);
                currentCircuitSaver.loadCircuit(nF);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            }
            else {
            Alert a = new Alert(Alert.AlertType.INFORMATION,"LE FICHIER N EXISTE PAS: "+nF) ;
            a.showAndWait() ;
            }
        }
        else {
            Alert a = new Alert(Alert.AlertType.WARNING , "You didn't give a name !!") ;
            a.showAndWait();
        }
       }

    private MenuBar createMenu(HBox bottom, Label plus, ScrollPane scroll){
        MenuBar menu = new MenuBar(); 

        Menu file = new Menu("File");
        MenuItem save = new MenuItem("Save");
        MenuItem load = new MenuItem("Load");
        MenuItem clear = new MenuItem("Clear");

        file.getItems().addAll(save, load, clear);
        save.setOnAction(e -> AfficherFenetreNom());
        load.setOnAction(e -> AfficherTelechargement(bottom, plus, scroll));
        clear.setOnAction(e -> currentCircuit.clearAll());

        // ---

        Menu view = new Menu("View");

        MenuItem zoom = new MenuItem("Zoom in");
        MenuItem zoomOut = new MenuItem("Zoom out");

        view.getItems().addAll(zoom, zoomOut);
        zoom.setOnAction(e -> currentCircuit.zoom());
        zoomOut.setOnAction(e -> currentCircuit.unzoom());


        menu.getMenus().addAll(file, view);
        return menu;
    }

    private void circuitsButtons(HBox bottom, Label plus, ScrollPane scroll, String circuitName){
        bottom.getChildren().remove(plus);
        Label olddLabel = (Label) bottom.getChildren().get(myPanes.indexOf(currentPane)*2);
        olddLabel.setStyle("-fx-background-color: white");
        olddLabel.setTextFill(Color.BLACK);
        initCircuitAndPane();
        Label newLabel = new Label(circuitName);
        newLabel.setOnMouseClicked(m -> setSelectedCircuit(bottom, newLabel, scroll));
        newLabel.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            BorderWidths.DEFAULT)));
        newLabel.setPadding(new Insets(4));
        newLabel.setFont(Unity.font);
        scroll.setContent(currentPane);
        newLabel.setStyle("-fx-background-color: black");
        newLabel.setTextFill(Color.WHITE);
        
        Label deleteButtonFirst = new Label(" x ");
        deleteButtonFirst.setStyle("-fx-background-color: red");
        deleteButtonFirst.setOnMouseClicked(m -> deleteSetSelectedCircuit(bottom, newLabel, scroll));
        deleteButtonFirst.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            BorderWidths.DEFAULT)));    
        deleteButtonFirst.setPadding(new Insets(4));
        deleteButtonFirst.setFont(Unity.font);

        bottom.getChildren().addAll(newLabel, deleteButtonFirst, plus);
    }

    private void setSelectedCircuit(HBox bottom, Label button, ScrollPane scroll){
        if(myPanes.indexOf(currentPane) != bottom.getChildren().indexOf(button)/2){
            Label oldLabel = (Label) bottom.getChildren().get(myPanes.indexOf(currentPane)*2);
            oldLabel.setStyle("-fx-background-color: white");
            oldLabel.setTextFill(Color.BLACK);
            currentCircuit = myCircuits.get(bottom.getChildren().indexOf(button)/2);
            currentPane = myPanes.get(bottom.getChildren().indexOf(button)/2);
            currentCircuitSaver = myCircuitSavers.get(bottom.getChildren().indexOf(button)/2);
            scroll.setContent(currentPane);
            button.setStyle("-fx-background-color: black");
            button.setTextFill(Color.WHITE);
        }
        else{
            scroll.setContent(currentPane);
            button.setStyle("-fx-background-color: black");
            button.setTextFill(Color.WHITE);
        }
    }

    private void deleteSetSelectedCircuit(HBox bottom, Label button, ScrollPane scroll){
        if(myCircuits.size() <= 1) return;
        int index = bottom.getChildren().indexOf(button)/2;
        int currentIndex = myCircuits.indexOf(currentCircuit);
        myPanes.remove(index);
        myCircuits.remove(index);
        myCircuitSavers.remove(index);
        bottom.getChildren().remove(index*2);
        bottom.getChildren().remove(index*2);

        if(currentIndex == index){
            if(index != 0){
                currentCircuit = myCircuits.get(index-1);
                currentCircuitSaver = myCircuitSavers.get(index-1);
                currentPane = myPanes.get(index-1);
                setSelectedCircuit(bottom, (Label)bottom.getChildren().get((index-1)*2), scroll);
            }
            else{
                currentCircuit = myCircuits.get(index);
                currentCircuitSaver = myCircuitSavers.get(index);
                currentPane = myPanes.get(index);
                setSelectedCircuit(bottom, (Label)bottom.getChildren().get(index*2), scroll);
            }
        }
    }

}