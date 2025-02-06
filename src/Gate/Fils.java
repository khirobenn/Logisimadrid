package Gate;

import java.util.Deque;
import java.util.LinkedList;

import javafx.scene.Group;

public class Fils {
    
    private Group group;
    private Deque <OIput> fils;

    public Fils(Group group){
        this.group = group;
        fils = new LinkedList<OIput>();
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
}
