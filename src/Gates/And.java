package Gates;
import Gate.Gate;
import Gate.GatesShapes;
public class And extends Gate {
    /**
     * @param nb
     * @requires nb >= 2
     */
    public And(int nb){
        super("AND", nb);
        setShape(GatesShapes.andShape());
    }

    @Override
    public void evaluateOutput(){
        Gate[] inputs = getInputs();
        boolean result = inputs[0].getOutput();
        for(int i = 1; i < inputs.length; i++){
            result = result && inputs[i].getOutput();
        }

        setOutput(result);
        setIHaveOutput(true);
    }
}