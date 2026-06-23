# Roteamento Multimodal em Bairro — Biblioteca de Grafos (TPA)

> **Estado: PROVISÓRIO / WIP.** A base algorítmica (biblioteca genérica + BFS + Dijkstra) já compila e roda; as decisões de modelagem do modo *a pé + ônibus* e a camada de aplicação (Etapa 3) ainda estão em aberto.

## 1. Problema

Dado um ponto de partida e um ponto de chegada **no mesmo bairro** (Jardim Camburi ou Bairro de Fátima, Vitória/Serra — ES), calcular a melhor rota entre eles para cada modalidade:

- **I.** A pé somente;
- **II.** A pé + de ônibus;
- **III.** De bicicleta somente (percorre qualquer rua que um pedestre percorre).

Abstração: a espera do ônibus é fixada em **8 minutos** (ignoram-se oscilações reais).

Saída esperada: quais modalidades atendem às restrições de tempo aplicadas, o tempo total de cada rota e os tempos individuais por meio de transporte.

### Complicadores (opcionais)
- Em trechos com **ciclovia**, o ciclista gasta **metade** do tempo esperado;
- Impressão do **guia da trajetória** (passo a passo).

## 2. Algoritmos

- **Caminhamento em largura (BFS)** — exigência mínima da biblioteca; cobre também grafos desconexos.
- **Dijkstra** — algoritmo do problema escolhido (caminho mínimo em grafo ponderado com pesos não-negativos, i.e. tempos).

## 3. Estratégia de representação — **lista de adjacências**

A malha viária de um bairro é um grafo **esparso**: cada cruzamento liga-se a poucos vizinhos. O Dijkstra itera sobre os vizinhos de cada vértice, operação que a lista de adjacências entrega em *O(grau)*. A matriz de adjacências desperdiçaria *O(V²)* de memória; a lista de arestas tornaria cada consulta de vizinhança *O(E)*. A matriz só compensaria em grafos densos, ou com muitas consultas pontuais de adjacência (*«i ~ j?»* em *O(1)*).

A biblioteca é **genérica** (`<T>`) e configurável na instanciação quanto a *direcionado/não-direcionado* e *ponderado/não-ponderado*.

## 4. Modelagem dos modais (em discussão)

Plano atual: **3 grafos distintos** — pedestre, ônibus, ciclista. Os modos **I** e **III** resolvem-se com um Dijkstra na respectiva camada.

O modo **II** (a pé + ônibus) não vive numa única camada. Duas vias em aberto:
- **(a) grafo de estados:** compor pedestre + ônibus, com arestas de transferência nos pontos de parada carregando os 8 min de espera — globalmente óptimo;
- **(b) costura de trechos:** Dijkstra por camada e junção posterior — mais simples, mas pode perder a optimalidade.

> **A confirmar com o professor:** a anotação fixa *«3 grafos distintos»*; é preciso definir se o modo II será um quarto grafo composto ou costura.

## 5. Estrutura do código

```
src/
├── grafos/
│   ├── Vertice.java   # valor genérico + lista de adjacência (forward)
│   ├── Aresta.java    # destino + peso
│   ├── Grafo.java     # adj. list; add/remove vértice e aresta; BFS; Dijkstra
│   └── Caminho.java   # resultado: rota + custo total
└── Main.java          # demonstração provisória (recorte fictício)
```

## 6. Como compilar e executar

```bash
javac -d out src/grafos/*.java src/Main.java
java -cp out Main
```

## 7. Pendências (TODO)

- [ ] Decidir modelagem do modo II (grafo de estados vs costura).
- [ ] Leitura dos dados por arquivo (CSV/JSON) — exigência da Etapa 3.
- [ ] Camada de aplicação em arquitetura em camadas.
- [ ] Tratamento da ciclovia (peso já descontado vs flag na aresta).
- [ ] Impressão do guia da trajetória.
- [ ] Verificar se os requisitos extras dos slides (detecção de ciclos; caminhamento a partir de vértices com grau de recepção zero) se aplicam a *este* trabalho ou eram requisitos gerais da biblioteca.

## 8. Etapa 4 (a preencher na entrega)

- Planilha de divisão por componente (biblioteca e aplicativo).
- URL do repositório no GitHub.
- Relato de uso de LLM (ferramenta, processo e resultados).

---

Ferramenta de apoio sugerida pelo professor: <https://graphonline.top/en/>
