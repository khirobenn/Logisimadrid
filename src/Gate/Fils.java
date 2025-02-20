package Gate;

import Gates.NotQuad;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import javafx.scene.layout.Pane;

public class Fils {
    
    private Pane group;
    private Deque <OIput> fils;
    private Set<Gate> variables;
    private Set<Gate> gates;

    public Fils(Pane group){
        this.group = group;
        fils = new LinkedList<OIput>();
        variables = new HashSet<Gate>();
        gates = new HashSet<Gate>();
    }

    public void addElement(OIput element){
        element.addPoint(group);
        fils.push(element);
    }

    public OIput removeElement(){
        return fils.pop();
    }

    public Pane getPane(){ return group; } 
    public Deque <OIput> getFilsList(){ return fils; }
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
            variableClicked.setOutput(NotQuad.Not(variableClicked.getOutput()));
        }
        for(Gate varia : variables){
            varia.setOutput(varia.getOutput());
        }
        for(Gate gate : gates){
            gate.getOutput();
        }
        
        reinitialiseOI(gates);
    }

    private void reinitialiseOI(Set<Gate> gates){
        for(Gate gate : gates){
            gate.reinitialiseOutput();
        }
    }
    
}
