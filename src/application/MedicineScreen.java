package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MedicineScreen extends Secondery {
	private ObservableList<Medicine> medicineList;
	private TableView<Medicine> tableView;

	public MedicineScreen() {
		// Clear the existing content
		getChildren().clear();

		medicineList = FXCollections.observableArrayList(); // Create the observable list for martyrs

		// Create a grid pane for the search field
		GridPane searchPane = new GridPane();
		searchPane.setAlignment(Pos.CENTER);
		searchPane.setHgap(10);
		searchPane.setVgap(10);
		searchPane.setPadding(new Insets(20));

		// Create a label and text field for the search
		Label searchLabel = new Label("Medicine name");
		searchLabel.setFont(Font.font("Arial", 20));
		searchLabel.setTextFill(Color.DARKBLUE);
		TextField searchField = new TextField();
		searchField.setPromptText("Search");

		// Add the label and text field to the search pane
		searchPane.add(searchLabel, 0, 0);
		searchPane.add(searchField, 1, 0);

		// Create a table view for displaying the search results
		tableView = new TableView<>();
		TableColumn<Medicine, Integer> idColumn = new TableColumn<>("Id");
		idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMid()).asObject());
		idColumn.setMinWidth(100);

		TableColumn<Medicine, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMname()));
		nameColumn.setMinWidth(100);

		TableColumn<Medicine, String> packagerColumn = new TableColumn<>("Package");
		packagerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMpackage()));
		packagerColumn.setMinWidth(100);

		TableColumn<Medicine, Integer> quantityColumn = new TableColumn<>("Quantity");
		quantityColumn.setCellValueFactory(
				cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
		quantityColumn.setMinWidth(100);

		TableColumn<Medicine, String> dosageColumn = new TableColumn<>("Dosage Form");
		dosageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDosage_form()));
		dosageColumn.setMinWidth(100);

		TableColumn<Medicine, Double> priceColumn = new TableColumn<>("Price");
		priceColumn
				.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
		priceColumn.setMinWidth(100);

		TableColumn<Medicine, String> discriptionColumn = new TableColumn<>("Discription");
		discriptionColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
		discriptionColumn.setMinWidth(400);

		TableColumn<Medicine, Void> actionColumn = new TableColumn<>("Actions");
		actionColumn.setMinWidth(100);
		actionColumn.setSortable(false);

		actionColumn.setCellFactory(param -> new TableCell<>() {
			private final Button deleteButton = createButton("delete.png");
			private final Button updateButton = createButton("update.png");

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
						try {
							DataBaseServices.deleteMedicine(medicine.getMid());
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							DataBaseServices.deleteMedicine(medicine.getMid());
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (button == updateButton) {
						// Implement update action
						Medicine medicine = getTableView().getItems().get(getIndex());
						getChildren().clear();
						setCenter(new UpdateMedicineScreen(medicine, "medicine"));
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
					setGraphic(new HBox(5, deleteButton, updateButton));
					setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
				}
			}
		});
		tableView.setStyle("-fx-border-width: 2px; -fx-border-color: #ccc; -fx-background-color: white;");
		idColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;");
		nameColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;");
		packagerColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;"); 
		quantityColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;");
		dosageColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;");
		priceColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;");
		discriptionColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;");
		actionColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;");
		

		tableView.getColumns().addAll(idColumn, nameColumn, packagerColumn, quantityColumn, dosageColumn, priceColumn,
				discriptionColumn, actionColumn);

		// Retrieve all medicines from the database
		try {
			List<Medicine> allMedicines = DataBaseServices.getAllMedicineFromPharmacy();
			medicineList.addAll(allMedicines);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace(); // Handle the exception appropriately
		}

		// Add event handler to the search field
		searchField.setOnKeyReleased(event -> {
			String searchText = searchField.getText().toLowerCase(); // Convert to lowercase for case-insensitive search

			if (searchText.isEmpty()) {
				// If the search text is empty, show all medicines
				tableView.setItems(medicineList);
			} else {
				// Filter medicines based on the search text
				ObservableList<Medicine> filteredList = medicineList
						.filtered(medicine -> medicine.getMname().toLowerCase().contains(searchText)
								|| medicine.getMpackage().toLowerCase().contains(searchText)
								|| medicine.getDosage_form().toLowerCase().contains(searchText));

				tableView.setItems(filteredList);
			}
		});

		// Create a back button
		Button backButton = createStyledButton("Back");
		backButton.setOnAction(e -> {
			setCenter(new Secondery());
		});

		// Create a title text
		Text titleText = new Text("Search for a Medicine");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		titleText.setFill(Color.GOLD);

		// Create a vertical box to hold the title, search pane, and table view
		VBox contentBox = new VBox(20);
		contentBox.setAlignment(Pos.CENTER);
		contentBox.getChildren().addAll(titleText, searchPane, tableView, backButton);

		setCenter(contentBox);

		// Set the table view data source to the martyrs list
		tableView.setItems(medicineList);
	}

	public MedicineScreen(int oid, String text, String page) {
		// Clear the existing content
		getChildren().clear();

		medicineList = FXCollections.observableArrayList(); // Create the observable list for martyrs

		// Create a grid pane for the search field
		GridPane searchPane = new GridPane();
		searchPane.setAlignment(Pos.CENTER);
		searchPane.setHgap(10);
		searchPane.setVgap(10);
		searchPane.setPadding(new Insets(20));

		// Create a label and text field for the search
		Label searchLabel = new Label("Medicine name");
		searchLabel.setFont(Font.font("Arial", 20));
		searchLabel.setTextFill(Color.DARKBLUE);
		TextField searchField = new TextField();
		searchField.setPromptText("Search");

		// Add the label and text field to the search pane
		searchPane.add(searchLabel, 0, 0);
		searchPane.add(searchField, 1, 0);

		// Create a table view for displaying the search results
		tableView = new TableView<>();
		tableView.setStyle("-fx-border-width: 2px; -fx-border-color: #ccc; -fx-background-color: white;");
		TableColumn<Medicine, Integer> idColumn = new TableColumn<>("Id");
		idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMid()).asObject());
		idColumn.setMinWidth(100);

		TableColumn<Medicine, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMname()));
		nameColumn.setMinWidth(100);

		TableColumn<Medicine, String> packagerColumn = new TableColumn<>("Package");
		packagerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMpackage()));
		packagerColumn.setMinWidth(100);

		TableColumn<Medicine, Integer> quantityColumn = new TableColumn<>("Quantity");
		quantityColumn.setCellValueFactory(
				cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
		quantityColumn.setMinWidth(100);

		TableColumn<Medicine, String> dosageColumn = new TableColumn<>("Dosage Form");
		dosageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDosage_form()));
		dosageColumn.setMinWidth(100);

		TableColumn<Medicine, Double> priceColumn = new TableColumn<>("Price");
		priceColumn
				.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
		priceColumn.setMinWidth(100);

		TableColumn<Medicine, String> discriptionColumn = new TableColumn<>("Discription");
		discriptionColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
		discriptionColumn.setMinWidth(500);
		
		idColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-text-fill: #2E8B57;-fx-font-style: italic;");
		nameColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-text-fill: #2E8B57;-fx-font-style: italic;");
		packagerColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-text-fill: #2E8B57;-fx-font-style: italic;"); 
		quantityColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-text-fill: #2E8B57;-fx-font-style: italic;");
		dosageColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-text-fill: #2E8B57;-fx-font-style: italic;");
		priceColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-text-fill: #2E8B57;-fx-font-style: italic;");
		discriptionColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-text-fill: #2E8B57;-fx-font-style: italic;");

		
		tableView.getColumns().addAll(idColumn, nameColumn, packagerColumn, quantityColumn, dosageColumn, priceColumn,
				discriptionColumn);

		if (page.equals("companyOrder")) {
			// Retrieve all medicines from the database
			try {
				List<Medicine> allMedicines = DataBaseServices.getMedicinesInOrder(oid);
				medicineList.addAll(allMedicines);
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace(); // Handle the exception appropriately
			}

		} else {
			// Retrieve all medicines from the database
			try {
				List<Medicine> allMedicines = DataBaseServices.getMedicinesInSaleOrder(oid);
				medicineList.addAll(allMedicines);
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace(); // Handle the exception appropriately
			}

		}

		// Set the table view data source to the martyrs list
		tableView.setItems(medicineList);

		// Create a back button
		Button backButton = createStyledButton("Back");
		backButton.setOnAction(e -> {
			if (page.equals("companyOrder"))
				setCenter(new CompanyOrderScreen());
			else
				setCenter(new SaleOrderScreen());
		});

		// Create a title text
		Text titleText = new Text(text);
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		titleText.setFill(Color.GOLD);

		// Create a vertical box to hold the title, search pane, and table view
		VBox contentBox = new VBox(20);
		contentBox.setAlignment(Pos.CENTER);
		contentBox.getChildren().addAll(titleText, searchPane, tableView, backButton);

		setCenter(contentBox);

	}

}
