package ExamplePackage;

public class Account {
	String FirstName;
	String Id;
	String Username;
	String Password;
	
	Account(String FN, String ID, String USER, String PASS){
		FirstName = FN;
		Id = ID;
		Username = USER;
		Password = PASS;
	}
	Account(String FN, String ID){
		FirstName = FN;
		Id = ID;
	}
	String FNID_Print(){
		return (FirstName + ":" + Id);
	}
}