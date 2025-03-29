package Gates;
import Circuit.Circuit;
import Circuit.Fils;
import Circuit.Gate;
import Circuit.QuadBool;
import javafx.scene.layout.Pane;

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
}
