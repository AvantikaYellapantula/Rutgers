package com.cs336.pkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;


public class ApplicationDAO {

	Connection connection = null;
	
	public Connection getConnection(){
		String connectionUrl = "jdbc:mysql://localhost:3306/myDB?autoReconnect=true";
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {e.printStackTrace();} catch (IllegalAccessException e) {e.printStackTrace();} catch (ClassNotFoundException e) {e.printStackTrace();}
		
		try {
			connection = DriverManager.getConnection(connectionUrl,"root", "TheWizLL83v");
			//connection = DriverManager.getConnection(connectionUrl,"root", "root");
		} catch (SQLException e) {e.printStackTrace();}
		
		return connection;
	}
	
	public void closeConnection(){
		try {
			connection.close();
		} catch (SQLException e) {e.printStackTrace();}
	}
		
public Users CheckUser(String username, String password) throws SQLException{
		
		LinkedList<Users> all =new LinkedList<Users>();
		all = getAllUsers();
		
		//check
		while(!all.isEmpty()) {
			Users user = all.removeFirst();
			
			if(user.getUserName().equals(username) && user.getPassword().equals(password)){
					return user;
					}
			else{
			
				continue;			
						
					}		
			}
		return null;
			
		}
public LinkedList<Users> getAllUsers() throws SQLException{
	
	LinkedList<Users> listOfPeople = new LinkedList<Users>();
	
	//display all tuples
	String selectString = "select * from Users;";
	Connection dbConnection = getConnection();
	PreparedStatement preparedStatement = dbConnection.prepareStatement(selectString);
	
	ResultSet rs = preparedStatement.executeQuery(); 
	
	//creating a ResultSet
	while(rs.next( )) {
		listOfPeople.add(new Users(rs.getString("UserName"), rs.getString("Password"),rs.getString("Email"),rs.getString("FName"),rs.getString("LName"),rs.getString("State"),rs.getString("Street"),rs.getString("City"),rs.getInt("ZipCode"),rs.getBoolean("Iscustomerrep"),rs.getBoolean("Isadmin"),rs.getBoolean("IsBuyer"),rs.getBoolean("IsSeller")));
	}
	
	//close everything
	preparedStatement.close();
	dbConnection.close();
	
	return listOfPeople;
}

 int counter = 0;
 
 
 
