package Objetos;

import java.io.Serializable;

public class ListaJogador implements Serializable{

    private NodeJogador inicio;
    private NodeJogador fim;
    private int qtd;

    public void ListaJogador() {
        this.qtd = 0;
    }

    private boolean isEmpty() {

        if (inicio == null) {

            return true;
        }
        return false;
    }

    private NodeJogador buscar(String nome) {

        NodeJogador aux;
        aux = inicio;
        while (aux != null) {

            if (aux.getData().getNome().equals(nome)) {
                return aux;
            }
            aux = aux.getNext();
        }
        return null;
    }

    public void adicionar(Jogador novo) { // COLOCAR VITORIAS DEPOIS PERCETUAL

        NodeJogador aux, aux2, aux3;
        NodeJogador jogador = new NodeJogador(novo);

        if (isEmpty() == true) {
            inicio = jogador;
            inicio.setPrev(null);
            fim = inicio;
            fim.setPrev(inicio);
            fim.setNext(null);
            qtd = 1;
        } else if (novo.getPontuacao() > inicio.getData().getPontuacao()) { // O JOGADOR TEM MAIS VITORIAS QUE O PRIMEIRO ELEMENTO

            jogador.setNext(inicio);
            jogador.setPrev(null);
            inicio.setPrev(jogador);
            inicio = jogador;
            qtd++;
            
        } else if (novo.getPontuacao() == inicio.getData().getPontuacao() && novo.getPartidaJogadas() <= inicio.getData().getPartidaJogadas()) {
            // O NOVO JOGADOR TEM A MESMA QUANTIDADE DE VIORIAS QUE E PRIMEIRO LUGAR
            jogador.setNext(inicio);                // E TEM MELHOR RENDIMENTO QUE O PRIMEIRO LUGAR
            jogador.setPrev(null);
            inicio.setPrev(jogador);
            inicio = jogador;
            qtd++;

        } else if (novo.getPontuacao() < fim.getData().getPontuacao()) { // O NOVO JOGADOR TEM MENOS PONTUAÇÃO QUE O ULTIMO JOGADOR
            jogador.setPrev(fim);
            fim.setNext(jogador);
            fim = jogador;
            qtd++;
                                                                
        } else if (novo.getPontuacao() == fim.getData().getPontuacao() && novo.getPartidaJogadas() > fim.getData().getPartidaJogadas()) {
            
            // O NOVO JOGADOR TEM A MESMA PONTUACAO QUE O ULTIMO JOGADOR MAS JOGOU MAIS VEZES QUE O ULTIMO JOGADOR, LOGO SERÁ O ULTIMO
            jogador.setPrev(fim);
            fim.setNext(jogador);
            fim = jogador;
            qtd++;

        } else { // O ELEMENTO DEVE SER INSERIDO NO MEIO DE INICIO E FIM

            aux = inicio;
            aux2 = inicio.getNext();

            while (aux2 != null) {
                // AUX < NOVO < AUX2
                if (novo.getPontuacao() > aux2.getData().getPontuacao()) {

                    aux.setNext(jogador);
                    jogador.setPrev(aux);
                    jogador.setNext(aux2);
                    aux2.setPrev(jogador);
                    qtd++;
                    return;
                    
                } else if (novo.getPontuacao() == aux2.getData().getPontuacao()) { // NUMERO DE PARTIDA IGUAIS

                    aux = aux2.getPrev();
                    aux3 = aux2;

                    while (aux3 != null && novo.getPontuacao() == aux3.getData().getPontuacao()) {

                        if (novo.getPartidaJogadas() <= aux3.getData().getPartidaJogadas()) {
                            // Novo jogador tem num de Vitorias == aux3
                            aux.setNext(jogador);   //  Porem tem Menos partidas ou a mesma quantidade
                            jogador.setPrev(aux);
                            jogador.setNext(aux3);
                            aux3.setPrev(jogador);
                            qtd++;
                            return;

                        }
                        aux = aux3;
                        aux3 = aux3.getNext();
                    }                          // Jogador tem a mesma quantidade de vitoria mas, tem mais partidas jogadas
                    if (aux3 != null) {          // Logo deve ser o ultimo jogador com a quela quantidad de vitoria
                        jogador.setNext(aux3);
                        jogador.setPrev(aux);
                        aux3.setPrev(jogador);
                        aux.setNext(jogador);
                        qtd++;
                        return;
                    }
                }
                aux = aux2;
                aux2 = aux2.getNext();
            }
        }
    }

    public Jogador remover(String existe) {

        NodeJogador aux, aux2, aux3;

        if (isEmpty() == true) {
            return null;
        } else {
            aux = buscar(existe);
            System.out.println("JOGADOR ENCONTRADO ?");
            System.out.println(aux);
            if (aux != null) {
                if (qtd == 1) {
                    inicio = null;
                    fim = null;
                    qtd = 0;
                    return aux.getData();
                } else if (aux.getData() == inicio.getData()) { // O ELEMENTO É O PRIMEIRO ELEMENTO
                    inicio = inicio.getNext();
                    inicio.setPrev(null);
                    qtd--;
                    return aux.getData();

                } else if (aux.getData() == fim.getData()) { // O ELEMENTO É O ULTIMO ELEMENTO
                    aux2 = fim.getPrev();
                    aux2.setNext(null);
                    fim = aux2;
                    qtd--;
                    return aux.getData();
                } else { // O ELEMENTO ESTA NO MEIO
                    aux2 = aux.getPrev();
                    aux3 = aux.getNext();
                    aux2.setNext(aux3);
                    aux3.setPrev(aux2);
                    qtd--;
                    return aux.getData();
                }
            }
            return null;
        }
    }

    public void Exibir() {
        NodeJogador aux;
        System.out.println("JOGADORES");
        System.out.println();
        aux = inicio;
        while (aux != null) {

            System.out.println(aux.getData());
            aux = aux.getNext();
        }

    }

    public int getQtd() {
        return qtd;
    }

    public NodeJogador getInicio() {
        return inicio;
    }

}
