package com.iox.ast.func;
import com.iox.ast.InlineFun;
import com.iox.parser.*;
import com.iox.util.*;

public class AlltrimNode extends InlineFun {
	public AlltrimNode(Parser parser) {
		super(parser);
	}
	public Object eval() {
		Object parameter = getVariable("alltrimNodeParameter");
		String result = "";
		if (Util.isDefaultValue(parameter)) {
			ErrorMsg.exit(ErrorType.FEW_ARGUMENTS);
		} else if (Util.isString(parameter)) {
			result = parameter.toString().trim();
		} else {
			ErrorMsg.exit(ErrorType.INVALID_PARAMS);
		}
		return result;
	}
}
