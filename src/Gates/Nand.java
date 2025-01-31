package Gates;
import Gate.Gate;
import Gate.GatesShapes;
public class Nand extends Gate{
    /**
     * @param nb
     * @requires nb >= 2
     */
    public Nand(int nb){
        super("NAND", nb);
        setShape(GatesShapes.nandShape());

    }

    @Override
    public void evaluateOutput(){
        Gate[] inputs = getInputs();
        boolean result = inputs[0].getOutput();
        for(int i = 1; i < inputs.length; i++){
            result = result && inputs[i].getOutput();
        }
        
        setOutput(!result);
        setIHaveOutput(true);
    }
}