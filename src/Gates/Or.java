package Gates;
import Circuit.Circuit;
import Circuit.Fils;
import Circuit.Gate;
import Circuit.QuadBool;
import javafx.scene.layout.Pane;
public class Or extends Gate {
    /**
     * @param nb
     * @requires nb >= 2
     */
    public Or(int nb, Circuit circuit, Pane layout, double x, double y){
        super("OR", nb, circuit, layout, x, y);
    }

    @Override
    public void evaluateOutput(){
        Fils[] inputs = getInputs();
        QuadBool result = inputs[0].getOutput();
        for(int i = 1; i < inputs.length; i++){
            result = OrQuad.Or(result, inputs[i].getOutput());
        }
        setOutput(result);
        setIHaveOutput(true);
    }
}