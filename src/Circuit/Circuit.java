package Circuit;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

public class Circuit {
    
    private Pane group;
    private Set<Fils> fils;
    private Set<Gate> variables;
    private Set<Gate> gates;
    private Gate selectedGate;
    private double zoom = 1.;
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
    }

    public void addElement(Fils element){
        element.addPoint(group);
        fils.add(element);
    }

    public void setSelectedGate(Gate selectedGate){
        if(selectedGate == this.selectedGate){
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
        if(selectedGate == null) return;
        removeGate(selectedGate);
        selectedGate = null;
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
}