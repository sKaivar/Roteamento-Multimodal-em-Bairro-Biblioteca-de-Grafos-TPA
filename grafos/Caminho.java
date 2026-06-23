package grafos;

import java.util.List;
import java.util.stream.Collectors;

/** Resultado de uma busca de caminho minimo: a rota (sequencia de valores) e o custo total. */
public class Caminho<T> {
    private final List<T> rota;
    private final float custoTotal;

    public Caminho(List<T> rota, float custoTotal) {
        this.rota = rota;
        this.custoTotal = custoTotal;
    }

    public List<T> getRota() {
        return rota;
    }

    public float getCustoTotal() {
        return custoTotal;
    }

    @Override
    public String toString() {
        String trajeto = rota.stream().map(String::valueOf).collect(Collectors.joining(" -> "));
        return trajeto + "  (custo total: " + custoTotal + ")";
    }
}
