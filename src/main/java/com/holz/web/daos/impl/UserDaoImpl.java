package com.holz.web.daos.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.holz.web.daos.UserDao;
import com.holz.web.models.User;
import com.holz.web.models.enums.RoleType;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired 
	JdbcTemplate jdbcTemplate; 

	String SELECT_USER = "SELECT U.username, U.firstName, U.lastName, U.email, U.enabled, U.userId, U.forcePasswordReset, U.farmId, "+
				"(SELECT GROUP_CONCAT(R.role) FROM ROLES R WHERE U.username = R.username) AS roles "+
				"FROM USERS U ";
	@Override
	public List<User> getUsersForFarm(int farmId) {
		String sql = SELECT_USER + "WHERE U.farmId=" + farmId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<User>>() {
			@Override
			public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<User> users = new ArrayList<User>();
				while(rs.next()) {
					User u = buildUser(rs);
					users.add(u);
				}
				return users;
			}
		});
	}
	
	private User buildUser(ResultSet rs) throws SQLException{
		User u = new User();
		u.setEmail(rs.getString("email"));
		u.setUsername(rs.getString("username"));
		u.setFirstName(rs.getString("firstName"));
		u.setLastName(rs.getString("lastName"));
		u.setEnabled(rs.getBoolean("enabled"));
		u.setUserId(rs.getInt("userId"));
		u.setForcePasswordReset(rs.getBoolean("forcePasswordReset"));
		String[] arr = rs.getString("roles").split(",");
		List<RoleType> roles = new ArrayList<RoleType>();
		for(int i = 0; i < arr.length; i++){
			roles.add(RoleType.valueOf(arr[i].trim()));
		}
		u.setRoles(roles);
		return u;
	}

	@Override
	public void saveOrUpdate(User user, int farmId) {
		String sql = "INSERT INTO USERS "+
				"VALUES (?,?,?,?,?,?,?,?,?)";
		if(user.getId() != 0){
			sql = "UPDATE USERS SET firstName=?,lastName=?, email=?,farmId=?,enabled=? where userId =?";
			this.jdbcTemplate.update(sql, new Object[]{user.getFirstName(),user.getLastName(), user.getEmail(),farmId, user.isEnabled(),user.getId()});
		} else {
			this.jdbcTemplate.update(sql, new Object[]{user.getId(), user.getUsername(),user.getFirstName(),user.getLastName(), user.getEmail(),user.getPassword(),farmId, user.isEnabled(),true});
		}
		
		//Update roles (deletes old ones and adds new ones)
		this.jdbcTemplate.update("DELETE FROM ROLES WHERE username=?", new Object[]{user.getUsername()});
		String insertRoles = "";
		Object[] obj = new Object[user.getRoles().size()*2];
		int i = 0;
		for(RoleType r : user.getRoles()){
			insertRoles = insertRoles + ",(?,?)";
			obj[i] = user.getUsername();
			i++;
			obj[i] = r.toString();
			i++;
		}
		this.jdbcTemplate.update("INSERT INTO ROLES VALUES " + insertRoles.substring(1),obj);
	}

	@Override
	public void updatePassword(String username, String password, boolean isReset) {
		String sql =  "UPDATE USERS SET password=?, forcePasswordReset=? WHERE username=?";
		this.jdbcTemplate.update(sql, new Object[]{password,isReset,username});
	}

	@Override
	public void enableDisableUser(User user, int farmId) {
		String sql =  "UPDATE USERS SET enabled =? WHERE userId=? AND farmId=?";
		this.jdbcTemplate.update(sql, new Object[]{user.isEnabled(),user.getId(),farmId});
	}

	@Override
	public User getUser(String usernameOrEmail, boolean useEmail) {
		String sql = SELECT_USER + "WHERE " + (useEmail ? "U.email=?" : "U.username=?");
		return jdbcTemplate.query(sql,new Object[]{usernameOrEmail}, new ResultSetExtractor<User>() {
			@Override
			public User extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.first()) {
					return buildUser(rs);
				}
				return null;
			}
		});
	}
	
	@Override
	public List<String> getAdminUserEmailsForFarm(int farmId) {
		String sql = "SELECT U.email FROM USERS U JOIN ROLES R ON R.username = U.username WHERE R.role = 'Admin' AND U.farmId = "+farmId;
		return jdbcTemplate.query(sql,new Object[]{}, new ResultSetExtractor<List<String>>() {
			@Override
			public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<String> emails = new ArrayList<String>();
				while(rs.next()) {					
					emails.add(rs.getString("email"));
				}
				return emails;
			}
		});
	}

}