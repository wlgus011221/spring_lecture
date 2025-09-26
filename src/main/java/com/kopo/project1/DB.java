package com.kopo.project1;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.sqlite.SQLiteConfig;

public class DB {
	private Connection connection;

	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void open() {
		try {
<<<<<<< HEAD
			String dbFileName = "project1.sqlite";
			SQLiteConfig config = new SQLiteConfig();
			config.setEncoding(SQLiteConfig.Encoding.UTF8);
			this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbFileName, config.toProperties());
=======
			String dbFileName = "home/ubuntu/project1.sqlite";
			SQLiteConfig config = new SQLiteConfig();
			config.setEncoding(SQLiteConfig.Encoding.UTF8);
			this.connection = DriverManager.getConnection("jdbc:sqlite:/" + dbFileName, config.toProperties());
>>>>>>> 87ee6831d7d478bb6f1f29baefacf38b58096c83
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void close() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 테이블 생성
	public void createTable() {
		this.open();
<<<<<<< HEAD
		String userQuery = "CREATE TABLE user (idx INTEGER PRIMARY KEY AUTOINCREMENT, user_type TEXT, id TEXT, pwd TEXT, name TEXT, phone TEXT, address TEXT, created TEXT, last_updated TEXT);";
		String postQuery = "CREATE TABLE post (idx INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, contents TEXT, writer TEXT, created TEXT, last_updated TEXT, title_chosung TEXT);";
		try {
			Statement statement = this.connection.createStatement();
			statement.executeUpdate(userQuery);
			statement.executeUpdate(postQuery);
=======
		String query = "CREATE TABLE user (idx INTEGER PRIMARY KEY AUTOINCREMENT, user_type TEXT, id TEXT, pwd TEXT, name TEXT, phone TEXT, address TEXT, created TEXT, last_updated TEXT);";
		try {
			Statement statement = this.connection.createStatement();
			statement.executeQuery(query);
>>>>>>> 87ee6831d7d478bb6f1f29baefacf38b58096c83
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
	}
<<<<<<< HEAD

	// pwd 암호화
	public static String sha512(String input) {
		try {
			// SHA-512 해시 알고리즘 사용
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] hashedBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));

			// 바이트 배열을 16진수 문자열로 변환
			StringBuilder sb = new StringBuilder();
			for (byte b : hashedBytes) {
				sb.append(String.format("%02x", b));
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("SHA-512 알고리즘을 사용할 수 없습니다.", e);
		}
	}

	// user 데이터 삽입
=======
	
	// pwd 암호화
	public static String sha512(String input) {
        try {
            // SHA-512 해시 알고리즘 사용
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashedBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));

            // 바이트 배열을 16진수 문자열로 변환
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-512 알고리즘을 사용할 수 없습니다.", e);
        }
    }
	
	// 데이터 삽입
>>>>>>> 87ee6831d7d478bb6f1f29baefacf38b58096c83
	public void insertData(User user) {
		this.open();
		String query = "INSERT INTO user (user_type, id, pwd, name, phone, address, created, last_updated) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement statement = this.connection.prepareStatement(query);
			statement.setString(1, "guest");
			statement.setString(2, user.id);
			user.pwd = sha512(user.pwd);
			statement.setString(3, user.pwd);
			statement.setString(4, user.name);
			statement.setString(5, user.phone);
			statement.setString(6, user.address);
			String now = (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date());
			statement.setString(7, now);
			statement.setString(8, now);
			statement.execute();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
	}
