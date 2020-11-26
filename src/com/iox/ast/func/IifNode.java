package com.iox.ast.func;
import com.iox.ast.InlineFun;
import com.iox.parser.*;
import com.iox.util.ErrorMsg;
import com.iox.util.ErrorType;
import com.iox.util.Util;
public class IifNode extends InlineFun {
	/*
	 * Constructor.
	 */
	public IifNode(Parser parser) {
		super(parser);
	}
	public Object eval() {
		Object result = null;
		Object parameter1 = getVariable("iifNodeParameter");
		Object parameter2 = getVariable("iifNodeParameter2");
		Object parameter3 = getVariable("iifNodeParameter3");
		if (Util.isDefaultValue(parameter1) && Util.isDefaultValue(parameter2) && Util.isDefaultValue(parameter3)) {
			ErrorMsg.exit(ErrorType.FEW_ARGUMENTS);
		}
		if (Util.isBoolean(parameter1)) {
			Boolean expression1 = Boolean.valueOf(parameter1.toString());
			result = (expression1) ? parameter2 : parameter3;
		} else {
			ErrorMsg.exit(ErrorType.INVALID_PARAMS);
		}
		return result;
	}
}
