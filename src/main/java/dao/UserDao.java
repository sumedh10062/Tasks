package dao;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import pojos.Role;
import pojos.User;

public interface UserDao {
//add a method to register user : via openSession
	String registerUser(User newUser);

	// add a method to register user : via getCurrentSession
	String registerUserViaGetCurrentSession(User newUser);
	// add a method to get user details by it's id
    User getUserDetailsById(int userId);
    //add a method to get all user details
    List<User> getAllUsers();
  //add a method to get user details by date n role
    List<User> getUsersByDateAndRole(LocalDate startDate,LocalDate endDate,Role userRole);
  //add a method to get user first names by role
    List<String> getUserFirstNamesByRole(Role userRole);
   //add a method to get partial user details , reged after a sepcified date n having specific role
    List<User> getPartialUserDetails(LocalDate date,Role userRole);
    //change password
    String changePassword(String email,String oldPwd,String newPwd);
    //apply discount to multiple users : bulk updation
    String applyBulkDiscount(double discount,LocalDate regDate);
    //un subscribe user
    String deleteUserDetails(int userId);
    //save image for the specific user
    String saveImage(int userId,String imageFilePath) throws IOException;

}
