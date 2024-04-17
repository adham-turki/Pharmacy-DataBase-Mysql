package application;

import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Secondery extends BorderPane {

	public Secondery() {
		getChildren().clear();
		setBackground(getBackground1());

		// Create a vertical box to hold the main content
		VBox contentBox = new VBox(20);
		contentBox.setAlignment(Pos.CENTER);

		// Add a title text
		Text titleText = new Text("Pharmacy System");
		titleText.setFont(Font.font("Arial", 48));
		titleText.setFill(Color.DARKBLUE);

		// Create buttons with symbols
		Button buyMedicineButton = createStyledButton("Buy Medicine", "🏪");
		Button sellMedicineButton = createStyledButton("Sell Medicine", "🛒");
		Button saleOrderButton = createStyledButton("Sale Orders", "📊");
		Button medicineButton = createStyledButton("Medicine", "💉");
		Button employeesButton = createStyledButton("Employees", "🧑🏽‍🏭");
		Button orderCompanyButton = createStyledButton("Company Orders", "🏬");
		Button companyButton = createStyledButton("Companies", "🏢");

		
		buyMedicineButton.setOnAction(e -> {
			setCenter(new AddMedicineScreen());
		});
		sellMedicineButton.setOnAction(e -> {
			setCenter(new SellMedicineScreen());
		});
		saleOrderButton.setOnAction(e -> {
			setCenter(new SaleOrderScreen());
		});
		medicineButton.setOnAction(e -> {
			setCenter(new MedicineScreen());
		});
		employeesButton.setOnAction(e -> {
			setCenter(new EmployeesScreen());
		});
		orderCompanyButton.setOnAction(e -> {
			setCenter(new CompanyOrderScreen());
		});
		companyButton.setOnAction(e -> {
			setCenter(new CompanyScreen());
		});


		

		// Set button widths
		buyMedicineButton.setPrefWidth(300);
		sellMedicineButton.setPrefWidth(300);
		saleOrderButton.setPrefWidth(300);
		medicineButton.setPrefWidth(300);
		employeesButton.setPrefWidth(300);
		orderCompanyButton.setPrefWidth(300);
		companyButton.setPrefWidth(300);
		
		Button logOut = createStyledButton("Log Out", "🔒");
		logOut.setOnAction(e -> {
					try {
						DataBaseServices.setEmployeeNoActive();
						setCenter(new Login());
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		});
					logOut.setPrefWidth(300);
	


		// Add the buttons to the content box
		contentBox.getChildren().addAll(titleText, buyMedicineButton, sellMedicineButton, saleOrderButton, medicineButton,employeesButton,orderCompanyButton,companyButton,logOut);

		setCenter(contentBox);

	}

	protected Button createStyledButton(String text, String symbol) {
		Button button = new Button(text);
		button.setStyle(
				"-fx-background-color: #009688; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 18px;");
		Text symbolText = new Text(symbol);
		symbolText.setFill(Color.web("#FFD700")); // Set the symbol color
		symbolText.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 24px;");
		button.setGraphic(symbolText);

		// Hover effect to change button color to black
		button.setOnMouseEntered(e -> {
			button.setStyle(
					"-fx-background-color: black; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 18px;");
			symbolText.setFill(Color.WHITE);
		});
		button.setOnMouseExited(e -> {
			button.setStyle(
					"-fx-background-color: #009688; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 18px;");
			symbolText.setFill(Color.web("#FFD700")); // Restore the symbol color
		});

		return button;
	}
	protected Button createStyledButton(String text) {
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