 public void priceIncrement()throws SQLException{

		LinkedList<Bid> bids = new LinkedList<Bid>();
		String selectString = "select B.ItemNum,B.UserName,B.maxPrice,B.BidID,B.IsOld,B.Increment,B.IntPrice,B.IsManual from Bid B, Bid B1 where B1.UserName != B.Username and B1.ItemNum=B.ItemNum and B.IntPrice < B1.IntPrice and B.IsManual = 0 and B.IsOld = 0 ;";
		Bid b; 
		String usernamee = null; 
		float price = 0;
		String bID = null;
		float maxpr = 0; 
		float inc = 0;
		float newprice= 0; 
		int insert = 0;
		String oldbid = null;
		
		
				
		try{
			Connection dbConnection = getConnection();
			PreparedStatement preparedStatement = dbConnection.prepareStatement(selectString);

			ResultSet rs = preparedStatement.executeQuery(); 
		
			while(rs.next( )) {
				float m = Float.parseFloat(rs.getString("maxPrice"));
				float o = Float.parseFloat(rs.getString("Increment"));
				float p = Float.parseFloat(rs.getString("IntPrice")); 
//String itemnum,float intprice, String bidid, float increment, float maxprice,String username, int ismanual,int isold
				bids.add(new Bid(rs.getString("ItemNum"),p,rs.getString("BidID"),o,m, rs.getString("UserName"), rs.getInt("IsManual"),rs.getInt("IsOld")));
				}

			if(!bids.isEmpty()){
				
				//go thru and get price and increment and max price and add and check 
				b = bids.removeFirst();
				
				usernamee = b.getUserName();
				bID = b.getItemNum();
				price = b.getIntPrice();
				oldbid = b.getBidID();
				maxpr = b.getMaxPrice();
				inc = b.getIncrement(); 
				newprice = price + inc; 
				
			
				
				if(newprice <= maxpr){
					//increment 
					insert = 1; 
					
				}
				else{
					//alert buyer
					insert = 2; 
					
				}	
			}

			//close everything
			preparedStatement.close();
			dbConnection.close();
			}
			catch(Exception e){
			System.out.println("Someone has bid higher than your maximum price");
			}

			if(insert == 1){
				String bidID = UUID.randomUUID().toString(); 

				String insertString = "INSERT INTO Bid VALUES ('"+bID+"','"+newprice+"','"+bidID+"', '"+inc+"','"+maxpr+"', '"+usernamee+"', '"+0+"','"+0+"')";
				Connection dbConnection1 = getConnection();
				PreparedStatement preparedStatement2 = dbConnection1.prepareStatement(insertString);
				preparedStatement2.executeUpdate();

				
				// execute select SQL statement

				System.out.println("Person added to bids");

				
				counter++; 
				System.out.println(counter);

				if(counter > 0){
					String updateString = "UPDATE Bid set IsOld=1 where BidID = '"+oldbid+"';"; 
					PreparedStatement preparedStatement3 = dbConnection1.prepareStatement(updateString);
					preparedStatement3.executeUpdate();
					System.out.println("Second updated");
					preparedStatement3.close(); 
				}
				preparedStatement2.close();
				dbConnection1.close();

				priceIncrement(); 
				
			}
			else if (insert == 2){
				
				String alertID = UUID.randomUUID().toString(); 
				String des = "error";
				
				String insertString = "INSERT INTO Alerts VALUES ('"+alertID+"','"+des+"','"+usernamee+"','"+bID+"')";
				Connection dbConnection1 = getConnection();
				PreparedStatement preparedStatement2 = dbConnection1.prepareStatement(insertString);

				// execute select SQL statement
				preparedStatement2.executeUpdate();
				System.out.println("Person added to alerts");

				preparedStatement2.close();
				dbConnection1.close();
				
			}else{
				//do nothing 
				System.out.println("carryon");
				 
			}
		
		
			}






public void alertUser()throws SQLException{
	
	LinkedList<Bid> bids = new LinkedList<Bid>();
	String selectString = "select B.ItemNum,B.UserName,B.maxPrice,B.BidID,B.IsOld,B.Increment,B.IntPrice,B.IsManual from Bid B, Bid B1 where B1.UserName != B.UserName and B1.ItemNum=B.ItemNum and B.IntPrice < B1.IntPrice and B.IsManual = 0 and B.IsOld = 0 ;";
	Bid b; 
	
	
	String usernamee = null; 
	String bID = null; 
	int insert = 0;
	
			
	try{
		Connection dbConnection = getConnection();
		PreparedStatement preparedStatement = dbConnection.prepareStatement(selectString);

		ResultSet rs = preparedStatement.executeQuery(); 
	
		while(rs.next( )) {
			float m = Float.parseFloat(rs.getString("maxPrice"));
			float o = Float.parseFloat(rs.getString("Increment"));
			float p = Float.parseFloat(rs.getString("IntPrice")); 
			

			bids.add(new Bid(rs.getString("ItemNum"),p,rs.getString("BidID"),o,m, rs.getString("UserName"), rs.getInt("IsManual"),rs.getInt("IsOld")));
		}
	

		if(!bids.isEmpty()){
			
			b = bids.removeFirst();
			
			usernamee = b.getUserName();
			bID = b.getItemNum();
			
			
			//go thru and get price and increment and max price and add and check 
			
			 
			
			insert = 2; 
				
			}	
		

		//close everything
		preparedStatement.close();
		dbConnection.close();
		}
		catch(Exception e){
		System.out.println("error");
		}

		
		if (insert == 2){
			
			String alertID = UUID.randomUUID().toString(); 
			String des = "error";
			
			String insertString = "INSERT INTO Alerts VALUES ('"+alertID+"','"+des+"','"+usernamee+"','"+bID+"')";
			Connection dbConnection1 = getConnection();
			PreparedStatement preparedStatement2 = dbConnection1.prepareStatement(insertString);

			// execute select SQL statement
			preparedStatement2.executeUpdate();
			System.out.println("Person added to alerts");

			preparedStatement2.close();
			dbConnection1.close();
			
		}else{
			//do nothing 
			System.out.println("carryon");
			 
		}
	
	
		}

public float sellersStuff(String bID) throws SQLException{
	
	LinkedList<Sells> sell = new LinkedList<Sells>();
	float Sprice= 0; 
	Sells s;


	
	try{
		String selectString = "SELECT * FROM Sells S WHERE  S.ItemNum ='"+bID+"' AND S.IsClosed = 1;";
		Connection dbConnection = getConnection();
		PreparedStatement preparedStatement = dbConnection.prepareStatement(selectString);
		ResultSet rs2 = preparedStatement.executeQuery(); 
			
		
		//public Sells(String B_ID, String usernamee,float minprice,Date date, int isClosed) {
		
		while(rs2.next( )) {
			
			float a = Float.parseFloat(rs2.getString("MinPrice"));
			 
			sell.add(new Sells(rs2.getDate("EndDate"),a, rs2.getString("UserName"), rs2.getInt("IsClosed"),rs2.getString("ItemNum")));
			}

		if(!sell.isEmpty()){
			
			s = sell.removeFirst();
			Sprice = s.getMinPrice();
			}
	}
	
		catch(Exception e){
				System.out.println("error");
		}
		return Sprice; 
	}

		
				
		
	

	
public void checkWinner() throws SQLException{
	
	//case one --> highest bid is greater than seller max price

	LinkedList<Bid> bids = new LinkedList<Bid>();

	String selectString = "SELECT B.ItemNum,B.UserName,B.maxPrice,B.BidID,B.IsOld,B.Increment,B.IntPrice,B.IsManual FROM Sells S, Bid B WHERE S.UserName != B.UserName AND S.ItemNum = B.ItemNum AND S.IsClosed = 1 AND B.IntPrice = (SELECT MAX(B1.IntPrice) FROM Bid B1 WHERE B1.ItemNum = B.ItemNum) AND (B.IntPrice > S.MinPrice OR B.IntPrice = S.MinPrice OR B.maxPrice > S.MinPrice);";
			
		Bid b; 
		String usernamee = null; 
		float price = 0;
		String bID = null;
		float maxpr = 0; 
		float Sprice= 0; 
		boolean insert = false; 
				
		try{
			
			Connection dbConnection = getConnection();
			PreparedStatement preparedStatement = dbConnection.prepareStatement(selectString);

			ResultSet rs = preparedStatement.executeQuery(); 
		
			while(rs.next()) {
				
				float m = Float.parseFloat(rs.getString("maxPrice"));
				float o = Float.parseFloat(rs.getString("Increment"));
				float p = Float.parseFloat(rs.getString("IntPrice")); 

				bids.add(new Bid(rs.getString("ItemNum"),p,rs.getString("BidID"),o,m, rs.getString("UserName"), rs.getInt("IsManual"),rs.getInt("IsOld")));
				}

			if(!bids.isEmpty()){
				
				//go thru and get price and increment and max price and add and check 
				b = bids.removeFirst();
				
				usernamee = b.getUserName();
				bID = b.getItemNum();
				price = b.getIntPrice();
				maxpr = b.getMaxPrice();
			
					
			}

			//close everything
			preparedStatement.close();
			dbConnection.close();
			}
			catch(Exception e){
			System.out.println("error");
			}

			
		Sprice = sellersStuff(bID); 
				
				
				if(Sprice < price ){
					//winner
					insert = true; 
					
				}
				else if(Sprice < maxpr){
					//make new price 
					price = Sprice;
					insert = true;
					
				}
				
			
			
		if(insert == true){
			//insert into buyers table
		
			String insertString = "INSERT INTO Buys VALUES ('"+price+"','"+usernamee+"','"+bID+"')";
			Connection dbConnection1 = getConnection();
			PreparedStatement preparedStatement2 = dbConnection1.prepareStatement(insertString);

		// execute select SQL statement
		preparedStatement2.executeUpdate();
		System.out.println("Person added to buys");

		preparedStatement2.close();
		dbConnection1.close();
		}
		
	}
	

public Boolean CheckUser(String username) throws SQLException{
		String selectString = "SELECT * FROM Users WHERE UserName= '"+username+"';";
		Connection dbConnection = getConnection();
		PreparedStatement preparedStatement = dbConnection.prepareStatement(selectString);
		ResultSet rs = preparedStatement.executeQuery(); 
		
		rs.last();
		if (rs.getRow()>=1)
			return true;
		
		return false;
		}
	
	
	
public Boolean CheckCreditCard(String cardNum) throws SQLException{
		String selectString = "SELECT * FROM creditcard WHERE CreditcardNumber= '"+cardNum+"';";
		Connection dbConnection = getConnection();
		PreparedStatement preparedStatement = dbConnection.prepareStatement(selectString);
		ResultSet rs = preparedStatement.executeQuery(); 
		
		rs.last();
		if (rs.getRow()>=1)
			return true;
		
		return false;
		}
	
	
	
	
public Boolean CheckAccount(String accNum) throws SQLException{
		String selectString = "SELECT * FROM bankinfo WHERE AccountNum= '"+accNum+"';";
		Connection dbConnection = getConnection();
		PreparedStatement preparedStatement = dbConnection.prepareStatement(selectString);
		ResultSet rs = preparedStatement.executeQuery(); 
		rs.last();
		if (rs.getRow()>=1)
			return true;
		
		return false;
		}
	
	
	
public ResultSet query(String buy_type,String buy_name,String buy_con,String buy_color,String buy_size) throws SQLException{
		if (buy_type.equals(""))
			buy_type="";
		else
			buy_type=" Typee = '"+buy_type+"'";
		
		if (buy_name.equals(""))
			buy_name="";
		else
			buy_name=" Namee = '"+buy_name+"'";
		
		if (buy_con.equals(""))
			buy_con="";
		else
			buy_con=" Conditionn = '"+buy_con+"'";
		
		if (buy_color.equals(""))
			buy_color="";
		else
			buy_color=" Color = '"+buy_color+"'";
		
		if (buy_size.equals(""))
			buy_size="";
		else
			buy_size=" Size = '"+buy_size+"'";
		
//        if (buy_price.equals(""))
//			buy_price="";
//		else
//			buy_price=" Price = '"+buy_price+"'";
		
		String selectString;
		
		System.out.println("SELECT * FROM Footwear WHERE" +buy_type+buy_name+buy_con+buy_color+";");
		if (buy_type==""&&buy_name==""&&buy_con==""&&buy_color==""&&buy_size=="")
			selectString = "SELECT * FROM Footwear;";
		else
			selectString = "SELECT * FROM Footwear WHERE"+buy_type+buy_name+buy_con+buy_color+";";
		Connection dbConnection = getConnection();
		PreparedStatement preparedStatement = dbConnection.prepareStatement(selectString);
		return preparedStatement.executeQuery(); 
		}
	
	public static void main(String[] args) {
		ApplicationDAO dao = new ApplicationDAO();
		try {
			dao.checkWinner();
			System.out.println(dao.CheckCreditCard("8"));
		} catch (SQLException e) {e.printStackTrace();}
	}
	
	

}

