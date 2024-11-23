package Gates;
import Gate.Gate;
import Gate.GatesShapes;
public class Or extends Gate {
    /**
     * @param nb
     * @requires nb >= 2
     */
    public Or(int nb){
        super("OR", nb);
        setShape(GatesShapes.orShape());
    }

    @Override
    public void evaluateOutput(){
        Gate[] inputs = getInputs();
        boolean result = inputs[0].getOutput();
        for(int i = 1; i < inputs.length; i++){
            result = result || inputs[i].getOutput();
        }
        
        setOutput(result);
        setIHaveOutput(true);
    }
}