package Circuit;


import java.io.*;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CircuitSaver {
    
    private Circuit circuit;

    public CircuitSaver(Circuit circuit) {
        this.circuit = circuit;
    }

   // creer un fichier avec le text
    public void saveCircuit(String fileName){
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(exportCircuit().toCharArray());
            System.out.println("Circuit enregistré avec succès dans " + fileName);
            writer.close();
        } catch (IOException e) {
            System.out.println("Erreur pendant l enregistrement du circuit : " + e.getMessage());
        }
    }

  // transformer le circuit en texte 
    @SuppressWarnings("unchecked")
    private String exportCircuit() {
        JSONObject obj = new JSONObject();
        JSONArray array = new JSONArray();
        Set<Gate> gates = circuit.getGates();  // je dois faire une fonction qui recupere toute les porte §§§ 
        for (Gate gate : gates) { // je sais pas est ce que je dois recuperer dabord les portes ou le fils aucune idéé
            // la je dois parcourir la liste des portes avec les valeures et les fils mais jai pas reuissi a le faire 
            JSONObject o = new JSONObject();
            o.put("name", gate.getName());
            o.put("inputs_length", gate.getInputs().length);
            o.put("x", gate.getShape().getLayoutX());
            o.put("y", gate.getShape().getLayoutY());
            array.add(o);
        }
        obj.put("gates", array);
        return obj.toJSONString();
    }

    //charger le circuit 
    
    public void loadCircuit(String fileName) throws ParseException{
        try {

            JSONParser parser = new JSONParser();
            Reader reader = new FileReader(fileName);

            Object jsonObj = parser.parse(reader);

            JSONObject jsonObject = (JSONObject) jsonObj;

            JSONArray gates = (JSONArray) jsonObject.get("gates");

            for(Object gate : gates){
                JSONObject jsObj = (JSONObject) gate;
                System.out.println(jsObj.toJSONString());
            }

            reader.close();
        }
        catch (IOException e) {
            System.out.println("Erreur lors du chargement du circuit : " + e.getMessage());
        }
    }
}

