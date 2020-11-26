package com.iox.ast.func;
import com.iox.ast.InlineFun;
import com.iox.parser.*;
import com.iox.util.ErrorMsg;
import com.iox.util.ErrorType;
import com.iox.util.Util;
public class EmptyNode extends InlineFun {
	/*
	 * Constructor.
	 */
	public EmptyNode (Parser parser) {
		super(parser);
	}
	
	public Object eval() {
		boolean empty = true;
		Object parameter = getVariable("emptyNodeParameter");
		if (Util.isNumber(parameter)) {
			empty = (Double.parseDouble(parameter.toString()) == 0);
		} else if (Util.isString(parameter)) {
			empty = (parameter.toString().length() == 0);
		} else if (Util.isBoolean(parameter)) {
			empty = (Boolean.valueOf(parameter.toString()) == false);
		} else {
			ErrorMsg.exit(ErrorType.FEW_ARGUMENTS);
		}
		return empty;
	}
}
