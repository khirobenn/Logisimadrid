package Gates;
import Circuit.Circuit;
import Circuit.Fils;
import Circuit.Gate;
import Circuit.QuadBool;
import Circuit.Unity;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class RSflipflop extends Gate {
    
    private QuadBool current = QuadBool.FALSE;  // represente Qn
    private Circuit circuit;

    public RSflipflop(Circuit circuit, Pane layout, double x, double y) {
        super("RS FlipFlop", 2, circuit, layout, x, y);
        this.circuit = circuit;
        Shape shape = getShape();
        setText("RS", shape.getLayoutBounds().getMaxX() / 2 + shape.getLayoutX(), shape.getLayoutBounds().getMaxY() / 2 + shape.getLayoutY());
       
    }

   

    @Override
    public void evaluateOutput() {
        Fils[] inputs = getInputs();
        QuadBool R = inputs[0].getOutput();
        QuadBool S = inputs[1].getOutput();
        
        QuadBool nextQ = OrQuad.Or(AndQuad.And(NotQuad.Not(R), current), S);
        setOutput(nextQ);
        current = nextQ; //mise à jour

        setIHaveOutput(true);
    }

    @Override
    public void addPoints(Circuit circuit) {
       super.addPoints(circuit);
    }
    
    @Override
    public void updatePoints(){
        updateOnePoint(-1, false, true);
        if(getInputs() != null){
            for(int i = 1; i < getInputs().length+1; i++){
                updateOnePoint(i, true, false);
            }
        }

       
    }

} 

