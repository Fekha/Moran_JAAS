package ExamplePackage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilePermission;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.security.Principal;

import javax.security.auth.Subject;
import javax.security.auth.login.*;

import ExamplePackage.RootAction; 


/* 
 * This class demonstrates 
 *        how to create a logincontext;
 *        how to invoke the login method; 
 *        how to store and extract the logged in subject.
 * This class is also the driver program. 
 * This class uses: 
 *       class LoginModuleExample: implements login method 
 *                                 to perform authentication, 
 *                                 creates a Subject object, and
 *                                 implements the logout method.                     
 */

public class LoginContextExample {
	
		
    static LoginContext lc; 	
    static ArrayList<Employee> list = new ArrayList<Employee>();
    
    public static void main(String[] args) {
    	
		LoginContextExample lce = new LoginContextExample();
		
		/* Create a call back handler. This call back handler will be populated with
                 * different callbacks by the various login modules. For example, 
                 * if a login module implements a username/password style login, it populates this object
                 * with NameCallback (to get username) and PasswordCallback (which gets password).
		 */
		CallBackHandlerExample cbe = new CallBackHandlerExample(); 
		
		/* Create a new login context. 
		 * @param Policy Name : We defined a policy in the file JAASPolicy.txt 
		 *                      and it is called "JAASExample"
		 * @param Call Back Handler
		 */
		try {
			lc = new LoginContext("JAASExample", cbe);
		}
		catch (LoginException e) {
			System.err.println("Login exception."); 
		}
		
		/*tests
		list = getAccounts();
		for(Employee emp : list) {
			System.out.println(emp.FNID_Print());
	    }

		addEmployee(new Employee("a","b","c","d","e"));
		list = getEmployees();
		*/
		
		/* 
		 * Perform login.
		 */
		try {
			lc.login(); 
			
			// If we reach this point then login has succeeded.
			System.out.println("You are now logged in!");
			/* 
			 * Print the various Principals attached with the logged Subject.
			 * In this example, we attach only one principal with each subject.
			 */
			Subject loggedUser = lc.getSubject();
			Set principals = lc.getSubject().getPrincipals();
			Iterator i = principals.iterator();
			while (i.hasNext()) {
				String s = ((Principal)i.next()).getName();
				/* 
				 *  An example of how to perform authorization actions. 
				 *  E.g., class RootAction contains the methods that a root can 
				 *  perform. 
				 */
				if (s.equals("root")) {
					RootAction ra = new RootAction();
					ra.runAsRoot(loggedUser);
				}
			}
		}		
		catch (LoginException e) {
			System.out.println("Username/password incorrect! " + e);	
		}
		catch (SecurityException e) {
			System.out.println(" " + e);
		}
	}
    
    public static ArrayList<Employee> getEmployees(){
		ArrayList<Employee> EDBL = new ArrayList<Employee>();
		String[] emps = null;
        String strTxt = "example/EmployeeDB.txt";

        try {
        	BufferedReader bufferedReader = new BufferedReader(new FileReader(strTxt));
        	String line = bufferedReader.readLine();
            while(line != null) {
                System.out.println(line);
                emps=line.split(":");
                EDBL.add(new Employee(emps[0],emps[1],emps[2],emps[3],emps[4]));
                line = bufferedReader.readLine();
            }   
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Make sure EmployeeDB is in your src folder!");                
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
		return EDBL;
	}

	public static void addEmployee(Employee e){
			System.out.print(e.FirstName+e.Id+e.Position+e.NameAndIdOfSupervisor+e.Salary);
		    
		    FileWriter fWriter = null;
		    BufferedWriter writer = null;
		    try {
		    	String text = e.FirstName+":"+e.Id+":"+e.Position+":"+e.NameAndIdOfSupervisor+":"+e.Salary;
				fWriter = new FileWriter("example/EmployeeDB.txt", true);
				writer = new BufferedWriter(fWriter);
				writer.write(text);
				writer.newLine();
				writer.close();
		    } catch (Exception c) {
		    	c.printStackTrace();
		    }
	}
	public static void addAccount(String FN, String ID, String User, String Pass){    
	    FileWriter fWriter = null;
	    BufferedWriter writer = null;
	    try {
	    	String text = FN+":"+ ID+":"+ User+":"+Pass;
			fWriter = new FileWriter("example/AccountDB.txt", true);
			writer = new BufferedWriter(fWriter);
			writer.write(text);
			writer.newLine();
			writer.close();
	    } catch (Exception c) {
	    	c.printStackTrace();
	    }
	}
}
