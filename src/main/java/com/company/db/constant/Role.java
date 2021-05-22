package com.company.db.constant;


import com.company.db.entity.User;


/**
 * Enum for user Roles
 */
public enum Role {
	USER, ADMIN;


	public static Role getRole(User user) {
		int roleId = user.getRoleId();
		return Role.values()[roleId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}

	public int getId(){
		Role[]roles = Role.values();
		for (int i=0;i<roles.length;i++){
			if(this.equals(roles[i])){
				return i;
			}
		}
		return -1;
	}
	
}
