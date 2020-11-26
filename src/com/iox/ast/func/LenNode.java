package com.iox.ast.func;
import com.iox.parser.*;
import com.iox.util.ErrorMsg;
import com.iox.util.ErrorType;
import com.iox.util.Util;
import com.iox.ast.InlineFun;

public class LenNode extends InlineFun{
	/*
	 * Constructor.
	 */
	public LenNode(Parser parser) {
		super(parser);
	}
	public Object eval() {
		Object parameter = getVariable("lenNodeParameter");
		if (Util.isDefaultValue(parameter)) {
			return ErrorMsg.exit(ErrorType.FEW_ARGUMENTS);
		} else if (Util.isString(parameter)) {			
			return parameter.toString().length();
		} else {
			return ErrorMsg.exit(ErrorType.INVALID_PARAMS);
		}
	}
}
