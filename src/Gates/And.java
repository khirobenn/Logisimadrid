package Gates;
import Gate.Circuit;
import Gate.Fils;
import Gate.Gate;
import Gate.QuadBool;
import javafx.scene.layout.Pane;

public class And extends Gate {
    /**
     * @param nb
     * @requires nb >= 2
     */
    public And(int nb, Circuit circuit, Pane layout, double x, double y){
        super("AND", nb, circuit, layout, x, y);
    }

    @Override
    public void evaluateOutput(){
        Fils[] inputs = getInputs();
        QuadBool result = inputs[0].getOutput();
        for(int i = 1; i < inputs.length; i++){
            result =AndQuad.And(result, inputs[i].getOutput());
        }

        setOutput(result);
        setIHaveOutput(true);
    }
}