<<<<<<< HEAD

	// user 전체 조회
	public ArrayList<User> selectAllUser() {
=======
	
	// 회원 전체 조회
	public ArrayList<User> selectAll() {
>>>>>>> 87ee6831d7d478bb6f1f29baefacf38b58096c83
		this.open();
		ArrayList<User> data = new ArrayList<>();
		try {
			String query = "SELECT * FROM user";
			PreparedStatement statement = this.connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				int idx = result.getInt("idx");
				String userType = result.getString("user_type");
				String id = result.getString("id");
				String pwd = result.getString("pwd");
				String name = result.getString("name");
				String phone = result.getString("phone");
				String address = result.getString("address");
				String created = result.getString("created");
				String lastUpdated = result.getString("last_updated");
				data.add(new User(idx, userType, id, pwd, name, phone, address, created, lastUpdated));
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
		return data;
	}
<<<<<<< HEAD

	// user 데이터 수정
	public void updateData(User user, int idx) {
		this.open();
		String query = "UPDATE user SET id=?, pwd=?, name=?, phone=?, address=?, last_updated=? WHERE idx=?";

=======
	
	// 데이터 수정
	public void updateData(User user, int idx) {
		this.open();
		String query = "UPDATE user SET id=?, pwd=?, name=?, phone=?, address=?, last_updated=? WHERE idx=?";
		
>>>>>>> 87ee6831d7d478bb6f1f29baefacf38b58096c83
		try {
			PreparedStatement statement = this.connection.prepareStatement(query);
			statement.setString(1, user.id);
			user.pwd = sha512(user.pwd);
			statement.setString(2, user.pwd);
			statement.setString(3, user.name);
			statement.setString(4, user.phone);
			statement.setString(5, user.address);
			String now = (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date());
			statement.setString(6, now);
			statement.setInt(7, idx);
			statement.execute();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
	}
<<<<<<< HEAD

	// user 데이터 삭제
	public void deleteData(int idx) {
		this.open();
		String query = "DELETE FROM user WHERE idx=?";

=======
	
	// 데이터 삭제
	public void deleteData(int idx) {
		this.open();
		String query = "DELETE FROM user WHERE idx=?";
		
>>>>>>> 87ee6831d7d478bb6f1f29baefacf38b58096c83
		try {
			PreparedStatement statement = this.connection.prepareStatement(query);
			statement.setInt(1, idx);
			statement.execute();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
	}
<<<<<<< HEAD

=======
	
>>>>>>> 87ee6831d7d478bb6f1f29baefacf38b58096c83
	// 로그인 체크
	public User login(User user) {
		this.open();
		User returnData = new User();
		try {
			String query = "SELECT * FROM user WHERE id=? AND pwd=?";
			PreparedStatement statement = this.connection.prepareStatement(query);
			statement.setString(1, user.id);
			user.pwd = sha512(user.pwd);
			statement.setString(2, user.pwd);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				int idx = result.getInt("idx");
				String userType = result.getString("user_type");
				String id = result.getString("id");
				String pwd = result.getString("pwd");
				String name = result.getString("name");
				String phone = result.getString("phone");
				String address = result.getString("address");
				String created = result.getString("created");
				String lastUpdated = result.getString("last_updated");
				returnData = new User(idx, userType, id, pwd, name, phone, address, created, lastUpdated);
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
		return returnData;
	}
<<<<<<< HEAD

=======
	
>>>>>>> 87ee6831d7d478bb6f1f29baefacf38b58096c83
	// 회원 조회
	public User select(int idx) {
		this.open();
		User data = new User();
		try {
			String query = "SELECT * FROM user WHERE idx=?";
			PreparedStatement statement = this.connection.prepareStatement(query);
			statement.setInt(1, idx);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				String userType = result.getString("user_type");
				String id = result.getString("id");
				String pwd = result.getString("pwd");
				String name = result.getString("name");
				String phone = result.getString("phone");
				String address = result.getString("address");
				String created = result.getString("created");
				String lastUpdated = result.getString("last_updated");
				data = new User(idx, userType, id, pwd, name, phone, address, created, lastUpdated);
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
		return data;
	}
<<<<<<< HEAD

	// 총 회원 수 조회
	public int countAll() {
		this.open();

=======
	
	// 총 회원 수 조회
	public int countAll() {
		this.open();
		
>>>>>>> 87ee6831d7d478bb6f1f29baefacf38b58096c83
		int count = 0;
		try {
			String query = "SELECT count(*) AS count FROM user";
			PreparedStatement statement = this.connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				count = result.getInt("count");
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
<<<<<<< HEAD

		return count;
	}

=======
		
		return count;
	}
	
>>>>>>> 87ee6831d7d478bb6f1f29baefacf38b58096c83
	// 금일 가입한 회원 수 조회
	public int countDay() {
		this.open();
		String now = (new java.text.SimpleDateFormat("yyyy-MM-dd")).format(new java.util.Date());
		int count = 0;
		try {
			String query = "SELECT count(*) AS count FROM user WHERE substr(created, 1, 10) = ?;";
			PreparedStatement statement = this.connection.prepareStatement(query);
			statement.setString(1, now);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				count = result.getInt("count");
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
<<<<<<< HEAD

		return count;
	}

	// 게시판
	// post 데이터 삽입
	public void insertData(Post post) {
		this.open();
		String query = "INSERT INTO post (title, contents, writer, created, last_updated, title_chosung) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement statement = this.connection.prepareStatement(query);
			statement.setString(1, post.title);
			statement.setString(2, post.contents);
			statement.setString(3, post.writer);
			String now = (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date());
			statement.setString(4, now);
			statement.setString(5, now);
			statement.setString(6, post.titleChosung);
			statement.execute();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
	}

	// postList 조회
	public ArrayList<Post> selectAllPost() {
		this.open();
		ArrayList<Post> data = new ArrayList<>();
		try {
			String query = "SELECT * FROM post";
			PreparedStatement statement = this.connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				int idx = result.getInt("idx");
				String title = result.getString("title");
				String writer = result.getString("writer");
				String created = result.getString("created");
				String lastUpdated = result.getString("last_updated");
				String titleChosung = result.getString("title_chosung");
				data.add(new Post(idx, title, writer, created, lastUpdated, titleChosung));
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
		return data;
	}
	
	// postList 조회 (최신순) - 최대 5개
	public ArrayList<Post> selectPostRecent() {
		this.open();
		ArrayList<Post> data = new ArrayList<>();
		try {
			String query = "SELECT * FROM post ORDER BY created DESC LIMIT 5";
			PreparedStatement statement = this.connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				int idx = result.getInt("idx");
				String title = result.getString("title");
				String writer = result.getString("writer");
				String created = result.getString("created");
				String lastUpdated = result.getString("last_updated");
				String titleChosung = result.getString("title_chosung");
				data.add(new Post(idx, title, writer, created, lastUpdated, titleChosung));
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
		return data;
	}
	
	// postList 조회 (수정 최신순) - 최대 5개
	public ArrayList<Post> selectPostUpdateRecent() {
		this.open();
		ArrayList<Post> data = new ArrayList<>();
		try {
			String query = "SELECT * FROM post WHERE created != last_updated ORDER BY last_updated DESC LIMIT 5";
			PreparedStatement statement = this.connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				int idx = result.getInt("idx");
				String title = result.getString("title");
				String writer = result.getString("writer");
				String created = result.getString("created");
				String lastUpdated = result.getString("last_updated");
				String titleChosung = result.getString("title_chosung");
				data.add(new Post(idx, title, writer, created, lastUpdated, titleChosung));
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
		return data;
	}

	// 게시글 조회
	public Post selectPost(int idx) {
		this.open();
		Post data = new Post();
		try {
			String query = "SELECT * FROM post WHERE idx=?";
			PreparedStatement statement = this.connection.prepareStatement(query);
			statement.setInt(1, idx);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				String title = result.getString("title");
				String contents = result.getString("contents");
				String writer = result.getString("writer");
				String created = result.getString("created");
				String lastUpdated = result.getString("last_updated");
				data = new Post(title, contents, writer, created, lastUpdated);
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
		return data;
	}

	// 게시글 검색 조회
	public ArrayList<Post> searchPost(String keyword) {
		this.open();
		ArrayList<Post> data = new ArrayList<>();
		try {
			String query = "SELECT * FROM post WHERE title LIKE ? OR contents LIKE ? OR title_chosung LIKE ?";
	        PreparedStatement statement = this.connection.prepareStatement(query);
	        String pattern = "%" + keyword + "%"; // %를 미리 붙여서 패턴 생성
	        statement.setString(1, pattern);
	        statement.setString(2, pattern);
	        statement.setString(3, pattern);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				int idx = result.getInt("idx");
				String title = result.getString("title");
				String writer = result.getString("writer");
				String created = result.getString("created");
				String lastUpdated = result.getString("last_updated");
				String titleChosung = result.getString("title_chosung");
				data.add(new Post(idx, title, writer, created, lastUpdated, titleChosung));
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
		return data;
	}
	
	// 게시글 삭제
	public void deletePost(int idx) {
		this.open();
		String query = "DELETE FROM post WHERE idx=?";

		try {
			PreparedStatement statement = this.connection.prepareStatement(query);
			statement.setInt(1, idx);
			statement.execute();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
	}
	
	// 게시글 수정
	public void updatePost(Post post, int idx) {
		this.open();
		String query = "UPDATE post SET title=?, contents=?, title_chosung=? ,last_updated=? WHERE idx=?";

		try {
			PreparedStatement statement = this.connection.prepareStatement(query);
			statement.setString(1, post.title);
			statement.setString(2, post.contents);
			statement.setString(3, post.titleChosung);
			String now = (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date());
			statement.setString(4, now);
			statement.setInt(5, idx);
			statement.execute();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
	}
=======
		
		return count;
	}
>>>>>>> 87ee6831d7d478bb6f1f29baefacf38b58096c83
}
