package com.iox.ast.func;
import com.iox.ast.InlineFun;
import com.iox.parser.*;
import com.iox.util.ErrorMsg;
import com.iox.util.ErrorType;
import com.iox.util.Util;
public class RtrimNode extends InlineFun {
	/*
	 * Constructor.
	 */
	public RtrimNode(Parser parser) {
		super(parser);
	}
	public Object eval() {
		Object parameter = getVariable("rtrimNodeParameter");
		if (Util.isDefaultValue(parameter)) {
			return ErrorMsg.exit(ErrorType.FEW_ARGUMENTS);
		} else if (Util.isString(parameter)) {			
			String resultStr = parameter.toString();
			int len = resultStr.length() - 1;
			int index = 0;
			while (Character.isWhitespace(resultStr.charAt(len - index))) {
				index++;
			}
			return resultStr.substring(0, resultStr.length() - index);
		} else {
			return ErrorMsg.exit(ErrorType.INVALID_PARAMS);
		}
	}
}
