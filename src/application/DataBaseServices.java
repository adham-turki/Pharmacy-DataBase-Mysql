package application;

import java.sql.*;
import java.util.Properties;
import java.util.ArrayList;

import java.util.Date;
import java.util.Properties;
import java.util.ArrayList;

public class DataBaseServices {
	static Connection connection;

	public static void addMedicineInDatabase(Medicine m) throws ClassNotFoundException, SQLException {
		connectDB();
		String query = "INSERT INTO Medicine (Mid, Mname,Package, Quantity, Mdescription, dosage_form,price, Compid) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, m.getMid());
		statement.setString(2, m.getMname());
		statement.setString(3, m.getMpackage());
		statement.setInt(4, m.getQuantity());
		statement.setString(5, m.getDescription());
		statement.setString(6, m.getDosage_form());
		statement.setDouble(7, m.getPrice());
		statement.setInt(8, m.getCompid());

		statement.executeUpdate();

		connection.close();

	}

	public static ArrayList<Medicine> getAllMedicineFromPharmacy() throws SQLException, ClassNotFoundException {
		connectDB();
		ArrayList<Medicine> AllMedicine = new ArrayList();
		String query = "SELECT * FROM Medicine";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet rs = statement.executeQuery(query);
		while (rs.next()) {
			int Mid = rs.getInt("Mid");
			String Mname = rs.getString("Mname");
			String Mpackage = rs.getString("package");
			int quantity = rs.getInt("quantity");
			String Mdescription = rs.getString("Mdescription");
			String dosage_form = rs.getString("dosage_form");
			Double price = rs.getDouble("price");
			int Compid = rs.getInt("Compid");
			AllMedicine.add(new Medicine(Mid, Mname, Mpackage, quantity, Mdescription, dosage_form, price, Compid));
		}
		return AllMedicine;
	}

	public static ArrayList<SaleOrder> getAllSaleOrders() throws SQLException, ClassNotFoundException {
		connectDB();
		ArrayList<SaleOrder> allOrders = new ArrayList<>();

		String query = "SELECT * FROM orders";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			int Oid = rs.getInt("Oid");
			Date Odate = rs.getDate("Odate");
			double price = rs.getDouble("Oprice");
			int Eid = rs.getInt("Eid");

			
			SaleOrder order = new SaleOrder(Oid, (java.sql.Date) Odate, price, Eid);
			allOrders.add(order);
		}

