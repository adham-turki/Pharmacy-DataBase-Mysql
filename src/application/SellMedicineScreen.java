package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SellMedicineScreen extends Secondery {
	private ObservableList<Medicine> medicineList;
	Label messageLabel;

	public SellMedicineScreen() {
		// Clear the existing content
		getChildren().clear();
		medicineList = FXCollections.observableArrayList();

		// Create a grid pane for the input fields
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(20));

		Label idLabel = new Label("ID Number");
		idLabel.setFont(Font.font("Arial", 24));
		idLabel.setTextFill(Color.DARKBLUE);

		// Create Fields and labels
		TextField idField = new TextField();
		idField.setPrefHeight(40);

		// Create Fields and labels
		Label quantityLabel = new Label("Quantity");
		quantityLabel.setFont(Font.font("Arial", 24));
		quantityLabel.setTextFill(Color.DARKBLUE);

		// Create Fields and labels
		TextField quantityField = new TextField();
		quantityField.setPrefHeight(40);

		// Create Fields and labels
		messageLabel = new Label();
		messageLabel.setFont(Font.font("Arial", 28));

		gridPane.add(idLabel, 0, 1);
		gridPane.add(idField, 1, 1);
		gridPane.add(quantityLabel, 0, 2);
		gridPane.add(quantityField, 1, 2);
		gridPane.add(messageLabel, 1, 3);

		// Create a TableView to display SaleOrder objects
		TableView<Medicine> tableView = new TableView<>();
		tableView.setPrefWidth(500); // Set your preferred width

		TableColumn<Medicine, Integer> idColumn = new TableColumn<>("Id");
		idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMid()).asObject());
		idColumn.setMinWidth(100);

		TableColumn<Medicine, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMname()));
		nameColumn.setMinWidth(100);

		TableColumn<Medicine, Integer> quantityColumn = new TableColumn<>("Quantity");
		quantityColumn.setCellValueFactory(
				cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
		quantityColumn.setMinWidth(100);
		TableColumn<Medicine, Double> priceColumn = new TableColumn<>("Price");
		priceColumn
				.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
		priceColumn.setMinWidth(100);
		TableColumn<Medicine, Void> actionColumn = new TableColumn<>("Actions");
		actionColumn.setMinWidth(100);
		actionColumn.setSortable(false);

		actionColumn.setCellFactory(param -> new TableCell<>() {
			private final Button deleteButton = createButton("delete.png");

			private Button createButton(String iconPath) {
				Button button = new Button();
				try {
					// Load the image from the file path
					Image image = new Image(new FileInputStream(new File(iconPath)));
					ImageView imageView = new ImageView(image);

					// Set the size of the ImageView to make the icon smaller
					imageView.setFitWidth(26); // Adjust the width as needed
					imageView.setFitHeight(26); // Adjust the height as needed
					button.setGraphic(imageView);
					button.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				button.setOnAction(event -> {
					// Handle button action (delete or update)
					if (button == deleteButton) {
						// Implement delete action
						Medicine medicine = getTableView().getItems().get(getIndex());
						medicineList.remove(medicine);

						// Update total cost after deletion
						double totalCost = medicineList.stream().mapToDouble(Medicine::getPrice).sum();
						messageLabel.setText("Total Cost: " + totalCost);
					}
				});
				return button;
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);

				if (empty) {
					setGraphic(null);
				} else {
					// Set the buttons in the cell
					setGraphic(new HBox(5, deleteButton));
					setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
				}
			}
		});

		// Add columns to the TableView
		tableView.getColumns().addAll(idColumn, nameColumn, quantityColumn, priceColumn, actionColumn);

		// Create a back button
		Button backButton = createStyledButton("Back");
		backButton.setOnAction(e -> {
			getChildren().clear();
			setCenter(new Secondery());
		});

		// Create a back button
		Button finishButton = createStyledButton("Finish Order");
		finishButton.setOnAction(e -> {
			ArrayList<Medicine> list = new ArrayList<>();
			list.addAll(medicineList);
						double totalCost = medicineList.stream()
								.mapToDouble(medicine -> medicine.getPrice() * medicine.getQuantity()).sum();
						String formattedTotalCost = String.format("%.2f", totalCost);

						try {
							DataBaseServices.addSaleOrder(Double.parseDouble(formattedTotalCost), list);
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			// Clear the medicineList after finishing the order
			medicineList.clear();

			tableView.getItems().clear(); // Clear the items in the table view

			// Calculate total cost and update the label
			double clearCost = medicineList.stream()
					.mapToDouble(medicine -> medicine.getPrice() * medicine.getQuantity()).sum();
			messageLabel.setText("Total Cost: " + clearCost);
		});

		Button addButton = createStyledButton("Add to Cart");
		addButton.setOnAction(e -> {
			// Validate user input
			if (validateInput(idField, quantityField)) {
				// Parse user input
				int id = Integer.parseInt(idField.getText());
				int quantity = Integer.parseInt(quantityField.getText());
				boolean exist = false;
				try {
					exist = DataBaseServices.sallMedicine(id, quantity);
				} catch (ClassNotFoundException | SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				if (exist) {
					try {

						Medicine newMedicine = DataBaseServices.getMedicineById(id);
						newMedicine.setQuantity(quantity);

						// Calculate the total price
						double totalPrice = newMedicine.getPrice() * quantity;

						// Add the new Medicine object to the medicineList
						medicineList.add(newMedicine);

						// Calculate total cost and update the label
						double totalCost = medicineList.stream()
								.mapToDouble(medicine -> medicine.getPrice() * medicine.getQuantity()).sum();
						messageLabel.setText("Total Cost: " + totalCost);
						// Clear input fields
						idField.clear();
						quantityField.clear();

					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					messageLabel.setText("does not exist");

				}
			}

		});
		// Set the table view data source to the medicineList
		tableView.setItems(medicineList);

		// Create an HBox to hold the buttons
		HBox buttonBox = new HBox(10);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.getChildren().addAll(addButton, backButton,finishButton);

		// Create a title text
		Text titleText = new Text("Sell Medicines");
		titleText.setFont(Font.font("Arial", 38));
		titleText.setFill(Color.GOLD);

		VBox titleBox = new VBox(20);
		titleBox.setAlignment(Pos.CENTER);
		titleBox.getChildren().addAll(titleText);

		// Create a vertical box to hold the title, grid pane, and buttons
		VBox contentBox = new VBox(20);
		contentBox.setAlignment(Pos.CENTER);
		contentBox.getChildren().addAll(gridPane, buttonBox, messageLabel);
		setTop(titleBox);
		setLeft(contentBox);
		setRight(tableView);

	}

	// Method to validate user input
	private boolean validateInput(TextField... fields) {
		for (TextField field : fields) {
			if (field.getText().isEmpty()) {
				messageLabel.setText("Please fill in all fields.");
				return false;
			}
		}
		return true;
	}
}
