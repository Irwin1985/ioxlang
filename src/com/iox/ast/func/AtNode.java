package com.iox.ast.func;
import com.iox.ast.InlineFun;
import com.iox.parser.*;
import com.iox.util.ErrorMsg;
import com.iox.util.ErrorType;
import com.iox.util.Util;
import java.util.List;
public class AtNode extends InlineFun {
	/*
	 * Constructor.
	 */
	public AtNode(Parser parser) {
		super(parser);
	}
	public Object eval() {

		Object parameter1 = getVariable("atNodeParameter");
		Object parameter2 = getVariable("atNodeParameter2");
		
		if (Util.isDefaultValue(parameter1) || Util.isDefaultValue(parameter2)) {
			return ErrorMsg.exit(ErrorType.FEW_ARGUMENTS);
		} else if (Util.isString(parameter1) && Util.isString(parameter2)){			
			int result = 0;
			String searchExpression = parameter1.toString();
			String expressionSearched = parameter2.toString();
			
			Integer occurrences = Integer.parseInt(getVariable("atNodeParameter3").toString()) - 1;
			if (occurrences < 0) {
				occurrences = 0;
			}
			
			if (occurrences >= 0) {			
				List<Integer> occurs = Util.occurs(searchExpression, expressionSearched);
				if (occurs != null && occurs.size() > 0) {
					if (occurrences < occurs.size()) {
						result = occurs.get(occurrences);
					}
				}
			}
			return result;
		} else {
			return ErrorMsg.exit(ErrorType.INVALID_PARAMS);
		}
		
	}
}
