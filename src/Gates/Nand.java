package Gates;
import Circuit.Circuit;
import Circuit.Fils;
import Circuit.Gate;
import Circuit.QuadBool;
import javafx.scene.layout.Pane;

public class Nand extends Gate{
    /**
     * @param nb
     * @requires nb >= 2
     */
    public Nand(int nb, Circuit circuit, Pane layout, double x, double y){
        super("NAND", nb, circuit, layout, x, y);
    }

    @Override
    public void evaluateOutput(){
        Fils[] inputs = getInputs();
        QuadBool result = inputs[0].getOutput();
        for(int i = 1; i < inputs.length; i++){
            result = AndQuad.And(result,inputs[i].getOutput());
        }
        
        setOutput(NotQuad.Not(result));
        setIHaveOutput(true);
    }
}