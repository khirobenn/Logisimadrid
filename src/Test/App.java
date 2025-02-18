package Test;

import Gates.*;
import Gate.Fils;
import Gate.Unity;

import java.util.HashSet;
import java.util.Set;

import Gate.Gate;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class App extends Application{
    Scene scene;
    private final int height = Unity.height;
    private final int width = Unity.width;
    private final int x = Unity.x;
    private final int y = Unity.y;
    private Shape selectedItem;
    private final Rectangle background = new Rectangle(-1, -1, width+1, height+1);

    public static void main(String[] args) throws Exception {
        launch(args);
    }
    
    @Override
    public void init() throws Exception{
        background.setFill(Color.WHITE);
        // background.setOpacity(1);
        background.setOnMouseClicked(e -> selectedItem = null);

        super.init();
    }

    @Override
    public void start(Stage window) throws Exception {
        Group layout = new Group();
        Fils fils = new Fils(layout);
        layout.getChildren().add(background);
        grid(layout);
        Gate or = new Or(2, fils, layout, Unity.width - 600, 0);
        Gate or2 = new Or(2, fils, layout, Unity.width - 600, 400);
        Gate or3 = new Or(2, fils, layout, Unity.width - 200, 200);
        Gate and = new And(2, fils, layout, Unity.width - 400, 200);
        Gate not = new Not(fils, layout, 0, 0);
        Gate variable = new Variable(fils, layout, 0, 100);
        fils.addGate(or);
        fils.addGate(not);
        fils.addGate(or3);
        fils.addGate(or2);
        fils.addGate(and);
        fils.addVariable(variable);
        Gate variable2 = new Variable(fils, layout, 0, 200);
        fils.addVariable(variable2);
        scene = new Scene(layout, width, height);

        window.setScene(scene);
        window.show();
    }

    // private void setResult(Set<Gate> gates){
    //     for(Gate varia : variables){
    //         varia.setOutput(varia.getOutput());
    //     }
    //     for(Gate gate : gates){
    //         gate.getOutput();
    //     }
        
    //     reinitialiseOI(gates);
    // }

    // private void reinitialiseOI(Set<Gate> gates){
    //     for(Gate gate : gates){
    //         gate.reinitialiseOutput();
    //     }
    // }

    // private void addShape(Shape item){
    //     item.setOnMouseClicked(e -> selectedItem = item);
    //     shapes.add(item);
    // }

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