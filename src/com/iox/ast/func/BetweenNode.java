package com.iox.ast.func;
import com.iox.ast.InlineFun;
import com.iox.parser.*;
import com.iox.util.ErrorMsg;
import com.iox.util.ErrorType;
import com.iox.util.Util;
public class BetweenNode extends InlineFun {
	/*
	 * Constructor.
	 */
	public BetweenNode(Parser parser) {
		super(parser);
	}
	
	public Object eval() {
		Object result = null;
		
		Object parameter1 = getVariable("betweenNodeParameter1");
		Object parameter2 = getVariable("betweenNodeParameter2");
		Object parameter3 = getVariable("betweenNodeParameter3");
		if (Util.isDefaultValue(parameter1) && Util.isDefaultValue(parameter2) && Util.isDefaultValue(parameter3)) {
			ErrorMsg.exit(ErrorType.FEW_ARGUMENTS);
		}
		if (Util.isString(parameter1) && Util.isString(parameter2) && Util.isString(parameter3)) {
			char testValue = parameter1.toString().charAt(0);
			char lowValue = parameter2.toString().charAt(0);
			char highValue = parameter3.toString().charAt(0);
			result = (testValue >= lowValue && testValue <= highValue) ? true : false;
			
		} else if (Util.isNumber(parameter1) && Util.isNumber(parameter2) && Util.isNumber(parameter3)) {
			Double testValue = Double.parseDouble(parameter1.toString());
			Double lowValue = Double.parseDouble(parameter2.toString());
			Double highValue = Double.parseDouble(parameter3.toString());
			result = (testValue >= lowValue && testValue <= highValue) ? true : false;
		} else if (Util.isBoolean(parameter1) && Util.isBoolean(parameter2) && Util.isBoolean(parameter3)) {
			Boolean testValue = Boolean.valueOf(parameter1.toString());
			Boolean lowValue = Boolean.valueOf(parameter2.toString());
			Boolean highValue = Boolean.valueOf(parameter3.toString());
			result = (testValue == lowValue && highValue) ? true : false;
		} else {
			ErrorMsg.exit(ErrorType.INVALID_PARAMS);
		}
		return result;
	}
}
