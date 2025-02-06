package Gates;
import Gate.Fils;
import Gate.Gate;
import Gate.GatesShapes;
import Gate.OIput;
import javafx.scene.Group;
public class Nand extends Gate{
    /**
     * @param nb
     * @requires nb >= 2
     */
    public Nand(int nb, Fils fils, Group layout){
        super("NAND", nb, fils, layout);
        setShape(GatesShapes.nandShape());
        addShapeToGroup();
        addPoints();

    }

    @Override
    public void evaluateOutput(){
        OIput[] inputs = getInputs();
        boolean result = inputs[0].getOutput();
        for(int i = 1; i < inputs.length; i++){
            result = result && inputs[i].getOutput();
        }
        
        setOutput(!result);
        setIHaveOutput(true);
    }
}