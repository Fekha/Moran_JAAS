package ExamplePackage;

public class Employee {
	String FirstName;
	String Id;
	String Position;
	String NameAndIdOfSupervisor;
	String Salary;
	Boolean HasAccount = false;
	
	Employee(String FN, String ID, String POS, String NOS_IDOS,String SAL){
		FirstName = FN;
		Id = ID;
		Position = POS;
		NameAndIdOfSupervisor = NOS_IDOS;
		Salary = SAL;
	}
	Employee(String FN, String ID){
		FirstName = FN;
		Id = ID;
	}
	String FNID_Print(){
		return (FirstName + ":" + Id);
	}
}
	