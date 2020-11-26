package com.iox.ast.func;
import com.iox.ast.InlineFun;
import com.iox.parser.*;
import com.iox.util.ErrorMsg;
import com.iox.util.ErrorType;
import com.iox.util.Util;
public class ReplicateNode extends InlineFun {
	/*
	 * Constructor
	 */
	public ReplicateNode(Parser parser) {
		super(parser);
	}
	public Object eval() {
		Object parameter = getVariable("replicateNodeParameter");
		Object parameter2 = getVariable("replicateNodeParameter2");
		if (Util.isDefaultValue(parameter) && Util.isDefaultValue(parameter2)) {
			return ErrorMsg.exit(ErrorType.FEW_ARGUMENTS);
		} else if (Util.isString(parameter) && Util.isValidInteger(parameter2)) {
			int size = Integer.parseInt(parameter2.toString());
			return String.format("%0" + size + "d", 0).replace("0", parameter.toString());
		} else {
			return ErrorMsg.exit(ErrorType.INVALID_PARAMS);
		}
	}
}
