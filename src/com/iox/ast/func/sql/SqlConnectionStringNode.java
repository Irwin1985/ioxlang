package com.iox.ast.func.sql;
import com.iox.ast.InlineFun;
import com.iox.parser.*;
import com.iox.util.ErrorMsg;
import com.iox.util.ErrorType;
import com.iox.util.Util;
import java.sql.*;
public class SqlConnectionStringNode extends InlineFun {
	private static int handle = 0;
	/*
	 * Establishes a connection to a data source using a connection string.
	 */
	public SqlConnectionStringNode(Parser parser) {
		super(parser);
	}
	public Object eval() {
		Object parameter = getVariable("sqlConnectionStringNodeParameter");
		if (Util.isDefaultValue(parameter)) {
			return ErrorMsg.exit(ErrorType.FEW_ARGUMENTS);
		} else if (Util.isString(parameter)) {
			String str = parameter.toString();
			if (str.length() > 0 ) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "1234");
					handle++;
					parser.setVariable("sqlConnectionStringNodeConnectionClass" + handle, con);
				} catch(Exception e) {
					System.out.println(e);
					handle = -1;
					System.exit(-1);
				}
			} else {
				return ErrorMsg.exit(ErrorType.INVALID_PARAMS);
			}
		} else {
			return ErrorMsg.exit(ErrorType.INVALID_PARAMS);
		}
		return handle;
	}
}
