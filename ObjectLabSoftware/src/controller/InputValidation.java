package controller;

public class InputValidation
{
	/* Constants */
	public static final int TYPE_UNKOWN = -1;
	public static final int TYPE_DOUBLE = 0;
	public static final int TYPE_STRING = 1;

	/* Object Type Validation */
	public static int getDataType(String valueString)
	{
		if(valueString == null) 
		{
			return TYPE_UNKOWN;
		}
		else if(isNumber(valueString))
		{
			return TYPE_DOUBLE;
		}
		/*else if(isAlpha(valueString))
		{
			return TYPE_STRING;
		}*/
		else
		{
			/*return TYPE_UNKOWN;*/
			return TYPE_STRING;
		}
	}

	/* String Input Validation */
	public static boolean isNumber(String input)
	{
		/* Checks string for 0-9 */
		if(input == null)
			return false;
		return input.matches("[0-9]+");
	}
	
	public static boolean isAlpha(String input)
	{
		/* Checks string for a-z */
		if(input == null)
			return false;
		return input.matches("^[a-zA-Z]*$");
	}
	
	public static boolean isEmpty(String input)
	{
		/* Checks string for nothing */
		if(input == null)
			return false;
		return input.equals("");
	}
	
	/* Tools for validation */
	public static int boolToInt(Boolean myBool)
	{
		return(myBool) ? 1 : 0;
	}
}
