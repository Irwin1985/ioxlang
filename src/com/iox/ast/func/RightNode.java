package com.iox.ast.func;
import com.iox.ast.InlineFun;
import com.iox.parser.*;
import com.iox.util.ErrorMsg;
import com.iox.util.ErrorType;
import com.iox.util.Util;
public class RightNode extends InlineFun {
	/*
	 * Constructor.
	 */
	public RightNode(Parser parser) {
		super(parser);
	}

	public Object eval() {
		Object parameter = getVariable("rightNodeParameter");
		Object parameter2 = getVariable("rightNodeParameter2");
		if (Util.isDefaultValue(parameter) && Util.isDefaultValue(parameter2)) {
			return ErrorMsg.exit(ErrorType.FEW_ARGUMENTS);
		} else if (Util.isString(parameter) && Util.isInteger(parameter2)) {
			String expression = parameter.toString();
			int index = Integer.parseInt(parameter2.toString());
			int length = expression.length();
			if (index > length) {
				index = length;
			}
			return expression.substring(expression.length() - index);
		} else {
			return ErrorMsg.exit(ErrorType.INVALID_PARAMS);
		}
	}

}
