package com.iox.ast.func;
import com.iox.ast.InlineFun;
import com.iox.parser.*;
import com.iox.util.ErrorMsg;
import com.iox.util.ErrorType;
import com.iox.util.Util;
public class LtrimNode extends InlineFun {
	/*
	 * Constructor.
	 */
	public LtrimNode(Parser parser) {
		super(parser);
	}
	public Object eval() {
		Object parameter = getVariable("ltrimNodeParameter");
		if (Util.isDefaultValue(parameter)) {
			return ErrorMsg.exit(ErrorType.FEW_ARGUMENTS);
		} else if (Util.isString(parameter)) {			
			String resultStr = parameter.toString();
			int index = 0;
			while (index < resultStr.length() && Character.isWhitespace(resultStr.charAt(index))) {
				index++;
			}
			return resultStr.substring(index);
		} else {
			return ErrorMsg.exit(ErrorType.INVALID_PARAMS);
		}
	}
}
