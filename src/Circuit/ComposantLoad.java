package Circuit ;

import java.io.File ;
import java.io.FileReader ;
import org.json.simple.JSONObject ;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser ;

public class ComposantLoad {

    public static NouveauComposant chargerComp ( String d ) {
        // on cherche si dans notre dossier on a des nouveaux composants a récupérer , et on recupere leurs valeurs dans une liste
        File fichier = new File( d ) ;

        if ( fichier.exists() && fichier.isFile() ) {
            
                try ( FileReader reader = new FileReader(fichier )){
                    JSONObject o = (JSONObject) new JSONParser().parse(reader);
                    String nom = (String) o.get("nom") ;
                    String image = (String) o.get("image") ;
                    int nbEntree = ( (Long)o.get("nbEntree")).intValue() ;
                    int nbSortie = ( (Long)o.get("nbSortie")).intValue() ;
                    JSONArray table = ( JSONArray ) o.get("tableVerite") ;
                    boolean[][] tableVerite = new boolean[table.size()][] ;

                    // recuperer les valeures de la table de verité 
                    for (int i =0 ; i<table.size() ; i++ ) {
                        JSONArray l = (JSONArray) table.get(i) ;
                        boolean[] lb = new boolean[l.size()] ;
                        for ( int j=0 ; j<l.size() ; j++ ) {
                            lb[j] = (boolean) l.get(j) ;
                        }
                        tableVerite[i] = lb ;

                    }

                     return  new NouveauComposant(nom,image,nbEntree,nbSortie,tableVerite) ;
               
                    } catch (Exception e){
                    System.err.println ( " Erreur lors de lecture de fichier Json de composant inserer "+ fichier.getName() ) ;
                    e.printStackTrace() ;
                    return null ;
                }
            
        } 
        else {
            System.err.println (" Il n'existe pas un fichier pour le telecharger " ) ; 
            return null ;
        }
        

    }


}