		connection.close();
		return allOrders;
	}

	public static void editMedicine(Medicine medicine) throws SQLException, ClassNotFoundException {
		connectDB();
		String query = "UPDATE Medicine M SET M.Compid = ?, M.Mname = ?, M.Mid = ?, M.package = ?, "
				+ "M.quantity = ?, M.Mdescription = ?, M.dosage_form = ?, M.price = ? WHERE M.Mid = ?";

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, medicine.getCompid());
		statement.setString(2, medicine.getMname());
		statement.setInt(3, medicine.getMid());
		statement.setString(4, medicine.getMpackage());
		statement.setInt(5, medicine.getQuantity());
		statement.setString(6, medicine.getDescription());
		statement.setString(7, medicine.getDosage_form());
		statement.setDouble(8, medicine.getPrice());
		statement.setInt(9, medicine.getMid());

		int rowsAffected = statement.executeUpdate();
		statement.close();
		connection.close();
	}

	public static void deleteMedicine(int Mid) throws SQLException, ClassNotFoundException {
		connectDB();
		String query = "UPDATE Medicine M SET M.quantity = ? WHERE M.Mid = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, 0);
		statement.setInt(2, Mid);
		int rowsAffected = statement.executeUpdate();
		statement.close();
		connection.close();
	}

	public static Medicine getMedicineById(int mid) throws SQLException, ClassNotFoundException {
		connectDB();
		String query = "SELECT * FROM Medicine WHERE Mid = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, mid);
		ResultSet rs = statement.executeQuery();

		Medicine medicine = null;

		while (rs.next()) {
			int Mid = rs.getInt("Mid");
			String Mname = rs.getString("Mname");
			String Mpackage = rs.getString("package");
			int quantity = rs.getInt("quantity");
			String Mdescription = rs.getString("Mdescription");
			String dosage_form = rs.getString("dosage_form");
			Double price = rs.getDouble("price");
			int Compid = rs.getInt("Compid");

			medicine = new Medicine(Mid, Mname, Mpackage, quantity, Mdescription, dosage_form, price, Compid);
		}

		return medicine;
	}

	public static ArrayList<Company> getAllCompany() throws ClassNotFoundException, SQLException {
		connectDB();
		ArrayList<Company> allCompany = new ArrayList<>();
		String query = "SELECT * FROM Company";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			int Compid = rs.getInt("Compid");
			String Compname = rs.getString("Compname");
			String location = rs.getString("location");
			String establsihDate = rs.getString("establishDate");
			allCompany.add(new Company(Compid, Compname, location, establsihDate));
		}
		return allCompany;

	}

	public static void addCompanyInDatabase(Company c) throws ClassNotFoundException, SQLException {
		connectDB();

		String query = "INSERT INTO Company (Compid, Compname, location, establishDate) " + "VALUES (?, ?, ?, ?)";

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, c.getCompid());
		statement.setString(2, c.getCompname());
		statement.setString(3, c.getLocation());
		statement.setString(4, c.getEstablsihDate());

		statement.executeUpdate();

		connection.close();

	}

	public static void editCompany(Company company) throws SQLException, ClassNotFoundException {
		connectDB();
		String query = "UPDATE Company C SET C.Compname = ?, C.location = ?, C.establishDate = ? WHERE C.Compid = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, company.getCompname());
		statement.setString(2, company.getLocation());
		statement.setString(3, company.getEstablsihDate());
		statement.setInt(4, company.getCompid());
		statement.executeUpdate();
		connection.close();
	}

	public static void deleteCompany(int Copmid) throws SQLException, ClassNotFoundException {
		connectDB();
		String query = "DELETE FROM Company WHERE Company Copmid = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, Copmid);
		statement.executeUpdate();
		connection.close();
	}

	public static void deleteEmployee(int Eid) throws SQLException, ClassNotFoundException {
		connectDB();
		String query = "DELETE FROM Employee WHERE Eid = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, Eid);
		statement.executeUpdate();
		connection.close();
	}

	public static ArrayList<Employee> getAllEmployeeFromPharmacy() throws SQLException, ClassNotFoundException {
		connectDB();
		ArrayList<Employee> employees = new ArrayList();
		String query = "SELECT Eid,Ename,salary,phoneNumber FROM Employee";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet rs = statement.executeQuery(query);
		while (rs.next()) {
			int Eid = rs.getInt("Eid");
			String Ename = rs.getString("Ename");
			Double salary = rs.getDouble("salary");
			String phoneNumber = rs.getString("phoneNumber");
			employees.add(new Employee(Eid, Ename, salary, phoneNumber));
		}
		connection.close();
		return employees;
	}

	public static void setEmployeeActive(int employeeId) throws SQLException, ClassNotFoundException {
		connectDB();
		String query = "UPDATE employee SET Active = true WHERE Eid = ?";
		PreparedStatement statement = connection.prepareStatement(query);

		statement.setInt(1, employeeId);
		statement.executeUpdate();

		statement.close();
		connection.close();
	}

	public static void setEmployeeNoActive() throws SQLException, ClassNotFoundException {
		connectDB();
		String query = "UPDATE employee SET Active = false ";
		PreparedStatement statement = connection.prepareStatement(query);

		statement.executeUpdate();

		statement.close();
		connection.close();
	}

	public static int getActiveEmployee() throws SQLException, ClassNotFoundException {
		connectDB();
		String query = "SELECT Eid FROM Employee WHERE active = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setBoolean(1, true);
		ResultSet rs = statement.executeQuery();
		int id = 0;
		while (rs.next()) {
			id = rs.getInt("Eid");
		}
		return id;
	}

	public static boolean getEmployeeByIdandName(int id, String name) throws SQLException, ClassNotFoundException {
		connectDB();
		String query = "SELECT Eid, Ename FROM Employee WHERE Eid = ? AND Ename = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		statement.setString(2, name);

		ResultSet rs = statement.executeQuery();
		return rs.next(); // Returns true if the employee exists

	}

	public static void saleMedicine() throws SQLException, ClassNotFoundException {
		connectDB();
	}

	public static boolean sallMedicine(int Mid, int quantity) throws SQLException, ClassNotFoundException {
		connectDB();
		Medicine medicine = getMedicineById(Mid);
		if (medicine != null) {
			if (medicine.getQuantity() == 0) {
				return false;
			} else if (medicine.getQuantity() < quantity) {
				return false;
			} else {
				return true;
			}
		}
		return false;

	}

	public static void editEmployee(Employee employee) throws SQLException, ClassNotFoundException {
		connectDB();
		String query = "UPDATE Employee SET Ename = ?, salary = ?, phoneNumber = ? WHERE Eid = ?";

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, employee.getEname());
		statement.setDouble(2, employee.getSalary());
		statement.setString(3, employee.getPhoneNumber());
		statement.setInt(4, employee.getEid());

		int rowsAffected = statement.executeUpdate();
		statement.close();
		connection.close();
	}
	public static void addEmployee(Employee employee) throws SQLException, ClassNotFoundException {
		connectDB();
		String query = "INSERT INTO employee (Ename, salary, phoneNumber,Eid,active) " + "VALUES (?, ?, ?, ?, ?)";

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, employee.getEname());
		statement.setDouble(2, employee.getSalary());
		statement.setString(3, employee.getPhoneNumber());
		statement.setInt(4, employee.getEid());
		statement.setBoolean(5, false);


		int rowsAffected = statement.executeUpdate();
		statement.close();
		connection.close();
	}

	public static void addSaleOrder(double price, ArrayList<Medicine> medicinesList) throws SQLException, ClassNotFoundException {
	    connectDB();
	    java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
	    int Eid = getActiveEmployee();
	    String query = "INSERT INTO orders (Odate, Oprice, Eid) VALUES (?, ?, ?)";
	    PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    statement.setDate(1, date);
	    statement.setDouble(2, price);
	    statement.setInt(3, Eid);
	    statement.executeUpdate();
	    ResultSet generatedKeys = statement.getGeneratedKeys();
	    int oid = -1;
	    if (generatedKeys.next()) {
	        oid = generatedKeys.getInt(1);
	    } else {
	        // Handle the case where no keys were generated
	        throw new SQLException("Failed to get the generated Oid.");
	    }

	    // Insert into 'order_medicine' table for each medicine in the list
	    String orderMedicineQuery = "INSERT INTO order_medicine (Oid, Mid, quantity) VALUES (?, ?, ?)";
	    PreparedStatement orderMedicineStatement = connection.prepareStatement(orderMedicineQuery);

	    for (Medicine medicine : medicinesList) {
	        orderMedicineStatement.setInt(1, oid);
	        orderMedicineStatement.setInt(2, medicine.getMid());
	        orderMedicineStatement.setInt(3, medicine.getQuantity());
	        orderMedicineStatement.executeUpdate();

	        updateSaleMedicineQuantity(medicine);
	    }

	    connection.close();
	}

	
	private static void updateSaleMedicineQuantity(Medicine medicine) throws SQLException {
		String selectQuery = "SELECT * FROM medicine WHERE Mid = ?";
	    String updateQuery = "UPDATE medicine SET quantity = quantity - ? WHERE Mid = ?";
		try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
			// Check if the medicine already exists in the table
			selectStatement.setInt(1, medicine.getMid());
			try (ResultSet resultSet = selectStatement.executeQuery()) {
				if (resultSet.next()) {
					// Medicine exists, update quantity
					try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
						updateStatement.setInt(1, medicine.getQuantity());
						updateStatement.setInt(2, medicine.getMid());
						updateStatement.executeUpdate();
					}
				} 
			}
		}
	}

	public static ArrayList<Medicine> getMedicinesInSaleOrder(int orderId) throws SQLException, ClassNotFoundException {
		connectDB();
		ArrayList<Medicine> medicinesInOrder = new ArrayList<>();

		// Query to retrieve medicines in the given order
		String query = "SELECT Mid, Quantity FROM order_medicine WHERE Oid = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, orderId);
		ResultSet rs = statement.executeQuery();

		
		while (rs.next()) {
			int medicineId = rs.getInt("Mid");
			int quantity = rs.getInt("Quantity");

			Medicine medicine = getMedicineById(medicineId);
			if (medicine != null) {
				medicine.setQuantity(quantity);
				medicinesInOrder.add(medicine);
			}
		}

		connection.close();
		return medicinesInOrder;

	}

	public static ArrayList<Medicine> getMedicinesInOrder(int orderId) throws SQLException, ClassNotFoundException {
		connectDB();
		ArrayList<Medicine> medicinesInOrder = new ArrayList<>();

		// Query to retrieve medicines in the given order
		String query = "SELECT Mid, Quantity FROM orderfromcompany_medicine WHERE orderId = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, orderId);
		ResultSet rs = statement.executeQuery();

		
		while (rs.next()) {
			int medicineId = rs.getInt("Mid");
			int quantity = rs.getInt("Quantity");

			Medicine medicine = getMedicineById(medicineId);
			if (medicine != null) {
				medicine.setQuantity(quantity);
				medicinesInOrder.add(medicine);
			}
		}

		connection.close();
		return medicinesInOrder;
	}

	public static void addOrderFromCompany(ArrayList<Medicine> medicines) throws ClassNotFoundException, SQLException {
		connectDB();

		try {
			connection.setAutoCommit(false);

			//Insert into orderfromcompany table
			String orderQuery = "INSERT INTO orderfromcompany (price, Compid) VALUES (?, ?)";
			PreparedStatement orderStatement = connection.prepareStatement(orderQuery,
					PreparedStatement.RETURN_GENERATED_KEYS);

			double totalOrderPrice = 0;
			for (Medicine medicine : medicines) {
				totalOrderPrice += medicine.getPrice() * medicine.getQuantity();
			}

			orderStatement.setDouble(1, totalOrderPrice);
			orderStatement.setInt(2, medicines.get(0).getCompid());
			orderStatement.executeUpdate();

			// Retrieve the generated order ID
			ResultSet generatedKeys = orderStatement.getGeneratedKeys();
			int orderId = -1;
			if (generatedKeys.next()) {
				orderId = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Failed to retrieve generated order ID.");
			}

			// Insert into orderfromcompany_medicine table
			String medicineOrderQuery = "INSERT INTO orderfromcompany_medicine (orderId, Mid, Quantity) VALUES (?, ?, ?)";
			PreparedStatement medicineOrderStatement = connection.prepareStatement(medicineOrderQuery);

			for (Medicine medicine : medicines) {
				medicineOrderStatement.setInt(1, orderId);
				medicineOrderStatement.setInt(2, medicine.getMid());
				medicineOrderStatement.setInt(3, medicine.getQuantity());
				medicineOrderStatement.addBatch();
				// Update the quantity in the Medicine table
				updateMedicineQuantity(medicine);
			}

			medicineOrderStatement.executeBatch();

			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
			connection.close();
		}
	}

	private static void updateMedicineQuantity(Medicine medicine) throws SQLException {
		String selectQuery = "SELECT * FROM medicine WHERE Mid = ?";
		String updateQuery = "UPDATE medicine SET quantity = quantity + ? WHERE Mid = ?";
		String insertQuery = "INSERT INTO medicine (Mid, Mname, package, quantity, Mdescription, dosage_form, price, Compid) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
			// Check if the medicine already exists in the table
			selectStatement.setInt(1, medicine.getMid());
			try (ResultSet resultSet = selectStatement.executeQuery()) {
				if (resultSet.next()) {
					// Medicine exists, update quantity
					try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
						updateStatement.setInt(1, medicine.getQuantity());
						updateStatement.setInt(2, medicine.getMid());
						updateStatement.executeUpdate();
					}
				} else {
					// Medicine does not exist, insert the new medicine
					try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
						insertStatement.setInt(1, medicine.getMid());
						insertStatement.setString(2, medicine.getMname());
						insertStatement.setString(3, medicine.getMpackage());
						insertStatement.setInt(4, medicine.getQuantity());
						insertStatement.setString(5, medicine.getDescription());
						insertStatement.setString(6, medicine.getDosage_form());
						insertStatement.setDouble(7, medicine.getPrice());
						insertStatement.setInt(8, medicine.getCompid());
						insertStatement.executeUpdate();
					}
				}
			}
		}
	}

	public static void deleteCompanyOrder(int orderId) throws SQLException, ClassNotFoundException {
		connectDB();

		try {
			connection.setAutoCommit(false);

			// Delete from orderfromcompany_medicine table
			String deleteMedicineOrderQuery = "DELETE FROM orderfromcompany_medicine WHERE orderId = ?";
			PreparedStatement deleteMedicineOrderStatement = connection.prepareStatement(deleteMedicineOrderQuery);
			deleteMedicineOrderStatement.setInt(1, orderId);
			deleteMedicineOrderStatement.executeUpdate();

			// Delete from orderfromcompany table
			String deleteOrderQuery = "DELETE FROM orderfromcompany WHERE orderId = ?";
			PreparedStatement deleteOrderStatement = connection.prepareStatement(deleteOrderQuery);
			deleteOrderStatement.setInt(1, orderId);
			deleteOrderStatement.executeUpdate();

			connection.commit();
		} catch (SQLException e) {
			// Rollback the transaction in case of an exception
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
			connection.close();
		}
	}
	
	public static void deleteSaleOrder(int orderId) throws SQLException, ClassNotFoundException {
		connectDB();

		try {
			connection.setAutoCommit(false);

			// Delete from orderfromcompany_medicine table
			String deleteMedicineOrderQuery = "DELETE FROM order_medicine WHERE Oid = ?";
			PreparedStatement deleteMedicineOrderStatement = connection.prepareStatement(deleteMedicineOrderQuery);
			deleteMedicineOrderStatement.setInt(1, orderId);
			deleteMedicineOrderStatement.executeUpdate();

			// Delete from orderfromcompany table
			String deleteOrderQuery = "DELETE FROM orders WHERE Oid = ?";
			PreparedStatement deleteOrderStatement = connection.prepareStatement(deleteOrderQuery);
			deleteOrderStatement.setInt(1, orderId);
			deleteOrderStatement.executeUpdate();

			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			connection.setAutoCommit(true);
			connection.close();
		}
	}

	public static ArrayList<OrderFromCompany> getAllOrdersFromCompany() throws SQLException, ClassNotFoundException {
		connectDB();
		ArrayList<OrderFromCompany> allOrders = new ArrayList<>();

		String query = "SELECT * FROM orderfromcompany";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			int orderId = rs.getInt("orderId");
			double price = rs.getDouble("price");
			int compId = rs.getInt("Compid");

			
			OrderFromCompany order = new OrderFromCompany(orderId, price, compId);
			allOrders.add(order);
		}

		connection.close();
		return allOrders;
	}
	
	public static void deleteMedicineFromOrder(int orderId, int medicineId, int quantity) throws SQLException, ClassNotFoundException {
	    connectDB();

	    try {
	        // Delete from order_medicine table
	        String deleteMedicineOrderQuery = "DELETE FROM order_medicine WHERE Oid = ? AND Mid = ?";
	        PreparedStatement deleteMedicineOrderStatement = connection.prepareStatement(deleteMedicineOrderQuery);
	        deleteMedicineOrderStatement.setInt(1, orderId);
	        deleteMedicineOrderStatement.setInt(2, medicineId);
	        deleteMedicineOrderStatement.executeUpdate();

	        // Update the quantity in the Medicine table
	        String updateMedicineQuery = "UPDATE Medicine SET Quantity = Quantity + ? WHERE Mid = ?";
	        PreparedStatement updateMedicineStatement = connection.prepareStatement(updateMedicineQuery);
	        updateMedicineStatement.setInt(1, quantity);
	        updateMedicineStatement.setInt(2, medicineId);
	        updateMedicineStatement.executeUpdate();

	        // Update the price in the orders table
	        String updateOrderPriceQuery = "UPDATE orders SET Oprice = Oprice - ? WHERE Oid = ?";
	        PreparedStatement updateOrderPriceStatement = connection.prepareStatement(updateOrderPriceQuery);
	        double medicinePrice = getMedicineById(medicineId).getPrice() * quantity;
	        updateOrderPriceStatement.setDouble(1, medicinePrice);
	        updateOrderPriceStatement.setInt(2, orderId);
	        updateOrderPriceStatement.executeUpdate();

	    } catch (SQLException e) {
	        // Handle the exception as needed
	        e.printStackTrace();
	    } finally {
	        // Close resources
	        connection.close();
	    }
	}


	public static void connectDB() throws ClassNotFoundException, SQLException {
		String dbURL = "jdbc:mysql://" + "127.0.0.1" + ":" + "3306" + "/" + "pharmacy_managment_system"
				+ "?verifyServerCertificate=false";
		Properties p = new Properties();
		p.setProperty("User", "root");
		p.setProperty("password", "adham123");
		p.setProperty("useSSL", "false");
		p.setProperty("autoReconnect", "true");
		Class.forName("com.mysql.cj.jdbc.Driver");

		connection = DriverManager.getConnection(dbURL, p);
	}
}