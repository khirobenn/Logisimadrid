package Gates;
import Gate.Fils;
import Gate.Gate;
import Gate.GatesShapes;
import Gate.OIput;
import Gate.QuadBool;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
public class Or extends Gate {
    /**
     * @param nb
     * @requires nb >= 2
     */
    public Or(int nb,  Fils fils, Pane layout, double x, double y){
        super("OR", nb, fils, layout);
        Shape sh = GatesShapes.orShape();
        setShape(sh);
        addShapeToGroup();
        sh.setLayoutX(x);
        sh.setLayoutY(y);
        addPoints();
    }

    @Override
    public void evaluateOutput(){
        OIput[] inputs = getInputs();
        QuadBool result = inputs[0].getOutput();
        for(int i = 1; i < inputs.length; i++){
            result = OrQuad.Or(result,inputs[i].getOutput());
        }
        
        setOutput(result);
        setIHaveOutput(true);
    }
}