package Circuit;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set ;
import org.json.simple.JSONArray ;
import org.json.simple.parser.JSONParser ;
import org.json.simple.parser.ParseException ;

import Gates.Variable;
import javafx.geometry.Point2D;

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
        System.out.println ( "Erreur  pendant l'enregistrement " + e.getMessage() ) ;
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
            o.put("inputs_length", gate.getInputs().length);
            o.put("x", gate.getShape().getLayoutX());
            o.put("y", gate.getShape().getLayoutY());
            gateArray.add(o);
        }
        obj.put("gates", gateArray );



        // RECUPERER LES FILS
        JSONArray filsArray = new JSONArray();
        List<Fils> filesList = circuit.getFils() ;

        for (int i = 0; i < filesList.size(); i++) {
            // on cree l objet et apres on l ajoute dans le tableau des fils
            Fils currentFil = filesList.get(i);
            JSONObject o = new JSONObject() ;
            o.put("id", i);
        
            o.put("circle1_X", currentFil.getCircle1Coord().getX()) ;
            o.put("circle1_Y", currentFil.getCircle1Coord().getY() ) ;

            o.put("circle2_X", currentFil.getCircle2Coord().getX()) ;
            o.put("circle2_Y", currentFil.getCircle2Coord().getY() ) ;

            if ( currentFil.getParent() != null ) {
                o.put("Parent" , filesList.indexOf(currentFil.getParent()));
            }
            else{
                o.put("Parent", -1);
            }
            // on verifie si les lignes pour les remplir
            if ( currentFil.getL1() != null ) {
                o.put ("Line1_X_start" , currentFil.getL1().getStartX()) ;
                o.put ("Line1_X_end" , currentFil.getL1().getEndX()) ;
                o.put ("Line1_Y_start" , currentFil.getL1().getStartY()) ;
                o.put ("Line1_Y_end" , currentFil.getL1().getEndY()) ;
            }
            if ( currentFil.getL2() != null ) {
                o.put ("Line2_X_start" , currentFil.getL2().getStartX()) ;
                o.put ("Line2_X_end" , currentFil.getL2().getEndX()) ;
                o.put ("Line2_Y_start" , currentFil.getL2().getStartY()) ;
                o.put ("Line2_Y_end" , currentFil.getL2().getEndY()) ;
            }
            o.put("isFilsRelatedToSomething", currentFil.getIsFilsRelatedToSomething());
            JSONArray connected = new JSONArray();

            for(Fils fil : currentFil.getConnected()){
                int index = filesList.indexOf(fil);
                if(index < i){
                    connected.add(index);
                }
            }
            o.put("connected", connected);

            filsArray.add(o);
        }
        obj.put("fils", filsArray );


        // recuperer toutes les variables : 
        JSONArray VarArray = new JSONArray();
        Set<Gate> variable = circuit.getVar(); 
        for (Gate var : variable ) { 
            JSONObject o = new JSONObject() ;
            o.put("x", var.getShape().getLayoutX());
            o.put("y", var.getShape().getLayoutY());
            o.put("value", var.getOutputValue().toString() );
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

            circuit.clearAll();

            loadFils(jsonObj);
            loadGates(jsonObj);
            loadVariable(jsonObj);
        
            reader.close();
        } 
        catch ( IOException e ) {
            System.out.println("Erreur lors du chargement de fichier :" + e.getMessage() ) ;
        }
    }

    private void loadFils(Object jsonObj){
        JSONObject jsonObject = ( JSONObject) jsonObj ;
        JSONArray fils = ( JSONArray) jsonObject.get("fils") ;
        List<Fils> filsArray = new ArrayList<Fils>();

        for ( Object fil : fils ) {
            JSONObject jsObj = (JSONObject) fil;

            double circle1_x = (double) jsObj.get("circle1_X");
            double circle1_y = (double) jsObj.get("circle1_Y");

            Fils newFil = new Fils(circle1_x, circle1_y, circuit, null, null, (boolean) jsObj.get("isFilsRelatedToSomething"));
            
            double circle2_x = (double) jsObj.get("circle2_X");
            double circle2_y = (double) jsObj.get("circle2_Y");

            Point2D startL1 = new Point2D((double) jsObj.get("Line1_X_start"), (double) jsObj.get("Line1_Y_start"));
            Point2D endL1 = new Point2D((double) jsObj.get("Line1_X_end"), (double) jsObj.get("Line1_Y_end"));

            Point2D startL2 = new Point2D((double) jsObj.get("Line2_X_start"), (double) jsObj.get("Line2_Y_start"));
            Point2D endL2 = new Point2D((double) jsObj.get("Line2_X_end"), (double) jsObj.get("Line2_Y_end"));
            
            newFil.setCircle1Coord(circle1_x, circle1_y);
            newFil.setCircle2Coord(circle2_x, circle2_y);
            newFil.setL1Coord(startL1, endL1);
            newFil.setL2Coord(startL2, endL2);
            newFil.setItemsToTransparent();

            filsArray.add(newFil);
        }

        for( Object fil : fils ){
            JSONObject jsObj = (JSONObject) fil;
            int id = ((Number) jsObj.get("id")).intValue();
            int parentIndex = ((Number) jsObj.get("Parent")).intValue();
            Fils currentFil = filsArray.get(id);
            if(parentIndex >= 0){
                currentFil.setParent(filsArray.get(parentIndex));
            }

            JSONArray connected = (JSONArray) jsObj.get("connected");
            for(Object i : connected){
                int j = ((Number) i).intValue();
                currentFil.addToConnected(filsArray.get(j));
            }
        }
    }

    private void loadGates(Object jsonObj){
        JSONObject jsonObject = ( JSONObject) jsonObj ;
        JSONArray gates = ( JSONArray) jsonObject.get("gates") ;

        for ( Object gate : gates ) {
            JSONObject jsObj = (JSONObject) gate;

            double x = (double) jsObj.get("x");
            double y = (double) jsObj.get("y");

            String name = (String) jsObj.get("name");

            int inputsLength = ((Number) jsObj.get("inputs_length")).intValue();

            circuit.createGate(name, inputsLength, x, y);
        }
    }

    private void loadVariable(Object jsonObj){
        JSONObject jsonObject = ( JSONObject) jsonObj ;
        JSONArray variables = ( JSONArray) jsonObject.get("Variable") ;

        for ( Object varia : variables ) {
            JSONObject jsObj = (JSONObject) varia;

            double x = (double) jsObj.get("x");
            double y = (double) jsObj.get("y");

            QuadBool value = QuadBool.stringToQuadBool((String)jsObj.get("value"));

            Gate var = new Variable(value, circuit, circuit.getPane(), x, y);
            circuit.addVariable(var);
        }
    }


}