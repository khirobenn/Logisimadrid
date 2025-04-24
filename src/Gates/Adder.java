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

public class Adder extends Gate {
	private Fils retenuIn;
	private boolean isRetenuInReleased;

	private Fils retenuOut;
	private boolean isRetenuReleased;
	Circuit circuit;

	public Adder(Circuit circuit, Pane layout, double x, double y) {
		super("ADDER", 2, circuit, layout, x, y);
		this.circuit = circuit;
		Shape shape = getShape();
		float distance = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxY());
		retenuOut = new Fils(Unity.tranformDoubleToInt(shape.getLayoutX()), Unity.tranformDoubleToInt(shape.getLayoutY() + distance/2),  circuit, null, this, true);
		retenuIn = new Fils(Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxX() + shape.getLayoutX()), Unity.tranformDoubleToInt(shape.getLayoutY() + distance/2),  circuit, null, this, true);
		isRetenuReleased = false;
		isRetenuInReleased = false;
		setText("in", shape.getLayoutBounds().getMaxX() / 2 + shape.getLayoutX(), shape.getLayoutBounds().getMaxY() / 2 + shape.getLayoutY());
		addFilsToOthers(retenuIn);
		addFilsToOthers(retenuOut);
	}

	public Fils getRetenuOut() { 
		return retenuOut; 
	}

	public void searchConnectedOtherFils(){
		retenuIn.searchConnected();
		retenuOut.searchConnected();
	}

	@Override
	public void evaluateOutput() {
		Fils[] inputs = getInputs();
		QuadBool a = inputs[0].getOutput();
		QuadBool b = inputs[1].getOutput();
		QuadBool retenu = retenuIn.getOutput();

		setOutput(QuadBool.xor(QuadBool.xor(a, b), retenu));
		retenuOut.setOutput(OrQuad.Or(AndQuad.And(a, b), AndQuad.And(retenu, QuadBool.xor(a, b))));
		setIHaveOutput(true);
	}

	@Override
	public void addPoints(Circuit circuit) {
		Fils[] inputs = getInputs();
		Shape shape = getShape();
		int distance = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxX());
		Fils output = new Fils(Unity.tranformDoubleToInt(shape.getLayoutX() + distance/2), Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxY() + shape.getLayoutY()), circuit, null, this, true);
		setOutputFils(output);
		if(inputs != null){
			distance /= (inputs.length + 1);
			for(int i = 1; i < inputs.length+1; i++){
				inputs[i-1] = new Fils(Unity.tranformDoubleToInt(shape.getLayoutX() + i*distance), Unity.tranformDoubleToInt(shape.getLayoutY()), circuit, null, null, true);
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

		updateRetenuPoint(true, true);
		updateRetenuInPoint(false, false);
	}

	public void updateRetenuPoint(boolean a, boolean b){
		int distance;
		Shape shape = getShape();
		if(a == b){
			distance = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxY());
		}
		else{
			distance = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxX());
		}

		double coef = 0.5;

		int x = 0, y = 0;

		if(a == true && b == true){
			x = Unity.tranformDoubleToInt(shape.getLayoutX());
			y = Unity.tranformDoubleToInt(shape.getLayoutY() + distance*coef);
		}
		else if(a == true && b == false){
			x = Unity.tranformDoubleToInt(shape.getLayoutX() + distance*coef);
			y = Unity.tranformDoubleToInt(shape.getLayoutY());
		}
		else if(a == false && b == true){
			x = Unity.tranformDoubleToInt(shape.getLayoutX() + distance*coef);
			y = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxY() + shape.getLayoutY());
		}
		else if(a == false && b == false){
			x = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxX() + shape.getLayoutX());
			y = Unity.tranformDoubleToInt(shape.getLayoutY() + distance*coef);
		}

		if(retenuOut.getConnectedNb() == 0){
			retenuOut.changePlaceForPoints(x, y);
		}
		else if(isRetenuReleased){
			retenuOut.changeColor(Color.BLACK);
			retenuOut.moveFils(x, y);
		}
		else{
			Point2D coordPreviousOutput = retenuOut.getCircle2Coord();
			Fils newOutput = new Fils(coordPreviousOutput.getX(), coordPreviousOutput.getY(), circuit, null, this, true);
			newOutput.setCircleFill(Color.BLACK);
			newOutput.setCircle2Fill(Color.TRANSPARENT);
			retenuOut.setGate(null);
			retenuOut.addToConnected(newOutput);
			retenuOut.setCircleToTransparent();

			newOutput.setLp(retenuOut);
			retenuOut = newOutput;
			isRetenuReleased = true;
		}
	}

	public void updateRetenuInPoint(boolean a, boolean b){
		int distance;
		Shape shape = getShape();
		if(a == b){
			distance = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxY());
		}
		else{
			distance = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxX());
		}

		double coef = 0.5;

		int x = 0, y = 0;

		if(a == true && b == true){
			x = Unity.tranformDoubleToInt(shape.getLayoutX());
			y = Unity.tranformDoubleToInt(shape.getLayoutY() + distance*coef);
		}
		else if(a == true && b == false){
			x = Unity.tranformDoubleToInt(shape.getLayoutX() + distance*coef);
			y = Unity.tranformDoubleToInt(shape.getLayoutY());
		}
		else if(a == false && b == true){
			x = Unity.tranformDoubleToInt(shape.getLayoutX() + distance*coef);
			y = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxY() + shape.getLayoutY());
		}
		else if(a == false && b == false){
			x = Unity.tranformDoubleToInt(shape.getLayoutBounds().getMaxX() + shape.getLayoutX());
			y = Unity.tranformDoubleToInt(shape.getLayoutY() + distance*coef);
		}

		if(retenuIn.getConnectedNb() == 0){
			retenuIn.changePlaceForPoints(x, y);
		}
		else if(isRetenuInReleased){
			retenuIn.changeColor(Color.BLACK);
			retenuIn.moveFils(x, y);
		}
		else{
			Point2D coordPreviousOutput = retenuIn.getCircle2Coord();
			Fils newOutput = new Fils(coordPreviousOutput.getX(), coordPreviousOutput.getY(), circuit, null, this, true);
			newOutput.setCircleFill(Color.BLACK);
			newOutput.setCircle2Fill(Color.TRANSPARENT);
			retenuIn.setGate(null);
			retenuIn.addToConnected(newOutput);
			retenuIn.setCircleToTransparent();

			newOutput.setLp(retenuIn);
			retenuIn = newOutput;
			isRetenuInReleased = true;
		}
	}

	@Override
	public void onRelease(){
		super.onRelease();
		if(isRetenuReleased){
			retenuOut.swapCircles();
		}
		retenuOut.onRelease();

		if(isRetenuInReleased){
			retenuIn.swapCircles();
		}
		retenuIn.onRelease();
	}
	public Fils getRetenuIn() {
		return retenuIn;
	}
} 
