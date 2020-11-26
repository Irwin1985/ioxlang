package com.iox.ast;
import com.iox.ast.Node;
public class NullNode extends Node{
	private Node nullValue;
	
	public NullNode(Node nullVal) {
		this.nullValue = nullVal;
	}
	
	public Object eval() {
		return nullValue;
	}
}
