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

	@Override
	public List<User> getUsersForFarm(int farmId) {
		String sql = "SELECT U.username, U.firstName, U.lastName, U.email, U.enabled, U.userId, "+
				"(SELECT GROUP_CONCAT(R.role) FROM ROLES R WHERE U.username = R.username) AS roles "+
				"FROM USERS U WHERE U.farmId=" + farmId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<User>>() {
			@Override
			public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<User> users = new ArrayList<User>();
				while(rs.next()) {
					User u = new User();
					u.setEmail(rs.getString("email"));
					u.setUsername(rs.getString("username"));
					u.setFirstName(rs.getString("firstName"));
					u.setLastName(rs.getString("lastName"));
					u.setEnabled(rs.getBoolean("enabled"));
					u.setUserId(rs.getInt("userId"));
					String[] arr = rs.getString("roles").split(",");
					List<RoleType> roles = new ArrayList<RoleType>();
					for(int i = 0; i < arr.length; i++){
						roles.add(RoleType.valueOf(arr[i].trim()));
					}
					u.setRoles(roles);
					users.add(u);
				}
				return users;
			}
		});
	}

	@Override
	public void saveOrUpdate(User user, int farmId) {
		String sql = "INSERT INTO USERS "+
				"VALUES (?,?,?,?,?,?,?,?)";
		if(user.getId() != 0){
			sql = "UPDATE USERS SET firstName=?,lastName=?, email=?,farmId=?,enabled=? where userId =?";
			this.jdbcTemplate.update(sql, new Object[]{user.getFirstName(),user.getLastName(), user.getEmail(),farmId, user.isEnabled(),user.getId()});
		} else {
			this.jdbcTemplate.update(sql, new Object[]{user.getId(), user.getUsername(),user.getFirstName(),user.getLastName(), user.getEmail(),user.getPassword(),farmId, user.isEnabled()});
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
	public void updatePassword(String username, String password) {
		String sql =  "UPDATE USERS SET password=? WHERE username=?";
		this.jdbcTemplate.update(sql, new Object[]{password,username});
	}

	@Override
	public void enableDisableUser(User user, int farmId) {
		String sql =  "UPDATE USERS SET enabled =? WHERE userId=? AND farmId=?";
		this.jdbcTemplate.update(sql, new Object[]{user.isEnabled(),user.getId(),farmId});
	}

}