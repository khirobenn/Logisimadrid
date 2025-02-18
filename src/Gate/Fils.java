package Gate;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javafx.scene.Group;

public class Fils {
    
    private Group group;
    private Deque <OIput> fils;
    private Set<Gate> variables;
    private Set<Gate> gates;

    public Fils(Group group){
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

    public Group getGroup(){ return group; } 
    public Deque <OIput> getFilsList(){ return fils; }
    public void setOutputToFils(){
        for(OIput oi : fils){
            oi.setOutput(false);
        }
    }

    public void addVariable(Gate var){
        variables.add(var);
    }

    public void addGate(Gate gate){
        gates.add(gate);
    }

    public void eval(Gate variableClicked){
        variableClicked.setOutput(!variableClicked.getOutput());
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
