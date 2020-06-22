package Telas;

import Objetos.ListaJogador;
import Objetos.ManipulacaoArquivo;
import Objetos.Numero;
import Objetos.OperarArquivo;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Menu extends Application {

    private AnimationTimer loop;
    private int cont;
    private Button start;
    private Button tutorial;
    private Button scoreBoard;
    private Button exit;
    private Label tituilo;
    private static Stage tela;
    private ListaJogador lista;
    private Media somMenu;
    private Media somExit;
    private Media somBt;
    private MediaPlayer menuS;
    private MediaPlayer buttonsS;
    private boolean b1 = false;
    private boolean b2 = false;
    private boolean b3 = false;
    private boolean b4 = false;
    private Canvas canvas;
    
    public Menu(ListaJogador list) {
        this.lista = list;
    }

    public void start(Stage stage) {
        tela = stage;
        tela.setTitle("MENU");
        Pane root = new Pane();
        canvas = new Canvas();
        Scene theScene = new Scene(root,775,670);
        tela.setScene(theScene);
        ImageView fundo = new ImageView("/Imagem/menuImage.png");       
        canvas.setHeight(670);
        canvas.setWidth(775);
        root.getChildren().addAll(fundo, canvas);
        Numero imagensCaindo[] = new Numero[3];
        ArrayList<Numero> numeroList = new ArrayList<>();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        criarImagem(imagensCaindo);
        CriarBotoes(root);
        criarTitulo(root);
        criarSom();
        stage.initStyle(StageStyle.UNDECORATED);
        cont = 0;
        System.out.println("TELA MENU");
        lista.Exibir();
        loop = new AnimationTimer() {

            int pos = 1;

            @Override
            public void handle(long currentNanoTime) {
                
                if (cont == 250) { // ACADA 5 SEGUNDO COLOCAR UMA IMAGEN NA MESA

                    if (pos == 1) {
                        pos = 2;
                    } else {
                        pos = 1;
                    }
                    cairImagens(imagensCaindo, numeroList, pos);
                    cont = 0;                    
                }
                gc.clearRect(0, 0,root.getScene().getWidth()+20 , root.getScene().getHeight()+20);
                queda(numeroList, gc);
                start.setOnMouseClicked((MouseEvent e) -> {
                    menuS.stop();
                    b1 = true;
                    buttonsS.play();
                });

                start.setOnKeyPressed((KeyEvent e) -> {
                    if (e.getCode() == KeyCode.ENTER) {
                        menuS.stop();
                        b1 = true;
                        buttonsS.play();
                    }
                });
                tutorial.setOnMouseClicked((MouseEvent e) -> {
                    menuS.stop();
                    buttonsS.play();
                    b2 = true;
                });

                tutorial.setOnKeyPressed((KeyEvent e) -> {
                    if (e.getCode() == KeyCode.ENTER) {
                        menuS.stop();
                        buttonsS.play();
                        b2 = true;
                    }
                });
                scoreBoard.setOnMouseClicked((MouseEvent e) -> {
                    menuS.stop();
                    buttonsS.play();
                    b3 = true;
                });

                scoreBoard.setOnKeyPressed((KeyEvent e) -> {
                    if (e.getCode() == KeyCode.ENTER) {
                        menuS.stop();
                        buttonsS.play();
                        b3 = true;
                    }
                });
                exit.setOnMouseClicked((MouseEvent e) -> {
                    menuS.stop();
                    buttonsS = new MediaPlayer(somExit);
                    buttonsS.play();
                    b4 = true;
                });

                exit.setOnKeyPressed((KeyEvent e) -> {
                    if (e.getCode() == KeyCode.ENTER) {
                        menuS.stop();
                        buttonsS = new MediaPlayer(somExit);
                        buttonsS.play();
                        b4 = true;
                    }
                });
                if (menuS.getStatus() == Status.PLAYING) {   // TEMPO DE DURAÇÃO DE CADA SOM
                    double ct = menuS.getCurrentTime().toSeconds();
                    if (ct > 226) { // RESETAR A MUSICA --- 4  e 18 segundos Min
                        menuS.stop();
                        menuS.play();
                    }
                }
                
                if (buttonsS.getStatus() == Status.PLAYING) {   // TEMPO DE DURAÇÃO DE CADA SOM
                    double ct = buttonsS.getCurrentTime().toMillis();
                    if (b1 == true && ct > 160) { // START
                        buttonsS.stop();
                        loop.stop();
                        tela.close();
                        try {
                            comecarJogo();
                        } catch (IOException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (b2 == true && ct > 160) {  // TUTORIAL
                        buttonsS.stop();
                        loop.stop();
                        tela.close();
                        comecarTutorial();
                    } else if (b3 == true && ct > 160) { // SCORE BOARD
                        buttonsS.stop();
                        loop.stop();
                        tela.close();
                        comecarScoreBoard();

                    } else if (b4 == true && ct > 500) { // EXIT
                        buttonsS.stop();
                        loop.stop();
                        tela.close();
                        finalizarPrograma();
                    }
                }
            }
        };
        loop.start();
        stage.show();
    }
    
    private void criarTitulo(Pane root){
        tituilo = new Label();
        tituilo.setPrefSize(380, 103);
        tituilo.setLayoutX(208);
        tituilo.setLayoutY(51);
        tituilo.setText("Alien Attack");
        tituilo.setAlignment(Pos.CENTER);
        tituilo.setFont(Font.font("Berlin Sans FB", 60));
        tituilo.setTextFill(Color.WHITE);        
        root.getChildren().add(tituilo);
    }
    
    private void criarSom(){
        somMenu = new Media(this.getClass().getResource("/Som/menu.mp4").toExternalForm());
        somExit = new Media(this.getClass().getResource("/Som/Good Bye.mp3").toExternalForm());
        somBt = new Media(this.getClass().getResource("/Som/Start.mp3").toExternalForm());
        menuS = new MediaPlayer(somMenu);
        buttonsS = new MediaPlayer(somBt);
        menuS.setVolume(0.4);
        menuS.play();
    }
    
    private void CriarBotoes(Pane root) {
        start = new Button();
        tutorial = new Button();
        scoreBoard = new Button();
        exit = new Button();
        // ---------------------------- TAMANHO BOTÕES
        start.setPrefSize(260, 50);
        tutorial.setPrefSize(260, 50);
        scoreBoard.setPrefSize(260, 50);
        exit.setPrefSize(260, 50);
        // ------------------------------- POSIÇÃO BOTÕES
        start.setLayoutX(255);
        start.setLayoutY(195);
        tutorial.setLayoutX(255);
        tutorial.setLayoutY(285);
        scoreBoard.setLayoutX(255);
        scoreBoard.setLayoutY(375);
        exit.setLayoutX(255);
        exit.setLayoutY(465);
        // ------------------------------------ NOME / IMAGEM
        start.setText("START");
        start.setTextFill(Color.DARKBLUE);
        tutorial.setText("TUTORIAL");
        tutorial.setTextFill(Color.DARKBLUE);
        scoreBoard.setText("SCORE BOARD");
        scoreBoard.setTextFill(Color.DARKBLUE);
        exit.setText("EXIT");
        exit.setTextFill(Color.DARKBLUE);
        
        // -------------------------------------
        start.setStyle("-fx-base: rgb(211,211,211);");
        tutorial.setStyle("-fx-base: rgb(211,211,211);");
        scoreBoard.setStyle("-fx-base: rgb(211,211,211);");
        exit.setStyle("-fx-base: rgb(211,211,211);");
        
        // ------------------------------------- BackgroundColor

        root.getChildren().addAll(start, tutorial, scoreBoard, exit);
    }

    private void cairImagens(Numero img[], ArrayList<Numero> numeroList, int pos) {

        SecureRandom sorteio = new SecureRandom();
        int sorte;
        double posX1, posX2;
        posX1 = 60;
        posX2 = 625;
        Numero imagem;
        sorte = sorteio.nextInt(3);
        imagem = img[sorte];
        if (pos == 1) {
            imagem.setPosition(posX1, -25);
            
        } else {
            imagem.setPosition(posX2, -25);
        }
        numeroList.add(imagem);
    }

    private void criarImagem(Numero imagensCaindo[]) {
        SecureRandom sorteio = new SecureRandom();
        int i;
        Image imagem = null;
        for (i = 0; i < imagensCaindo.length; i++) {
            Numero num = new Numero();
            switch (i) {
                case 0:
                    imagem = new Image("/Imagem/alienMenu.png");
                    break;
                case 1:
                    imagem = new Image("/Imagem/nave1Menu.png");
                    break;
                case 2:
                    imagem = new Image("/Imagem/nave2Menu.png");
                    break;                
            }
            num.setImage(imagem);
            imagensCaindo[i] = num;
        }
    }

    private void queda(ArrayList<Numero> numeroList, GraphicsContext gc) {

        Image imagem;
        double positionX;
        double positionY;
        loop.stop();
        Numero valor;

        for (int i = 0; i < numeroList.size(); i++) {
            valor = numeroList.get(i);
            imagem = valor.getImage();
            positionX = valor.getPositionX();
            positionY = valor.getPositionY();
            gc.drawImage(imagem, positionX, positionY);
            valor.update(3.7);
            if (valor.getPositionY() > 685) {
                numeroList.remove(i);               
            }
        }
        cont = cont + 1;
        loop.start();
    }

    private void comecarJogo() throws IOException, ClassNotFoundException {
        SpaceGameP1 jogo = new SpaceGameP1(lista);
        jogo.start(new Stage());
    }

    private void comecarTutorial() {
        Tutorial tuto = new Tutorial(lista);
        tuto.start(new Stage());
    }

    private void comecarScoreBoard() {
        ScoreBoard score = new ScoreBoard(lista);
        score.start(new Stage());
    }

    public void finalizarPrograma() {

        ManipulacaoArquivo mani = new ManipulacaoArquivo();
        OperarArquivo opArq = new OperarArquivo(mani, "Jogadores");
        opArq.GravarJogadores(lista);
    }  

    public static void main(String[] args) {
        launch(args);
    }
}
