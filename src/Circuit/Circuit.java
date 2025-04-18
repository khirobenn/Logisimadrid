package Circuit;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

public class Circuit {
    
    private Pane group;
    private Set<Fils> fils;
    private Set<Gate> variables;
    private Set<Gate> gates;
    private Gate selectedGate;
    private Fils filSelected;
    private double zoom = 1.;

    private Rectangle rec1;
    private Rectangle rec2;
    Scale scale;

    public Circuit(Pane group){
        this.group = group;
        fils = new HashSet<Fils>();
        variables = new HashSet<Gate>();
        gates = new HashSet<Gate>();
        scale = new Scale();
        scale.setPivotX(0);
        scale.setPivotY(0);
        this.group.getTransforms().add(scale);
        rec1 = new Rectangle(Unity.x/2, Unity.x/2);
        rec2 = new Rectangle(Unity.x/2, Unity.x/2);
    }

    public void addElement(Fils element){
        element.addPoint(group);
        fils.add(element);
    }

    public void setSelectedGate(Gate selectedGate){
        if(selectedGate == null){
            if(this.selectedGate != null){
                this.selectedGate.getShape().setFill(Color.TRANSPARENT);
            }
            this.selectedGate = selectedGate;
        }
        else if(selectedGate == this.selectedGate){
            selectedGate.getShape().setFill(Color.TRANSPARENT);
            this.selectedGate = null;
            System.out.println("No gate selected");
        }
        else{
            if(this.selectedGate != null){
                this.selectedGate.getShape().setFill(Color.TRANSPARENT);
            }
            this.selectedGate = selectedGate;
            selectedGate.getShape().setFill(Color.rgb(219, 219, 219));
            System.out.println("Gate selected !!!");
            setFilSelected(null);
        }

    }

    public void removeFilSelected(){
        if(filSelected != null){
            filSelected.removeFil();
        }
    }

    public void removeElement(Fils element){
        fils.remove(element);
    }

    public void rotateSelectedEement(){
        if(selectedGate == null) return;
        GatesShapes.rotate(selectedGate.getShape());
        selectedGate.updatePoints();
    }

    public void setFilSelected(Fils fil){
        filSelected = fil;
        if(filSelected == null){
            group.getChildren().removeAll(rec1, rec2);
            return;
        }
        rec1.setX(filSelected.getL1().getStartX() - Unity.x/4);
        rec1.setY(filSelected.getL1().getStartY() - Unity.x/4);

        rec2.setX(filSelected.getL1().getEndX() - Unity.x/4);
        rec2.setY(filSelected.getL1().getEndY() - Unity.x/4);

        if(!filSelected.isNoLine(fil.getL2())){
            rec2.setX(filSelected.getL2().getEndX() - Unity.x/4);
            rec2.setY(filSelected.getL2().getEndY() - Unity.x/4);
        }
        group.getChildren().removeAll(rec1, rec2);
        group.getChildren().addAll(rec1, rec2);
    }

    public Pane getPane(){ return group; } 
    public Set <Fils> getFilsList(){ return fils; }
    public void setOutputToFils(){
        for(Fils oi : fils){
            oi.setOutput(QuadBool.FALSE);
        }
    }

    public void addVariable(Gate var){
        variables.add(var);
    }

    public void addGate(Gate gate){
        gates.add(gate);
    }

    public void removeSelectedGate(){
        if(selectedGate == null) {
            removeFilSelected();
            setFilSelected(null);
        }
        else{
            removeGate(selectedGate);
            selectedGate = null;
        }
        eval(null);
        fixFilsColors();
    }

    public void fixFilsColors(){
        for(Fils fil : fils){
            QuadBool value = fil.getOutputValue();
            switch(value){
                case QuadBool.TRUE: fil.changeColor(Unity.ON); break;
                case QuadBool.FALSE: fil.changeColor(Unity.OFF); break;
                case QuadBool.NOTHING: fil.changeColor(Unity.NOTH); break;
                case QuadBool.ERROR: fil.changeColor(Unity.ERR); break;
            }
        }
    }

    public void removeGate(Gate gate){
        gate.removeGate();
        if(variables.contains(gate)){
            variables.remove(gate);
        }
        else{
            gates.remove(gate);
        }

        if(gate.getText() != null){
            group.getChildren().remove(gate.getText());
        }
        
        group.getChildren().remove(gate.getShape());
    }

    public void eval(Gate variableClicked){
        reinitialiseOI();
        if(variableClicked != null){
            String o = variableClicked.getText().getText();
            if(o.equals("1")){
                variableClicked.setOutputValue(QuadBool.FALSE);
            }
            else{
                variableClicked.setOutputValue(QuadBool.TRUE);
            }
        }

        for(Gate varia : variables){
            String o = varia.getText().getText();
            if(o.equals("0")){
                varia.setOutputValue(QuadBool.FALSE);
            }
            else{
                varia.setOutputValue(QuadBool.TRUE);
            }
        }

        for(Gate varia : variables){
            QuadBool o = varia.getOutputValue();
            varia.setOutput(o);
        }

        for(Gate gate : gates){
            gate.getOutput();
        }
        
    }

    private void reinitialiseOI(){
        for(Fils oi : fils){
            oi.reinitialiseOutput();
        }

        for(Gate gate : gates){
            gate.setIHaveOutput(false);
        }

        for(Gate var : variables){
            var.setIHaveOutput(true);
        }
    }

    public void zoom(){
        if(zoom < 1.5){
            zoom += 0.1;
            scale.setX(zoom);
            scale.setY(zoom);
            System.out.println("Zoom! " + zoom);
        }
    }

    public void unzoom(){
        if(zoom > 0.41){
            zoom -= 0.1;
            scale.setX(zoom);
            scale.setY(zoom);
            System.out.println("Unzoom! " + zoom);
        }
    }

    public double getZoom(){
        return zoom;
    }

    public Set<Gate> getGates(){
        return gates;
    }

    public Set<Gate> getVar(){
        return variables ;
    }

    public Set<Fils> getFils () {
        return fils ;
    }


    public void resetSelectedItems(){
        setSelectedGate(null);
        setFilSelected(null);
    }
 
}

