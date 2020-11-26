package com.iox.ast;

import com.iox.lexer.TokenType;
import com.iox.util.Util;

/*
 * BinaryOpNode handles binaries operators like
 * +, -, *, /, <, >, <=, >=, ==, !=
 * Example tree for 1 + 2:
 * 			(+)
 * 		(1)		(2)
 */
public class BinaryOpNode extends Node {
	private Node nodeLeft;
	private Node nodeRight;
	private TokenType type;
	/*
	 * Constructor
	 */
	public BinaryOpNode(TokenType type, Node nodeLeft, Node nodeRight) {
		this.type = type;
		this.nodeLeft = nodeLeft;
		this.nodeRight = nodeRight;
	}

	@Override
	public Object eval() {
		Object result = null;
		Object leftVal = nodeLeft.eval();
		Object rightVal = nodeRight.eval();
		switch(type) {
		// Arithmetic Operators
		case ADD:
			if (Util.isNumber(leftVal) && Util.isNumber(rightVal)) {				
				if (Util.isInteger(leftVal) && Util.isInteger(rightVal)) {				
					result = Util.parseInteger(TokenType.ADD, leftVal, rightVal);
				} else {
					result = Util.parseDouble(TokenType.ADD, leftVal, rightVal);
				}
			} else {
				result = leftVal.toString() + rightVal.toString();
			}
			break;
		case SUB:
			if (Util.isInteger(leftVal) && Util.isInteger(rightVal)) {				
				result = Util.parseInteger(TokenType.SUB, leftVal, rightVal);
			} else {
				result = Util.parseDouble(TokenType.SUB, leftVal, rightVal);
			}
			break;
		case MUL:
			if (Util.isInteger(leftVal) && Util.isInteger(rightVal)) {				
				result = Util.parseInteger(TokenType.MUL, leftVal, rightVal);
			} else {
				result = Util.parseDouble(TokenType.MUL, leftVal, rightVal);
			}
			break;
		case DIV:
			if (Double.parseDouble(rightVal.toString()) == 0) {
				System.out.println("Error: Division by Zero.");
				System.exit(0);
			} else {				
				result = Double.parseDouble(leftVal.toString()) / Double.parseDouble(rightVal.toString());
			}			
			break;
		case POW:
			result = Util.parseDouble(TokenType.POW, leftVal, rightVal);
			break;
		// Relational Operator
		case LESS:
			result = Util.parseRelation(TokenType.LESS, leftVal, rightVal);
			break;
		case GREATER:
			result = Util.parseRelation(TokenType.GREATER, leftVal, rightVal);
			break;
		case LESSEQUAL:
			result = Util.parseRelation(TokenType.LESSEQUAL, leftVal, rightVal);
			break;
		case GREATEREQUAL:
			result = Util.parseRelation(TokenType.GREATEREQUAL, leftVal, rightVal);
			break;
		case EQUAL:
			result = Util.parseRelation(TokenType.EQUAL, leftVal, rightVal);
			break;
		case NOTEQUAL:
			result = Util.parseRelation(TokenType.NOTEQUAL, leftVal, rightVal);
			break;
		// Logical Operator
		case OR:
			result = Boolean.valueOf(Boolean.parseBoolean(leftVal.toString()) || Boolean.parseBoolean(rightVal.toString()));
			break;
		case AND:
			result = Boolean.valueOf(Boolean.parseBoolean(leftVal.toString()) && Boolean.parseBoolean(rightVal.toString()));
			break;
		default:
			System.out.println("unknown binary operator '" + type + "'");
			System.exit(-1);
			break;
		}
		return result;
	}
}