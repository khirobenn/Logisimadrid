package Test;

import Gates.*;

import java.util.Arrays;
import java.util.List;

import Gate.Fils;
import Gate.Unity;

import Gate.Gate;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class App extends Application{
    Scene scene;
    private double widthOfButton = Unity.x*5;
    private double widthOfShape = Unity.x*2;
    // private final int height = Unity.height;
    private final int height = 1080;
    // private final int width = Unity.width;
    private final int width = 1980;
    private final int x = Unity.x;
    private final int y = Unity.y;
    private Shape selectedItem;
    private final Rectangle background = new Rectangle(-1, -1, width+1, height+1);

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
    });

    public static void main(String[] args) throws Exception {
        launch(args);
    }
    
    @Override
    public void init() throws Exception{
        background.setFill(Color.BLACK);
        super.init();
    }

    @Override
    public void start(Stage window) throws Exception {
        Pane sp = new Pane();
        grid(sp);
        sp.setMinSize(Unity.width - widthOfButton - widthOfShape, Unity.height);
        sp.setMaxSize(width - widthOfButton - widthOfShape, height);
        Fils fils = new Fils(sp);
        
        sp.setOnMouseClicked(e -> addItem(e, fils, sp));

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

        BorderPane border = new BorderPane();
        border.setLeft(hb);
        border.setCenter(sp);

        BorderPane.setMargin(sp, new Insets(Unity.x));

        scene = new Scene(border, Unity.width, Unity.height);

        window.setScene(scene);
        window.show();
    }

    private void addItem(MouseEvent e, Fils fils, Pane pane){
        double x = Unity.tranformDoubleToInt(e.getX());
        double y = Unity.tranformDoubleToInt(e.getY());
        Gate gate;
        switch (nbOfButtonSelected) {
            case 0:
                gate = new And(2, fils, pane, x, y);
                fils.addGate(gate);
            break;
    
            case 1:
                gate = new Or(2, fils, pane, x, y);
                fils.addGate(gate);
            break;
            case 2:
                gate = new Xor(2, fils, pane, x, y);
                fils.addGate(gate);
                
            break;
            
            case 3:
                gate = new Nand(2, fils, pane, x, y);
                fils.addGate(gate);
            break;
            
            case 4:
                gate = new Nor(2, fils, pane, x, y);
                fils.addGate(gate);
            break;
            
            case 5:
                gate = new Xnor(2, fils, pane, x, y);
                fils.addGate(gate);
            break;
            
            case 6:
                gate = new Not(fils, pane, x, y);
                fils.addGate(gate);
            break;

            case 7:
                gate = new Variable(fils, pane, x, y);
                fils.addVariable(gate);
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
        String myPath = System.getProperty("user.dir");
        System.out.println(myPath + "/src/pictures/" + str.toLowerCase() + ".png");
        
        Button btn = new Button(str);

        if(!str.equals("VARIABLE")){
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

    private void grid(Pane layout){
        for(int i = 0; i*x <= width; i++ ){
            for(int j = 0; j*y <= height; j++ ){
                Line line = new Line(i*x, 0, i*x, height);
                line.setFill(Color.GRAY);
                Line line2 = new Line(0, j*y, width, j*y);
                line2.setFill(Color.GRAY);
                Shape dot = Line.intersect(line, line2);
                dot.setFill(Color.PURPLE);
                layout.getChildren().add(dot);
            }
        }
    }
}