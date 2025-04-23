package Circuit;
 
import javafx.scene.layout.Pane;

public class GateTestImpl extends Gate {

    public GateTestImpl(String name, int nb, Circuit circuit, Pane layout, double x, double y) {
        super(name, nb, circuit, layout, x, y);
    }

    @Override
    public void evaluateOutput() {
        
    }
}
