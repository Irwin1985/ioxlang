package com.iox.ast;
import java.util.List;
import com.iox.parser.*;
public class FunctionCallNode extends Node{
	private String funName;
	private List<Node> paramVal;
	private Parser parser;
	private static final int PARAMETERS_DEFINITION = 0;
	private static final int FUNCTION_BLOCK = 1;
	private static final int MANDATORY_PARAMS = 2;
	/*
	 * Constructor.
	 */
	public FunctionCallNode(String funName, List<Node> paramVal, Parser parser) {
		this.funName = funName;
		this.paramVal = paramVal;
		this.parser = parser;
	}
	@Override
	public Object eval() {
		Object lastStmt = new Integer(0);
		boolean garbageCollector = false;
		boolean mandatoryParams = true;
		
		@SuppressWarnings("unchecked")
		List<Object> funcDef = (List<Object>)parser.getVariable(funName);
		if (funcDef == null) {			
			System.out.println("function '" + funName + "' does not exist.");
			System.exit(-1);
		}
		@SuppressWarnings("unchecked")
		List<String> paramDef = (List<String>)funcDef.get(PARAMETERS_DEFINITION);
		Node funcBlock = (Node)funcDef.get(FUNCTION_BLOCK);
		mandatoryParams = (Boolean)funcDef.get(MANDATORY_PARAMS);
		
		int paramDefSize = (paramDef != null) ? paramDef.size() : 0;
		int paramValSize = (paramVal != null) ? paramVal.size() : 0;
		
		if (mandatoryParams && paramDefSize > 0 && paramValSize == 0) { // has mandatory params and passed nothing.
			//System.out.println("function '" + funName + "' was expecting " + paramDefSize + " parameter(s) but got nothing.");
			System.out.println("error: too few arguments in function '" + funName + "'");
			System.exit(-1);
		} else if (paramDefSize == 0 && paramValSize > 0) { // hasn't params and passed any.
			System.out.println("unexpected parameter in function '" + funName + "'");
			System.exit(-1);
		} else if (paramDefSize > 0 && paramValSize > 0) {
			if (paramDefSize < paramValSize) { // passed more params than expected.				
				System.out.println("must specify additional parameters in function '" + funName + "'");
				System.exit(-1);
			} else if (!mandatoryParams && paramDefSize > paramValSize) {
				System.out.println("error: too few arguments in function '" + funName + "'");
				System.exit(-1);				
			} else if (paramDefSize > paramValSize){ // passed less params that expected.
				int index = 0;
				garbageCollector = true;
				for(String paramName: paramDef) {
					AssignmentNode assignNode = null;
					if (index < paramValSize) {
						assignNode = new AssignmentNode(paramName, paramVal.get(index), parser);
					} else {
						// remaining parameters will be zero by default.
						assignNode = new AssignmentNode(paramName, new IntegerNode(0), parser);
					}
					assignNode.eval();
					index++;
				}				
			} else { // both params match each other.
				int index = 0;
				garbageCollector = true;
				for(String paramName: paramDef) {
					AssignmentNode assignNode = new AssignmentNode(paramName, paramVal.get(index), parser);
					assignNode.eval();
					index++;
				}				
			}
		} else if (!mandatoryParams && paramDefSize > 0) {
			// all parameters will be set to zero.
			for(String paramName: paramDef) {
				Node assignNode = new AssignmentNode(paramName, new IntegerNode(0), parser);
				assignNode.eval();
			}
		}
		// Execute Function block
		if (funcBlock != null) {
			lastStmt = funcBlock.eval();
		}
		// Garbage Collector
		if (garbageCollector) {
			for (String paramName: paramDef) {
				parser.removeVariable(paramName);
			}
		}
		return lastStmt;
	}
}
