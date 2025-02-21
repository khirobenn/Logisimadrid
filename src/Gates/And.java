package Gates;
import Gate.Fils;
import Gate.Gate;
import Gate.GatesShapes;
import Gate.OIput;
import Gate.QuadBool;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
public class And extends Gate {
    /**
     * @param nb
     * @requires nb >= 2
     */
    public And(int nb, Fils fils, Pane layout, double x, double y){
        super("AND", nb, fils, layout);
        Shape sh = GatesShapes.andShape();
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
        System.out.println(result);
        for(int i = 1; i < inputs.length; i++){
            QuadBool o = inputs[i].getOutput();
            System.out.println(o);
            result =AndQuad.And(result, o);
            System.out.println(result);
        }

        setOutput(result);
        setIHaveOutput(true);
    }
}
