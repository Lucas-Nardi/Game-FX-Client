package Telas;

import Objetos.ListaJogador;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Tutorial extends Application {

    private ImageView proximo;
    private ImageView voltar;
    private ImageView sair;
    private AnimationTimer loop;
    private int anima;
    private ListaJogador lista;
    private Media pageTurn;
    private Media btSair;
    private MediaPlayer buttomS;

    public Tutorial(ListaJogador list) {
        this.lista = list; // LISTA DO ARQUIVO
    }

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root);
        Canvas canvas = new Canvas(775, 670);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        criarBotoes(root);
        Image animacao[] = new Image[4];
        anima = 0;

        stage.initStyle(StageStyle.UNDECORATED);

        btSair = new Media(this.getClass().getResource("/Som/Start.mp3").toExternalForm());
        buttomS = new MediaPlayer(btSair);

        for (int i = 0; i < animacao.length; i++) { // COLOCA TODAS AS ANIMAÇÕES NO VETOR

            animacao[i] = new Image("/Imagem/tutorial/ani" + i + ".png");

        }
        loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, 775, 670);
                
                
                gc.drawImage(animacao[anima], 0, 0,775,670);
               
                             

                scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent ke) {
                        if (ke.getCode() == KeyCode.RIGHT) {
                            
                            avancarImagem(animacao);
                        }
                        if (ke.getCode() == KeyCode.LEFT ) {
                            
                            voltarImagem(animacao);
                        }
                        if (ke.getCode() == KeyCode.ESCAPE ) {
                            buttomS.play();
                        }
                    }
                });
                proximo.setOnMouseClicked((MouseEvent e) -> {                                   
                    
                    avancarImagem(animacao);
                });

                voltar.setOnMouseClicked((MouseEvent e) -> {                    
                    
                    voltarImagem(animacao);
                });

                sair.setOnMouseClicked((MouseEvent e) -> {
                    buttomS.play();
                });

                if (buttomS.getStatus() == MediaPlayer.Status.PLAYING) {   // TEMPO DE DURAÇÃO DE CADA SOM
                    double ct = buttomS.getCurrentTime().toMillis();
                    if (ct > 160) { // START
                        buttomS.stop();
                        loop.stop();
                        stage.close();
                        voltarMenu();
                    }
                }
            }
        };
        loop.start();
        stage.setTitle("Turorial");
        stage.setScene(scene);
        stage.show();
    }

    private void criarBotoes(Group can) {

        proximo = new ImageView("/Imagem/tutorial/setaProximo.png");
        voltar = new ImageView("/Imagem/tutorial/setaAnterior1.png");
        sair = new ImageView("/Imagem/tutorial/setaSair.png");

        // POSIÇÂO DOS BOTÕES
        proximo.setLayoutX(650);
        proximo.setLayoutY(615);
        voltar.setLayoutX(20);
        voltar.setLayoutY(615);
        sair.setLayoutX(10);
        sair.setLayoutY(5);
        can.getChildren().addAll(proximo, voltar, sair);
    }

    private void avancarImagem(Image animacao[]) {
        
        if (anima == animacao.length - 1) { // PENULTIMA  PAGINA
            anima = animacao.length - 1;            
            proximo.setImage(new Image("/Imagem/tutorial/setaProximo1.png"));

        } else if (anima < animacao.length - 1) { // NÃO É A ULTIMA PÁGINA           
            anima = anima + 1;
            if(anima == animacao.length - 1){
                proximo.setImage(new Image("/Imagem/tutorial/setaProximo1.png"));
            }
            voltar.setImage(new Image("/Imagem/tutorial/setaAnterior.png"));
        }
    }

    private void voltarImagem(Image animacao[]) {
               
        
       if (anima == 0) {                        
            
            voltar.setImage(new Image("/Imagem/tutorial/setaAnterior1.png"));
            
        } else if (anima <= animacao.length - 1) { // ULTIMA PAGINA
            
            anima = anima - 1; 
            if(anima == 0){
                
                voltar.setImage(new Image("/Imagem/tutorial/setaAnterior1.png"));
            }
            proximo.setImage(new Image("/Imagem/tutorial/setaProximo.png"));
            
        }
    }

    private void voltarMenu() {
        Menu menu = new Menu(lista);
        menu.start(new Stage());
    }

    public static void main(String[] args) {
        launch(args);
    }

}
