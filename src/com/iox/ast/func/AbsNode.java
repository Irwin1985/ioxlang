package com.iox.ast.func;
import com.iox.ast.InlineFun;
import com.iox.parser.*;
import com.iox.util.ErrorMsg;
import com.iox.util.ErrorType;
import com.iox.util.Util;

import java.lang.Math;

public class AbsNode extends InlineFun{
	public AbsNode(Parser parser) {
		super(parser);
	}

	public Object eval() {
		Object parameter = getVariable("absNodeParameter");
		if (Util.isDefaultValue(parameter)) {
			return ErrorMsg.exit(ErrorType.FEW_ARGUMENTS);
		} else if (Util.isString(parameter) || Util.isBoolean(parameter)) {
			return ErrorMsg.exit(ErrorType.INVALID_PARAMS);
		} else if (parameter.toString().contains(".")) {
			return Math.abs(Double.parseDouble(parameter.toString()));
		} else {			
			return Math.abs(Integer.parseInt(parameter.toString()));
		}
	}
}
