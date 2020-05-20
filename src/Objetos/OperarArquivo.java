package Objetos;

import java.io.File;
import java.io.IOException;

public class OperarArquivo {
    private ManipulacaoArquivo mani;
    private String arqJogador;
    
    public OperarArquivo(ManipulacaoArquivo mani, String arqJogador) {
        this.mani = mani;
        this.arqJogador = arqJogador;
    }
    public void LerArqJogador(ListaJogador lista) {
        Jogador jogador;
        try {
            File file = new File(arqJogador); // ----Arquivo para clientes
            if (!file.exists()) { // Arquivo de clientes não existe
                file.createNewFile();
            } else { // Arquivo existe e eu posso ler
                mani.openToRead(arqJogador);
                jogador = (Jogador) mani.lerObjeto();
                while (jogador != null) {
                    System.out.println(jogador);
                    lista.adicionar(jogador);
                    jogador = (Jogador) mani.lerObjeto();
                }
            }
        } catch (IOException err) {
            System.err.println("Falha ao tentar pegar os arquvios");
            System.exit(1);
        }
        mani.closeAfterRead();
    }
        
    public void GravarJogadores(ListaJogador lista) {
        mani.openToWrite(arqJogador);
        NodeJogador aux = lista.getInicio();
        while(aux != null){
            mani.gravarObjeto(aux.getData());
            aux = aux.getNext();
        }
        mani.closeAfterWrite();
    }
}
