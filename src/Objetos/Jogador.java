package Objetos;

import java.io.Serializable;

public class Jogador implements Serializable{

    private String nome;
    private int pontuacao;
    private int partidaJogadas;

    public Jogador(String nome) {

        this.nome = nome;
        this.pontuacao = 0;
        this.partidaJogadas = 0;
    }

    public String getNome() {
        return nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }
    
    public void setPartidaJogadas() {
        partidaJogadas = partidaJogadas + 1;
    }

    public int getPartidaJogadas() {
        return partidaJogadas;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ",Pontuação: " + pontuacao + ", Patidas jogadas: " + partidaJogadas;
    }

}
