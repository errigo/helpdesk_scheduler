package model.structures;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import model.bo.Shift;
import model.bo.UserSupportEng;
import model.structures.Vertex.Colours;

/** Graph realizzato con liste di adiacenza e matrice per le capacità; siano:
 *  n: numero di dottori;
 *  k: numero di periodi;
 *  z: numero di giorni festivi totali.
 * Nell'array dei vertici (e anche nella matrice delle capacità), si avrà:
 *  0 -> sorgente
 *  1..n -> dottori
 *  n+1..n+nk -> periodi
 *  n+nk+1..n+nk+z -> giorni
 *  n+nk+z+1 -> pozzo
 */
public class Graph {

    /* Array di vertici che costituiscono il grafo */
    private Vertex[] vertici;
    /* Matrice delle capacita' (per ottenere informazioni in tempo O(1) */
    private int[][] capacita;
    /* Numero di vertici del grafo */
    private int numVertici;


    public Graph(List<UserSupportEng> tecnici, List<String> lingue, List<Shift> turni) {
        /* Calcola il numero di vertici che dovrà contenere il grafo */
        final int NUM_TECNICI       = tecnici.size();
        final int NUM_TURNI         = turni.size();
        final int NUM_LINGUE        = lingue.size();
        final int IND_PRIMA_LINGUA  = 1 + NUM_TECNICI;
        final int IND_ULTIMA_LINGUA = IND_PRIMA_LINGUA + NUM_LINGUE;
        final int IND_PRIMO_TURNO   = IND_ULTIMA_LINGUA + 1;
        final int IND_ULTIMO_TURNO  = IND_PRIMO_TURNO + NUM_TURNI;
        numVertici = NUM_TECNICI + NUM_LINGUE + NUM_TURNI + 2;
        final int POZZO = numVertici - 1;
        final int SORGENTE = 0;

        /* Crea l'array di vertici e la matrice delle capacità */
        vertici = new Vertex[numVertici];
        capacita = new int[numVertici][numVertici]; // inizializzata di default a 0
        /* Crea la sorgente e il pozzo */
        vertici[SORGENTE] = new Vertex(SORGENTE);
        vertici[POZZO] = new Vertex(POZZO);

        // Aggiungo i turni al grafo e li collego al pozzo
        // Mi posiziono sul primo indice dei turni
        // NB: pozzo + #tecnici + #lingue
        int indice = 1 + NUM_TECNICI + NUM_LINGUE;
        for(Shift t: turni) {
            // creo il vertice associato al tunro
            Vertex turno = new Vertex(t, indice);
            // memorizzo nell'array dei vertici
            vertici[indice] = turno;
            // aggiungo l'arco del turno al pozzo
            turno.aggiungiArco(vertici[POZZO]);
            // e ne imposto la capacità
            capacita[indice][POZZO] = Integer.MAX_VALUE;
            indice++;
        }

        // mi posiziono al primo posto libero per le lingue
        indice = IND_PRIMA_LINGUA;
        for(String l : lingue) {
            /* Crea il vertice associato al giorno */
            Vertex lingua = new Vertex(l, indice);
            /* Memorizzalo nell'array dei vertici */
            vertici[indice] = lingua;
            /* Aggiungi l'arco model.dal giorno al pozzo */
            lingua.aggiungiArco(vertici[POZZO]);
            /* E imposta la capacita' */
            capacita[indice][numVertici - 1] = 1;
            /* Aggiorna l'indice associato al giorno */
            indice++;
        }

        indice = 1;
        for(UserSupportEng t: tecnici) {
            // creo il vertice associato al tecnico
            Vertex tecnico = new Vertex(t, indice);
            // memorizzo nell'array dei vertici
            vertici[indice] = tecnico;
            // aggiungo un arco dalla sorgente al tecnico
            vertici[SORGENTE].aggiungiArco(tecnico);
            // e ne imposto la capacità
            capacita[SORGENTE][indice] = Integer.MAX_VALUE;
            // associa i tecnici alle lingue
            for(int i = 0; i < NUM_LINGUE; i++) {
                vertici[IND_PRIMA_LINGUA + i] = new Vertex(null, IND_PRIMA_LINGUA + i);
                capacita[indice][IND_PRIMA_LINGUA + i] = 1;
                tecnico.aggiungiArco(vertici[IND_PRIMA_LINGUA + i]);
            }

            // mi ripasso tutti i vertici di lingue creati per vedere quali corrispondono con quelle parlate model.dal tecnico
            // Se trovo la lingua uguale (--> il tecnico la parla) aggiungo l'arco a quel vertice
            for(String l: t.getLanguages())
                for(int i = IND_PRIMA_LINGUA; i < IND_ULTIMA_LINGUA; i++) {
                    String lingua = (String) vertici[i].getOggettoAssociato();
                    if(l.equals(lingua)) {
                        tecnico.aggiungiArco(vertici[i]);
                        capacita[indice][i] = t.getHoursPerDay();
                    }
                }
            indice++;
        }

        // da lingue ai turni (creati a ritroso)
        for(int ind_turni = IND_PRIMO_TURNO; ind_turni < IND_ULTIMO_TURNO; ind_turni++) {
            Shift t = (Shift) vertici[ind_turni].getOggettoAssociato();
            List<String> t_lingue = t.getRequiredLanguages();
            for(String l: t_lingue) {
                for(int ind_lingue = IND_PRIMA_LINGUA; ind_lingue < IND_ULTIMA_LINGUA; ind_lingue++) {
                    String verticeLingua = (String) vertici[ind_lingue].getOggettoAssociato();
                    if(l.equals(verticeLingua))
                        vertici[ind_lingue].aggiungiArco(vertici[ind_turni]);
                        capacita[ind_lingue][ind_turni] = t.getDuration();
                }
            }
        }
    }

