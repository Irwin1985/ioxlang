package com.iox.ast.func;
import com.iox.ast.InlineFun;
import com.iox.parser.*;
import com.iox.util.ErrorMsg;
import com.iox.util.ErrorType;
import com.iox.util.Util;
public class AscNode extends InlineFun {
	public AscNode(Parser parser) {
		super(parser);
	}
	public Object eval() {
		Object parameter = getVariable("ascNodeParameter");
		if (Util.isDefaultValue(parameter)) {
			return ErrorMsg.exit(ErrorType.FEW_ARGUMENTS);
		} else if (Util.isString(parameter)) {
			return (int) parameter.toString().charAt(0);
		} else {
			return ErrorMsg.exit(ErrorType.INVALID_PARAMS);			
		}
	}
}
