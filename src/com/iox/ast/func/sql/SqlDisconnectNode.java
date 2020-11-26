package com.iox.ast.func.sql;
import java.sql.Connection;

import com.iox.ast.InlineFun;
import com.iox.parser.*;
import com.iox.util.ErrorMsg;
import com.iox.util.ErrorType;
import com.iox.util.Util;
public class SqlDisconnectNode extends InlineFun {
	/*
	 * Constructor.
	 */
	public SqlDisconnectNode(Parser parser) {
		super(parser);
	}
	public Object eval() {
		Object parameter = getVariable("sqlDisconnectNodeParameter");
		if (Util.isDefaultValue(parameter)) {
			return ErrorMsg.exit(ErrorType.FEW_ARGUMENTS);
		} else if (Util.isValidInteger(parameter)) {
			try {				
				String variableName = "sqlConnectionStringNodeConnectionClass" + Integer.parseInt(parameter.toString());
				Connection con = (Connection)getVariable(variableName);
				con.close();
				return true;
			} catch (Exception e) {
				System.out.println(e);
				System.exit(-1);
				return false;
			}
		} else {
			return ErrorMsg.exit(ErrorType.INVALID_PARAMS);
		}
	}
}
