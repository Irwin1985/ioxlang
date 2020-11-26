package com.iox.ast;

public class ReturnNode extends Node{
	public ReturnNode() {}
	@Override
	public Object eval() {
		return "return";
	}
}
