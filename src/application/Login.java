package application;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends BorderPane {
	static Stage stage;

	public Login(){
		BorderPane root = new BorderPane();
		root.setBackground(getBackground1());

		// Create a vertical box to hold the main content
		VBox contentBox = new VBox(20);
		contentBox.setAlignment(Pos.CENTER);

		// Add a title text
		Text titleText = new Text("Login");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 50));
		titleText.setFill(Color.GOLD);

		// Create Fields and labels
		Label messageLabel = new Label();
		messageLabel.setFont(Font.font("Arial", 28));

		// Create text fields for username and password
		TextField usernameField = new TextField();
		usernameField.setPromptText("Username");
		usernameField.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 12px; ");

		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Password");
		passwordField.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 12px; ");

		VBox inputBox = new VBox(10);
		inputBox.setAlignment(Pos.CENTER);
		inputBox.getChildren().addAll(usernameField, passwordField);
		inputBox.setMaxWidth(200);

		// Create a login button
		Button loginButton = createStyledButton("Login");

		// Action for the login button
		loginButton.setOnAction(e -> {
			String name = usernameField.getText();
			try {
				int pass = Integer.parseInt(passwordField.getText());
				boolean isEmployee = DataBaseServices.getEmployeeByIdandName(pass, name);

				if (isEmployee) {
					DataBaseServices.setEmployeeActive(pass);
					setCenter(new Secondery());
				} else {
					messageLabel.setText("This account is not exist");
			        messageLabel.setTextFill(Color.RED);
				}
			} catch (NumberFormatException ex) {
				messageLabel.setText("This account is not exist");
		        messageLabel.setTextFill(Color.RED);
			} catch (ClassNotFoundException | SQLException ex) {
				ex.printStackTrace();
			}
		});

		contentBox.getChildren().addAll(titleText, inputBox, loginButton,messageLabel);
		contentBox.setPadding(new Insets(50));

		setCenter(contentBox);

		
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
