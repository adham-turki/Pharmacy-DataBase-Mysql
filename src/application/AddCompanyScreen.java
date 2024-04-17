package application;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AddCompanyScreen extends Secondery {
	public AddCompanyScreen() {
		// Clear the existing content
		getChildren().clear();
		// Create a grid pane for the input fields
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(20));
		// Create Fields and labels
		Label idLabel = new Label("ID Number");
		idLabel.setFont(Font.font("Arial", 24));
		idLabel.setTextFill(Color.DARKBLUE);

		// Create Fields and labels
		TextField idField = new TextField();
		idField.setPrefHeight(40);

		// Create Fields and labels
		Label nameLabel = new Label("Company Name");
		nameLabel.setFont(Font.font("Arial", 24));
		nameLabel.setTextFill(Color.DARKBLUE);

		// Create Fields and labels
		TextField nameField = new TextField();
		nameField.setPrefHeight(40);

		// Create Fields and labels
		Label locationLabel = new Label("Location");
		locationLabel.setFont(Font.font("Arial", 24));
		locationLabel.setTextFill(Color.DARKBLUE);

		// Create Fields and labels
		TextField locationField = new TextField();
		locationField.setPrefHeight(40);

		// Create Fields and labels
		Label dateLabel = new Label("Establish Date");
		dateLabel.setFont(Font.font("Arial", 24));
		dateLabel.setTextFill(Color.DARKBLUE);

		// Create Fields and labels
		TextField dateField = new TextField();
		dateField.setPrefHeight(40);

		// Create Fields and labels
		Label messageLabel = new Label();
		messageLabel.setFont(Font.font("Arial", 28));

		gridPane.add(idLabel, 0, 2);
		gridPane.add(idField, 1, 2);
		gridPane.add(nameLabel, 0, 3);
		gridPane.add(nameField, 1, 3);
		gridPane.add(locationLabel, 0, 4);
		gridPane.add(locationField, 1, 4);
		gridPane.add(dateLabel, 0, 5);
		gridPane.add(dateField, 1, 5);
		gridPane.add(messageLabel, 1, 6);


		// Create a back button
		Button backButton = createStyledButton("Back");
		backButton.setOnAction(e -> {
			setCenter(new CompanyScreen());

		});

		Button insertButton = createStyledButton("Insert");
		
		insertButton.setOnAction(e -> {
		    // Create an Employee object with the updated information
		    Company company = new Company(
		            Integer.parseInt(idField.getText()),
		            nameField.getText(),
		            locationField.getText(),
		            dateField.getText()
		    );

		    // Call the editEmployee method to update the employee in the database
		    try {
		        DataBaseServices.addCompanyInDatabase(company);
		        messageLabel.setText("Employee Added successfully");
		        messageLabel.setTextFill(Color.GREEN);
		    } catch (SQLException | ClassNotFoundException ex) {
		        ex.printStackTrace();
		        messageLabel.setText("Error Adding employee");
		        messageLabel.setTextFill(Color.RED);
		    }
		});


		// Create an HBox to hold the buttons
		HBox buttonBox = new HBox(10);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.getChildren().addAll(insertButton, backButton);

		// Create a title text
		Text titleText = new Text("Update Medicine");
		titleText.setFont(Font.font("Arial", 38));
		titleText.setFill(Color.DARKBLUE);

		// Create a vertical box to hold the title, grid pane, and buttons
		VBox contentBox = new VBox(20);
		contentBox.setAlignment(Pos.CENTER);
		contentBox.getChildren().addAll(titleText, gridPane, buttonBox, messageLabel);

		setCenter(contentBox);

	}
}
