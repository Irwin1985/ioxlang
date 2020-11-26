package com.iox.ast.func;
import com.iox.parser.*;
import com.iox.util.ErrorType;
import com.iox.util.ErrorMsg;
import com.iox.util.Util;
import com.iox.ast.InlineFun;
public class StrTranNode extends InlineFun {
	/*
	 * Constructor.
	 */
	public StrTranNode(Parser parser) {
		super(parser);
	}
	public Object eval() {
		String resultStr = "";
		Object parameter = getVariable("strTranNodeParameter");
		Object parameter2 = getVariable("strTranNodeParameter2");
		Object parameter3 = getVariable("strTranNodeParameter3");
		if (Util.isDefaultValue(parameter2)) {
			ErrorMsg.exit(ErrorType.FEW_ARGUMENTS);
		}
		if (Util.isString(parameter) && Util.isString(parameter2)) {
			resultStr = parameter.toString().replace(parameter2.toString(), (Util.isDefaultValue(parameter3)) ? "" : parameter3.toString());
		} else {
			ErrorMsg.exit(ErrorType.INVALID_PARAMS);
		}
		return resultStr;
	}
}
