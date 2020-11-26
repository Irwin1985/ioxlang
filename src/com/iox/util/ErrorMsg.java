package com.iox.util;

public class ErrorMsg {
	public static int exit(ErrorType type) {
		String message = "error: ";
		switch(type) {
		case INVALID_PARAMS:
			message += "function argument value, type or count is invalid.";
			break;
		case FEW_ARGUMENTS:
			message += "too few arguments.";
			break;
		default:
			message += "unknown error message.";
			break;
		}
		System.out.println(message);
		System.exit(1);
		return 1;
	}
}
