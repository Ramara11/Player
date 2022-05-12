package com.example.javafxplayer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane pane = new BorderPane();
        VBox root = new VBox();
        String style = getClass().getResource("style.css").toExternalForm();

        String src = getClass().getResource("vid_1.mp4").toExternalForm();
        Media media = new Media(src);
        MediaPlayer player = new MediaPlayer(media);
        MediaView mediaView = new MediaView();
        mediaView.setMediaPlayer(player);
        mediaView.setFitHeight(600);
        mediaView.setPreserveRatio(true);

        ImageView image= new ImageView(getClass().getResource("play.png").toExternalForm());
        Label play = new Label();
        image.setFitHeight(25);
        image.setPreserveRatio(true);
        play.setGraphic(image);

        ImageView image2= new ImageView(getClass().getResource("stop.png").toExternalForm());
        Label stop = new Label();
        image2.setFitHeight(25);
        image2.setPreserveRatio(true);
        stop.setGraphic(image2);

        ImageView image3= new ImageView(getClass().getResource("pause-button.png").toExternalForm());
        Label pause = new Label();
        image3.setFitHeight(25);
        image3.setPreserveRatio(true);
        pause.setGraphic(image3);

        /* creating vollume slider */
        Slider slider= new Slider();
        slider.setMaxWidth(Region.USE_PREF_SIZE);
        player.volumeProperty().bind(slider.valueProperty().divide(100));
        slider.valueProperty().setValue(50);
        Label label= new Label("volume");
        slider.setId("exit");

        HBox hBox = new HBox(30, play,pause, stop,slider,label);
        hBox.setId("player-controls");
        hBox.setAlignment(Pos.CENTER);

        Slider timer= new Slider();
        timer.setPrefWidth(700);
        timer.setPrefHeight(20);
        timer.setId("bar");

        Label timelabel= new Label();

        //timelabel.textProperty().bind(Bindings.createStringBinding()->{

       // });
        player.setOnReady(new Runnable() {
            @Override
            public void run() {
                timer.setMax(player.getTotalDuration().toSeconds());
            }
        });
        player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                timer.setValue(t1.toSeconds());
            }
        });
        timer.setOnMouseClicked(value->{
            player.seek(Duration.seconds(timer.getValue()));
        });
        HBox time= new HBox(timer);
        time.setAlignment(Pos.CENTER);

        play.setOnMouseClicked(event -> {
            player.play();
        });
        pause.setOnMouseClicked(event->{
            player.pause();
        });
        stop.setOnMouseClicked(event -> {
            player.stop();
        });
        pane.setBottom(hBox);
        root.getChildren().addAll(mediaView,pane,time);

        Scene scene = new Scene(root, 1075, 720);
        scene.getStylesheets().add(style);
        stage.setTitle("Multimedia Player");

        stage.setScene(scene);
        stage.setMaxWidth(1090);
        stage.setMaxHeight(750);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}