package Gate;

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

public class OIput {
    private Fils fils;
    private Gate gate;
    private OIput parent;

    private boolean isOutPutSet;
    private QuadBool output;
    // used for connected fils
    private Set <OIput> connected;

    public Line l1;
    public Line l2;

    //used as the end point of OIput
    public Circle circle;
    //used as the start point of OIput
    public Circle circle2;

    private boolean dx, dy;

    private boolean isPositionEmpty[][];

    private boolean isOutputOfVariable = false;

    public OIput(Fils fils, OIput parent, Gate gate, boolean[][] isPositionEmpty){
        this.parent = parent;
        this.gate = gate;
        if(parent != null){
            output = parent.output;
        }
        else{
            output = QuadBool.NOTHING;
        }

        if(isPositionEmpty == null){
            initPositionArray();
        }
        else{
            this.isPositionEmpty = isPositionEmpty;
        }

        isOutPutSet = false;
        int pos = 20;
        this.fils = fils;
        connected = new HashSet<OIput>();

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
        circle2.setFill(Unity.NOTH);
        circle2.setOnMouseDragged(e -> dragOIput(e, circle2, circle));
        circle2.setOnMouseReleased(e -> onRelease());
        circle2.setOnMouseEntered(e -> addStroke(circle2));
        circle2.setOnMouseExited(e -> removeStroke(circle2));

        fils.addElement(this);
    }

    private void initPositionArray(){
        isPositionEmpty = new boolean[1920][1080];
        for(int i = 0; i < 1920; i++){
            for(int j = 0; j < 1080; j++){
                isPositionEmpty[i][j] = false;
            }
        }
    }

    public void setOIputAsVariable(){
        isOutputOfVariable = true;
    }

    public boolean getIsAVariable(){
        return isOutputOfVariable;
    }

    public OIput(double x, double y, Fils fils, OIput parent, Gate gate, boolean[][] isPositionEmpty){
        this(fils, parent, gate, isPositionEmpty);
        circle.setCenterX(x);
        circle2.setCenterX(x);

        circle.setCenterY(y);
        circle2.setCenterY(y);
        this.isPositionEmpty[(int)x][(int)y] = true;
    }

    private void recurseSetOutput(Deque<OIput> l, OIput oi){
        for(OIput elem : oi.connected){
            if(!l.contains(elem)){
                l.add(oi);
                if(elem.isOutPutSet == true && 
                ((output == QuadBool.TRUE && elem.output == QuadBool.FALSE)
                || output == QuadBool.FALSE && elem.output == QuadBool.TRUE)){
                    elem.output = QuadBool.ERROR;
                    output = QuadBool.ERROR;
                    changeColor(Unity.ERR);
                    elem.changeColor(Unity.ERR);
                }
                else{
                    elem.output = output;
                    switch (output) {
                        case TRUE : elem.changeColor(Unity.ON); break;
                        case FALSE : elem.changeColor(Unity.OFF); break;
                        case ERROR : elem.changeColor(Unity.ERR); break;
                        default : elem.changeColor(Unity.NOTH);
                    }
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
                    case TRUE : elem.changeColor(Unity.ON); break;
                    case FALSE : elem.changeColor(Unity.OFF); break;
                    case ERROR : elem.changeColor(Unity.ERR); break;
                    default : elem.changeColor(Unity.NOTH);
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

    public QuadBool getOutputValue(){ return output; }

    public void setOutput(QuadBool value){
        output = value;
        isOutPutSet = true;
        recurseSetOutput(new LinkedList<OIput>(), this);
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

    private boolean isNoLine(Line l){
        if(l == null) return true;
        return l.getStartX() == l.getEndX() && l.getStartY() == l.getEndY();
    }

    private void onRelease(){
        if(dx || dy){
            if(l1.getStartX() != l1.getEndX() || l1.getStartY() != l1.getEndY()){
                createPointOnEachLine(l1);
            }
            if(l2.getStartX() != l2.getEndX() || l2.getStartY() != l2.getEndY()){
                createPointOnEachLine(l2);
            }
            searchConnected();
            circle.setFill(Color.TRANSPARENT);
        }
        fils.eval(null);
    }

    private int sign(double start, double end){
        if(end > start) return 1;
        return -1;
    }

    private void dragOIput(MouseEvent e, Circle circle, Circle circle2){
        if(!(fils.getPane().getChildren().contains(l1) && fils.getPane().getChildren().contains(l2))){
            fils.getPane().getChildren().addAll(l1, l2);
        }
        changeColor(Color.BLACK);
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
            OIput element = new OIput(xCord, yCord, fils, this, null, isPositionEmpty);
            
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

    /** 
     * Adding fils for our two lines, we call addFilsInLine for the two line in our class.
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
        Set <OIput> list = fils.getFilsList();
        for(OIput elem : list){
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
        if(!(circle2.getFill() == Color.TRANSPARENT)){
            circle2.setFill(clr);
        }
        l1.setStroke(clr);
        l2.setStroke(clr);
    }

    public int getConnectedNb(){
        return connected.size();
    }


    public void removeAttributesAndDelete(){
        fils.getPane().getChildren().removeAll(circle, circle2);
        if(isNoLine(l1)) fils.getPane().getChildren().remove(l1);
        if(isNoLine(l2)) fils.getPane().getChildren().remove(l2);
        if(isNoLine(l2) && isNoLine(l2) && connected.size() == 0){
            fils.removeElement(this);
        }
    }
}
