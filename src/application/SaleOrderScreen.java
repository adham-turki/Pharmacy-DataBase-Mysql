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
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
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

public class SaleOrderScreen extends Secondery {
	private ObservableList<SaleOrder> orderList;
	private TableView<SaleOrder> tableView;
    private DatePicker datePicker;


	public SaleOrderScreen() {
		// Clear the existing content
		getChildren().clear();

		orderList = FXCollections.observableArrayList(); // Create the observable list for martyrs

		

		
        datePicker = new DatePicker();
        datePicker.setPromptText("Select Date");



		// Create a table view for displaying the search results
		tableView = new TableView<>();
		tableView.setMaxWidth(BASELINE_OFFSET_SAME_AS_HEIGHT);
		// Create TableColumn for each property in SaleOrder
		TableColumn<SaleOrder, Integer> oidColumn = new TableColumn<>("Order ID");
		oidColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getOid()).asObject());

		TableColumn<SaleOrder, java.sql.Date> odateColumn = new TableColumn<>("Order Date");
		odateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getOdate()));
		odateColumn.setMinWidth(150);

		TableColumn<SaleOrder, Double> priceColumn = new TableColumn<>("Price");
		priceColumn
				.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
		priceColumn.setMinWidth(100);

		TableColumn<SaleOrder, Integer> eidColumn = new TableColumn<>("Employee ID");
		eidColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEid()).asObject());

		TableColumn<SaleOrder, Void> actionColumn = new TableColumn<>("Actions");
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
						SaleOrder order = getTableView().getItems().get(getIndex());
						orderList.remove(order);
						try {
							DataBaseServices.deleteSaleOrder(order.getOid());
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (button == updateButton) {
						// Implement update action
						SaleOrder order = getTableView().getItems().get(getIndex());
						getChildren().clear();
						setCenter(new MedicineScreen(order.getOid(), "Medicines in this order","saleOrder"));					}
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
		oidColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;");
		odateColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;");
		priceColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;"); 
		eidColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;");
		actionColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;");
		
		tableView.getColumns().addAll(oidColumn, odateColumn, priceColumn, eidColumn, actionColumn);

		// Retrieve all medicines from the database
        try {
            List<SaleOrder> allOrders = DataBaseServices.getAllSaleOrders();
            orderList.addAll(allOrders);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        
        
     // Add event handler to the date picker
        datePicker.setOnAction(event -> {
            java.sql.Date selectedDate = java.sql.Date.valueOf(datePicker.getValue());

            // Filter sale orders based on the selected date
            ObservableList<SaleOrder> filteredList = orderList.filtered(order ->
                    order.getOdate().toLocalDate().isEqual(selectedDate.toLocalDate())
            );

            tableView.setItems(filteredList);
        });
		
		// Create a back button
		Button backButton = createStyledButton("Back");
		backButton.setOnAction(e -> {
			setCenter(new Secondery());
		});

		// Create a title text
		Text titleText = new Text("Search for a Sale Order");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		titleText.setFill(Color.GOLD);

		// Create a vertical box to hold the title, search pane, and table view
		VBox contentBox = new VBox(20);
		contentBox.setAlignment(Pos.CENTER);
		contentBox.getChildren().addAll(titleText, datePicker, tableView, backButton);

		setCenter(contentBox);
		// Set the table view data source to the martyrs list
		tableView.setItems(orderList);

	}

}
