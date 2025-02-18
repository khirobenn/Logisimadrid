package Gates;
import Gate.Fils;
import Gate.Gate;
import Gate.GatesShapes;
import Gate.OIput;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
public class And extends Gate {
    /**
     * @param nb
     * @requires nb >= 2
     */
    public And(int nb, Fils fils, Pane layout, double x, double y){
        super("AND", nb, fils, layout);
        Shape sh = GatesShapes.andShape();
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
            result = result && inputs[i].getOutput();
        }

        setOutput(result);
        setIHaveOutput(true);
    }
}