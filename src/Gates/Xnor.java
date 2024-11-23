package Gates;
import Gate.Gate;
import Gate.GatesShapes;
public class Xnor extends Gate {
    /**
     * @param nb
     * @requires nb >= 2
     */
    public Xnor(int nb){
        super("XNOR", nb);
        setShape(GatesShapes.xnorShape());
    }

    @Override
    public void evaluateOutput(){
        Gate[] inputs = getInputs();
        boolean result = inputs[0].getOutput();
        for(int i = 1; i < inputs.length; i++){
            result = result == inputs[i].getOutput();
        }
        
        setOutput(!result);
        setIHaveOutput(true);
    }
}