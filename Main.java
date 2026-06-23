import grafos.Caminho;
import grafos.Grafo;

/**
 * Demonstracao PROVISORIA da biblioteca -- recorte ficticio de Jardim Camburi.
 * NAO e' o aplicativo final (Etapa 3): serve apenas para validar BFS e Dijkstra.
 */
public class Main {
    public static void main(String[] args) {
        // Grafo pedestre: direcionado=false (ruas de mao dupla a pe), ponderado=true (minutos)
        Grafo<String> pedestre = new Grafo<>(false, true);

        // arestas ficticias: (origem, destino, tempo em minutos a pe)
        pedestre.adicionarAresta("Praca_Local", "Av_Dante_Michelini", 4f);
        pedestre.adicionarAresta("Av_Dante_Michelini", "Shopping", 6f);
        pedestre.adicionarAresta("Praca_Local", "Rua_Anselmo", 3f);
        pedestre.adicionarAresta("Rua_Anselmo", "Shopping", 9f);

        System.out.println("== BFS (cobre tambem grafos desconexos) ==");
        pedestre.buscaEmLargura();

        System.out.println();
        System.out.println("== Dijkstra: Praca_Local -> Shopping ==");
        Caminho<String> c = pedestre.dijkstra("Praca_Local", "Shopping");
        System.out.println(c != null ? c : "Sem rota.");
    }
}
