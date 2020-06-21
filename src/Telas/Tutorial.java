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
    private int duracao;
    private int anima;
    private boolean animar = false;
    private boolean voltando = false;
    private int cont;
    private ListaJogador lista;
    private Media pageTurn;
    private Media btSair;
    private MediaPlayer sound;
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
        Image animacao[] = new Image[70];
        duracao = 0;
        anima = 0;
        cont = 0;
        stage.initStyle(StageStyle.UNDECORATED);
        pageTurn = new Media(this.getClass().getResource("/Som/PageTurn.mp3").toExternalForm());
        sound = new MediaPlayer(pageTurn);
        btSair = new Media(this.getClass().getResource("/Som/Start.mp3").toExternalForm());
        buttomS = new MediaPlayer(btSair);

        for (int i = 0; i < animacao.length; i++) { // COLOCA TODAS AS ANIMAÇÕES NO VETOR

            animacao[i] = new Image("/Imagem/ImagemTutorial/ani" + i + ".png");

        }
        loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, 775, 670);
                gc.drawImage(animacao[anima], 0, 0);
                if (animar == true) {
                    cont = cont + 1;
                }
                if (cont % 15 == 0 && animar == true) { // MUDA A ANIMAÇÃO ACADA 15 FRAMES
                    anima = anima + 1;
                }
                if (voltando == true && cont == 45) { // VOLTANDO AS PAGINAS
                    animar = false;
                    voltando = false;
                    cont = 0;
                    proximo.setVisible(true);
                    voltar.setVisible(true);
                    sair.setVisible(true);
                }
                if (cont == 60) { // ANIMOU UMA PAGINA
                    cont = 0;
                    proximo.setVisible(true);
                    voltar.setVisible(true);
                    sair.setVisible(true);
                    animar = false;                    
                }

                scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent ke) {
                        if (ke.getCode() == KeyCode.RIGHT && animar == false) {
                            sound.stop();
                            avancarImagem(animacao);
                        }
                        if (ke.getCode() == KeyCode.LEFT && animar == false) {
                            sound.stop();
                            voltarImagem(animacao);
                        }
                        if (ke.getCode() == KeyCode.ESCAPE && animar == false) {
                            buttomS.play();
                        }
                    }
                });
                proximo.setOnMouseClicked((MouseEvent e) -> {                                   
                    sound.stop();
                    avancarImagem(animacao);
                });

                voltar.setOnMouseClicked((MouseEvent e) -> {                    
                    sound.stop();
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

        proximo = new ImageView("/Imagem/ImagemTutorial/setaProximo.png");
        voltar = new ImageView("/Imagem/ImagemTutorial/setaAnterior1.png");
        sair = new ImageView("/Imagem/ImagemTutorial/setaSair.png");

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
        if (anima == 68) { // PENULTIMA  PAGINA
            sound.play();
            anima = 69;
            animar = false;
            proximo.setImage(new Image("/Imagem/ImagemTutorial/setaProximo1.png"));
        } else if (anima != animacao.length - 1) { // NÃO É A ULTIMA PÁGINA
            sound.play();
            animar = true;
            proximo.setVisible(false);
            voltar.setVisible(false);
            sair.setVisible(false);
            voltar.setImage(new Image("/Imagem/ImagemTutorial/setaAnterior.png"));
        }
    }

    private void voltarImagem(Image animacao[]) {
        if (anima == 4) {
            anima = 0;
            animar = false;
            voltar.setVisible(true);
            voltar.setImage(new Image("/Imagem/ImagemTutorial/setaAnterior1.png"));
            sound.play();
        } else if (anima == animacao.length - 1) { // ULTIMA PAGINA
            anima = animacao.length - 5;
            animar = true;
            voltando = true;
            proximo.setVisible(false);
            voltar.setVisible(false);
            sair.setVisible(false);
            proximo.setImage(new Image("/Imagem/ImagemTutorial/setaProximo.png"));
            sound.play();
        } else if (anima > 4) {
            anima = anima - 7;
            animar = true;
            voltando = true;
            proximo.setVisible(false);
            voltar.setVisible(false);
            sair.setVisible(false);
            proximo.setImage(new Image("/Imagem/ImagemTutorial/setaProximo.png"));
            sound.play();
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
