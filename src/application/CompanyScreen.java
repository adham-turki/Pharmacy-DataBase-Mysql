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

public class CompanyScreen extends Secondery {
	private ObservableList<Company> companyList;
	private TableView<Company> tableView;

	public CompanyScreen() {
		// Clear the existing content
		getChildren().clear();

		companyList = FXCollections.observableArrayList(); // Create the observable list for martyrs

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
		tableView.setMaxWidth(BASELINE_OFFSET_SAME_AS_HEIGHT);
		TableColumn<Company, Integer> idColumn = new TableColumn<>("Comp Id");
		idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCompid()).asObject());
		idColumn.setMinWidth(100);

		TableColumn<Company, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompname()));
		nameColumn.setMinWidth(100);

		TableColumn<Company, String> locationColumn = new TableColumn<>("Location");
		locationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
		locationColumn.setMinWidth(200);

		TableColumn<Company, String> dateColumn = new TableColumn<>("Establish Date");
		dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstablsihDate()));
		dateColumn.setMinWidth(100);

		tableView.setStyle("-fx-border-width: 2px; -fx-border-color: #ccc; -fx-background-color: white;");
		idColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;");
		nameColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;");
		locationColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;");
		dateColumn.setStyle("-fx-alignment: CENTER;-fx-font-weight: bold;-fx-font-style: italic;");

		tableView.getColumns().addAll(idColumn, nameColumn, locationColumn, dateColumn);

		// Retrieve all medicines from the database
		try {
			List<Company> allCompanies = DataBaseServices.getAllCompany();
			companyList.addAll(allCompanies);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace(); // Handle the exception appropriately
		}

		// Create a back button
		Button backButton = createStyledButton("Back");
		backButton.setOnAction(e -> {
			setCenter(new Secondery());
		});
		// Create a back button
		Button insertButton = createStyledButton("insert");
		insertButton.setOnAction(e -> {
			setCenter(new AddCompanyScreen());
		});

		// Create a title text
		Text titleText = new Text("Search for a Medicine");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		titleText.setFill(Color.GOLD);

		// Create a vertical box to hold the title, search pane, and table view
		VBox contentBox = new VBox(20);
		contentBox.setAlignment(Pos.CENTER);
		contentBox.getChildren().addAll(titleText, searchPane, tableView, insertButton,backButton);

		setCenter(contentBox);

		// Set the table view data source to the martyrs list
		tableView.setItems(companyList);
	}

}
