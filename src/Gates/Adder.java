package Gates;
import Circuit.Circuit;
import Circuit.Fils;
import Circuit.Gate;
import Circuit.QuadBool;
import Circuit.Unity;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class Adder extends Gate {
    
    private Fils retenuOut;
    public Adder(Circuit circuit, Pane layout, double x, double y) {
        super("ADDER", 3, circuit, layout, x, y);
        this.retenuOut = new Fils(circuit, null, this);
    }

    public Fils getRetenuOut() { 
        return retenuOut; 
    }

    @Override
    public void evaluateOutput() {
        Fils[] inputs = getInputs();
        QuadBool result = inputs[0].getOutput();
        QuadBool retenu = inputs[0].getOutput();

        retenu = AndQuad.And(
            OrQuad.Or(retenu, inputs[1].getOutput()), 
            NotQuad.Not(AndQuad.And(retenu, inputs[1].getOutput()))
        ); // a XOR b

        retenu = AndQuad.And(retenu, inputs[2].getOutput()); // (a XOR b) AND r0 (retenue)
        retenu = OrQuad.Or(retenu, AndQuad.And(inputs[0].getOutput(), inputs[1].getOutput())); 
        // ((a XOR b) AND r0) OR (a AND b) => résultat de la retenue

        for (int i = 1; i < inputs.length; i++) {
            result = AndQuad.And(
                OrQuad.Or(result, inputs[i].getOutput()), 
                NotQuad.Not(AndQuad.And(result, inputs[i].getOutput()))
            );
        }

        setOutput(result);
        retenuOut.setOutput(retenu);
        setIHaveOutput(true);
    }

    @Override
    public void addPoints(Circuit circuit) {
        Fils[] inputs = getInputs();
        Shape shape = getShape();
        int distance = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxX());
        Fils output = new Fils(Unity.tranformDoubleToInt(shape.getLayoutX() + distance/2), Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxY() + shape.getLayoutY()), circuit, null, this);
        setOutputFils(output);
        if(inputs != null){
            distance /= inputs.length + 1;
            for(int i = 1; i < inputs.length+1; i++){
                inputs[i-1] = new Fils(Unity.tranformDoubleToInt(shape.getLayoutX() + i*distance), Unity.tranformDoubleToInt(shape.getLayoutY()), circuit, null, null);
            }   
        }
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
