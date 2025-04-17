package Circuit;

public class NouveauComposant {

    private String nom ;
    private String image ;
    private int nbEntree ;
    private int nbSortie ;
    private boolean [][] tableVerite ;

    public NouveauComposant ( String n , String img , int nbE , int nbS , boolean[][] tv ) {
        this.nom = n ;
        this.image=img;
        this.nbEntree=nbE;
        this.nbSortie=nbS;
        this.tableVerite=tv;
    }

    public String getNameComp () {
        return this.nom ;
    }

    public String getImgComp () {
        return this.image ;
    }

    public int getNbENtComp () {
        return this.nbEntree ;
    }

    public int getNbSortComp () {
        return this.nbSortie;
    }


    public boolean [][] getTableVerite () {
        return tableVerite ;
    }



}
