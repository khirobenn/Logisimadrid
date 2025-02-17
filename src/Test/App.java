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
    private Set<Gate> gates;
    private Set<Gate> variables;
    private final Rectangle background = new Rectangle(-1, -1, width+1, height+1);

    public static void main(String[] args) throws Exception {
        launch(args);
    }
    
    @Override
    public void init() throws Exception{
        gates = new HashSet<Gate>();
        variables = new HashSet<Gate>();
        background.setFill(Color.PURPLE);
        background.setOpacity(0.2);
        background.setOnMouseClicked(e -> selectedItem = null);

        super.init();
    }

    @Override
    public void start(Stage window) throws Exception {
        Group layout = new Group();
        Fils fils = new Fils(layout);
        layout.getChildren().add(background);
        grid(layout);
        Gate or = new Or(2, fils, layout);
        Gate or2 = new Or(2, fils, layout);
        Gate and = new And(2, fils, layout);
        Gate variable = new Variable(fils, layout);
        Button btn = new Button("Resultat");
        gates.add(or);
        gates.add(or2);
        gates.add(and);
        variables.add(variable);
        Gate variable2 = new Variable(fils, layout);
        gates.add(variable2);
        variables.add(variable2);
        btn.setOnMouseClicked(e -> setResult(gates));
        layout.getChildren().add(btn);
        scene = new Scene(layout, width, height);

        window.setScene(scene);
        window.show();
    }

    private void setResult(Set<Gate> gates){
        for(Gate varia : variables){
            varia.setOutput(varia.getOutput());
        }
        for(Gate gate : gates){
            gate.getOutput();
        }
    }

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