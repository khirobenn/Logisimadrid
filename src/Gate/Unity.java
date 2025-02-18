package Gate;

public class Unity {
    public static final int x = 20;
    public static final int y = 20;
    public static final int width = 1200;
    public static final int height = 600;
    public static final double STROKE_WIDTH = 2.0;
    public static int tranformDoubleToInt(double xPoint){
        int x = Unity.x;
        return (int) (Math.floor(xPoint) - Math.floor(xPoint)%x);
    }
}
