package Gates;
import Gate.Fils;
import Gate.Gate;
import Gate.GatesShapes;
import Gate.OIput;
import Gate.QuadBool;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
public class Xor extends Gate {
    /**
     * @param nb
     * @requires nb >= 2
     */
    public Xor(int nb,  Fils fils, Pane layout, double x, double y){
        super("XOR", nb, fils, layout, x, y);
    }

    @Override
    public void evaluateOutput(){
        OIput[] inputs = getInputs();

        QuadBool result=inputs[0].getOutput();

        
        for(int i = 1 ; i < inputs.length; i++){
        result = AndQuad.And(OrQuad.Or(result, inputs[i].getOutput()),NotQuad.Not(AndQuad.And(result,inputs[i].getOutput())));
        }
        
        
        
        setOutput(result);
        setIHaveOutput(true);
    }
}