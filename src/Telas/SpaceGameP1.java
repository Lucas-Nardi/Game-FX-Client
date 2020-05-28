package Telas;

import Objetos.Alien;
import Objetos.Bala;
import Objetos.DadosDoJogo;
import Objetos.Jogador;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 *
 * @author Lucas
 */
public class SpaceGameP1 extends Application {

    private AnimationTimer loop;
    private static Stage tela;

    boolean novoAlien = false;
    boolean novaBala = false;
    private int score = 0;
    private boolean pararServer = false;

    private Label palavraScore;
    private Label scoreImage;

    private int tempoCriarBala = 0;

    private Image boneco1;
    private Image boneco2;

    private ImageView background = new ImageView(new Image("/Imagem/background.png"));

    private ArrayList<Shape> balaImage;
    private ArrayList<Bala> balaObjeto;
    private ArrayList<Shape> alienImage;
    private ArrayList<Alien> alienObjeto;

    private Rectangle nave1;
    public static double pox1 = 55;
    public static double poy1 = 610;
    ProgressBar vida1;
    Label nome1;
    private Jogador jogador1;

    private Rectangle nave2;
    public static double pox2 = 693;
    public static double poy2 = 610;
    ProgressBar vida2;
    Label nome2;
    private Jogador jogador2;

    //private LinkedList<Jogador> lista;

    private Media backgroundSound;
    private Media fire;
    private Media explosion;
    private Media revive;
    private MediaPlayer mainSound;
    private MediaPlayer fire1Sound;
    private MediaPlayer fire2Sound;
    private MediaPlayer explosionSound;

    private Socket client = null;
    ObjectInputStream inObject;
    ObjectOutputStream outObject;

//    public SpaceGameP1(Jogador j, LinkedList<Jogador> l) {
//        player = j;
//        lista = l;
//    }
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {

        tela = stage;
        tela.setTitle("Cliente ( <- / ->");
        tela.setResizable(false);
        Group root = new Group();
        Scene theScene = new Scene(root, 840, 700);
        tela.setScene(theScene);
        Canvas canvas = new Canvas(840, 700);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        criarJogador();
        cliente();
        paint(root, theScene);
        criarSom();
        loop = new AnimationTimer() {

            @Override
            public void handle(long currentNanoTime) {

                if (nave1.isVisible() == false && nave2.isVisible() == false) { // CONDICAO PARA PARAR O JOGO
                    //Voltar para o menu 

                    jogador1.setPontuacao(score);
                    jogador2.setPontuacao(score);
                    loop.stop();
                    tela.close();
                    pararServer = true;
                    //rodarSom.stop();
                    //irMenu(lista);

                }

                if (novoAlien == true) {

                    cairAlien(root);
                    novoAlien = false;
                }

                if (novaBala == true) {
                    adicionarBala(root);
                    novaBala = false;
                }

                gc.clearRect(0, 0, 840, 520);

                if (balaImage.size() > 0) {
                    colisao_Bala(root);
                }

                if (nave1.isVisible() == true) {

                    if (alienObjeto.size() > 0) {
                        colisao_Boneco(nave1, 2, root);
                    }
                    nave1.setLayoutX(pox1);
                }
                if (nave2.isVisible() == true) {

                    if (alienObjeto.size() > 0) {
                        colisao_Boneco(nave2, 2, root);
                    }
                    nave2.setLayoutX(pox2);
                }

                theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent ke) {

                        if (nave1.isVisible() == true) {

                            if (ke.getCode() == KeyCode.RIGHT) {

                                if (pox1 >= 693) {

                                    pox1 = 688;

                                } else {

                                    pox1 = pox1 + 5;
                                }
                                try {

                                    outObject.writeObject(new DadosDoJogo(pox1, false, false));
                                    outObject.flush();
                                } catch (IOException ex) {
                                    Logger.getLogger(SpaceGameP1.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            if (ke.getCode() == KeyCode.LEFT) {

                                if (pox1 <= 25) {

                                    pox1 = 30;

                                } else {

                                    pox1 = pox1 - 5;
                                }
                                try {
                                    outObject.writeObject(new DadosDoJogo(pox1, false, false));
                                    outObject.flush();

                                } catch (IOException ex) {
                                    Logger.getLogger(SpaceGameP1.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                            if (ke.getCode() == KeyCode.UP) {

                                if (tempoCriarBala >= 90) {

                                    criarBala(root);
                                    tempoCriarBala = 0;
                                }

                            }
                        }
                    }
                });

                theScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent ke) {

                    }
                });

                if (mainSound.getStatus() == Status.PLAYING) {   // TEMPO DE DURAÇÃO DE CADA SOM
                    double ct = mainSound.getCurrentTime().toSeconds();
                    if (ct > 313) { // RESETAR A MUSICA --- 4 min  e 37 segundos Min // 277 Segundos
                        mainSound.stop();
                        mainSound.play();
                    }
                }
                if(explosionSound.getStatus() == Status.PLAYING){
                    double ct = explosionSound.getCurrentTime().toSeconds();
                    if (ct >= 1.5) {
                        mainSound.stop();                        
                    }
                }
                if(fire2Sound.getStatus() == Status.PLAYING){
                    double ct = fire2Sound.getCurrentTime().toMillis();
                    if (ct >= 800) { 
                        fire2Sound.stop();                        
                    }
                }
                if(fire1Sound.getStatus() == Status.PLAYING){
                    double ct = fire1Sound.getCurrentTime().toSeconds();
                    if (ct >= 1.5) { 
                        fire1Sound.stop();                        
                    }
                }
                tempoCriarBala++;
            }
        };
        loop.start();
        stage.show();
    }

//    public void irMenu(LinkedList<Jogador> l) {
//        Menu novo = new Menu(l);
//        novo.start(new Stage());
//    }
    public void criarSom() {

        backgroundSound = new Media(this.getClass().getResource("/Som/game03.mp4").toExternalForm());
        mainSound = new MediaPlayer(backgroundSound);
        mainSound.setStartTime(Duration.seconds(220));
        mainSound.setVolume(0.15);
        mainSound.play();
        explosion = new Media(this.getClass().getResource("/Som/exposion.mp3").toExternalForm());
        explosionSound = new MediaPlayer(explosion);

        fire = new Media(this.getClass().getResource("/Som/bala1.mp3").toExternalForm());
        fire1Sound = new MediaPlayer(fire);

        fire2Sound = new MediaPlayer(new Media(this.getClass().getResource("/Som/bala3.mp3").toExternalForm()));
        fire2Sound.setVolume(0.2);
    }

