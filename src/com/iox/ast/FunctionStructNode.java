package com.iox.ast;
import java.util.ArrayList;
import java.util.List;
public class FunctionStructNode extends Node{
	private List<String> parameterDefinition;
	private boolean mandatoryParams = true;
	private Node functionBlock;
	/*
	 * Constructor.
	 */
	public FunctionStructNode(List<String> parameterDefinition, Node functionBlock) {
		this.parameterDefinition = parameterDefinition;
		this.functionBlock = functionBlock;
	}
	/*
	 * Constructor with optional parameter.
	 */
	public FunctionStructNode(List<String> parameterDefinition, Node functionBlock, boolean mandatoryParams) {
		this.parameterDefinition = parameterDefinition;
		this.functionBlock = functionBlock;
		this.mandatoryParams = mandatoryParams;
	}	
	@Override
	public Object eval() {
		List<Object> funcDef = new ArrayList<Object>();
		funcDef.add(parameterDefinition);
		funcDef.add(functionBlock);
		funcDef.add(mandatoryParams);
		return funcDef;
	}
}
