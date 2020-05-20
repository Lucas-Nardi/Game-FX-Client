package Objetos;

import java.io.Serializable;

public class DadosDoJogo implements Serializable {

    private double posicaoX;
    private Alien aliens;
    boolean novoAlien;
    private Bala bullet;
    boolean novaBala;

    public DadosDoJogo(double posicaoX, boolean novoAlien, boolean novaBala) {
        this.posicaoX = posicaoX;
        this.novoAlien = novoAlien;
        this.novaBala = novaBala;
    }

    public DadosDoJogo(double posicaoX, boolean novoAlien, Alien alien, boolean novaBala) {
        this.posicaoX = posicaoX;
        this.novoAlien = novoAlien;
        this.aliens = alien;
        this.novaBala = novaBala;
    }

    public DadosDoJogo(double posicaoX, boolean novoAlien, boolean novaBala, Bala bala) {
        this.posicaoX = posicaoX;
        this.novoAlien = novoAlien;
        this.novaBala = novaBala;
        this.bullet = bala;
    }

    public double getPosicaoX() {
        return posicaoX;
    }

    public void setPosicaoX(double posicaoX) {
        this.posicaoX = posicaoX;
    }

    public Alien getAliens() {
        return aliens;
    }

    public void setAliens(Alien aliens) {
        this.aliens = aliens;
    }

    public boolean isNovoAlien() {
        return novoAlien;
    }

    public void setNovoAlien(boolean novoAlien) {
        this.novoAlien = novoAlien;
    }

    public Bala getBullet() {
        return bullet;
    }

    public void setBullet(Bala bullet) {
        this.bullet = bullet;
    }

    public boolean isNovaBala() {
        return novaBala;
    }

    public void setNovaBala(boolean novaBala) {
        this.novaBala = novaBala;
    }

    @Override
    public String toString() {
        return "DadosDoJogo{" + "posicaoX=" + posicaoX + ", aliens=" + aliens + ", novoAlien=" + novoAlien + ", bullet=" + bullet + ", novaBala=" + novaBala + '}';
    }
}
