package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.UserRepositoryException;
import com.douzone.mysite.vo.UserVo;
@Repository
public class UserRepository {

	public void insert(UserVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "insert into user values(null,?,?,password(?),?,now())";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public UserVo findByEmailAndPassword(UserVo vo) {
		UserVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "elect no,name,email,gender from user where email = ? and password = password(?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getEmail());
			pstmt.setString(2, vo.getPassword());

			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = new UserVo();
				
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String gender = rs.getString(4);
				result.setNo(no);
				result.setName(name);
				result.setEmail(email);
				result.setGender(gender);
			}
			
		} catch (SQLException e) {
			throw new UserRepositoryException(e.toString());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public UserVo findByNo(Long no) {
		UserVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select no,name,email,password,gender from user where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1,no);

			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = new UserVo();
				result.setNo(rs.getLong(1));
				result.setName(rs.getString(2));
				result.setEmail(rs.getString(3));
				result.setPassword(rs.getString(4));
				result.setGender(rs.getString(5));
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public UserVo update(int i, UserVo vo,Long no) {
		UserVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = getConnection();
			switch(i) {
			case 1:
				sql = "update user set gender=? where no = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,vo.getGender());
				pstmt.setLong(2,no);
				break;
			case 2:
				sql = "update user set password = password(?),gender=? where no = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,vo.getPassword());
				pstmt.setString(2,vo.getGender());
				pstmt.setLong(3,no);
				break;
			case 3:
				sql = "update user set name=?,gender=? where no = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,vo.getName());
				pstmt.setString(2,vo.getGender());
				pstmt.setLong(3,no);
				break;
			case 4:
				sql = "update user set name=?, password = password(?),gender=? where no = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,vo.getName());
				pstmt.setString(2,vo.getPassword());
				pstmt.setString(3,vo.getGender());
				pstmt.setLong(4,no);
				break;
			default :
				sql = "";
				break;
			}
			pstmt.executeUpdate();
			result = findByNo(no);
			
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://192.168.10.111:3307/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
		return conn;
	}

}
