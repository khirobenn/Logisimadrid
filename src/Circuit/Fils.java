package Circuit;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class Fils {
	private Circuit circuit;
	private Gate gate;
	private Fils parent;

	private boolean isOutPutSet;
	private QuadBool output;
	// used for connected circuit
	private Set <Fils> connected;

	public Line l1;
	public Line l2;
	public Set<Line> lp;



	//used as the end point of Fils
	public Circle circle;
	//used as the start point of Fils
	public Circle circle2;

	private boolean dx, dy;

	private boolean isOutputOfVariable = false;

	public Fils(Circuit circuit, Fils parent, Gate gate){
		this.parent = parent;
		this.gate = gate;
		if(parent != null){
			output = parent.output;
		}
		else{
			output = QuadBool.NOTHING;
		}

		isOutPutSet = false;
		int pos = 20;
		this.circuit = circuit;
		connected = new HashSet<Fils>();

		dx = false;
		dy = false;

		circle = new Circle(0, 0, 10);
		circle.setCenterX(pos);
		circle.setCenterY(pos);

		circle2 = new Circle(0, 0, 10);
		circle2.setCenterX(pos);
		circle2.setCenterY(pos);

		l1 = new Line(10, -200, 10, -200);
		l2 = new Line(10, -200, 10, -200);

		lp = new HashSet<Line>();

		l1.setStrokeWidth(6);
		l2.setStrokeWidth(6);

		circle.setFill(Color.TRANSPARENT);
		circle2.setFill(Unity.NOTH);
		circle2.setOnMouseDragged(e -> dragFils(e, circle2, circle));
		circle2.setOnMouseReleased(e -> onRelease());   
		circle2.setOnMouseEntered(e -> addStroke(circle2));
		circle2.setOnMouseExited(e -> removeStroke(circle2));

		circuit.addElement(this);
	}

	public Fils(double x, double y, Circuit circuit, Fils parent, Gate gate){
		this(circuit, parent, gate);
		circle.setCenterX(x);
		circle2.setCenterX(x);

		circle.setCenterY(y);
		circle2.setCenterY(y);
	}

	public void setCircleToTransparent(){
		circle.setFill(Color.TRANSPARENT);
		circle2.setFill(Color.TRANSPARENT);
	}

	public void setFilsAsVariable(){
		isOutputOfVariable = true;
	}

	public void setGate(Gate gate){
		this.gate = gate;
	}

	public void addToConnected(Fils fils){
		connected.add(fils);
		fils.connected.add(this);
	}

	public boolean getIsAVariable(){
		return isOutputOfVariable;
	}


	private void recurseSetOutput(Deque<Fils> l, Fils oi){
		for(Fils elem : oi.connected){
			if(!l.contains(elem)){
				l.add(oi);
				if(elem.isOutPutSet == true && 
						((output == QuadBool.TRUE && elem.output == QuadBool.FALSE)
						 || output == QuadBool.FALSE && elem.output == QuadBool.TRUE)){
					elem.output = QuadBool.ERROR;
					output = QuadBool.ERROR;
					// changeColor(Unity.ERR);
					// elem.changeColor(Unity.ERR);
						 }
				else{
					elem.output = output;
					// switch (output) {
					//     case TRUE : elem.changeColor(Unity.ON); break;
					//     case FALSE : elem.changeColor(Unity.OFF); break;
					//     case ERROR : elem.changeColor(Unity.ERR); break;
					//     default : elem.changeColor(Unity.NOTH);
					// }
				}

				elem.isOutPutSet = true;
				recurseSetOutput(l, elem);
			}
		}
	}

	private void recurseGetOutput(Deque<Fils> l, Fils oi){
		for(Fils elem : oi.connected){
			if(!l.contains(elem)){
				l.add(oi);
				recurseGetOutput(l, elem);
				if(elem.gate != null){
					elem.gate.getOutput();
					output = elem.getOutput();
				}
				else output = elem.output;
				isOutPutSet = true;
			}
		}
	}

	public QuadBool getOutput(){
		if(!isOutPutSet){
			recurseGetOutput(new LinkedList<Fils>() , this);
		}
		return output;
	}

	public QuadBool getOutputValue(){ return output; }

	public void setOutput(QuadBool value){
		output = value;
		isOutPutSet = true;
		recurseSetOutput(new LinkedList<Fils>(), this);
	}

	public void setOutputValue(QuadBool value){
		output = value;

	}

	public void reinitialiseOutput(){
		isOutPutSet = false;
		output = QuadBool.NOTHING;
		changeColor(Unity.NOTH);
	}

	public void setIsOutputSet(boolean value){
		isOutPutSet = value;
	}

	public void changePlaceForPoints(double x, double y){
		circle.setCenterX(x);
		circle2.setCenterX(x);
		circle.setCenterY(y);
		circle2.setCenterY(y);
	}

	private void reinitialiseLine(Line l){
		double r = -200;
		setLineCoord(l1, 10, r, 10, r);
		circuit.getPane().getChildren().remove(l);
	}
	private void reinitialiseLines(){
		reinitialiseLine(l1);
		reinitialiseLine(l2);
	}

	private void setLineCoord(Line l, double startX, double startY, double endX, double endY){
		l.setStartX(startX);
		l.setStartY(startY);

		l.setEndX(endX);
		l.setEndY(endY);
	}

	private boolean isNoLine(Line l){
		if(l == null) return true;
		return l.getStartX() == l.getEndX() && l.getStartY() == l.getEndY();
	}


	private int sign(double start, double end){
		if(end > start) return 1;
		return -1;
	}

	private void dragFils(MouseEvent e, Circle circle, Circle circle2){

		changeColor(Color.BLACK);
		double xPoint = e.getX();
		double yPoint = e.getY();

		moveFils(xPoint, yPoint);
	}


	private boolean isSonInTheParent (Line l, Line ll){
		return (ll.contains(l.getStartX(), l.getStartY()) && ll.contains(l.getEndX(), l.getEndY()));
	}

	private void reinitLine(Line l){
		l.setStartX(-10);
		l.setStartY(-200);
		l.setEndX(-10);
		l.setEndY(-200);
	}

	public void onRelease(){
		if(dx || dy){
			if(l1.getStartX() != l1.getEndX() || l1.getStartY() != l1.getEndY()){
				if(!lp.isEmpty()){
					boolean access=false;
					for (Line l : lp) {
						if( l == l1) continue;
						if(isSonInTheParent(l1, l)){
							access = true;
                            break;
						}
					}
					if(!access){
                        createPointOnEachLine(l1);
                        System.out.println("Created");
					}
                    else{
                        System.out.println("Reini");
                        reinitLine(l1);
                    }
				}
				else{
					createPointOnEachLine(l1);
				}
			}
			if(l2.getStartX() != l2.getEndX() || l2.getStartY() != l2.getEndY()){
				if(!lp.isEmpty()){
					boolean access=false;
					for (Line l : lp) {
						if( l == l2) continue;
						if(isSonInTheParent(l2, l)){
							access = true;
							break;
						}
						
					}
					if(!access){
                        createPointOnEachLine(l2);
                        System.out.println("Created 2");
					}
                    else{
                        reinitLine(l2);
                        System.out.println("reini 2");

                    }

				}
				else{
					createPointOnEachLine(l2);  
				}
			}
			searchConnected();
		}
		circuit.eval(null);
		circuit.fixFilsColors();
	}

	public void swapCircles(){
		Circle tmp = circle;
		circle = circle2;
		circle2 = tmp;
        if(!isNoLine(l2)){
            Line tmpLine = l1;
            l1 = l2;
            l2 = tmpLine;
        }
        swapStartAndEnd(l1);
        swapStartAndEnd(l2);
	}

    private void swapStartAndEnd(Line l){
        double x = l.getStartX();
        double y = l.getStartY();

        l.setStartX(l.getEndX());
        l.setStartY(l.getEndY());

        l.setEndX(x);
        l.setEndY(y);
    }

	public void moveFils(double xPoint, double yPoint){
		if(!(circuit.getPane().getChildren().contains(l1) && circuit.getPane().getChildren().contains(l2))){
			circuit.getPane().getChildren().addAll(l1, l2);
		}

		xPoint = Unity.tranformDoubleToInt(xPoint);
		yPoint = Unity.tranformDoubleToInt(yPoint);

		if(xPoint == circle2.getCenterX() && yPoint != circle2.getCenterY()) dy = true;
		else if(xPoint != circle2.getCenterX() && yPoint == circle2.getCenterY()) dx = true;
		else if(xPoint == circle2.getCenterX() && yPoint == circle2.getCenterY()){
			dx = false;
			dy = false;
		}

		if(dx){
			setLineCoord(l1, circle2.getCenterX(), circle2.getCenterY(), xPoint, circle2.getCenterY());
			setLineCoord(l2, xPoint, circle2.getCenterY(), xPoint, yPoint);
		}
		else if(dy){
			setLineCoord(l1, circle2.getCenterX(), circle2.getCenterY(), circle2.getCenterX(), yPoint);
			setLineCoord(l2, circle2.getCenterX(), yPoint, xPoint, yPoint);

		}
		else if(!dx && !dy){
			reinitialiseLines();
		}

		circle.setCenterX(xPoint);
		circle.setCenterY(yPoint);
	}

	public Point2D getCircle2Coord(){
		return new Point2D(circle2.getCenterX(), circle2.getCenterY());
	}
	
	public Point2D getCircle1Coord(){
		return new Point2D(circle.getCenterX(), circle.getCenterY());
	}

	public void setCircleFill(Color color){
		circle.setFill(color);
	}

	public void setCircle2Fill(Color color){
		circle2.setFill(color);
	}

	public void addPoint(Pane layout){
		layout.getChildren().addAll(circle, circle2, l1, l2);
	}

	private void addStroke(Shape sh){
		circuit.getPane().getChildren().removeAll(l1, l2);
		circuit.getPane().getChildren().addAll(l1, l2);
		sh.setStrokeWidth(Unity.STROKE_WIDTH);
		sh.setStroke(Unity.OFF);
	}

	private void removeStroke(Shape sh){
		sh.setStrokeWidth(0);
		sh.setStroke(null);
	}



	/**
	 * Adding fils for a line we created, such that for each point we can drag it.
	 */
	private void addFilsInLine(double p1, double p2, double constantCoord, int n, Line l){ 
		// the n is for the position of the line
		// n = 0 , x is constant else y is constant
		p1 = Unity.tranformDoubleToInt(p1);
		p2 = Unity.tranformDoubleToInt(p2);
		if(p1 == p2) return;
		int x = sign(p1, p2) * Unity.x;
		int distance = sign(p1, p2)*(int)(p2-p1);

		double start = p1;

		double xCord, yCord;
		if(n == 0){
			xCord = Unity.tranformDoubleToInt(constantCoord);
			yCord = start;
		}
		else{
			xCord = start;
			yCord = Unity.tranformDoubleToInt(constantCoord);
		}

		for(int i = 0; i <= distance; i += Unity.x) {
			Fils element = new Fils(xCord, yCord, circuit, this, null);
			element.lp=lp;
			lp.add(l);

			if(parent != null && (parent.l1.contains(xCord, yCord) || parent.l2.contains(xCord, yCord))
					&& (parent.l2.getEndX() != xCord || parent.l2.getEndY() != yCord)){
				element.circle2.setFill(circle2.getFill());
					}
			else{
				element.circle2.setFill(Color.TRANSPARENT);
			}

			connected.add(element);
			element.connected.add(this);

			if(n == 0){
				yCord += x;
			}
			else{
				xCord += x;
			}
		}
	}

    public void setLp(Fils source){
        lp = source.lp;
        lp.add(l1);
        lp.add(l2);
    }

	/** 
	 * Adding circuit for our two lines, we call addFilsInLine for the two line in our class.
	 */
	private void createPointOnEachLine(Line l){
		if(l.getEndX() == l.getStartX()){
			addFilsInLine(l.getStartY(), l.getEndY(), l.getEndX(), 0, l);
		}
		else if(l.getEndY() == l.getStartY()){
			addFilsInLine(l.getStartX(), l.getEndX(), l.getEndY(), 1, l);
		}
	}

	private void searchConnected(){
		Point2D point = new Point2D(circle.getCenterX(), circle.getCenterY());
		Set <Fils> list = circuit.getFilsList();
		for(Fils elem : list){
			if( elem != this && 
					!connected.contains(elem) && 
					// !elem.isPointFixe() &&
					(elem.l1.contains(point) 
					 || elem.l2.contains(point)
					 || (elem.circle.getCenterX() == point.getX() && elem.circle.getCenterY() == point.getY())
					 || (elem.circle2.getCenterX() == point.getX() && elem.circle2.getCenterY() == point.getY()))){
				elem.connected.add(this);
				connected.add(elem);
					 }
		}
	}

	public void changeColor(Color clr){
		if(!(circle2.getFill().equals(Color.TRANSPARENT))){
			circle2.setFill(clr);
		}

		if(!(circle.getFill().equals(Color.TRANSPARENT))){
			circle.setFill(clr);
		}

		l1.setStroke(clr);
		l2.setStroke(clr);
	}

	public int getConnectedNb(){
		return connected.size();
	}


	public void removeAttributesAndDelete(){
		circuit.getPane().getChildren().removeAll(circle, circle2);
		if(isNoLine(l1)) circuit.getPane().getChildren().remove(l1);
		if(isNoLine(l2)) circuit.getPane().getChildren().remove(l2);
		if(isNoLine(l2) && isNoLine(l2) && connected.size() == 0){
			circuit.removeElement(this);
		}
	}

	public void hide(){
		circuit.getPane().getChildren().removeAll(circle, circle2, l1, l2);
	}

	public Fils getParent () {
		return this.parent ;
	}

	public Gate getGate () {
		return this.gate ;
	}

}
