package Gate;

import Gate.Unity;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javax.lang.model.util.ElementScanner14;

public abstract class Gate {
    private String name;
    private OIput[] inputs;
    private OIput output;
    private boolean iHaveOutput;
    private Shape shape;
    private Text text;
    Fils fils;
    Pane layout;

    public Gate(String name, Fils fils, Pane layout){
        this.layout = layout;
        this.name = name;
        this.iHaveOutput = false;
        this.fils = fils;
        if(name == "Variable"){
            text = new Text("0");
        }
        else text = null;
    }

    public Gate(String name, int nb, Fils fils, Pane layout){
        this.layout = layout;
        this.name = name;
        this.iHaveOutput = false;
        this.inputs = new OIput[nb];
        this.fils = fils;
    }

    public void setText(String text){
        if(this.text == null){
            this.text = new Text(text);
            this.text.setFont(new Font(20));
            this.text.setLayoutX(shape.getLayoutBounds().getMaxX() / 2 + shape.getLayoutX());
            this.text.setLayoutY(shape.getLayoutBounds().getMaxY() / 2 + shape.getLayoutY());
            layout.getChildren().add(this.text);
            System.out.println("Text added");
        }
        else this.text.setText(text);
    }

    public Text getText(){
	    return text;
    }


    public String getName(){
        return name;
    }

    public OIput[] getInputs(){
        return inputs;
    }


    public void setOutput(QuadBool value){
        if(value == QuadBool.TRUE){
            changeOutputColor(Unity.ON);
            if(text != null){
                text.setText("1");
                text.setFill(Unity.ON);
                text.setStroke(Unity.ON);
                text.setStrokeWidth(2);
            }
        }
        else if(value== QuadBool.FALSE){
            changeOutputColor(Unity.OFF);
            if(text != null){
                text.setText("0");
                text.setFill(Unity.OFF);
                text.setStroke(Unity.OFF);
                text.setStrokeWidth(2);
            }
        }

        else if(value==QuadBool.ERROR){
            changeOutputColor(Unity.ERR);
        }
        else changeOutputColor(Unity.NOTH);


        output.setOutput(value);
}
    public void setShape(Shape shape){
        this.shape = shape;
    }

    public void changeOutputColor(Color clr){
        output.changeColor(clr);
    }

    public void setIHaveOutput(boolean value){
        iHaveOutput = value;
        output.setIsOutputSet(value);
    }

    private boolean checkIfIsConnected(){
        for(int i=0; i < inputs.length; i++){
            if(inputs[i].getConnectedNb() != 0){
                return true;
            }
        }
        return false;
    }

    public QuadBool getOutput(){
        if(iHaveOutput)
            return output.getOutput();

        if(changeOutput()) return getOutput();
        return output.getOutput();
    }

    public void setInput(int n, OIput gate){
        inputs[n] = gate;
    }

    public boolean changeOutput(){
        if(checkIfIsConnected()){
            evaluateOutput();
            return true;
        }
        return false;
    }

    public abstract void evaluateOutput();
    public void addPoints(){
        int distance = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxY());
        output = new OIput(shape.getLayoutBounds().getMaxX() + shape.getLayoutX(), shape.getLayoutY() + distance/2, fils, null, this);
        if(inputs != null){
            distance /= inputs.length + 1;
            for(int i = 1; i < inputs.length+1; i++){
                inputs[i-1] = new OIput(shape.getLayoutX(), shape.getLayoutY() + i*distance, fils, null, null);
            }   
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
        if(mouseCoord.getX() >= 0 && mouseCoord.getX() + shape.boundsInParentProperty().getValue().getWidth() <= layout.getWidth()){
            shape.setLayoutX(mouseCoord.getX());
            if(text != null){
                text.setLayoutX(shape.getLayoutBounds().getMaxX() / 2 + shape.getLayoutX());
            }
        }
        
        if(mouseCoord.getY() >= 0 && mouseCoord.getY() + shape.boundsInParentProperty().getValue().getHeight() <= layout.getHeight()){
            shape.setLayoutY(mouseCoord.getY());
            if(text != null){
                text.setLayoutY(shape.getLayoutBounds().getMaxY() / 2 + shape.getLayoutY());
            }
        }   
        updatePoints();
    }

    private void updatePoints(){
        int distance = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxY());
        output.changePlaceForPoints(shape.getLayoutBounds().getMaxX() + shape.getLayoutX(), shape.getLayoutY() + distance/2);
        if(inputs != null){
            distance /= inputs.length + 1;
            for(int i = 1; i < inputs.length+1; i++){
                inputs[i-1].changePlaceForPoints(shape.getLayoutX(), shape.getLayoutY() + i*distance);
            }
        }
    }

    public void reinitialiseOutput(){
        if(!name.equals("VARIABLE")){
            iHaveOutput = false;
            output.reinitialiseOutput();
            for(OIput oi : inputs){
                oi.reinitialiseOutput();
            }
        }
    }
}
