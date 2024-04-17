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

public class AddMedicineScreen extends Secondery {
	private ObservableList<Medicine> medicineList;
	Label messageLabel;

	public AddMedicineScreen() {
		// Clear the existing content
		getChildren().clear();

		medicineList = FXCollections.observableArrayList();

		// Create a grid pane for the input fields
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(20));

		// ComboBox for selecting the time option (Day, Month, Year, All data)
		ComboBox<String> CompanycomboBox = new ComboBox<>();
		// Set options and styles for the ComboBox
		CompanycomboBox.setItems(FXCollections.observableArrayList());
		CompanycomboBox.setPromptText("Company ID");
		CompanycomboBox.setStyle(
				"-fx-font-family: Arial; -fx-font-size: 14px; -fx-background-color: #D2E4F6; -fx-border-color: #A6A6A6; -fx-border-radius: 5; -fx-padding: 5px;"
						+ "-fx-effect: innershadow(three-pass-box, rgba(0,0,0,0.3), 2, 0.0, 0, 1);");
		ArrayList<Company> array = new ArrayList<>();
		try {
			array = DataBaseServices.getAllCompany();
		} catch (ClassNotFoundException | SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		for (Company company : array) {
			CompanycomboBox.getItems().add(company.getCompid()+"");
		}



		// Create Fields and labels
		Label idLabel = new Label("ID Number");
		idLabel.setFont(Font.font("Arial", 24));
		idLabel.setTextFill(Color.DARKBLUE);

		// Create Fields and labels
		TextField idField = new TextField();
		idField.setPrefHeight(40);

		// Create Fields and labels
		Label nameLabel = new Label("Medicine Name");
		nameLabel.setFont(Font.font("Arial", 24));
		nameLabel.setTextFill(Color.DARKBLUE);

		// Create Fields and labels
		TextField nameField = new TextField();
		nameField.setPrefHeight(40);

		// Create Fields and labels
		Label packageLabel = new Label("Package");
		packageLabel.setFont(Font.font("Arial", 24));
		packageLabel.setTextFill(Color.DARKBLUE);

		// Create Fields and labels
		TextField packageField = new TextField();
		packageField.setPrefHeight(40);

		// Create Fields and labels
		Label quantityLabel = new Label("Quantity");
		quantityLabel.setFont(Font.font("Arial", 24));
		quantityLabel.setTextFill(Color.DARKBLUE);

		// Create Fields and labels
		TextField quantityField = new TextField();
		quantityField.setPrefHeight(40);

		// Create Fields and labels
		Label dosageLabel = new Label("Dosage Form");
		dosageLabel.setFont(Font.font("Arial", 24));
		dosageLabel.setTextFill(Color.DARKBLUE);

		// Create Fields and labels
		TextField dosageField = new TextField();
		dosageField.setPrefHeight(40);

		// Create Fields and labels
		Label priceLabel = new Label("Price");
		priceLabel.setFont(Font.font("Arial", 24));
		priceLabel.setTextFill(Color.DARKBLUE);

		// Create Fields and labels
		TextField priceField = new TextField();
		priceField.setPrefHeight(40);

		// Create Fields and labels
		Label discriptionLabel = new Label("Discription");
		discriptionLabel.setFont(Font.font("Arial", 24));
		discriptionLabel.setTextFill(Color.DARKBLUE);

		// Create Fields and labels
		TextField discriptionField = new TextField();
		discriptionField.setPrefHeight(40);

		// Create Fields and labels
		messageLabel = new Label();
		messageLabel.setFont(Font.font("Arial", 28));

		gridPane.add(CompanycomboBox, 0, 1);
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
		priceColumn.setCellValueFactory(cellData -> {
			Medicine medicine = cellData.getValue();
			double totalPrice = medicine.getPrice() * medicine.getQuantity();
			return new SimpleDoubleProperty(totalPrice).asObject();
		});
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
			try {
				DataBaseServices.addOrderFromCompany(list);
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// Clear the medicineList after finishing the order
			medicineList.clear();

			tableView.getItems().clear(); // Clear the items in the table view

			// Calculate total cost and update the label
			double totalCost = medicineList.stream()
					.mapToDouble(medicine -> medicine.getPrice() * medicine.getQuantity()).sum();
			messageLabel.setText("Total Cost: " + totalCost);
		});

		Button InsertButton = createStyledButton("Insert");
		InsertButton.setOnAction(e -> {
			try {

				// Validate user input
				if (validateInput(idField, nameField, packageField, quantityField, dosageField, priceField,
						discriptionField)) {
					// Parse user input
					int id = Integer.parseInt(idField.getText());
					String name = nameField.getText();
					String pack = packageField.getText();
					int quantity = Integer.parseInt(quantityField.getText());
					String dosageForm = dosageField.getText();
					double price = Double.parseDouble(priceField.getText());
					String description = discriptionField.getText();
					int compId = Integer.parseInt(CompanycomboBox.getValue());

					// Calculate the total price
					double totalPrice = price * quantity;

					// Create a Medicine object with user input
					Medicine newMedicine = new Medicine(id, name, pack, quantity, description, dosageForm, price,
							compId);

					// Add the new Medicine object to the medicineList
					medicineList.add(newMedicine);

					// Calculate total cost and update the label
					double totalCost = medicineList.stream()
							.mapToDouble(medicine -> medicine.getPrice() * medicine.getQuantity()).sum();
					messageLabel.setText("Total Cost: " + totalCost);
					// Clear input fields
					idField.clear();
					nameField.clear();
					packageField.clear();
					quantityField.clear();
					dosageField.clear();
					priceField.clear();
					discriptionField.clear();
				}
			} catch (Exception e2) {
				messageLabel.setText("please Check the fields ");
			}

		});
		// Set the table view data source to the medicineList
		tableView.setItems(medicineList);

		// Create an HBox to hold the buttons
		HBox buttonBox = new HBox(10);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.getChildren().addAll(InsertButton, backButton, finishButton);

		// Create a title text
		Text titleText = new Text("Add Medicine From Company");
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
