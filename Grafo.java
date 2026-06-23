package grafos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Biblioteca generica de grafos por LISTA DE ADJACENCIAS.
 *
 * Configuravel na instanciacao (exigencia dos slides; o codigo original do professor
 * fixava essas escolhas): direcionado/nao-direcionado e ponderado/nao-ponderado.
 *
 * Metodos minimos exigidos no enunciado: adicionar vertice, adicionar aresta,
 * caminhamento em largura (BFS). Algoritmo do problema escolhido (caminho minimo): Dijkstra.
 */
public class Grafo<T> {

    private final List<Vertice<T>> vertices;
    private final boolean direcionado;
    private final boolean ponderado;

    public Grafo(boolean direcionado, boolean ponderado) {
        this.vertices = new ArrayList<>();
        this.direcionado = direcionado;
        this.ponderado = ponderado;
    }

    // ---------------------------------------------------------------- Construcao

    public Vertice<T> adicionarVertice(T valor) {
        Vertice<T> existente = obterVertice(valor);
        if (existente != null) {
            return existente; // nao duplica
        }
        Vertice<T> novo = new Vertice<>(valor);
        this.vertices.add(novo);
        return novo;
    }

    public void adicionarAresta(T origem, T destino, float peso) {
        float p = this.ponderado ? peso : 1f; // nao-ponderado => peso unitario
        Vertice<T> vOrigem = obterOuCriar(origem);
        Vertice<T> vDestino = obterOuCriar(destino);
        vOrigem.adicionarDestino(new Aresta<>(vDestino, p));
        if (!this.direcionado) {
            vDestino.adicionarDestino(new Aresta<>(vOrigem, p)); // simetria automatica
        }
    }

    public void removerAresta(T origem, T destino) {
        Vertice<T> vOrigem = obterVertice(origem);
        Vertice<T> vDestino = obterVertice(destino);
        if (vOrigem == null || vDestino == null) {
            return;
        }
        vOrigem.removerDestino(vDestino);
        if (!this.direcionado) {
            vDestino.removerDestino(vOrigem);
        }
    }

    public void removerVertice(T valor) {
        Vertice<T> alvo = obterVertice(valor);
        if (alvo == null) {
            return;
        }
        for (Vertice<T> v : this.vertices) {
            v.removerDestino(alvo); // remove arestas que chegam ao alvo
        }
        this.vertices.remove(alvo);
    }

    // --------------------------------------------------------- Consultas auxiliares

    private Vertice<T> obterVertice(T valor) {
        for (Vertice<T> v : this.vertices) {
            if (v.getValor().equals(valor)) {
                return v;
            }
        }
        return null;
    }

    private Vertice<T> obterOuCriar(T valor) {
        Vertice<T> v = obterVertice(valor);
        return (v != null) ? v : adicionarVertice(valor);
    }

    // ------------------------------------------------- Caminhamento em largura (BFS)

    /**
     * Imprime os vertices em ordem de largura. Reinicia a partir de vertices ainda
     * nao marcados, de modo a cobrir tambem grafos desconexos.
     */
    public void buscaEmLargura() {
        if (this.vertices.isEmpty()) {
            return;
        }
        Set<Vertice<T>> marcados = new HashSet<>();
        Queue<Vertice<T>> fila = new LinkedList<>();

        for (Vertice<T> raiz : this.vertices) {
            if (marcados.contains(raiz)) {
                continue;
            }
            fila.add(raiz);
            marcados.add(raiz);
            while (!fila.isEmpty()) {
                Vertice<T> atual = fila.poll();
                System.out.println(atual.getValor());
                for (Aresta<T> a : atual.getDestinos()) {
                    Vertice<T> prox = a.getDestino();
                    if (!marcados.contains(prox)) {
                        marcados.add(prox);
                        fila.add(prox);
                    }
                }
            }
        }
    }

    // ------------------------------------------------------- Caminho minimo (Dijkstra)

    /**
     * Caminho minimo de origem a destino assumindo pesos NAO-negativos (tempos).
     * Retorna null se algum extremo nao existir ou se o destino for inalcancavel.
     */
    public Caminho<T> dijkstra(T origem, T destino) {
        Vertice<T> vOrigem = obterVertice(origem);
        Vertice<T> vDestino = obterVertice(destino);
        if (vOrigem == null || vDestino == null) {
            return null;
        }

        Map<Vertice<T>, Float> dist = new HashMap<>();
        Map<Vertice<T>, Vertice<T>> anterior = new HashMap<>();
        for (Vertice<T> v : this.vertices) {
            dist.put(v, Float.POSITIVE_INFINITY);
        }
        dist.put(vOrigem, 0f);

        PriorityQueue<Vertice<T>> fila = new PriorityQueue<>(Comparator.comparingDouble(dist::get));
        fila.add(vOrigem);

        while (!fila.isEmpty()) {
            Vertice<T> u = fila.poll();
            if (u.equals(vDestino)) {
                break;
            }
            for (Aresta<T> a : u.getDestinos()) {
                Vertice<T> v = a.getDestino();
                float alternativo = dist.get(u) + a.getPeso();
                if (alternativo < dist.get(v)) {
                    dist.put(v, alternativo);
                    anterior.put(v, u);
                    fila.remove(v); // reordena a prioridade do vizinho
                    fila.add(v);
                }
            }
        }

        if (dist.get(vDestino) == Float.POSITIVE_INFINITY) {
            return null; // inalcancavel
        }

        LinkedList<T> rota = new LinkedList<>();
        for (Vertice<T> v = vDestino; v != null; v = anterior.get(v)) {
            rota.addFirst(v.getValor());
        }
        return new Caminho<>(rota, dist.get(vDestino));
    }
}