    /* Ritorna il numero di vertici del grafo */
    public int getNumVertici() {
        return vertici.length;
    }

    /* Aggiunge un arco al grafo, dati due vertici */
    public void aggiungiArco(Vertex partenza, Vertex arrivo) {
        if(partenza != null && arrivo != null)
            partenza.aggiungiArco(arrivo);
    }

    /* Rimuovi l'arco tra i due vertici dati, se esistono */
    public void rimuoviArco(Vertex partenza, Vertex arrivo) {
        if(partenza != null && arrivo != null)
            partenza.rimuoviArco(arrivo);
    }

    /* Varia la capacita tra il vertice partenza e il vertice arrivo della quantita
     * specificata (che puo' essere anche negativa */
    public void variaCapacita(Vertex partenza, Vertex arrivo, int quantita) {
        if(partenza != null && arrivo != null)
            capacita[partenza.getIdentificatore()][arrivo.getIdentificatore()] += quantita;
    }

    /* Ottieni la capacita' dell'arco tra il vertice partenza e il vertice arrivo */
    public int getCapacita(Vertex partenza, Vertex arrivo) {
        return capacita[partenza.getIdentificatore()][arrivo.getIdentificatore()];
    }

    /* Ricerca un cammino aumentante nel grafo, se presente. Colours:
     * GREEN -> Nodo non raggiunto
     * YELLOW -> Nodo raggiunto, ma non ancora visitato
     * RED -> Nodo visitato */
    public List<Vertex> ricercaCamminoAumentante() {
        /* Imposta il colore di tutti i vertici a verde */
        for(Vertex v : vertici) {
            v.setColore(Colours.GREEN);
            v.setPredecessore(null);
        }
        /* Crea la coda da usare per la visita in ampiezza e inserisci il nodo
         * sorgente */
        Queue<Vertex> coda = new LinkedList<Vertex>();
        coda.offer(vertici[0]);
        /* Flag per determinare se il pozzo e' stato raggiunto o meno */
        boolean raggiuntoPozzo = false;
        Vertex v = null;
        /* Fino a che la coda non e' vuota (e non ho raggiunto il pozzo, prosegui
         * con la visita */
        while(!coda.isEmpty() && !raggiuntoPozzo) {
            /* Estrai un elemento dalla coda */
            v = coda.poll();
            /* Se e' il pozzo, esci model.dal ciclo */
            if(v == vertici[numVertici - 1]) {
                raggiuntoPozzo = true;
                break;
            }
            /* Altrimenti marca il nodo come visitato */
            v.setColore(Colours.RED);
            /* Analizza i vertici adiacenti a quello appena visitato */
            for(Vertex adiacente : v.getVerticiAdiacenti())
                /* Se il vertice adiacente non e' ancora stato raggiunto,
                 * aggiungilo in coda */
                if(adiacente.getColore() == Colours.GREEN) {
                    /* Marcalo come visitato */
                    adiacente.setColore(Vertex.Colours.YELLOW);
                    /* Imposta il predecessore nel vertice adiacente */
                    adiacente.setPredecessore(v);
                    /* Aggiungi il figlio nella coda */
                    coda.offer(adiacente);
                }
        }
        /* Se il pozzo non e' stato raggiunto, restituisci il cammino nullo */
        if(!raggiuntoPozzo || v == null)
            return null;
        /* Altrimenti t contiene il riferimento al pozzo nell'albero, a partire
         * model.dal quale si puo' ricostruire il cammino */
        List<Vertex> cammino = new LinkedList<Vertex>();
        /* Aggiungi il pozzo al cammino */
        cammino.add(0, v);
        /* E ricostruisci il cammino in ordine (da sorgente a pozzo), inserendo
         * continuamente in testa */
        while(v.getPredecessore() != null) {
            v = v.getPredecessore();
            cammino.add(0, v);
        }
        /* Ritorna il cammino cosi' ottenuto */
        return cammino;
    }

    /* Restituisce il pozzo nella rete */
    public Vertex getPozzo() {
        return vertici[numVertici - 1];
    }

    /* Metodo toString: stampa le liste di adiacenza e la matrice di capacita' */
    @Override
    public String toString() {
        String out = "Liste di adiacenza\n";
        for(int i = 0; i < vertici.length; i++) {
            out += "Vertex " + i + ": ";
            for(Vertex v : vertici[i].getVerticiAdiacenti())
                out += v.getIdentificatore() + " ";
            out += "\n";
        }
        out += "Capacita\n";
        for(int i = 0; i < capacita.length; i++) {
            for(int j = 0; j < capacita.length; j++)
                out += capacita[i][j] + " ";
            out += "\n";
        }
        return out;
    }

}
