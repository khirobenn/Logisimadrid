package Circuit;

import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;   

public class Unity {
    public static final int x = 20;
    public static final int y = 20;
    public static final int width = 1400;
    public static final int height = 800;
    public static final double STROKE_WIDTH = 3.0;

    public static double minZoom = 0.41;
    public static double maxZoom = 1.5;
    public static int tranformDoubleToInt(double xPoint){
        int x = Unity.x;
        return (int) (Math.floor(xPoint) - Math.floor(xPoint)%x);
    }
    public static Color ON = Color.rgb(122, 240, 5);
    public static Color OFF = Color.rgb(0, 153, 0);
    public static Color ERR = Color.RED;
    public static Color NOTH = Color.BLUE;

    public static Font fontBold = Font.loadFont("file:fonts/Poppins-Bold.ttf", 18);
    public static Font font = Font.loadFont("file:fonts/Poppins-Medium.ttf", 16);

    public static TreeItem<Label> portesTree(){
        TreeItem<Label> tvPortes = new TreeItem<Label>(new Label("Portes"));
        tvPortes.getValue().setFont(fontBold);
        tvPortes.setExpanded(true);
        tvPortes.getChildren().addAll(
            new TreeItem<Label>(new Label("AND")),
            new TreeItem<Label>(new Label("OR")),
            new TreeItem<Label>(new Label("XOR")),
            new TreeItem<Label>(new Label("NAND")),
            new TreeItem<Label>(new Label("NOR")),
            new TreeItem<Label>(new Label("XNOR")),
            new TreeItem<Label>(new Label("NOT"))
        );

        for(TreeItem<Label> tm : tvPortes.getChildren()){
            tm.getValue().setFont(font);
        }

        return tvPortes;
    }

    public static TreeItem<Label> varAndOutTree(){
        TreeItem<Label> tvVarAndOut = new TreeItem<Label>(new Label("Variable & Output"));
        tvVarAndOut.getValue().setFont(fontBold);

        tvVarAndOut.getChildren().addAll(
            new TreeItem<Label>(new Label("VARIABLE")),
            new TreeItem<Label>(new Label("OUTPUT"))
        );

        for(TreeItem<Label> tm : tvVarAndOut.getChildren()){
            tm.getValue().setFont(font);
        }

        return tvVarAndOut;
    }

    public static TreeItem<Label> bitABitTree(){
        TreeItem<Label> tvBitaBit = new TreeItem<Label>(new Label("Bit a bit"));
        tvBitaBit.getValue().setFont(fontBold);

        tvBitaBit.getChildren().addAll(
            new TreeItem<Label>(new Label("ADDER")),
            new TreeItem<Label>(new Label("MULTIPLIER")),
            new TreeItem<Label>(new Label("ODDPARITY")),
            new TreeItem<Label>(new Label("EVENPARITY"))
        );

        for(TreeItem<Label> tm : tvBitaBit.getChildren()){
            tm.getValue().setFont(font);
        }

        return tvBitaBit;
    }

    public static TreeItem<Label> basculesTree(){
        TreeItem<Label> tvBascule = new TreeItem<Label>(new Label("Bascules & Horloge"));
        tvBascule.getValue().setFont(fontBold);

        tvBascule.getChildren().addAll(
            new TreeItem<Label>(new Label("BASCULE RS")) ,
            new TreeItem<Label>(new Label("HORLOGE")) ,
            new TreeItem<Label>(new Label("BASCULE JK")),
            new TreeItem<Label>(new Label("BASCULE D"))
        );

        for(TreeItem<Label> tm : tvBascule.getChildren()){
            tm.getValue().setFont(font);
        }

        return tvBascule;
    }
}
