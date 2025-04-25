package Circuit;


import javafx.scene.control.TreeItem;
import javafx.scene.paint.Color;   

public class Unity {
    public static final int x = 20;
    public static final int y = 20;
    public static final int width = 1400;
    public static final int height = 800;
    public static final double STROKE_WIDTH = 3.0;
    public static int tranformDoubleToInt(double xPoint){
        int x = Unity.x;
        return (int) (Math.floor(xPoint) - Math.floor(xPoint)%x);
    }
    public static Color ON = Color.rgb(122, 240, 5);
    public static Color OFF = Color.rgb(0, 153, 0);
    public static Color ERR = Color.RED;
    public static Color NOTH = Color.BLUE;

    public static TreeItem<String> portesTree(){
        TreeItem<String> tvPortes = new TreeItem<String>("Portes");
        tvPortes.setExpanded(true);
        tvPortes.getChildren().addAll(
            new TreeItem<String>("AND"),
            new TreeItem<String>("OR"),
            new TreeItem<String>("XOR"),
            new TreeItem<String>("NAND"),
            new TreeItem<String>("NOR"),
            new TreeItem<String>("XNOR"),
            new TreeItem<String>("NOT")
        );
        return tvPortes;
    }

    public static TreeItem<String> varAndOutTree(){
        TreeItem<String> tvVarAndOut = new TreeItem<String>("Variable & Output");
        tvVarAndOut.getChildren().addAll(
            new TreeItem<String>("VARIABLE"),
            new TreeItem<String>("OUTPUT")
        );
        return tvVarAndOut;
    }

    public static TreeItem<String> bitABitTree(){
        TreeItem<String> tvBitaBit = new TreeItem<String>("Bit a bit");
        tvBitaBit.getChildren().addAll(
            new TreeItem<String>("ADDER"),
            new TreeItem<String>("MULTIPLIER"),
            new TreeItem<String>("ODDPARITY"),
            new TreeItem<String>("EVENPARITY")
        );
        return tvBitaBit;
    }

    public static TreeItem<String> basculesTree(){
        TreeItem<String> tvBascule = new TreeItem<String>("Bascules & Horloge");
        tvBascule.getChildren().addAll(
            new TreeItem<String>("BASCULE RS") ,
            new TreeItem<String>("HORLOGE") ,
            new TreeItem<String>("BASCULE JK"),
            new TreeItem<String>("BASCULE D")
        );
        return tvBascule;
    }
}
