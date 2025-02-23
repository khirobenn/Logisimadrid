package Gate;

import java.util.HashSet;
import java.util.Set;
import javafx.scene.layout.Pane;

public class Fils {
    
    private Pane group;
    private Set<OIput> fils;
    private Set<Gate> variables;
    private Set<Gate> gates;
    private Gate selectedGate;

    public Fils(Pane group){
        this.group = group;
        fils = new HashSet<OIput>();
        variables = new HashSet<Gate>();
        gates = new HashSet<Gate>();
    }

    public void addElement(OIput element){
        element.addPoint(group);
        fils.add(element);
    }

    public void setSelectedGate(Gate selectedGate){
        if(selectedGate == this.selectedGate){
            this.selectedGate = null;
            System.out.println("No gate selected");
        }
        else{
            this.selectedGate = selectedGate;
            System.out.println("Gate selected !!!");

        }
    }

    public void removeElement(OIput element){
        fils.remove(element);
    }

    public Pane getPane(){ return group; } 
    public Set <OIput> getFilsList(){ return fils; }
    public void setOutputToFils(){
        for(OIput oi : fils){
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
    }

    public void removeGate(Gate gate){
        gate.removeGate();
        if(variables.contains(gate)){
            variables.remove(gate);
            group.getChildren().remove(gate.getText());
        }
        else{
            gates.remove(gate);
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
        for(OIput oi : fils){
            oi.reinitialiseOutput();
        }

        for(Gate gate : gates){
            gate.setIHaveOutput(false);
        }

        for(Gate var : variables){
            var.setIHaveOutput(true);
        }
    }
    
}
