package Telas;

import Objetos.ListaJogador;
import Objetos.ManipulacaoArquivo;
import Objetos.OperarArquivo;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Pichau
 */
public class Inicial extends Application {
    
    
    private Label nome1 = new Label();
    private Label nome2 = new Label();
    private Label nome3 = new Label();
    private Label nome4 = new Label();
    private AnimationTimer loop;
    private ImageView fundo;
    private int cont = 0;
    private int pos = 0;
    private Label [] anima = new Label[4];
    private Media blocDown;
    private MediaPlayer sound;
    
    
    @Override
    public void start(Stage stage) {
        
        ListaJogador lista;
        Pane root = new Pane();
        root.setPrefSize(775,670);
        Scene scene = new Scene(root);
        fundo = new ImageView(new Image("/Imagem/inicial.png"));
        
        lista = new ListaJogador();
        cairNome();
        prepararPrograma(lista);
        blocDown = new Media(this.getClass().getResource("/Som/blockDown.mp4").toExternalForm());
        sound = new MediaPlayer(blocDown);        
        root.getChildren().add(fundo);
        
        loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                cont = cont +1;
                if(cont % 45 == 0 && pos < 4){
                    sound.stop();
                    sound.play();
                    root.getChildren().add(anima[pos]);
                    pos = pos + 1;
                }else if (cont == 200){
                    loop.stop();                    
                    cont = 0;
                    pos = 0;
                    sound.stop();
                    stage.close();
                    Menu menu = new Menu(lista);
                    menu.start(new Stage());
                }
            }
        };
        loop.start();
        stage.setTitle("Patrocinadores");
        stage.setScene(scene);
        stage.show();
    }

    public void cairNome(){
        
        anima[0] = nome1;
        anima[1] = nome2;
        anima[2] = nome3;
        anima[3] = nome4;
        // NOME  E FONTE
        nome1.setText("Universidade");
        nome1.setFont(Font.font("System", 40));
        nome1.setTextFill(Color.BLACK);
        nome2.setText("Católica");
        nome2.setFont(Font.font("System", 40));
        nome2.setTextFill(Color.BLACK);
        nome3.setText("de");
        nome3.setFont(Font.font("System", 40));
        nome3.setTextFill(Color.BLACK);
        nome4.setText("Pernambuco");
        nome4.setFont(Font.font("System", 40));
        nome4.setTextFill(Color.BLACK);
        // TAMANHO DO LABEL
        nome1.setPrefSize(232, 61);
        nome2.setPrefSize(158, 61);
        nome3.setPrefSize(57, 61);
        nome4.setPrefSize(232, 61);
        // POSIÇÃO DO LABEL
        nome1.setLayoutX(55);
        nome1.setLayoutY(110);
        
        nome2.setLayoutX(293);
        nome2.setLayoutY(110);
        
        nome3.setLayoutX(442);
        nome3.setLayoutY(110);
       
        nome4.setLayoutX(499);
        nome4.setLayoutY(110);
        
    }
    
    public void prepararPrograma(ListaJogador list) {

        ManipulacaoArquivo mani = new ManipulacaoArquivo();
        OperarArquivo opArq = new OperarArquivo(mani, "Jogadores");
        opArq.LerArqJogador(list);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
