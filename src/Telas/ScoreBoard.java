package Telas;

import Objetos.ListaJogador;
import Objetos.NodeJogador;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ScoreBoard extends Application {

    private GridPane tabela;
    private Label scoreBoard;
    private Label rank;
    private Label nome;
    private Label pontuacao;
    private Label partidas;
    private ImageView sair;
    private ImageView fundo;
    private VBox local;
    private ListaJogador lista;
    private int qtd;
    private Media btSair;
    private MediaPlayer buttomS;
    private AnimationTimer loop;

    public ScoreBoard(ListaJogador list) {
        this.lista = list; // LISTA DO ARQUIVO
    }

    @Override
    public void start(Stage stage) {
        lista.Exibir();
        Pane visu = new Pane();
        Scene scene = new Scene(visu);
        visu.setPrefSize(775, 670);
        stage.initStyle(StageStyle.UNDECORATED);
        qtd = lista.getQtd();       
        System.out.println(qtd);
        local = new VBox();
        sair = new ImageView("/Imagem/setaSair.png");
        sair.setLayoutX(10);
        sair.setLayoutY(5);
        fundo = new ImageView("/Imagem/fundoScoreBoard.png");        
        visu.getChildren().addAll(fundo,sair);
        criarTitulo(visu);
        criarCategoria(visu);
        local.setPrefWidth(693);
        criarPlacar(local);
        preencherPlacar(tabela);
        ScrollPane scrollPane = new ScrollPane(local);
        scrollPane.setPrefHeight(459);
        scrollPane.setLayoutX(48);
        scrollPane.setLayoutY(190);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        visu.getChildren().add(scrollPane);
        btSair = new Media(this.getClass().getResource("/Som/Start.mp3").toExternalForm());
        buttomS = new MediaPlayer(btSair);
        
        loop = new AnimationTimer() {

            @Override
            public void handle(long now) {
                sair.setOnMouseClicked((MouseEvent e) -> {
                    buttomS.play();
                });
                scene.setOnKeyPressed((KeyEvent ke) -> {
                    if (ke.getCode() == KeyCode.ESCAPE) {
                        buttomS.play();
                    }
                });

                if (buttomS.getStatus() == MediaPlayer.Status.PLAYING) {   // TEMPO DE DURAÇÃO DE CADA SOM
                    double ct = buttomS.getCurrentTime().toMillis();
                    if (ct > 160) { // START
                        loop.stop();
                        buttomS.stop();
                        stage.close();
                        voltarMenu();
                    }
                }
            }
        };
        loop.start();
        stage.setTitle ("ScoreBoard");
        stage.setScene (scene);
        stage.show ();
    }

    private void voltarMenu() {
        Menu menu = new Menu(lista);
        menu.start(new Stage());
    }

    private void criarTitulo(Pane root) {
        scoreBoard = new Label();
        scoreBoard.setPrefSize(360, 103);
        scoreBoard.setLayoutX(208);
        scoreBoard.setLayoutY(51);
        scoreBoard.setText("ScoreBoard");
        scoreBoard.setAlignment(Pos.CENTER);
        scoreBoard.setFont(Font.font("Berlin Sans FB", 70));
        root.getChildren().add(scoreBoard);
    }

    private void criarCategoria(Pane root) {
        
        // ----------------------------------------------- CATEGORIA RANK
        rank = new Label();
        rank.setPrefSize(90, 36);
        rank.setLayoutX(48);
        rank.setLayoutY(157);
        rank.setText("Rank");
        rank.setAlignment(Pos.BASELINE_LEFT);
        rank.setFont(Font.font("Berlin Sans FB", 26));
        // ----------------------------------------------- CATEGORIA NOME
        nome = new Label();
        nome.setPrefSize(90, 36);
        nome.setLayoutX(142);
        nome.setLayoutY(157);
        nome.setText("Nome");
        nome.setAlignment(Pos.BASELINE_LEFT);
        nome.setFont(Font.font("Berlin Sans FB", 26));
        // ---------------------------------------------------- CATEGORIA PONTUACAO
        pontuacao = new Label();
        pontuacao.setPrefSize(231, 36);
        pontuacao.setLayoutX(414);
        pontuacao.setLayoutY(157);
        pontuacao.setText("Pontuação");
        pontuacao.setAlignment(Pos.BASELINE_LEFT);
        pontuacao.setFont(Font.font("Berlin Sans FB", 26));

        // ---------------------------------------------------- CATEGORIA PARTIDAS
        partidas = new Label();
        partidas.setPrefSize(102, 36);
        partidas.setLayoutX(645);
        partidas.setLayoutY(157);
        partidas.setText("Partidas");
        partidas.setAlignment(Pos.BASELINE_LEFT);
        partidas.setFont(Font.font("Berlin Sans FB", 26));
        
        root.getChildren().addAll(rank, nome, pontuacao, partidas);
    }

    private void criarPlacar(VBox caixa) { // PEGAR O QTD DA LISTA ENCADEADA E CRIAR O GRID PANE 
        tabela = new GridPane();
        tabela.setPrefSize(693, qtd * 51);
        
        int i;

        ColumnConstraints numero = new ColumnConstraints();
        ColumnConstraints nome = new ColumnConstraints();
        ColumnConstraints pontuacao = new ColumnConstraints();
        
        RowConstraints linha = new RowConstraints();
        numero.setHgrow(Priority.NEVER);
        numero.setMaxWidth(143);
        numero.setPrefWidth(143);
        
        pontuacao.setHgrow(Priority.NEVER);
        pontuacao.setMaxWidth(286);
        pontuacao.setPrefWidth(286);
        
        
        nome.setHgrow(Priority.NEVER);
        nome.setMaxWidth(320);
        nome.setPrefWidth(320);
        tabela.getColumnConstraints().addAll(numero, nome, pontuacao, numero);
        linha.setPrefHeight(51);
        linha.setVgrow(Priority.NEVER);
        linha.setMaxHeight(51);
        for (i = 0; i < qtd; i++) { // CRIAR A TABELA DE PONTUAÇÃO
            tabela.getRowConstraints().add(i, linha);
        }
        tabela.setGridLinesVisible(true);
        caixa.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        caixa.getChildren().add(tabela);
    }

    private void preencherPlacar(GridPane tabela) {

        int i; // LINHA
        int j; // COLUNA
        int valor;
        double number;
        Label dado;
        String num, nome;
        Color cor;
        NodeJogador aux = lista.getInicio();
        
        for (i = 0; aux!= null; i++) {            
            nome = aux.getData().getNome();
            
            for (j = 0; j < 5; j++) {

                if (j == 0) {                       // RANK
                    num = Integer.toString(i + 1);
                    dado = criarDado(num, 52, 92);
                    if (i == 0) {
                        cor = Color.GOLD;
                        dado.setTextFill(cor);
                    } else if (i == 1) {
                        cor = Color.SILVER;
                        dado.setTextFill(cor);
                    } else if (i == 2) {
                        cor = Color.BURLYWOOD;
                        dado.setTextFill(cor);
                    }else{
                        cor = Color.INDIGO;
                        dado.setTextFill(cor);
                    }
                    tabela.add(dado, j, i);

                } else if (j == 1) {                // NOME
                    cor = Color.DARKCYAN;
                    dado = criarDado(nome, 55, 317);
                    dado.setTextFill(cor);
                    tabela.add(dado, j, i);
                } else if (j == 2) {                // PONTUAÇAO 
                    cor = Color.BLACK;
                    number = aux.getData().getPontuacao();
                    valor = (int) number;
                    num = Integer.toString(valor);
                    dado = criarDado(num, 52, 231);
                    dado.setTextFill(cor);
                    tabela.add(dado, j, i);

                } else if (j == 3) {                             // NUMERO DE PARTIDAS
                    cor = Color.DARKMAGENTA;
                    number = aux.getData().getPartidaJogadas();
                    valor = (int) number;
                    num = Integer.toString(valor);
                    dado = criarDado(num, 52, 92);
                    dado.setTextFill(cor);
                    tabela.add(dado, j, i);
                } 
            }
            aux = aux.getNext();
        }

    }

    private Label criarDado(String dado, double altura, double comprimento) {

        Label inf = new Label(dado);       
        inf.setPrefSize(comprimento, altura);
        
        if (comprimento == 92 || comprimento == 231) { // FONTE DOS NUMERO
            inf.setFont(Font.font("Berlin Sans FB", 36));            
            inf.setAlignment(Pos.CENTER);
        }
        else {
            inf.setFont(Font.font("Bell MT", 36)); // FONTE DO NOME            
            inf.setAlignment(Pos.CENTER_LEFT);
        }
        return inf;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
