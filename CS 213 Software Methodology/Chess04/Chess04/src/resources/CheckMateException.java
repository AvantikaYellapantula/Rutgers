package resources;

public class CheckMateException extends Exception {

	/**
	 * 
	 */
	public String message;
	private static final long serialVersionUID = 1L;

	public CheckMateException() {
		// TODO Auto-generated constructor stub
	}
	
	public CheckMateException(colors colorCheck)
	{
		
		char[] chArray = colorCheck.toString().toLowerCase().toCharArray();
		chArray[0] = Character.toUpperCase(chArray[0]);
		String resignColor="";
		
		for(int i = 0; i<chArray.length; i++)
			resignColor+=chArray[i];
		
		message = (resignColor + " is in checkmate!\nGame ended");
		
		
	}

	public CheckMateException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CheckMateException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public CheckMateException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CheckMateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
