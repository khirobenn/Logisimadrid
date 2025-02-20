package Gate;

import java.util.Deque;
import java.util.LinkedList;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class OIput {
    private Fils fils;
    private Gate gate;
    private OIput parent;

    private boolean isOutPutSet;
    private QuadBool output;
    // used for connected fils
    private Deque <OIput> connected;

    private Line l1;
    private Line l2;

    //used as the end point of OIput
    private Circle circle;
    //used as the start point of OIput
    private Circle circle2;

    private boolean dx, dy;

    public OIput(Fils fils, OIput parent, Gate gate){
        this.parent = parent;
        this.gate = gate;
        if(parent != null){
            output = parent.output;
        }
        else{
            output = QuadBool.FALSE;
        }
        isOutPutSet = false;
        int pos = 20;
        this.fils = fils;
        connected = new LinkedList<OIput>();

        dx = false;
        dy = false;

        circle = new Circle(0, 0, 10);
        circle.setCenterX(pos);
        circle.setCenterY(pos);

        circle2 = new Circle(0, 0, 10);
        circle2.setCenterX(pos);
        circle2.setCenterY(pos);

        l1 = new Line(-10, -10, -10, -10);
        l2 = new Line(-10, -10, -10, -10);

        l1.setStrokeWidth(6);
        l2.setStrokeWidth(6);

        circle.setFill(Color.TRANSPARENT);
        circle2.setFill(Unity.OFF);
        circle2.setOnMouseDragged(e -> dragOIput(e, circle2, circle));
        circle2.setOnMouseReleased(e -> onRelease());
        circle2.setOnMouseEntered(e -> addStroke(circle2));
        circle2.setOnMouseExited(e -> removeStroke(circle2));

        fils.addElement(this);
    }

    public OIput(double x, double y, Fils fils, OIput parent, Gate gate){
        this(fils, parent, gate);
        circle.setCenterX(x);
        circle2.setCenterX(x);

        circle.setCenterY(y);
        circle2.setCenterY(y);
    }

    private void recurseSetOutput(Deque<OIput> l, OIput oi){
        for(OIput elem : oi.connected){
            if(!l.contains(elem)){
                l.add(oi);
                if(elem.isOutPutSet){
                    if((elem.output == QuadBool.FALSE && output == QuadBool.TRUE) ||
                    (elem.output == QuadBool.TRUE && output == QuadBool.FALSE)){
                        output = QuadBool.ERROR;
                        elem.output = QuadBool.ERROR;
                        changeColor(Unity.ERR);

                    }else  elem.output = output;
                }
                else elem.output = output;
          
                switch (output) {
                    case TRUE -> elem.changeColor(Unity.ON);
                    case FALSE -> elem.changeColor(Unity.OFF);
                    case ERROR -> elem.changeColor(Unity.ERR);
                    default -> elem.changeColor(Unity.NOTH);
                }
                
                elem.isOutPutSet = true;
                recurseSetOutput(l, elem);
            }
        }
    }

    private void recurseGetOutput(Deque<OIput> l, OIput oi){
        for(OIput elem : oi.connected){
            if(!l.contains(elem)){
                l.add(oi);
                recurseGetOutput(l, elem);
                if(elem.gate != null) output = elem.gate.getOutput();
                else output = elem.output;
                switch (output) {
                    case TRUE -> elem.changeColor(Unity.ON);
                    case FALSE -> elem.changeColor(Unity.OFF);
                    case ERROR -> elem.changeColor(Unity.ERR);
                    default -> elem.changeColor(Unity.NOTH);
                }
                isOutPutSet = true;
            }
        }
    }
    
    public QuadBool getOutput(){
        if(!isOutPutSet){
            recurseGetOutput(new LinkedList<OIput>() , this);
        }
        return output;
    }

    public void setOutput(QuadBool value){
        output = value;
        isOutPutSet = true;
        recurseSetOutput(new LinkedList<OIput>(), this);
    }

    public void reinitialiseOutput(){
        isOutPutSet = false;
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
        int r = -10;
        setLineCoord(l1, r, r, r, r);
        fils.getPane().getChildren().remove(l);
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

    private void onRelease(){
        if(dx || dy){
            boolean isL1InParent = false, isL2InParent = false;
            if(parent != null){
                isL1InParent = lineContainsLine(parent.l1, l1) || lineContainsLine(parent.l2, l1);
                isL2InParent = lineContainsLine(parent.l1, l2) || lineContainsLine(parent.l2, l2);
            }
            boolean fixOnL1 = false;
            boolean fixOnL2 = false;
            if(isL1InParent && isL2InParent){
                if(l1.getStartX() == parent.l2.getEndX() && l1.getStartY() == parent.l2.getEndY()){
                    parent.l2.setEndX(l1.getEndX());
                    parent.l2.setEndY(l1.getEndY());
                    parent.circle.setCenterX(l1.getEndX());
                    parent.circle.setCenterY(l1.getEndY());

                    // TO DO
                }
                reinitialiseLines();
                return;
            }
            else if(isL1InParent){
                // Have to add this func
                fixOnL1 = fixLineToAdd(l2, parent.l1);
                fixOnL2 = fixLineToAdd(l2, parent.l2);
                
                if(fixOnL1 || fixOnL2){
                    parent.createPointOnEachLine(l2);
                    reinitialiseLines();
                }
                else{
                    createPointOnEachLine(l2);
                }
            }
            else if(isL2InParent){
                
            }
            else{
                if(parent != null){
                    fixOnL1 = fixLineToAdd(l1, parent.l1);
                    fixOnL2 = fixLineToAdd(l1, parent.l2);
                }

                if(fixOnL1 || fixOnL2){
                    parent.createPointOnEachLine(l1);
                    reinitialiseLine(l1);
                }
                else{
                    createPointOnEachLine(l1);
                }

                if(parent != null 
                && parent.l2.getEndX() == parent.l2.getStartX() 
                && parent.l2.getEndY() == parent.l2.getStartY()){
                    parent.l2 = l2;
                    parent.createPointOnEachLine(l2);
                    l2.setStroke(Color.BLUE);
                    l2 = new Line();
                    reinitialiseLine(l2);
                }else{

                    createPointOnEachLine(l2);
                }
            }

            if(l1.getStartX() == -10 && l2.getStartX() == -10){
                reinitialiseParemeters();
            }
            else{
                if(l1.getStroke() == Color.BLACK){
                    l1.setStroke(Color.BLUE);
                    l2.setStroke(Color.BLUE);
                }
                circle.setCenterX(l2.getEndX());
                circle.setCenterY(l2.getEndY());
            }
        }

        searchConnected();
        fils.eval(null);
    }

    private void reinitialiseParemeters(){
        l1.setStroke(Color.DARKBLUE);
        l2.setStroke(Color.DARKBLUE);
        circle.setCenterX(circle2.getCenterX());
        circle.setCenterY(circle2.getCenterY());
    }

    private int sign(double start, double end){
        if(end > start) return 1;
        return -1;
    }

    // n is for direction to compare x or y ; n = 0, we compare x coordinates
    // n = 1, we compare y coordinates
    private boolean areInSameDirection(Line l, Line k, int n){
        if(n == 0) return sign(l.getStartX(), l.getEndX()) == sign(k.getStartX(), k.getEndX());
        return sign(l.getStartY(), l.getEndY()) == sign(k.getStartY(), k.getEndY());
    }

    private boolean fixLineToAdd(Line l, Line lToExtend){
        if(l.getStartX() == lToExtend.getStartX() 
        && l.getStartX() == l.getEndX() 
        && lToExtend.getStartX() == lToExtend.getEndX()){
            if(areInSameDirection(l, lToExtend, 1)){
                double start = lToExtend.getEndY();
                lToExtend.setEndY(l.getEndY());
                l.setStartY(start);
                return true;
            }
            else{
                l.setStartY(lToExtend.getStartY());
                return false;
            }
        }
        else if(l.getStartY() == lToExtend.getStartY() 
        && l.getStartY() == l.getEndY() 
        && lToExtend.getStartY() == lToExtend.getEndY()){
            if(areInSameDirection(l, lToExtend, 0)){
                double start = lToExtend.getEndX();
                lToExtend.setEndX(l.getEndX());
                l.setStartX(start);
                return true;
            }
            else{
                l.setStartX(lToExtend.getStartX());
                return false;
            }
        }
        return false;
    }
    /** 
     * returns true if line1 contains line2
     * */
    private boolean lineContainsLine(Line line1, Line line2){
        return line1.contains(new Point2D(line2.getStartX(), line2.getStartY())) && line1.contains(new Point2D(line2.getEndX(), line2.getEndY()));
    }


    private void dragOIput(MouseEvent e, Circle circle, Circle circle2){
        if(!(fils.getPane().getChildren().contains(l1) && fils.getPane().getChildren().contains(l2))){
            fils.getPane().getChildren().addAll(l1, l2);
        }
        double xPoint = e.getX();
        double yPoint = e.getY();

        xPoint = Unity.tranformDoubleToInt(xPoint);
        yPoint = Unity.tranformDoubleToInt(yPoint);

        if(xPoint == circle.getCenterX() && yPoint != circle.getCenterY()) dy = true;
        else if(xPoint != circle.getCenterX() && yPoint == circle.getCenterY()) dx = true;
        else if(xPoint == circle.getCenterX() && yPoint == circle.getCenterY()){
            dx = false;
            dy = false;
        }

        if(dx){
            setLineCoord(l1, circle.getCenterX(), circle.getCenterY(), xPoint, circle.getCenterY());
            setLineCoord(l2, xPoint, circle.getCenterY(), xPoint, yPoint);
        }
        else if(dy){
            setLineCoord(l1, circle.getCenterX(), circle.getCenterY(), circle.getCenterX(), yPoint);
            setLineCoord(l2, circle.getCenterX(), yPoint, xPoint, yPoint);
    
        }
        else if(!dx && !dy){
            reinitialiseLines();
        }

        circle2.setCenterX(xPoint);
        circle2.setCenterY(yPoint);
    }

    public void addPoint(Pane layout){
        layout.getChildren().addAll(circle, circle2);
    }

    private void addStroke(Shape sh){
        sh.setStrokeWidth(Unity.STROKE_WIDTH);
        sh.setStroke(Unity.OFF);
    }

    private void removeStroke(Shape sh){
        sh.setStrokeWidth(0);
        sh.setStroke(null);
    }


    /**
     * Adding Fils for a line we created, such that for each point we can drag it.
     */
    private void addFilsInLine(double p1, double p2, double constantCoord, int n){ 
        // the n is for the position of the line
        // n = 0 , x is constant else y is constant
        int x = sign(p1, p2) * Unity.x;
        int distance = sign(p1, p2)*(int)(p2-p1);
        double start = p1;

        double xCord, yCord;
        if(n == 0){
            xCord = constantCoord;
            yCord = start;
        }
        else{
            xCord = start;
            yCord = constantCoord;
        }
        
        for(int i = 0; i <= distance; i += Unity.x) {
            OIput element = new OIput(xCord, yCord, fils, this, null);
            
            if(parent != null && (parent.l1.contains(xCord, yCord) || parent.l2.contains(xCord, yCord))
            && (parent.l2.getEndX() != xCord || parent.l2.getEndY() != yCord)){
                element.circle2.setFill(circle2.getFill());
            }
            else{
                element.circle2.setFill(Color.TRANSPARENT);
            }

            if(n == 0){
                yCord += x;
            }
            else{
                xCord += x;
            }
            connected.push(element);
            element.connected.push(this);

            start += x;
        }
    }

    /** 
     * Adding fils for our two lines, we call addFilsInLine for the two line in our class.
    */
    private void createPointOnEachLine(Line l){
        if(l.getEndX() == l.getStartX()){
            addFilsInLine(l.getStartY(), l.getEndY(), l.getEndX(), 0);
        }
        else if(l.getEndY() == l.getStartY()){
            addFilsInLine(l.getStartX(), l.getEndX(), l.getEndY(), 1);
        }
    }

    private void searchConnected(){
        Point2D point = new Point2D(circle.getCenterX(), circle.getCenterY());
        Deque <OIput> list = fils.getFilsList();
        for(OIput elem : list){
            if( elem != this && 
            !connected.contains(elem) && 
            // !elem.isPointFixe() &&
            (elem.l1.contains(point) 
            || elem.l2.contains(point)
            || elem.circle.contains(point)
            || elem.circle2.contains(point))){
                elem.connected.add(this);
                connected.add(elem);
            }
        }
    }

    public void changeColor(Color clr){
        if(!(circle2.getFill() == Color.TRANSPARENT)){
            circle2.setFill(clr);
        }
        l1.setStroke(clr);
        l2.setStroke(clr);
    }

    public int getConnectedNb(){
        return connected.size();
    }
}
