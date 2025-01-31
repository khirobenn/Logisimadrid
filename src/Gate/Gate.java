package Gate;

import javafx.scene.shape.Shape;
import Gates.Variable;

public abstract class Gate {
    private String name;
    private Gate[] inputs;
    private boolean output;
    private boolean iHaveOutput;
    private Shape shape;

    public Gate(String name){
        this.name = name;
        this.iHaveOutput = false;
    }

    public Gate(String name, boolean value){
        this.name = name;
        this.output = value;
        this.iHaveOutput = true;
    }

    public Gate(String name, int nb){
        this.name = name;
        this.iHaveOutput = false;
        this.inputs = new Gate[nb];
    }

    public String getName(){
        return name;
    }

    public Gate[] getInputs(){
        return inputs;
    }

    public void setOutput(boolean value){
        output = value;
    }

    public void setShape(Shape shape){
        this.shape = shape;
    }

    public void setIHaveOutput(boolean value){
        iHaveOutput = value;
    }

    private void check(){
        for(int i=0; i < inputs.length; i++){
            if(inputs[i] == null){
                inputs[i] = new Variable(false);
            }
        }
    }

    public boolean getOutput(){
        if(iHaveOutput)
            return output;

        changeOutput();
        return getOutput();
    }

    public void setInput(int n, Gate gate){
        inputs[n] = gate;
    }

    public void changeOutput(){
        check();
        evaluateOutput();
    }

    public abstract void evaluateOutput();
    
}
