package model.structures;

import model.bo.Shift;
import model.bo.UserSupportEng;

import java.util.LinkedList;
import java.util.List;

public class FordFulkerson {

    /* Matrice del flusso */
    private int[][] flusso;
    /* Graph residuo, che sara' modificato dall'algoritmo */
    private Graph graphResiduo;
    /* Numero di giorni dati in input */
    private int numGiorni;

    public FordFulkerson(List<UserSupportEng> tecnici, List<String> lingue, List<Shift> turni) {
        graphResiduo = new Graph(tecnici, lingue, turni);
        int numeroNodi = graphResiduo.getNumVertici();
        flusso = new int[numeroNodi][numeroNodi];
        numGiorni = turni.size();
    }

    private void stampaCammino(List<Vertex> cammino) {
        for(Vertex v : cammino)
            System.out.print(v.getIdentificatore() + " ");
        System.out.println();
    }

    /* Calcola il flusso massimo (oss: tutti i cammini aumentanti hanno
     * capacita' 1, dunque non serve calcolarla) */
    private void calcolaFlussoMassimo() {
        List<Vertex> cammino;
        /* Finche' esiste un cammino aumentante nella rete residua... */
        while((cammino = graphResiduo.ricercaCamminoAumentante()) != null) {
            Vertex arrivo = cammino.remove(0);
            /* Fino a che non ho attraversato tutti gli archi del cammino... */
            while(!cammino.isEmpty()) {
                /* Aggiorna i vertici di partenza e arrivo */
                Vertex partenza = arrivo;
                arrivo = cammino.remove(0);
                /* Ricavane l'indice nella matrice del flusso */
                int indicePartenza = partenza.getIdentificatore();
                int indiceArrivo = arrivo.getIdentificatore();
                /* Incrementa di un'unita' il flusso nell'arco presente nel
                 * cammino e decrementa di un'unita' nel verso opposto */
                flusso[indicePartenza][indiceArrivo]++;
                flusso[indiceArrivo][indicePartenza]--;
                /* Diminuisci la capacita' residua dell'arco presente nel
                 * cammino di un'unita' e incrementala di un'unita' nel verso
                 * opposto */
                graphResiduo.variaCapacita(partenza, arrivo, -1);
                graphResiduo.variaCapacita(arrivo, partenza, 1);
                /* Se la capacita' residua dell'arco si e' azzerata, rimuovi
                 * l'arco dalla rete residua */
                if(graphResiduo.getCapacita(partenza, arrivo) == 0)
                    graphResiduo.rimuoviArco(partenza, arrivo);
                /* Se la capacita' residua del cammino nel verso opposto e'
                 * 1, significa che e' appena stata incrementata, dunque
                 * aggiungi l'arco corrispondente */
                if(graphResiduo.getCapacita(arrivo, partenza) == 1)
                    graphResiduo.aggiungiArco(arrivo, partenza);
            }
        }
    }

    /* Crea gli abbinamenti richiesti */
    public Pair<Boolean, List<Pair<UserSupportEng, Shift>>> getAbbinamenti() {
        List<Pair<UserSupportEng, Shift>> resultPairList = new LinkedList<Pair<UserSupportEng, Shift>>();

        // Calcola il flusso massimo, usando il grafo "residuo"
        calcolaFlussoMassimo();

        // conteggoi abbinamenti per sapere se otteniamo un risultato parziale e completo
        int countAbbinamenti = 0;

        // Attraversa il grafo e crea gli abbinamenti
        Vertex t = graphResiduo.getPozzo();
        for(Vertex g : t.getVerticiAdiacenti())
            for(Vertex p : g.getVerticiAdiacenti())
                for(Vertex d : p.getVerticiAdiacenti())
                    if(flusso[p.getIdentificatore()][d.getIdentificatore()] < 0) {
                        resultPairList.add(new Pair<UserSupportEng, Shift>((UserSupportEng) g.getOggettoAssociato(), (Shift) d.getOggettoAssociato()));
                        countAbbinamenti++;
                    }

        // Se il numero di abbinamenti non e' uguale al numero di giorni, poni
        // a false il primo campo della coppia di output, altrimenti ponilo a true */
        boolean isComplete = (countAbbinamenti == numGiorni);
        // il risultato dell'algoritmo sarà la coppia (boolean, lista_risultati)
        Pair<Boolean, List<Pair<UserSupportEng, Shift>>> result;
        result = new Pair<Boolean, List<Pair<UserSupportEng, Shift>>>(isComplete, resultPairList);
        return result;
    }

}
