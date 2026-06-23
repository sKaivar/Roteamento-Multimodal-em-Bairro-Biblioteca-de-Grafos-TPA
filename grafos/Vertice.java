package grafos;

import java.util.ArrayList;
import java.util.List;

/**
 * Vertice generico: armazena um objeto de tipo T e a sua lista de adjacencia
 * (arestas das quais e' origem -- lista "forward", conforme slides do Prof. Victorio).
 */
public class Vertice<T> {
    private final T valor;
    private final List<Aresta<T>> destinos;

    public Vertice(T valor) {
        this.valor = valor;
        this.destinos = new ArrayList<>();
    }

    public T getValor() {
        return valor;
    }

    public List<Aresta<T>> getDestinos() {
        return destinos;
    }

    void adicionarDestino(Aresta<T> aresta) {
        this.destinos.add(aresta);
    }

    void removerDestino(Vertice<T> destino) {
        this.destinos.removeIf(a -> a.getDestino().equals(destino));
    }

    @Override
    public String toString() {
        return String.valueOf(valor);
    }
}
