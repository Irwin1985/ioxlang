package com.iox.ast.func;

import com.iox.ast.InlineFun;
import com.iox.ast.IntegerNode;
import com.iox.parser.*;
public class PrintNode extends InlineFun{
	private boolean newLine = false;
	/*
	 * Contructor
	 */
	public PrintNode(Parser parser, boolean newLine) {
		super(parser);
		this.newLine = newLine;
	}
	public Object eval() {
		Object lastStmt = new IntegerNode(0);
		Object printMsg = getVariable("printNodeParameter");
		if (newLine) {
			System.out.println(printMsg);
		} else {
			System.out.print(printMsg);
		}
		return lastStmt;
	}
}
