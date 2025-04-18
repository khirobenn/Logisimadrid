package Circuit;

import java.io.*;
import java.util.Set ;
import org.json.simple.JSONArray ;
import org.json.simple.parser.JSONParser ;
import org.json.simple.parser.ParseException ;
import org.json.simple.JSONObject ;



public class CircuitSaver {
private Circuit circuit ;

public CircuitSaver (Circuit circuit ) {
    this.circuit = circuit ;
}

// creer un fichier avec le text

public void saveCircuit ( String filename ) {
try {
    FileWriter writer = new FileWriter(filename ) ;
    writer.write ( exportCircuit().toCharArray () );
    System.out.println("Circuit enregistré avec succés dans " + filename ) ;
    writer.close();
    } catch ( IOException e ) {
    System.out.println ( "Errzue  pendant l'enregistrement " + e.getMessage() ) ;
    }
}

// transformer le circuit en texte 

@SuppressWarnings ("unchecked")
    private String exportCircuit () { 
    JSONObject obj = new JSONObject();
    
    
    // remplire le fichier avec toutes les portes creer dans le circuit et aussi avec toutes les outputs
    JSONArray gateArray = new JSONArray();
    Set<Gate> gates = circuit.getGates();  
    for (Gate gate : gates) { 
        JSONObject o = new JSONObject() ;
        o.put("name", gate.getName());
        o.put("value", gate.getText() );
        o.put("inputs_length", gate.getInputs().length);
        o.put("x", gate.getShape().getLayoutX());
        o.put("y", gate.getShape().getLayoutY());
        gateArray.add(o);
    }
    obj.put("gates", gateArray );



    // RECUPERER LES FILS
    JSONArray filsArray = new JSONArray();
    Set<Fils> files = circuit.getFils() ;
    for ( Fils file : files ) {
        // on cree l objet et apres on l ajoute dans le tableau des fils
        JSONObject o = new JSONObject() ;
        if ( file.getGate()!= null ) {
            o.put("gate" , file.getGate() ) ;
        }else{
            o.put("gate" , "Unknown" ) ;
        }
        if ( file.getCircle1Coord()!= null ) {
            o.put("centre_cercle_1 " , file.getCircle1Coord() ) ;
        }else 
        {
            o.put("circle1_1X", 0 ) ;
            o.put("circle1_1Y", 0 ) ;
        }
        if ( file.getCircle1Coord()!= null ) {
            o.put("centre_cercle_2 " , file.getCircle2Coord() ) ;
        }else 
        {
            o.put("circle1_2X", 0 ) ;
            o.put("circle1_2Y", 0 ) ;
        }
        if ( file.getParent() != null ) {
            o.put("Parent " , file.getParent() ) ;
        }
        // on verifie si les lignes pour les remplir
        if ( file.getL1() != null ) {
            o.put ("Line_1X_start " , file.getL1().getStartX()) ;
            o.put ("Line_1X_end " , file.getL1().getEndX()) ;
            o.put ("Line_1Ystart " , file.getL1().getStartY()) ;
            o.put ("Line_1Y_end " , file.getL1().getEndY()) ;
        }
        if ( file.getL2() != null ) {
            o.put ("Line_2X_start " , file.getL2().getStartX()) ;
            o.put ("Line_2X_end " , file.getL2().getEndX()) ;
            o.put ("Line_2Y_start " , file.getL2().getStartY()) ;
            o.put ("Line_2Y_end " , file.getL2().getEndY()) ;
        }
        o.put("outPut " , file.getOutput() ) ;
        o.put("isFilsRelatedToSomething", file.getIsFilsRelatedToSomething());
        filsArray.add(o) ;
    }
    obj.put("fils", filsArray );


    // recuperer toutes les variables : 
        JSONArray VarArray = new JSONArray();
    Set<Gate> variable = circuit.getVar(); 
    for (Gate var : variable ) { 
        JSONObject o = new JSONObject() ;
        o.put("name", var.getName());
        if ( var.getInputs() != null ) {
            o.put("inputs_length", var.getInputs().length);
        }
        o.put("x", var.getShape().getLayoutX());
        o.put("y", var.getShape().getLayoutY());
        o.put("value", var.getText() );
        VarArray.add(o);
    }
    obj.put("Variable", VarArray );


    return obj.toJSONString();
}

//charger le circuit 
public void loadCircuit ( String filename ) throws ParseException {
    try {
        JSONParser parser = new JSONParser() ;
        Reader reader = new FileReader(filename) ;
        Object jsonObj = parser.parse (reader) ;
        JSONObject jsonObject = ( JSONObject) jsonObj ;
        JSONArray gates = ( JSONArray) jsonObject.get("gates") ;

        for ( Object gate : gates ) {
            JSONObject jsObj = (JSONObject) gate ;
            System.out.println(jsObj.toJSONString());
        }
        reader.close();
        } 
        catch ( IOException e ) {
        System.out.println("Erreur lors du chargement de fichier :" + e.getMessage() ) ;
        }
}

}