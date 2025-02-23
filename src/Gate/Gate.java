package Gate;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class Gate {
    private String name;
    private OIput[] inputs;
    private OIput output;
    private boolean isOutPutSet;
    private Shape shape;
    private Text text;
    Fils fils;
    Pane layout;

    public Gate(String name, Fils fils, Pane layout){
        this.layout = layout;
        this.name = name;
        this.fils = fils;
        isOutPutSet = false;

        if(name == "Variable"){
            text = new Text("0");
        }
        else text = null;
    }

    public Gate(String name, int nb, Fils fils, Pane layout){
        this.layout = layout;
        this.name = name;
        this.inputs = new OIput[nb];
        isOutPutSet = false;
        this.fils = fils;
    }

    public void setText(String text){
        if(this.text == null){
            this.text = new Text(text);
            this.text.setFont(new Font(20));
            this.text.setLayoutX(shape.getLayoutBounds().getMaxX() / 2 + shape.getLayoutX());
            this.text.setLayoutY(shape.getLayoutBounds().getMaxY() / 2 + shape.getLayoutY());
            layout.getChildren().add(this.text);
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

    private void textChange(QuadBool value){
        if(text != null){
            if(value == QuadBool.TRUE){
                text.setText("1");
                text.setFill(Unity.ON);
                text.setStroke(Unity.ON);
            }
            else if (value == QuadBool.FALSE){
                text.setText("0");
                text.setFill(Unity.OFF);
                text.setStroke(Unity.OFF);
            }
            text.setStrokeWidth(2);
            
        }   
    }

    public void setOutput(QuadBool value){
        if(value == QuadBool.TRUE){
            changeOutputColor(Unity.ON);
        }
        else if(value== QuadBool.FALSE){
            changeOutputColor(Unity.OFF);
        }
        
        else if(value==QuadBool.ERROR){
            changeOutputColor(Unity.ERR);
        }
        else changeOutputColor(Unity.NOTH);
        
        textChange(value);

        output.setOutput(value);
        isOutPutSet = true;
    }

    public void setOutputValue(QuadBool value){
        textChange(value);
        output.setOutputValue(value);
    }
    public void setShape(Shape shape){
        this.shape = shape;
    }

    public void changeOutputColor(Color clr){
        output.changeColor(clr);
    }

    public void setIHaveOutput(boolean value){
        isOutPutSet = value;
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


    public QuadBool getOutputValue(){
        return output.getOutputValue();
    }
    
    public QuadBool getOutput(){
        if(name == "VARIABLE" || isOutPutSet) return output.getOutput();
        if(changeOutput()) return getOutput();
        return output.getOutput();
    }

    public void setInput(int n, OIput gate){
        inputs[n] = gate;
    }

    public boolean changeOutput(){
        if(checkIfIsConnected()){
            evaluateOutput();
            isOutPutSet = true;
            return true;
        }
        return false;
    }

    public abstract void evaluateOutput();
    public void addPoints(){
        int distance = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxY());
        output = new OIput(Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxX() + shape.getLayoutX()), Unity.tranformDoubleToInt(shape.getLayoutY() + distance/2), fils, null, this, null);
        if(name == "VARIABLE") output.setOIputAsVariable();
        if(inputs != null){
            distance /= inputs.length + 1;
            for(int i = 1; i < inputs.length+1; i++){
                inputs[i-1] = new OIput(Unity.tranformDoubleToInt(shape.getLayoutX()), Unity.tranformDoubleToInt(shape.getLayoutY() + i*distance), fils, null, null, null);
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
        output.changePlaceForPoints(Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxX() + shape.getLayoutX()), Unity.tranformDoubleToInt(shape.getLayoutY() + distance/2));
        if(inputs != null){
            distance /= inputs.length + 1;
            for(int i = 1; i < inputs.length+1; i++){
                inputs[i-1].changePlaceForPoints(Unity.tranformDoubleToInt(shape.getLayoutX()), Unity.tranformDoubleToInt(shape.getLayoutY() + i*distance));
            }
        }
    }

    public void reinitialiseOutput(){
        if(!name.equals("VARIABLE")){
            output.reinitialiseOutput();
            for(OIput oi : inputs){
                oi.reinitialiseOutput();
            }
        }
    }

    public void removeGate(){
        output.removeConnection();
        output.removeAttributesAndDelete();
        for(OIput oi : inputs){
            oi.removeConnection();
            oi.removeAttributesAndDelete();
        }
    }
}