    private void paint(Group root, Scene theScene) {

        this.background.setFitHeight(720);
        this.background.setFitWidth(860);

        balaImage = new ArrayList<>();
        balaObjeto = new ArrayList<>();

        alienImage = new ArrayList<>();
        alienObjeto = new ArrayList<>();

        nave1 = new Rectangle();
        nave2 = new Rectangle();

        boneco1 = new Image("/Imagem/nave1.png");
        boneco2 = new Image("/Imagem/nave2.png");

        nave1.setFill(new ImagePattern(boneco1));
        nave1.setArcWidth(36);
        nave1.setArcHeight(36);
        nave1.setWidth(60);
        nave1.setHeight(60);
        nave1.setLayoutX(pox1);
        nave1.setLayoutY(poy1);
        vida1 = new ProgressBar();
        vida1.setPrefSize(153, 18);
        vida1.setLayoutX(9);
        vida1.setLayoutY(33);
        vida1.setStyle("-fx-accent: orange;");
        vida1.setProgress(1.0);
        nome1 = new Label(jogador1.getNome());
        nome1.setPrefSize(153, 18);
        nome1.setLayoutX(9);
        nome1.setLayoutY(9);
        nome1.setTextFill(Color.WHITE);
        nome1.setAlignment(Pos.CENTER_LEFT);
        nome1.setFont(Font.font("Berlin Sans FB", 16));

        nave2.setFill(new ImagePattern(boneco2));
        nave2.setArcWidth(36);
        nave2.setArcHeight(36);
        nave2.setWidth(60);
        nave2.setHeight(60);
        nave2.setLayoutX(pox2);
        nave2.setLayoutY(poy2);
        vida2 = new ProgressBar();
        vida2.setPrefSize(153, 18);
        vida2.setLayoutX(693);
        vida2.setLayoutY(33);
        vida2.setStyle("-fx-accent: blue;");
        vida2.setProgress(1.0);
        nome2 = new Label(jogador2.getNome());
        nome2.setPrefSize(153, 18);
        nome2.setLayoutX(693);
        nome2.setLayoutY(9);
        nome2.setTextFill(Color.WHITE);
        nome2.setAlignment(Pos.CENTER_RIGHT);
        nome2.setFont(Font.font("Berlin Sans FB", 16));

        palavraScore = new Label("Score:");
        palavraScore.setLayoutX(222);
        palavraScore.setLayoutY(18);
        palavraScore.setTextFill(Color.YELLOW);
        palavraScore.setAlignment(Pos.CENTER_LEFT);
        palavraScore.setFont(Font.font("Berlin Sans FB", 28));
        palavraScore.setPrefSize(76, 40);

        scoreImage = new Label("0");
        scoreImage.setLayoutX(305);
        scoreImage.setLayoutY(5);
        scoreImage.setTextFill(Color.YELLOW);
        scoreImage.setAlignment(Pos.CENTER_LEFT);
        scoreImage.setFont(Font.font("Berlin Sans FB", 30));
        scoreImage.setPrefSize(353, 65);

        root.getChildren().addAll(background, vida1, vida2, nome1, nome2, nave1, nave2, palavraScore, scoreImage);
    }

