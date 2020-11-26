package com.iox.ast.func;
import com.iox.parser.*;
import com.iox.util.ErrorMsg;
import com.iox.util.ErrorType;
import com.iox.util.Util;
import com.iox.ast.InlineFun;
public class LeftNode extends InlineFun {
	/*
	 * Constructor
	 */
	public LeftNode(Parser parser) {
		super(parser);
	}
	public Object eval() {
		Object parameter = getVariable("leftNodeParameter");
		Object parameter2 = getVariable("leftNodeParameter2");
		
		if (Util.isDefaultValue(parameter) && Util.isDefaultValue(parameter2)) {
			return ErrorMsg.exit(ErrorType.FEW_ARGUMENTS);
		} else if (Util.isString(parameter) && Util.isInteger(parameter2)) {
			String expression = parameter.toString();
			int length = expression.length();
			int index = Integer.parseInt(parameter2.toString());
			if (index > length) {
				index = length;
			}
			return expression.substring(0, index);
		} else {
			return ErrorMsg.exit(ErrorType.INVALID_PARAMS);
		}
	}
}
