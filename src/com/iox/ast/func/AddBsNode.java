package com.iox.ast.func;

import com.iox.ast.InlineFun;
import com.iox.parser.*;
import com.iox.util.ErrorMsg;
import com.iox.util.ErrorType;
import com.iox.util.Util;

import java.io.File;
public class AddBsNode extends InlineFun {
	/*
	 * Constructor.
	 */
	public AddBsNode(Parser parser) {
		super(parser);
	}
	public Object eval() {
		Object parameter = getVariable("addBsNodeParameter");
		if (Util.isDefaultValue(parameter)) {
			return ErrorMsg.exit(ErrorType.FEW_ARGUMENTS);
		} else if (Util.isString(parameter)) {
			return parameter.toString() + File.separator;
		} else {
			return ErrorMsg.exit(ErrorType.INVALID_PARAMS);			
		}
	}
}
