package Circuit;

import java.util.HashSet;
import java.util.Set;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class Gate {
    private String name;
    private Fils[] inputs;
    private Fils output;
    private boolean isOutPutSet;
    protected Shape shape;
    private Text text;
    Circuit circuit;
    Pane layout;

    Set<Fils> otherFils;

    boolean isReleased = false;
    boolean isReleasedForInputs[];

    public Gate(String name, Circuit circuit, Pane layout){
        this.layout = layout;
        this.name = name;
        isOutPutSet = false;
        this.circuit = circuit;
        otherFils = new HashSet<Fils>();
    }

    public Gate(String name, int nb, Circuit circuit, Pane layout, double x, double y){
        this(name, circuit, layout);
        this.inputs = new Fils[nb];
        this.isReleasedForInputs = new boolean[nb];
        if(!name.equals("OUTPUT")){
            initShape(x, y);
        }
    }

    public void addFilsToOthers(Fils fil){
        otherFils.add(fil);
    }

    private void initShape(double x, double y){
        switch (name) {
            case "AND":
            shape = GatesShapes.andShape();
            break;
            
            case "OR":
            shape = GatesShapes.orShape();
            break;

            case "XOR":
            shape = GatesShapes.xorShape();
            break;

            case "NAND":
            shape = GatesShapes.nandShape();
            break;

            case "NOR":
            shape = GatesShapes.norShape();
            break;

            case "XNOR":
            shape = GatesShapes.xnorShape();
            break;

            case "NOT":
            shape = GatesShapes.notShape();
            break;

            case "ADDER":
            shape = GatesShapes.adder();
            break;
            case "HORLOGE" :
            shape = GatesShapes.horloge();
            break ;
            case "Bascule JK" :
            shape = GatesShapes.Bascule_JK();
            break ;
            case "Bascule D" :
            shape = GatesShapes.Bascule_D();

	    case "EVENPARITY":
	    shape = GatesShapes.EvenParityShape();
	    break;
	    
	    case "ODDPARITY":
	    shape = GatesShapes.OddParityShape();
	    break;

	    case "MULTIPLIER":
	    shape = GatesShapes.multiplier();
	    break;

	    case "BASCULE RS":
	    shape = GatesShapes.Bascule_RS();
	    break;
        }
        addShapeToGroup();
        shape.setLayoutX(x);
        shape.setLayoutY(y);
        shape.setOnMouseClicked(e -> circuit.setSelectedGate(this));
        this.addPoints(circuit);
    }

    public void setText(String text, double x, double y){
        if(this.text == null){
            this.text = new Text(text);
            this.text.setFont(new Font(20));
            this.text.setLayoutX(x);
            this.text.setLayoutY(y);
            layout.getChildren().add(this.text);
        }
        else this.text.setText(text);
    }

    public void setOutputFils(Fils output){
        this.output = output;
    }

    public Text getText(){
	    return text;
    }


    public String getName(){
        return name;
    }

    public Fils[] getInputs(){
        return inputs;
    }

    public Fils getOutputFils(){
        return output;
    }

    public Shape getShape(){ return shape; }
    private void textChange(QuadBool value){
        if(text != null && !name.equals("HORLOGE") && !name.equals("BASCULE RS") && !name.equals("Bascule JK") && !name.equals("ADDER") && !name.equals("Bascule D")){
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
            else if (value == QuadBool.NOTHING && name.equals("OUTPUT")){
                text.setText("U");
                text.setFill(Unity.NOTH);
                text.setStroke(Unity.NOTH);
            }
            else if (value == QuadBool.ERROR && name.equals("OUTPUT")){
                text.setText("N");
                text.setFill(Unity.ERR);
                text.setStroke(Unity.ERR);
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
        
        
        output.setOutput(value);
        textChange(output.getOutputValue());
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
        for(Fils oi : otherFils){
            oi.setIsOutputSet(value);
        }
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

    public void setInput(int n, Fils gate){
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

    public void addPoints(Circuit circuit){
        int distance = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxY());
        output = new Fils(Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxX() + shape.getLayoutX()), Unity.tranformDoubleToInt(shape.getLayoutY() + distance/2), circuit, null, this, true);
        if(name == "VARIABLE") output.setFilsAsVariable();
        if(inputs != null){
            distance /= inputs.length + 1;
            for(int i = 1; i < inputs.length+1; i++){
                inputs[i-1] = new Fils(Unity.tranformDoubleToInt(shape.getLayoutX()), Unity.tranformDoubleToInt(shape.getLayoutY() + i*distance), circuit, null, null, true);
            }   
        }

    }
    
    public void addShapeToGroup(){
        layout.getChildren().add(shape);
        shape.setOnMouseDragged(e -> dragItem(e));
        shape.setOnMouseReleased(e -> onRelease());
    }

    public void onRelease(){
        if(isReleased){
            output.swapCircles();
        }
        output.onRelease();
        searchConnectedFils();
        searchConnectedOtherFils();

        if(inputs != null){
            for(int i = 0; i < inputs.length; i++){
                if(isReleasedForInputs[i]){
                    inputs[i].swapCircles();
                }
                inputs[i].onRelease();
            }
        }

        isReleased = false;
        if(isReleasedForInputs != null){
            for(int i = 0; i < isReleasedForInputs.length; i++){
                isReleasedForInputs[i] = false;
            }
        }
    }

    public void searchConnectedOtherFils(){
        
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
        if(mouseCoord.getX() >= 0 && mouseCoord.getX() + shape.boundsInParentProperty().getValue().getWidth() <= 1920*(1.5+circuit.getZoom())){
            shape.setLayoutX(mouseCoord.getX());
            if(text != null){
                text.setLayoutX(shape.getLayoutBounds().getMaxX() / 2 + shape.getLayoutX());
            }
        }
        
        if(mouseCoord.getY() >= 0 && mouseCoord.getY() + shape.boundsInParentProperty().getValue().getHeight() <= 1080*(1.5+circuit.getZoom())){
            shape.setLayoutY(mouseCoord.getY());
            if(text != null){
                text.setLayoutY(shape.getLayoutBounds().getMaxY() / 2 + shape.getLayoutY());
            }
        }
        
        this.updatePoints();
    }

    //              (true, false)
    //              ____________
    //              |          |
    // (true, true) |          | (false, false)
    //              |__________|
    //              (false, true)

    public void updatePoints(){
        updateOnePoint(-1, false, false);
        if(inputs != null){
            for(int i = 1; i < inputs.length+1; i++){
                updateOnePoint(i, true, true);
            }
        }
    }

    public void updateOnePoint(int index, boolean a, boolean b){
        int distance;
        if(a == b){
            distance = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxY());
        }
        else{
            distance = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxX());
        }

        double coef;

        boolean toUse;
        Fils filsToCheck;
        if(index != -1){
            toUse = isReleasedForInputs[index-1];
            filsToCheck = inputs[index-1];
            coef = index / (double)(inputs.length+1);
        }
        else{
            toUse = isReleased;
            filsToCheck = output;
            coef = 0.5;
        }

        int x = 0, y = 0;

        if(a == true && b == true){
            x = Unity.tranformDoubleToInt(shape.getLayoutX());
            y = Unity.tranformDoubleToInt(shape.getLayoutY() + distance*coef);
        }
        else if(a == true && b == false){
            x = Unity.tranformDoubleToInt(shape.getLayoutX() + distance*coef);
            y = Unity.tranformDoubleToInt(shape.getLayoutY());
        }
        else if(a == false && b == true){
            x = Unity.tranformDoubleToInt(shape.getLayoutX() + distance*coef);
            y = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxY() + shape.getLayoutY());
        }
        else if(a == false && b == false){
            x = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxX() + shape.getLayoutX());
            y = Unity.tranformDoubleToInt(shape.getLayoutY() + distance*coef);
        }

        if(filsToCheck.getConnectedNb() == 0){
            filsToCheck.changePlaceForPoints(x, y);
        }
        else if(toUse){
            filsToCheck.changeColor(Color.BLACK);
            filsToCheck.moveFils(x, y);
        }
        else{
            Point2D coordPreviousOutput = filsToCheck.getCircle2Coord();
            Fils newOutput = new Fils(coordPreviousOutput.getX(), coordPreviousOutput.getY(), circuit, null, this, true);
            newOutput.setCircleFill(Color.BLACK);
            newOutput.setCircle2Fill(Color.TRANSPARENT);
            filsToCheck.setGate(null);
            filsToCheck.addToConnected(newOutput);
            filsToCheck.setCircleToTransparent();
            if(index == -1){
                newOutput.setLp(output);
                output.setGate(null);
                output.setIsFilsRelatedToSomething(false);
                output = newOutput;
                isReleased = true;
            }
            else{
                newOutput.setLp(inputs[index-1]);
                inputs[index-1].setIsFilsRelatedToSomething(false);
                inputs[index-1] = newOutput;
                isReleasedForInputs[index-1] = true;
            }
        }
    }

    public void reinitialiseOutput(){
        if(!name.equals("VARIABLE")){
            output.reinitialiseOutput();
            for(Fils oi : inputs){
                oi.reinitialiseOutput();
            }
        }
    }

    public void removeGate(){
        output.removeAttributesAndDelete();
        if(inputs != null){
            for(Fils oi : inputs){
                oi.removeAttributesAndDelete();
            }
        }
        if(otherFils != null){
            for(Fils oi : otherFils){
                oi.removeAttributesAndDelete();
            }
        }
    }

    public void hideOutput(){
        output.hide();
        circuit.getFilsList().remove(output);
    }

    public void searchConnectedFils(){
        output.searchConnected();
        if(inputs != null){
            for(Fils fil: inputs){
                fil.searchConnected();
            }
        }
    }
    protected boolean isOutputSet() {
        return this.isOutPutSet;
    }
}
