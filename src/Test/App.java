package Test;

import Gates.*;
import Gate.Fils;
import Gate.Unity;

import Gate.Gate;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class App extends Application{
    Scene scene;
    private double widthOfButton = Unity.x*5;
    // private final int height = Unity.height;
    private final int height = Unity.height;
    // private final int width = Unity.width;
    private final int width = Unity.width;
    private final int x = Unity.x;
    private final int y = Unity.y;
    private Shape selectedItem;
    private final Rectangle background = new Rectangle(-1, -1, width+1, height+1);
    private Button[] buttons = { 
        new Button("AND"),
        new Button("OR"),
        new Button("XOR"),
        new Button("NAND"),
        new Button("NOR"),
        new Button("XNOR"),
        new Button("NOT")
    };

    public static void main(String[] args) throws Exception {
        launch(args);
    }
    
    @Override
    public void init() throws Exception{
        background.setFill(Color.BLACK);
        // background.setOnMouseClicked(e -> selectedItem = null);

        super.init();
    }

    @Override
    public void start(Stage window) throws Exception {
        Pane sp = new Pane();
        grid(sp);
        sp.setMinSize(width, height);
    
        Fils fils = new Fils(sp);

        VBox hb = new VBox();
        hb.setPrefWidth(widthOfButton);
        
        for(Button btn : buttons){
            btn.setPrefWidth(widthOfButton);
            hb.getChildren().add(btn);
        }



        
        BorderPane border = new BorderPane();
        border.setLeft(hb);
        border.setCenter(sp);
        
        Gate variable2 = new Variable(fils, sp, 0, 200);
        fils.addVariable(variable2);


        scene = new Scene(border, width, height);

        window.setScene(scene);
        window.show();
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