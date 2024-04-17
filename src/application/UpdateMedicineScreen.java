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

public class UpdateMedicineScreen extends Secondery {
	public UpdateMedicineScreen(Medicine medicine, String page) {
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
		idField.setText(medicine.getMid()+"");
		idField.setEditable(false);

		// Create Fields and labels
		Label nameLabel = new Label("Medicine Name");
		nameLabel.setFont(Font.font("Arial", 24));
		nameLabel.setTextFill(Color.DARKBLUE);

		// Create Fields and labels
		TextField nameField = new TextField();
		nameField.setPrefHeight(40);
		nameField.setText(medicine.getMname());
		nameField.setEditable(false);

		// Create Fields and labels
		Label packageLabel = new Label("Package");
		packageLabel.setFont(Font.font("Arial", 24));
		packageLabel.setTextFill(Color.DARKBLUE);

		// Create Fields and labels
		TextField packageField = new TextField();
		packageField.setPrefHeight(40);
		packageField.setText(medicine.getMpackage());
		packageField.setEditable(false);

		// Create Fields and labels
		Label quantityLabel = new Label("Quantity");
		quantityLabel.setFont(Font.font("Arial", 24));
		quantityLabel.setTextFill(Color.DARKBLUE);

		// Create Fields and labels
		TextField quantityField = new TextField();
		quantityField.setPrefHeight(40);
		quantityField.setText(medicine.getQuantity()+"");

		// Create Fields and labels
		Label dosageLabel = new Label("Dosage Form");
		dosageLabel.setFont(Font.font("Arial", 24));
		dosageLabel.setTextFill(Color.DARKBLUE);

		// Create Fields and labels
		TextField dosageField = new TextField();
		dosageField.setPrefHeight(40);
		dosageField.setText(medicine.getDosage_form());
		dosageField.setEditable(false);

		// Create Fields and labels
		Label priceLabel = new Label("Price");
		priceLabel.setFont(Font.font("Arial", 24));
		priceLabel.setTextFill(Color.DARKBLUE);

		// Create Fields and labels
		TextField priceField = new TextField();
		priceField.setPrefHeight(40);
		priceField.setText(medicine.getPrice()+"");

		// Create Fields and labels
		Label discriptionLabel = new Label("Discription");
		discriptionLabel.setFont(Font.font("Arial", 24));
		discriptionLabel.setTextFill(Color.DARKBLUE);

		// Create Fields and labels
		TextField discriptionField = new TextField();
		discriptionField.setPrefHeight(40);
		discriptionField.setText(medicine.getDescription());

		// Create Fields and labels
		Label messageLabel = new Label();
		messageLabel.setFont(Font.font("Arial", 28));

		gridPane.add(idLabel, 0, 2);
		gridPane.add(idField, 1, 2);
		gridPane.add(nameLabel, 0, 3);
		gridPane.add(nameField, 1, 3);
		gridPane.add(packageLabel, 0, 4);
		gridPane.add(packageField, 1, 4);
		gridPane.add(quantityLabel, 0, 5);
		gridPane.add(quantityField, 1, 5);
		gridPane.add(dosageLabel, 0, 6);
		gridPane.add(dosageField, 1, 6);
		gridPane.add(priceLabel, 0, 7);
		gridPane.add(priceField, 1, 7);
		gridPane.add(discriptionLabel, 0, 8);
		gridPane.add(discriptionField, 1, 8);
		gridPane.add(messageLabel, 1, 9);
		

		

		// Create a back button
		Button backButton = createStyledButton("Back");
		backButton.setOnAction(e -> {
			if (page.equals("medicine"))
				setCenter(new MedicineScreen());
			else if (page.equals("saleOrder"))
				setCenter(new SaleOrderScreen());
			else
				setCenter(new CompanyOrderScreen());

		});

		Button updateButton = createStyledButton("Update");
		updateButton.setOnAction(e -> {
		    // Create a Medicine object with the updated information
		    Medicine updatedMedicine = new Medicine(
		        Integer.parseInt(idField.getText()),
		        nameField.getText(),
		        packageField.getText(),
		        Integer.parseInt(quantityField.getText()),
		        discriptionField.getText(),
		        dosageField.getText(),
		        Double.parseDouble(priceField.getText()),
		        // Get the CompId from the existing Medicine object
		        medicine.getCompid()
		    );

		    try {
		        // Call the editMedicine method to update the record in the database
		        DataBaseServices.editMedicine(updatedMedicine);

		        // Display a success message or perform any other necessary actions
		        messageLabel.setText("Medicine updated successfully!");
		        messageLabel.setTextFill(Color.GREEN);
		    } catch (SQLException | ClassNotFoundException ex) {
		        ex.printStackTrace(); // Handle the exception appropriately
		        messageLabel.setText("Failed to update medicine.");
		        messageLabel.setTextFill(Color.RED);
		    }
		});

		// Create an HBox to hold the buttons
		HBox buttonBox = new HBox(10);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.getChildren().addAll(updateButton, backButton);

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
