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

public class CompanyOrderScreen extends Secondery {
	private ObservableList<OrderFromCompany> orderList;
	private TableView<OrderFromCompany> tableView;
    private DatePicker datePicker;


	public CompanyOrderScreen() {
		// Clear the existing content
		getChildren().clear();

		orderList = FXCollections.observableArrayList(); // Create the observable list for martyrs

		// Create a grid pane for the search field
		GridPane searchPane = new GridPane();
		searchPane.setAlignment(Pos.CENTER);
		searchPane.setHgap(10);
		searchPane.setVgap(10);
		searchPane.setPadding(new Insets(20));

		// Create a label and text field for the search
		Label searchLabel = new Label("Order id");
		searchLabel.setFont(Font.font("Arial", 20));
		searchLabel.setTextFill(Color.DARKBLUE);
		TextField searchField = new TextField();
        searchField.setPromptText("Search");


		// Add the label and text field to the search pane
		searchPane.add(searchLabel, 0, 0);
		searchPane.add(searchField, 1, 0);

		// Create a table view for displaying the search results
		tableView = new TableView<>();
		tableView.setMaxWidth(600);
		 // Create TableColumn for each property in SaleOrder
        TableColumn<OrderFromCompany, Integer> oidColumn = new TableColumn<>("Order ID");
        oidColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getOrderId()).asObject());
        oidColumn.setMinWidth(150);

        TableColumn<OrderFromCompany, Integer> eidColumn = new TableColumn<>("Company ID");
        eidColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCompId()).asObject());
        eidColumn.setMinWidth(150);


        TableColumn<OrderFromCompany, Double> priceColumn = new TableColumn<>("Price");
		priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
		priceColumn.setMinWidth(150);
		
		TableColumn<OrderFromCompany, Void> actionColumn = new TableColumn<>("Actions");
		actionColumn.setMinWidth(150);
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
						OrderFromCompany order = getTableView().getItems().get(getIndex());
						orderList.remove(order);
						try {
							DataBaseServices.deleteCompanyOrder(order.getOrderId());
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (button == updateButton) {
						OrderFromCompany order = getTableView().getItems().get(getIndex());
						getChildren().clear();
						setCenter(new MedicineScreen(order.getOrderId(), "Medicines in this order","companyOrder"));
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
		oidColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;");
		priceColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;"); 
		eidColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;");
		actionColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;");


        
		tableView.getColumns().addAll(oidColumn, eidColumn, priceColumn,actionColumn);
		
		// Retrieve all medicines from the database
        try {
            List<OrderFromCompany> allOrders = DataBaseServices.getAllOrdersFromCompany();
            orderList.addAll(allOrders);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        
        // Add event handler to search on key release
        searchField.setOnKeyReleased(event -> {
            String searchText = searchField.getText().toLowerCase();
            ObservableList<OrderFromCompany> filteredList = orderList.filtered(order ->
                    String.valueOf(order.getOrderId()).toLowerCase().contains(searchText)
            );
            tableView.setItems(filteredList);
        });
		
		// Create a back button
		Button backButton = createStyledButton("Back");
		backButton.setOnAction(e -> {
			setCenter(new Secondery());
		});

		// Create a title text
		Text titleText = new Text("Search for an Order");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		titleText.setFill(Color.GOLD);

		// Create a vertical box to hold the title, search pane, and table view
		VBox contentBox = new VBox(20);
		contentBox.setAlignment(Pos.CENTER);
		contentBox.getChildren().addAll(titleText, searchPane, tableView,backButton);

		setCenter(contentBox);

		// Set the table view data source to the martyrs list
		tableView.setItems(orderList);
	}

}
