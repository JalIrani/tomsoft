package ObjectLabEnterpriseSoftware;

public class InputValdidation
{
		
	/* Object Type Validation
	
	isDouble()
	{
		
	} */
			
	/* String Input Validation */
	
	public static int isNumber(String input)
	{
		return boolToInt(input.matches("[0-9]+"));
	}
	
	public static int isAlpha(String input)
	{
		return boolToInt(input.matches("^[a-zA-Z]*$"));
	}
	
	/* Tools for validation */
	
	public static int boolToInt(Boolean myBool)
	{
		return(myBool) ? 1 : 0;
	}
}
