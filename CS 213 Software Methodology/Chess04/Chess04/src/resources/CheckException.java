package resources;

public class CheckException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String message;

	public CheckException() {
		// TODO Auto-generated constructor stub
	}

	public CheckException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	public CheckException(colors colorCheck)
	{
		
		char[] chArray = colorCheck.toString().toLowerCase().toCharArray();
		chArray[0] = Character.toUpperCase(chArray[0]);
		String resignColor="";
		
		for(int i = 0; i<chArray.length; i++)
			resignColor+=chArray[i];
		
		message = (resignColor + " is in check!");
		
		
	}

	public CheckException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public CheckException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
