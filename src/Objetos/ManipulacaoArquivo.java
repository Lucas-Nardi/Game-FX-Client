package Objetos;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ManipulacaoArquivo {
    
    private ObjectOutputStream output;  // GRAVAR
    private ObjectInputStream input;    // LER
    
    public void openToRead(String nome) {
        try {
            FileInputStream file = new FileInputStream(nome + ".txt");
            input = new ObjectInputStream(file);
        } catch (IOException err) {
            System.err.println("Erro ao ler o arquivo.");
            System.exit(1);
        }
    }
    public void openToWrite(String nome) {
        try {
            FileOutputStream fos = new FileOutputStream(nome + ".txt");
            output = new ObjectOutputStream(fos);
        } catch (IOException err) {
            System.err.println("Erro ao escrever o arquivo.");
            System.exit(1);
        }
    }
    public void closeAfterRead() {
        try {
            if (input != null) {
                input.close();
                input = null;
            }
        } catch (IOException err) {
            System.err.println("Erro ao tentar fechar o arquivo.");
            System.exit(1);
        }
    }
    public void closeAfterWrite() {
        try {
            if (output != null) {
                output.close();
                output = null;
             }
        } catch (IOException err) {
            System.err.println("Erro ao tentar fechar o arquivo.");
            System.exit(1);
        }
    }
    public void gravarObjeto(Object a) {
        try {
            if (output != null) {
                output.writeObject(a);
                output.flush();
                System.out.println("Dado gravado com sucesso.");
            }
        } catch (IOException err) {
            System.err.println("Erro ao tentar gravar o arquivo.");
            System.exit(1);
        }
    }
    public Object lerObjeto() {
        Object o;
        try {
            if (input != null) {
                o = (Object) input.readObject();
                return o;
            }
        } catch (EOFException err) {
            return null;
        } catch (IOException err) {
            System.err.println("Erro ao tentar fechar o arquivo.");
            return null;
        } catch (ClassNotFoundException err) {
            System.err.println("Classe não encontrada.");
            return null;
        }
        return null;
    }
    
}
