package Gates;
import Gate.Fils;
import Gate.Gate;
import Gate.GatesShapes;
import Gate.OIput;
import javafx.scene.Group;
import javafx.scene.shape.Shape;
public class Xnor extends Gate {
    /**
     * @param nb
     * @requires nb >= 2
     */
    public Xnor(int nb,  Fils fils, Group layout, double x, double y){
        super("XNOR", nb, fils, layout);
        Shape sh = GatesShapes.xnorShape();
        setShape(sh);
        addShapeToGroup();
        sh.setLayoutX(x);
        sh.setLayoutY(y);
        addPoints();
    }

    @Override
    public void evaluateOutput(){
        OIput[] inputs = getInputs();
        boolean result = inputs[0].getOutput();
        for(int i = 1; i < inputs.length; i++){
            result = result == inputs[i].getOutput();
        }
        
        setOutput(!result);
        setIHaveOutput(true);
    }
}