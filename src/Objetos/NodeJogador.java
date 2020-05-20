package Objetos;

import java.io.Serializable;

public class NodeJogador implements Serializable{
    
    private Jogador data;
    private NodeJogador next;
    private NodeJogador prev;
    
    public NodeJogador (Jogador nume){
        
        data = nume;
    }    
    public Jogador getData() {
        
        return data;
    }
    public void setData(Jogador data) {
        
        this.data = data;
    }
    public NodeJogador getNext() {
        
        return next;
    }
    public void setNext(NodeJogador next) {
        
        this.next = next;
    }
    public NodeJogador getPrev() {
        
        return prev;
    }
    public void setPrev(NodeJogador prev) {
        
        this.prev = prev;
    }
}


  