package com.iox.ast.func;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.iox.ast.InlineFun;
import com.iox.parser.*;
import com.iox.util.Util;

public class DateNode extends InlineFun {
	/*
	 * Constructor.
	 */
	public DateNode(Parser parser) {
		super(parser);
	}
	public Object eval() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(new Date());
		Object parameter1 = getVariable("dateNodeParameter");
		Object parameter2 = getVariable("dateNodeParameter2");
		Object parameter3 = getVariable("dateNodeParameter3");
		
		if (!Util.isDefaultValue(parameter1) && !Util.isDefaultValue(parameter2) && !Util.isDefaultValue(parameter3)) {
			Integer year = Integer.parseInt(parameter1.toString());
			Integer month = Integer.parseInt(parameter2.toString());
			Integer day = Integer.parseInt(parameter3.toString());
			if (year > 0 && month > 0 && day > 0) {
				date = String.format("%s-%s-%s", parameter1.toString(), parameter2.toString(), parameter3.toString());
			} else {
				System.out.println("error: function, argument value, type, or count is invalid.");
				System.exit(-1);
			}
		} 
		return date;
	}
}
