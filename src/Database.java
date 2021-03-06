
import java.sql.*;
import java.util.*;

public class Database {

	Restaurant restObj;
	Reviewer reviewerObj;
	Ratings ratingObj;

	private static Statement st;
	private static Connection conn;

	public void openConnection() {
		String url = "jdbc:oracle:thin:testuser/password@localhost";
		Properties props = new Properties();
		props.setProperty("user", "testdb");
		props.setProperty("password", "password");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, props);
			st = conn.createStatement();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("connection established successfully...!!");
	}

	public Reviewer getReviewer(String email) {
		String sql= "Select * from reviewer where email= ? ";
		System.out.println(sql);
		reviewerObj = new Reviewer();
		ResultSet rs = null;
		try {
			openConnection();
			PreparedStatement preStatement = conn.prepareStatement(sql);
			preStatement.setString(1, email);
			rs=preStatement.executeQuery();
			if(rs.next()){
				reviewerObj= new Reviewer();
				reviewerObj.setEmail(email);
				reviewerObj.setReviewer_id(rs.getInt(1));
				reviewerObj.setReviewer_Name(rs.getString(2));
				reviewerObj.setZipcode(rs.getString(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reviewerObj;
	}
	
	public boolean duplicateReviewer(String email) {
		String sql= "Select * from reviewer where email= ? ";
		System.out.println(sql);
		reviewerObj = new Reviewer();
		ResultSet rs = null;
		try {
			openConnection();
			PreparedStatement preStatement = conn.prepareStatement(sql);
			preStatement.setString(1, email);
			rs=preStatement.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public Reviewer getReviewer(int reviewer_id) {
		String sql= "Select * from reviewer where REVIEWER_ID= ? ";
		System.out.println(sql);
		ResultSet rs = null;
		try {
			PreparedStatement preStatement = conn.prepareStatement(sql);
			preStatement.setInt(1, reviewer_id);
			rs=preStatement.executeQuery();
			if(rs.next()){
				reviewerObj= new Reviewer();
				reviewerObj.setEmail(rs.getString(3));
				reviewerObj.setReviewer_id(rs.getInt(1));
				reviewerObj.setReviewer_Name(rs.getString(2));
				reviewerObj.setZipcode(rs.getString(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reviewerObj;
	}
	
	public void addReviewer(Reviewer user) {
		String sql= "Insert into reviewer(REVIEWER_NAME,EMAIL,ZIPCODE) values(?, ?, ?)";

		try {
			PreparedStatement preStatement = conn.prepareStatement(sql);
			preStatement.setString(1, user.getReviewer_Name());
			preStatement.setString(2, user.getEmail());
			preStatement.setString(3, user.getZipcode());
			preStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void editProfile(Reviewer reviewer) {
		String sql= "Update reviewer set REVIEWER_NAME =?, EMAIL = ? ,ZIPCODE=? where Reviewer_id =?";
		try {
			PreparedStatement preStatement = conn.prepareStatement(sql);
			preStatement.setString(1, reviewer.getReviewer_Name());
			preStatement.setString(2, reviewer.getEmail());
			preStatement.setString(3, reviewer.getZipcode());
			preStatement.setInt(4, reviewer.getReviewer_id());
			preStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Ratings getRating(int restaurant_id, int reviewer_id) {
		String sql= "Select * from ratings where restaurant_id= ? and user_id=? ";
		System.out.println(sql);
		
		ResultSet rs = null;
		try {
			PreparedStatement preStatement = conn.prepareStatement(sql);
			preStatement.setInt(1, restaurant_id);
			preStatement.setInt(2, reviewer_id);
			rs=preStatement.executeQuery();
			if(rs.next()){
				ratingObj = new Ratings();
				ratingObj.setRestaurant_id(restaurant_id);
				ratingObj.setUser_id(reviewer_id);
				ratingObj.setReviewDate(rs.getDate(6));
				ratingObj.setDescription(rs.getString(5));
				ratingObj.setStars(rs.getInt(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ratingObj;
	}
	public void editRating(Ratings rating) {
		String sql= "Update ratings set  STARS=?,DESCRIPTION=?,REVIEWDATE=sysdate where USER_ID =? and RESTAURANT_ID =?";
		try {
			PreparedStatement preStatement = conn.prepareStatement(sql);
			preStatement.setInt(1, rating.getStars());
			preStatement.setString(2, rating.getDescription());
			preStatement.setInt(3, rating.getUser_id());
			preStatement.setInt(4, rating.getRestaurant_id());
			preStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<Restaurant> getAllRestaurant(int reviewerId) {
		String sql= "SELECT rs.RESTAURANT_ID, rs.RESTAURANT_NAME, rs.ADDRESS,"+
					"rs.Description, NVL(avg(stars),0) AS AVERAGE,"
					+ " NVL((SELECT STARS FROM RATINGS WHERE USER_ID = ? and "
					+ "RESTAURANT_ID = rs.RESTAURANT_ID),0) AS USER_RATING FROM restaurant rs "
					+ "Left outer JOIN ratings ra ON rs.RESTAURANT_ID=ra.RESTAURANT_ID"
					+ " group by rs.RESTAURANT_ID, rs.RESTAURANT_NAME, rs.ADDRESS, rs.Description order by AVERAGE desc";
		ArrayList<Restaurant> restArray= new ArrayList<Restaurant>();
		ResultSet rs = null;
		try {
			PreparedStatement preStatement = conn.prepareStatement(sql);
			preStatement.setInt(1, reviewerId);
			rs=preStatement.executeQuery();
			while(rs.next()){
				restObj=new Restaurant();
				restObj.setRestaurant_id(rs.getInt(1));
				restObj.setRestaurant_Name(rs.getString(2));
				restObj.setAddress(rs.getString(3));
				restObj.setDescription(rs.getString(4));
				restObj.setAvgRating(rs.getInt(5));
				restObj.setUser_rating(rs.getInt(6));
				restArray.add(restObj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return restArray;
	}
	
	
	public Restaurant getRestaurant(int restaurant_id) {
		String sql= "SELECT rs.RESTAURANT_ID, rs.RESTAURANT_NAME, rs.ADDRESS, rs.Description, "
				+ "NVL(avg(stars),0) AS AVERAGE FROM restaurant rs Left outer JOIN ratings ra "
				+ "ON rs.RESTAURANT_ID=ra.RESTAURANT_ID where rs.RESTAURANT_ID =? "
				+ "group by rs.RESTAURANT_ID, rs.RESTAURANT_NAME, rs.ADDRESS, rs.Description";
		System.out.println(sql);
		
		ResultSet rs = null;
		try {
			PreparedStatement preStatement = conn.prepareStatement(sql);
			preStatement.setInt(1, restaurant_id);
			rs=preStatement.executeQuery();
			restObj = new Restaurant();
			if(rs.next()){
				
				System.out.println("restaurnat_id = " + restaurant_id);
				restObj.setRestaurant_id(rs.getInt(1));
				restObj.setRestaurant_Name(rs.getString(2));
				restObj.setAddress(rs.getString(3));
				restObj.setDescription(rs.getString(4));
				restObj.setAvgRating(rs.getInt(5));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return restObj;
	}
	
	public void addRestaurant(Restaurant newRestaurant) {
		String sql= "Insert into restaurant(RESTAURANT_NAME,ADDRESS,Description) values(?, ?, ?)";
		try {
			PreparedStatement preStatement = conn.prepareStatement(sql);
			preStatement.setString(1, newRestaurant.getRestaurant_Name());
			preStatement.setString(2, newRestaurant.getAddress());
			preStatement.setString(3, newRestaurant.getDescription());
			preStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void editRestaurant(Restaurant restaurant) {
		String sql= "Update restaurant set RESTAURANT_NAME =?, ADDRESS = ? ,Description=? where restaurant_id =?";
		try {
			PreparedStatement preStatement = conn.prepareStatement(sql);
			preStatement.setString(1, restaurant.getRestaurant_Name());
			preStatement.setString(2, restaurant.getAddress());
			preStatement.setString(3, restaurant.getDescription());
			preStatement.setInt(4, restaurant.getRestaurant_id());
			preStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Ratings> getRatings(int restaurant_id) {
		String sql= "select * from ratings where RESTAURANT_ID=? order by REVIEWDATE desc";
		ArrayList<Ratings> ratingArray= new ArrayList<Ratings>();
		ResultSet rs = null;
		try {
			PreparedStatement preStatement = conn.prepareStatement(sql);
			preStatement.setInt(1, restaurant_id);
			rs=preStatement.executeQuery();
			while(rs.next()){
				ratingObj = new Ratings();
				ratingObj.setRating_id(rs.getInt(1));
				ratingObj.setRestaurant_id(rs.getInt(2));
				ratingObj.setUser_id(rs.getInt(3));
				ratingObj.setStars(rs.getInt(4));
				ratingObj.setDescription(rs.getString(5));
				ratingObj.setReviewDate(rs.getDate(6));
				ratingArray.add(ratingObj);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ratingArray;
	}
	
	public ArrayList<Ratings> getRatingsByUser(int reviewer_id) {
		String sql= "select * from ratings where user_id=? order by REVIEWDATE desc";
		ArrayList<Ratings> ratingbyUser= new ArrayList<Ratings>();
		ResultSet rs = null;
		try {
			PreparedStatement preStatement = conn.prepareStatement(sql);
			preStatement.setInt(1, reviewer_id);
			System.out.println(sql);
			rs=preStatement.executeQuery();
			while(rs.next()){
				ratingObj = new Ratings();
				ratingObj.setRating_id(rs.getInt(1));
				ratingObj.setRestaurant_id(rs.getInt(2));
				ratingObj.setUser_id(rs.getInt(3));
				ratingObj.setStars(rs.getInt(4));
				ratingObj.setDescription(rs.getString(5));
				ratingObj.setReviewDate(rs.getDate(6));
				ratingbyUser.add(ratingObj);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ratingbyUser;
	}
	public void addRating(Ratings newRating) {
		String sql= "Insert into ratings(RESTAURANT_ID,USER_ID,Stars,description,REVIEWDATE) values(?, ?, ?,?,SYSDATE)";

		try {
			PreparedStatement preStatement = conn.prepareStatement(sql);
			preStatement.setInt(1, newRating.getRestaurant_id());
			preStatement.setInt(2, newRating.getUser_id());
			preStatement.setInt(3, newRating.getStars());
			preStatement.setString(4, newRating.getDescription());
			
			preStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean isReviewed(int reviewerId,int restaurant_id) {
		String sql= "SELECT STARS FROM RATINGS WHERE USER_ID = ? and RESTAURANT_ID = ?";
		
		ResultSet rs = null;
		try {
			PreparedStatement preStatement = conn.prepareStatement(sql);
			preStatement.setInt(1, reviewerId);
			preStatement.setInt(2, restaurant_id);
			rs=preStatement.executeQuery();
			if(rs.next()){
				return true;
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void closeConnection()
	{
		try
		{
			conn.close();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

