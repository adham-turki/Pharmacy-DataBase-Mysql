package application;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
	static Stage stage;
	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		root.setBackground(getBackground1());

		// Create a vertical box to hold the main content
		VBox contentBox = new VBox(20);
		contentBox.setAlignment(Pos.CENTER);

		HBox buttonBox = new HBox(20);
		buttonBox.setAlignment(Pos.CENTER);

		// Add a title text
		Text titleText = new Text("Lets start 😉");
		titleText.setFont(Font.font("Arial", 48));
		titleText.setFill(Color.GOLD);
		//create a start button
		Button startButton = createStyledButton("Start");
		//action for the start button
		startButton.setOnAction(e -> {
			root.setCenter(new Login());
		});
		//make the width for the style button
		startButton.setPrefWidth(200);

		buttonBox.getChildren().addAll(startButton);
		contentBox.getChildren().addAll(titleText, buttonBox);

		root.setCenter(contentBox);

		// Create the scene
		Scene scene = new Scene(root, 1100, 700);
		scene.setFill(Color.LIGHTGRAY); // Set the scene background color
		primaryStage.setScene(scene);
		stage=primaryStage;
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);

	}

	private Button createStyledButton(String text) {
		Button button = new Button(text);
		button.setStyle(
				"-fx-background-color: #009688; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 18px;");

		// Hover effect to change button color to black
		button.setOnMouseEntered(e -> {
			button.setStyle(
					"-fx-background-color: black; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 18px;");
		});
		button.setOnMouseExited(e -> {
			button.setStyle(
					"-fx-background-color: #009688; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 18px;");

		});

		return button;
	}

	// Method to create the background with a color
	public Background getBackground1() {
		BackgroundFill backgroundFill = new BackgroundFill(Color.DARKSLATEBLUE, CornerRadii.EMPTY, Insets.EMPTY);
		return new Background(backgroundFill);
	}

}
