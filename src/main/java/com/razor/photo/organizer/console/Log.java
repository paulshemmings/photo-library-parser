package com.razor.photo.organizer.console;

public class Log {
	
	private static boolean loggingInformation = true;
	private static boolean loggingWarnings = true;
	private static boolean loggingErrors = true;
	
	public static void setLoggingVerbosity(boolean infoOn, boolean warningsOn, boolean errorsOn){
		loggingInformation = infoOn;
		loggingWarnings = warningsOn;
		loggingErrors = errorsOn;
	}
	
	public static void i(String format, Object ... objects){
		String i = String.format(format, objects);
		i(i);
	}
	public static void w(String format, Object ... objects){
		String i = String.format(format, objects);
		w(i);
	}
	public static void e(String format, Object ... objects){
		String i = String.format(format, objects);
		e(i);
	}
	public static void a(String format, Object ... objects){
		String i = String.format(format, objects);
		a(i);
	}	
	
	public static void i(String i){
		if(loggingInformation) logToSystem("Information",i);
	}
	public static void w(String i){
		if(loggingWarnings) logToSystem("Warning",i);
	}
	public static void e(String i){
		if(loggingErrors) logToSystem("Error",i);
	}	
	public static void a(String i){
		logToSystem("Always Shows",i);
	}	
	private static void logToSystem(String type, String i){
		String ip = String.format("%s: %s: %s",type,Thread.currentThread().getName(),i);
		System.out.println(ip);
	}	
}
