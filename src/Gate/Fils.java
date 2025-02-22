package Gate;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import javafx.scene.layout.Pane;

public class Fils {
    
    private Pane group;
    private Set<OIput> fils;
    private Set<Gate> variables;
    private Set<Gate> gates;

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

    public void eval(Gate variableClicked){
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
        
        reinitialiseOI();
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
