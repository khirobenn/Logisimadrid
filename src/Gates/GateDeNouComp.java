package Gates ;

import Circuit.Circuit;
import Circuit.Fils;
import Circuit.Gate;
import Circuit.QuadBool;
import Circuit.Unity;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import Circuit.NouveauComposant ;

public class GateDeNouComp extends Gate {
    
    private final boolean[][] tableVerite ;
    private final int nbEntree ;
    private final int nbSortie ;

    public GateDeNouComp ( NouveauComposant cmp , Circuit circuit , Pane layout , double x , double y ) {
        super(cmp.getNameComp() , cmp.getNbENtComp() , circuit , layout , x , y ) ;
        this.tableVerite = cmp.getTableVerite() ;
        this.nbEntree = cmp.getNbENtComp() ;
        this.nbSortie = cmp.getNbSortComp() ; 

        Shape shape = getShape() ;
        double shapeX = shape.getLayoutX() ;
        double shapeY = shape.getLayoutY() ;
        double shapeWidth = shape.getLayoutBounds().getWidth() ;
        double shapeHeight = shape.getLayoutBounds().getHeight() ;
        double centreX = shapeX + shapeWidth / 2 - 1 ;
        double centreY = shapeY + shapeHeight / 2 - 1 ;

        StringBuilder l = new StringBuilder() ;
        for (int i=1 ; i<= nbEntree ; i++ ) {
            l.append("E").append(i).append(" ") ;
         }
         setText(l.toString().trim(), centreX, centreY);
    }

    @Override 
    // la sortie de la porte 
    public void evaluateOutput () {
        Fils[] inputs = getInputs();
        QuadBool[] inpv = new QuadBool[nbEntree] ;
        for (int i=0 ; i<nbEntree ; i++ ) {
            inpv[i] = inputs[i].getOutput() ;
            // si les entrees sont erreur et nothing
            if ( inpv[i]== QuadBool.ERROR || inpv[i]==QuadBool.NOTHING) {
                setOutput(QuadBool.ERROR);
                setIHaveOutput(true);
                return ;
            }
        }

        // si dans la table y a des entier on fait la conversion vers nos cordonnées
        boolean[] in = new boolean[nbEntree] ;
        for ( int i=0 ; i<nbEntree ; i++ ) {
            in[i] = ( inpv[i] == QuadBool.TRUE ) ;
        }

        // on compare les entrees avec la table de verité donné
        for ( boolean[] ligne : tableVerite ){
            boolean m = true ;
            for (int i=0 ; i<nbEntree ; i++ ) {
               if ( ligne[i] != in[i]) {
                m=false ;
                break ;
               }
            }
            if ( m ) {
                boolean out = ligne[nbEntree] ;
                setOutput(out ? QuadBool.TRUE : QuadBool.FALSE);
                setIHaveOutput(true);
                return ;
            }
        }

        // dans le cas on a rien trouvé dans la table donnée
        setOutput(QuadBool.ERROR);
        setIHaveOutput(true);
        

    }

    @Override 
	public void addPoints(Circuit circuit) {
		Fils[] inputs = getInputs();
		Shape shape = getShape() ;
        double x = shape.getLayoutX() ;
        double y = shape.getLayoutY() ;
        int width = Unity.tranformDoubleToInt(shape.getLayoutBounds().getWidth()) ;
        int height = Unity.tranformDoubleToInt(shape.getLayoutBounds().getHeight()) ;
        if ( inputs!=null ) {
            int d = width / ( nbEntree+1 ) ;
            for ( int i=0 ; i<nbEntree ; i++ ) {
                inputs[i] = new Fils(Unity.tranformDoubleToInt(x+(i+1)*d), Unity.tranformDoubleToInt(y), circuit, null , null , true) ;
            }
        }
        Fils output = new Fils(Unity.tranformDoubleToInt(x+width/2), Unity.tranformDoubleToInt(y+height), circuit, null , this , true ) ;
        setOutputFils(output);

	}

    
    @Override
    public void updatePoints() {
        updateOnePoint(-1,false,true);
        for ( int i=1 ; i<= nbEntree ; i++ ) {
            updateOnePoint(i,true,false) ;
        }
    }


    @Override
    public void onRelease () {
        super.onRelease();
        for ( Fils input : getInputs()) {
            input.onRelease();
        }
    }

}
