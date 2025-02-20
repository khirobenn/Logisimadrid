package Gates;
import Gate.Fils;
import Gate.Gate;
import Gate.GatesShapes;
import Gate.OIput;
import Gate.QuadBool;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
public class Xnor extends Gate {
    /**
     * @param nb
     * @requires nb >= 2
     */
    public Xnor(int nb,  Fils fils, Pane layout, double x, double y){
        super("XNOR", nb, fils, layout);
        Shape sh = GatesShapes.xnorShape();
        setShape(sh);
        addShapeToGroup();
        sh.setLayoutX(x);
        sh.setLayoutY(y);
        addPoints();
    }

    @Override
    public void evaluateOutput(){
        OIput[] inputs = getInputs();

        QuadBool result=inputs[0].getOutput();

        
        for(int i = 1 ; i < inputs.length; i++){
        result = AndQuad.And(OrQuad.Or(result, inputs[i].getOutput()),NotQuad.Not(AndQuad.And(result,inputs[i].getOutput())));
        }
        
        
        
        setOutput(NotQuad.Not(result));
        setIHaveOutput(true);
    }
}