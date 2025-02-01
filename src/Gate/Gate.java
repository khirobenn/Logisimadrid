package Gate;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import Gates.Variable;

public abstract class Gate {
    private String name;
    private OIput[] inputs;
    private OIput output;
    private boolean iHaveOutput;
    private Shape shape;
    Fils fils;
    Group layout;

    public Gate(String name, Fils fils, Group layout){
        this.layout = layout;
        this.name = name;
        this.iHaveOutput = false;
        this.fils = fils;
    }

    public Gate(String name, int nb, Fils fils, Group layout){
        this.layout = layout;
        this.name = name;
        this.iHaveOutput = false;
        this.inputs = new OIput[nb];
        this.fils = fils;
    }

    public String getName(){
        return name;
    }

    public OIput[] getInputs(){
        return inputs;
    }

    public void setOutput(boolean value){
        output.setOutput(value);
    }
    public void setShape(Shape shape){
        this.shape = shape;
    }

    public void setIHaveOutput(boolean value){
        iHaveOutput = value;
    }

    private void check(){
        for(int i=0; i < inputs.length; i++){
            if(inputs[i] == null){
                // TO DO
            }
        }
    }

    public boolean getOutput(){
        if(iHaveOutput)
            return output.getOutput();

        changeOutput();
        return getOutput();
    }

    public void setInput(int n, OIput gate){
        inputs[n] = gate;
    }

    public void changeOutput(){
        check();
        evaluateOutput();
    }

    public abstract void evaluateOutput();
    public void addPoints(){
        int distance = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxY());
        output = new OIput(shape.getLayoutBounds().getMaxX() + shape.getLayoutX(), shape.getLayoutY() + distance/2, fils, null);
        distance /= inputs.length + 1;
        for(int i = 1; i < inputs.length+1; i++){
            inputs[i-1] = new OIput(shape.getLayoutX(), shape.getLayoutY() + i*distance, fils, null);
        }

    }
    
    public void addShapeToGroup(){
        layout.getChildren().add(shape);
        shape.setOnMouseDragged(e -> dragItem(e));
    }

    private void dragItem(MouseEvent e){
        // On convertit les coordonnées de la souris par rapport au parent (la fenetre)
        Point2D mouseCoord = shape.localToParent(e.getX(), e.getY());
        mouseCoord = new Point2D(
            Math.floor(mouseCoord.getX()) - Math.floor(mouseCoord.getX())%Unity.x,
            Math.floor(mouseCoord.getY()) - Math.floor(mouseCoord.getY())%Unity.y
        );

        // On vérifie que l'objet ne dépasse pas la fenetre
        // On note que setLayout, définit les coordonnées par rapport au parent
        if(mouseCoord.getX() >= 0 && mouseCoord.getX() + shape.boundsInParentProperty().getValue().getWidth() <= Unity.width)
            shape.setLayoutX(mouseCoord.getX());

        if(mouseCoord.getY() >= 0 && mouseCoord.getY() + shape.boundsInParentProperty().getValue().getHeight() <= Unity.height)
            shape.setLayoutY(mouseCoord.getY());
        
        updatePoints();
    }

    private void updatePoints(){
        int distance = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxY());
        output.changePlaceForPoints(shape.getLayoutBounds().getMaxX() + shape.getLayoutX(), shape.getLayoutY() + distance/2);
        distance /= inputs.length + 1;
        for(int i = 1; i < inputs.length+1; i++){
            inputs[i-1].changePlaceForPoints(shape.getLayoutX(), shape.getLayoutY() + i*distance);
        }
    }
}
