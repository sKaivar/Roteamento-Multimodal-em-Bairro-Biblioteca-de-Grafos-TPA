package grafos;

/**
 * Aresta dirigida e ponderada. Na lista de adjacencia guarda-se apenas o destino.
 * Para grafos nao-direcionados, a classe Grafo insere a aresta simetrica automaticamente.
 * Para grafos nao-ponderados, o peso assume 1.
 */
public class Aresta<T> {
    private final Vertice<T> destino;
    private final float peso;

    public Aresta(Vertice<T> destino, float peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public Vertice<T> getDestino() {
        return destino;
    }

    public float getPeso() {
        return peso;
    }
}
