package Objetos;

import java.io.Serializable;

public class Alien implements Serializable {

    int vida;
    double posicaoX;
    double posicaoY;
    private double velocityY;
    int tempoExplodido;
    int pontuacao;

    public Alien(int vida, int pontos) {
        this.vida = vida;
        velocityY = 0.5;
        tempoExplodido = 0;
        this.pontuacao = pontos;
    }

    public void setVida() {

        this.vida = this.vida - 1;
    }

    public void setTempoExplodido() {

        this.tempoExplodido = this.tempoExplodido + 1;
    }

    public void forwardAlien(double time) {

        posicaoY = posicaoY + velocityY * time;
    }

    public int getVida() {
        return vida;
    }

    public void setPosX(double x) {
        posicaoX = x;
    }

    public void setPosY(double y) {
        posicaoY = y;
    }

    public double getPosX() {
        return posicaoX;
    }

    public double getPosY() {
        return posicaoY;
    }

    public int getTempoExplodido() {
        return tempoExplodido;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }
}
