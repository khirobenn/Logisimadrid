package Test;

import Gates.*;
import Gate.Input;

import java.util.ArrayList;

import Gate.GatesShapes;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class App extends Application{
    Scene scene;
    private final int height = 200;
    private final int width = 400;
    private final int x = 10;
    private final int y = 10;
    private Shape selectedItem;
    private ArrayList<Shape> shapes;
    private final Rectangle background = new Rectangle(-1, -1, width+1, height+1);

    public static void main(String[] args) throws Exception {
        launch(args);
    }
    
    @Override
    public void init() throws Exception{
        shapes = new ArrayList<Shape>();

        background.setFill(Color.PURPLE);
        background.setOpacity(0.2);
        background.setOnMouseClicked(e -> selectedItem = null);

        super.init();
    }

    @Override
    public void start(Stage window) throws Exception {
        Group layout = new Group();
        layout.getChildren().add(background);
        grid(layout);
        Input in = new Input();
        in.addPoint(layout);
        scene = new Scene(layout, width, height);

        window.setScene(scene);
        window.show();
    }

    
    private void addShape(Shape item){
        item.setOnMouseClicked(e -> selectedItem = item);
        shapes.add(item);
    }

    private void grid(Group layout){
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