    private void cliente() throws IOException, ClassNotFoundException {
        int port = 16868;
        Jogador jogador2;
        //InetAddress server = InetAddress.getByName("192.168.0.25");
        InetAddress server = InetAddress.getLocalHost();
        client = new Socket(server, port);
        outObject = new ObjectOutputStream(client.getOutputStream());
        inObject = new ObjectInputStream(client.getInputStream());
        System.out.println("Conectado......");

        outObject.writeObject(jogador1);
        outObject.flush();
        jogador2 = (Jogador) inObject.readObject();
        this.jogador2 = jogador2;

        new Thread() {

            @Override
            public void run() {

                DadosDoJogo data;
                Alien ali;
                Bala bala;

                while (true) {

                    try {
                        if (pararServer == true) {

                            outObject.close();
                            inObject.close();
                            client.close();
                            break;
                        }
                        System.out.println("ESPERANDO DADOS DO JOGADOR 2");
                        data = (DadosDoJogo) inObject.readObject();
                        pox2 = data.getPosicaoX();
                        novoAlien = data.isNovoAlien();
                        novaBala = data.isNovaBala();

                        if (novoAlien) {

                            ali = data.getAliens();
                            alienObjeto.add(ali);
                            novoAlien = true;
                        }
                        if (novaBala) {

                            bala = data.getBullet();
                            balaObjeto.add(bala);
                            novaBala = true;
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(SpaceGameP1.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        System.out.println("CLASSE NAO ENCONTRADA");
                    }
                }
            }
        }.start();

    }

    private void cairAlien(Group root) {

        Alien ali;
        int tam = alienObjeto.size() - 1;
        ali = alienObjeto.get(tam);
        Rectangle rec = new Rectangle();
        rec.setFill(new ImagePattern(new Image("/Imagem/alien.png")));
        rec.setArcWidth(36);
        rec.setArcHeight(36);
        rec.setWidth(60);
        rec.setHeight(60);
        rec.setLayoutX(ali.getPosX());
        rec.setLayoutY(ali.getPosY());
        root.getChildren().add(rec);
        alienImage.add(rec);
    }

    private void colisao_Boneco(Shape boneco, int player, Group root) {

        boolean collisionDetected = false;
        Alien alien;
        Shape static_bloc;
        double qtdVida1;
        double qtdVida2;

        for (int i = 0; i < alienImage.size(); i++) {

            alien = alienObjeto.get(i);
            static_bloc = alienImage.get(i);

            if (static_bloc != boneco) {

                Shape intersect = Shape.intersect(boneco, static_bloc);
                if (intersect.getBoundsInLocal().getWidth() != -1) {

                    collisionDetected = true;
                }
            }
            if (collisionDetected) {

                if (player == 1) { // Alien Colidiu com player 1

                    alien.setVida();

                    if (vida1.getProgress() > 0) { // Fazer a animação da vida ir de um lado para o outro

                        qtdVida1 = vida1.getProgress();
                        qtdVida1 = qtdVida1 - 0.1;

                        if (qtdVida1 <= 0) {

                            nave1.setVisible(false);
                            qtdVida1 = qtdVida1 - 2;
                        }

                        vida1.setProgress(qtdVida1);

                    }

                    if (alien.getVida() == 0) {

                        score = score + alien.getPontuacao();
                        scoreImage.setText(Integer.toString(score));

                        for (int j = 0; j < root.getChildren().size(); j++) {

                            if (static_bloc == root.getChildren().get(j)) {

                                alienImage.remove(i);
                                alienObjeto.remove(i);
                                root.getChildren().remove(j);
                                break;
                            }
                        }

                    } else {

                        alien.forwardAlien(1.7);
                        static_bloc.setLayoutY(alien.getPosY());

                    }

                    root.getChildren().size();

                } else {  // Alien Colideiu com player 2

                    alien.setVida();

                    if (vida2.getProgress() > 0) { // Fazer a animação da vida ir de um lado para o outro

                        qtdVida2 = vida2.getProgress();
                        qtdVida2 = qtdVida2 - 0.1;

                        if (qtdVida2 <= 0) {

                            nave2.setVisible(false);
                            qtdVida1 = qtdVida2 - 2;
                        }
                        vida2.setProgress(qtdVida2);
                    }

                    if (alien.getVida() == 0) {

                        score = score + alien.getPontuacao();
                        scoreImage.setText(Integer.toString(score));

                        for (int j = 0; j < root.getChildren().size(); j++) {

                            if (static_bloc == root.getChildren().get(j)) {

                                alienImage.remove(i);
                                alienObjeto.remove(i);
                                root.getChildren().remove(j);
                                break;
                            }
                        }

                    } else {

                        alien.forwardAlien(1.7);
                        static_bloc.setLayoutY(alien.getPosY());
                    }

                }
            } else { // NAO COLIDIU ENTAO O ALIEN PODE PROSSEGUIR

                if (static_bloc.getLayoutY() > 705) {  // ALIEN PASSOU PELAS NAVES 

                    qtdVida1 = vida1.getProgress();
                    qtdVida2 = vida2.getProgress();

                    if (vida1.getProgress() > 0) { // Fazer a animação da vida ir de um lado para o outro

                        qtdVida1 = vida1.getProgress();
                        qtdVida1 = qtdVida1 - 0.1;

                        if (qtdVida1 <= 0) {

                            nave1.setVisible(false);
                            qtdVida1 = qtdVida1 - 2;
                        }
                        vida1.setProgress(qtdVida1);
                    }

                    if (vida2.getProgress() > 0) { // Fazer a animação da vida ir de um lado para o outro

                        qtdVida2 = vida2.getProgress();
                        qtdVida2 = qtdVida2 - 0.1;

                        if (qtdVida2 <= 0) {

                            nave2.setVisible(false);
                            qtdVida1 = qtdVida2 - 2;
                        }
                        vida2.setProgress(qtdVida2);
                    }

                    for (int j = 0; j < root.getChildren().size(); j++) {  // REMOVE A NAVE DO JOGO

                        if (static_bloc == root.getChildren().get(j)) {

                            alienImage.remove(i);
                            alienObjeto.remove(i);
                            root.getChildren().remove(j);
                        }
                    }
                } else if (alien.getVida() == 0) { // TEMPO QUE A IMAGEM DA EXPLOSAO VAI FICAR

                    alien.setTempoExplodido();

                    if (alien.getTempoExplodido() == 36) {
                        alien.setVida();
                    }

                } else if (alien.getVida() == -1) {

                    for (int j = 0; j < root.getChildren().size(); j++) {  // REMOVE A NAVE DO JOGO

                        if (static_bloc == root.getChildren().get(j)) {

                            alienImage.remove(i);
                            alienObjeto.remove(i);
                            root.getChildren().remove(j);
                        }
                    }

                } else {

                    alien.forwardAlien(1.7);
                    static_bloc.setLayoutY(alien.getPosY());

                }
            }
        }

    }

    private void criarBala(Group root) {

        Rectangle rec = new Rectangle();

        rec.setFill(new ImagePattern(new Image("/Imagem/bala1.png")));
        rec.setArcWidth(36);
        rec.setArcHeight(36);
        rec.setWidth(40);
        rec.setHeight(57);
        rec.setLayoutX(pox1 + 10);
        rec.setLayoutY(poy1 - 50);
        root.getChildren().add(rec);
        balaImage.add(rec);
        Bala bullet = new Bala(pox1 + 10, poy1 - 50);
        balaObjeto.add(bullet);
        fire1Sound.stop();
        fire1Sound.play();
        try {
            outObject.writeObject(new DadosDoJogo(pox1, false, true, bullet));
            outObject.flush();
        } catch (IOException ex) {
            Logger.getLogger(SpaceGameP1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void colisao_Bala(Group root) {

        boolean collisionDetected = false;
        Bala bullet;
        Shape aliImage;
        Shape bulletImage;
        Alien alien;

        for (int i = 0; i < balaImage.size(); i++) {

            bullet = balaObjeto.get(i);
            bulletImage = balaImage.get(i);

            if (alienImage.size() > 0) {

                for (int j = 0; j < alienImage.size(); j++) {

                    aliImage = alienImage.get(j);
                    alien = alienObjeto.get(j);

                    if (bulletImage != aliImage) {

                        Shape intersect = Shape.intersect(bulletImage, aliImage);
                        if (intersect.getBoundsInLocal().getWidth() != -1) {

                            collisionDetected = true;
                        }
                    }
                    if (collisionDetected) {

                        alienObjeto.get(j).setVida();

                        if (alien.getVida() == 0) {

                            aliImage.setFill(new ImagePattern(new Image("/Imagem/explosao.png")));
                            score = score + alien.getPontuacao();
                            scoreImage.setText(Integer.toString(score));
                            explosionSound.stop();
                            explosionSound.play();
                        }
                        for (int k = 0; k < root.getChildren().size(); k++) {

                            if (bulletImage == root.getChildren().get(k)) {

                                root.getChildren().remove(k);
                                balaObjeto.remove(i);
                                balaImage.remove(i);
                                break;
                            }
                        }
                        break; // A BALA JÁ COLIDIU LOGO, NAO PRECISO TESTAR A BALA COM MAIS NENHUM ALIEN 

                    } else { // A BALA NAO COLIDIU COM O ALIEN, LOGO PODE PROSSEGUIR

                        if (bulletImage.getLayoutY() < 100) { // RANGE MAXIMO DA BALA

                            for (int k = 0; k < root.getChildren().size(); k++) {

                                if (bulletImage == root.getChildren().get(k)) {

                                    root.getChildren().remove(k);
                                    balaImage.remove(i);
                                    balaObjeto.remove(i);
                                    break;
                                }
                            }

                            break; // BALA JÁ SAIU NO MAPA NAO PRECISA FICAR VERIFICANDO ELA COM OS OUTROS ALIENS

                        } else {

                            balaObjeto.get(i).forwardBullet(2.1);
                            balaImage.get(i).setLayoutY(balaObjeto.get(i).getPosY());
                        }
                    }
                }
            } else {

                if (bulletImage.getLayoutY() < 100) { // RANGE MAXIMO DA BALA

                    for (int k = 0; k < root.getChildren().size(); k++) {

                        if (bulletImage == root.getChildren().get(k)) {

                            root.getChildren().remove(k);
                            balaImage.remove(i);
                            balaObjeto.remove(i);
                            break;
                        }
                    }

                    break; // BALA JÁ SAIU NO MAPA NAO PRECISA FICAR VERIFICANDO ELA COM OS OUTROS ALIENS

                } else {

                    balaObjeto.get(i).forwardBullet(2.1);
                    balaImage.get(i).setLayoutY(balaObjeto.get(i).getPosY());
                }
            }
        }
    }

    private void adicionarBala(Group root) {

        Bala bala;
        int tam = balaObjeto.size() - 1;
        bala = balaObjeto.get(tam);
        Rectangle rec = new Rectangle();
        rec.setFill(new ImagePattern(new Image("/Imagem/bala2.png")));
        rec.setArcWidth(36);
        rec.setArcHeight(36);
        rec.setWidth(40);
        rec.setHeight(57);
        rec.setLayoutX(bala.getPosX());
        rec.setLayoutY(bala.getPosY());
        root.getChildren().add(rec);
        balaImage.add(rec);
        fire2Sound.stop();
        fire2Sound.play();
    }

    private void criarJogador() {

        String nome1 = null;
        int i, tam = 12, teste;

        tam = 12;
        do {

            while (tam > 9) {
                tam = 0;
                JOptionPane.showMessageDialog(null, "Seu nome deve ter no maximo 9 digítos e não deve usar espaço.", "Seu nome", JOptionPane.INFORMATION_MESSAGE);
                nome1 = JOptionPane.showInputDialog(null, "Nome: ", "Nome do jogador", JOptionPane.INFORMATION_MESSAGE);

                if (nome1.length() > 0 && !Character.isWhitespace(nome1.charAt(0))) {
                    for (teste = 0; teste < nome1.length(); teste++) {
                        if (Character.isWhitespace(nome1.charAt(teste))) {
                            tam = 12;
                            break;
                        } else {
                            tam = tam + 1;
                        }
                    }
                } else { // USOU O BOTÃO CANCELAR
                    tam = 12;
                }
            }
            i = JOptionPane.showConfirmDialog(null, "Tem certeza que quer este nome(" + nome1 + ") ?", "Nome do jogador 2familia toda juntin", JOptionPane.INFORMATION_MESSAGE);

            if (i == 2 || i == 1) { // USOU O BOTÃO CANCELAR OU O NÃO
                tam = 12;
            }

        } while (i == 2 || i == 1);
//        jogador2 = lista.remover(nome1);
        //if (jogador2 == null) { // JOGADOR NÂO EXISTE

        jogador1 = new Jogador(nome1);
        //    novoJogador = true;
        //}
        //novoJogador = false;
    }

    private static void main(String[] args) {
        launch(args);
    }
}
