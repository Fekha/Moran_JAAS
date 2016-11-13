package ExamplePackage;
import java.io.IOException;
import java.util.Scanner;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;


public class CallBackHandlerExample implements CallbackHandler {

	/* 
	 * Checks what authentication handlers the user has configured and calls them.
	 * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
	 */
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		int numCallBacks = 0; 
		for (int i=0; i < callbacks.length; i++) {
			/* 
			 * A Namecallback simply requests a username from the user.
			 */
			if (callbacks[i] instanceof NameCallback) {
				NameCallback user = (NameCallback) callbacks[i];
				// Get username
				System.out.print(user.getPrompt() + " ");
				// read in the username from the console and set the username.
				user.setName((new Scanner(System.in).next()));
				// one call back done. 
				numCallBacks++;
				
			} else if (callbacks[i] instanceof PasswordCallback) {
				PasswordCallback userpass = (PasswordCallback) callbacks[i];
				//Get password from user.
				System.out.print(userpass.getPrompt() + " ");
				// setPassword requires a character array as parameter.
				// so we read in the password as a string and convert to 
				// charArray. 
				userpass.setPassword((new Scanner(System.in).next()).toCharArray());
				// another callback done.
				numCallBacks++;
			}
		}
		/* Error check - if there was an error in processing either username 
		 * or password, throw an exception.  
		 */
		if (numCallBacks < 2) {
			throw new UnsupportedCallbackException(callbacks[0], 
					"Invalid or no callbacks.");
		}
	
		
	}

}
