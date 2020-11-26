package com.iox.ast.func;
import com.iox.ast.InlineFun;
import com.iox.parser.*;
import com.iox.util.ErrorMsg;
import com.iox.util.ErrorType;
import com.iox.util.Util;
public class PadrNode extends InlineFun {
	/*
	 * Constructor.
	 */
	public PadrNode(Parser parser) {
		super(parser);
	}
	public Object eval() {
		Object parameter = getVariable("padrNodeParameter");
		Object parameter2 = getVariable("padrNodeParameter2");
		Object parameter3 = getVariable("padrNodeParameter3");
		if (Util.isDefaultValue(parameter) && Util.isDefaultValue(parameter2) && Util.isDefaultValue(parameter3)) {
			return ErrorMsg.exit(ErrorType.FEW_ARGUMENTS);
		} else if (Util.isString(parameter) && Util.isInteger(parameter2) && Util.isString(parameter3)) {
			int size = Integer.parseInt(parameter2.toString());
			if (size < 0) {
				return ErrorMsg.exit(ErrorType.INVALID_PARAMS);	
			}
			String expression = parameter.toString();
			String padStr = parameter3.toString();
			if (size <= expression.length()) {
				return expression.substring(0, size);
			} else {
				String result = String.format("%" + (-size) + "s", expression).replace(" ", padStr);
				return result;
			}
		} else {
			return ErrorMsg.exit(ErrorType.INVALID_PARAMS);
		}
	}
}
