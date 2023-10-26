package pojos;
/*
 *  (PK) ,name,email,password,confirmPassword,role(enum), regAmount;
	 LocalDate/Date regDate;
	 byte[] image;
 */

import java.time.LocalDate;
import javax.persistence.*;

@Entity // mandatory cls level anno to tell hibnernate/JPA : following is an entity :
		// whose life cycle to be managed by HIb.
@Table(name = "users_tbl") // specifies table name
public class User {
	@Id // MANDATORY field level or propert level anno : PK constraint
	// @GeneratedValue //auto generation of PK : def strategy : table
	// for Mysql DB :
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "user_id") //col name : user_id
	// id gen will be auto : as per : auto_increment
	private Integer userId;// Till hibernate 5.x , it's MANDATORY , to make unique ID prop : Serializable
	// reco by Gavin King : Integer / Long
	@Column(name="first_name",length = 30)//length : varchar size
	private String firstName;
	@Column(name="last_name",length = 30)//length : varchar size
	private String lastName;
	@Column(length = 30,unique = true)//=> unique constraint
	private String email;
	@Column(length = 20,nullable = false)//=> not null constraint
	private String password;
	@Transient //=> skip from persistence : no col generation
	private String confirmPassword;
	@Enumerated(EnumType.STRING) //=> col type : varchar : enum const name
	@Column(name="user_role",length = 20)
	private Role userRole;
	@Column(name="reg_amount")
	private double regAmount;
	@Column(name="reg_date")//def col type : date 
	private LocalDate regDate;
	@Lob //=> large object , byte[] --> longblob : col type
	private byte[] image;

	// def ctor : MANDATORY
	public User() {
		System.out.println("in def ctor " + getClass());
	}

	public User(String firstName, String lastName, String email, String password, String confirmPassword, Role userRole,
			double regAmount, LocalDate regDate) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.userRole = userRole;
		this.regAmount = regAmount;
		this.regDate = regDate;
	}
	

	public User(String lastName, double regAmount, LocalDate regDate) {
		super();
		this.lastName = lastName;
		this.regAmount = regAmount;
		this.regDate = regDate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Role getUserRole() {
		return userRole;
	}

	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}

	public double getRegAmount() {
		return regAmount;
	}

	public void setRegAmount(double regAmount) {
		this.regAmount = regAmount;
	}

	public LocalDate getRegDate() {
		return regDate;
	}

	public void setRegDate(LocalDate regDate) {
		this.regDate = regDate;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", userRole=" + userRole + ", regAmount=" + regAmount + ", regDate=" + regDate + "]";
	}

	
}
