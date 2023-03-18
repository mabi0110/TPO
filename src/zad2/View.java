package zad2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class View extends Application {
    private String[] userInputData;
    private Service service;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("App");
        stage.setWidth(600);
        stage.setHeight(600);

        BorderPane borderPane = new BorderPane();
        HBox hBox = new HBox();
        VBox vBoxWeather = new VBox();
        VBox vBoxCurrency = new VBox();
        VBox vBoxPlnRate = new VBox();
        VBox vBoxChangeData = new VBox();

        Label changeDataLabel = new Label("Change data");
        TextField changeDataTextField = new TextField("Warsaw,Poland,USD");
        vBoxChangeData.getChildren().addAll(changeDataLabel, changeDataTextField);

        Label weatherLabel = new Label("Weather");
        TextArea weatherTextArea = new TextArea(new Service("Poland").getWeather("Warsaw"));
        weatherTextArea.setMaxHeight(100);
        weatherTextArea.setMaxWidth(150);
        vBoxWeather.getChildren().addAll(weatherLabel, weatherTextArea);

        Label currencyLabel = new Label("Currency rate");
        TextArea currencyTextArea = new TextArea(new Service("Poland").getRateFor("USD").toString());
        currencyTextArea.setMaxHeight(100);
        currencyTextArea.setMaxWidth(150);
        vBoxCurrency.getChildren().addAll(currencyLabel, currencyTextArea);

        Label plnRateLabel = new Label("PLN rate");
        TextArea plnRateTextArea = new TextArea("//TODO");
        plnRateTextArea.setMaxHeight(100);
        plnRateTextArea.setMaxWidth(150);
        vBoxPlnRate.getChildren().addAll(plnRateLabel, plnRateTextArea);

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load("https://en.wikipedia.org/wiki/"+"Warsaw");

        changeDataTextField.setOnAction(event -> {
            userInputData = changeDataTextField.getText().split(",");
            String city = userInputData[0];
            String country = userInputData[1];
            String curr = userInputData[2];
            service = new Service(country);
            webEngine.load("https://en.wikipedia.org/wiki/"+city);
            weatherTextArea.setText(service.getWeather(city));
            currencyTextArea.setText(service.getRateFor(curr).toString());
        });

        hBox.getChildren().addAll(vBoxWeather, vBoxCurrency, vBoxPlnRate, vBoxChangeData);
        borderPane.setTop(hBox);
        borderPane.setCenter(webView);

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }
}

