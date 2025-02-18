package Gates;
import Gate.Fils;
import Gate.Gate;
import Gate.GatesShapes;
import Gate.OIput;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
public class Xor extends Gate {
    /**
     * @param nb
     * @requires nb >= 2
     */
    public Xor(int nb,  Fils fils, Pane layout, double x, double y){
        super("XOR", nb, fils, layout);
        Shape sh = GatesShapes.xorShape();
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
        
        setOutput(result);
        setIHaveOutput(true);
    }
}