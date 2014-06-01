package model.structures;

import java.util.List;
import java.util.LinkedList;

/* Classe che modella i vertici usati per costruire i grafi */
public class Vertex {

    /* Campo contenente un oggetto associato al vertice, se presente (dottore
     oppure data) */
    private Object oggettoAssociato;
    /* Lista dei vertici adiacenti a questo nel grafo */
    private List<Vertex> edges;
    /* Posizione del vertice nella matrice delle capacita */
    private int identificatore;
    /* Campi colore e predecessore usato nella visita del grafo */
    private Colours colore;
    private Vertex predecessore;

    /* Tipo enumerativo per la modellazione dei colori */
    public enum Colours {
        GREEN, YELLOW, RED
    };

    /* Costruttori */

    public Vertex(Object oggettoAssociato, int identificatore) {
        this.identificatore = identificatore;
        this.oggettoAssociato = oggettoAssociato;
        this.edges = new LinkedList<Vertex>();
        this.colore = Colours.RED;
        this.predecessore = null;
    }

    public Vertex(int identificatore) {
        this(null, identificatore);
    }

    /* Metodi getter e setter utilizzati */

    public Object getOggettoAssociato() {
        return oggettoAssociato;
    }

    public int getIdentificatore() {
        return identificatore;
    }

    public List<Vertex> getVerticiAdiacenti() {
        return edges;
    }

    public Colours getColore() {
        return colore;
    }

    public void setColore(Colours colore) {
        this.colore = colore;
    }

    public Vertex getPredecessore() {
        return predecessore;
    }

    public void setPredecessore(Vertex p) {
        predecessore = p;
    }

    /* Aggiungi un arco uscente verso il vertice v */
    public void aggiungiArco(Vertex v) {
        edges.add(v);
    }

    /* Rimuovi un arco uscente verso il vertice v */
    public boolean rimuoviArco(Vertex v) {
        return edges.remove(v);
    }

}
