package com.razor.photo.organizer.console;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class ExceptionHelper {

	public static String getStackTrace(Exception ex){
	    Writer result = new StringWriter();
	    PrintWriter printWriter = new PrintWriter(result);
	    ex.printStackTrace(printWriter);
	    return result.toString();		
	}
}
