package com.iox.util;
/*
 * Util for miscelaneous operations.
 */

import com.iox.lexer.TokenType;
import com.iox.lexer.Tokenizer;
import java.util.List;
import java.util.ArrayList;
import com.iox.lexer.Token;
import com.iox.ast.*;
import com.iox.parser.*;
import com.iox.ast.func.*;
import com.iox.ast.func.sql.SqlConnectionStringNode;
import com.iox.ast.func.sql.SqlDisconnectNode;
public class Util {
	/*
	 * Print message.
	 */
	public static void Write(Object message) {
		System.out.print(message);
	}
	/*
	 * Prints and Carriage Return.
	 */
	public static void Writeln(Object message) {
		System.out.println(message);
	}
	/*
	 * Empty line.
	 */
	public static void Writeln() {
		System.out.println();
	}
	/*
	 * Checks if the node parameter class type belongs to Double.
	 */
	public static boolean isInteger(Object node) {
		return node.getClass() == Integer.class;
	}
	/*
	 * isValidInteger = positive integer.
	 */
	public static boolean isValidInteger(Object param) {
		return param.getClass() == Integer.class && Integer.parseInt(param.toString()) > 0;
	}
	/*
	 * Checks if the node parameter class type is Integer or Double.
	 */
	public static boolean isNumber(Object node) {
		return node.getClass() == Integer.class 
				|| node.getClass() == Double.class;
	}
	/*
	 * Checks if the node parameter class type belongs to Integer.
	 */
	public static boolean isDouble(Object node) {
		return node.getClass() == Double.class;
	}
	/*
	 * Checks if the node parameters class type belongs to String
	 */
	public static boolean isString(Object node) {
		return node.getClass() == String.class;
	}
	/*
	 * Checks if the node class type belongs to Boolean
	 */
	public static boolean isBoolean(Object node) {
		return node.getClass() == Boolean.class;
	}
	/*
	 * isAlpha checks for a valid identifier character.
	 */
	public static boolean isAlpha(char chr) {
		return Character.isLetter(chr) || chr == '_';
	}
	/*
	 * Performs an Arithmetic operation using Integers converted nodes.
	 */
	public static int parseInteger(TokenType operator, Object nodeLeft, Object nodeRight) {
		int result = 0;
		switch(operator) {
		case ADD:
			result = Integer.parseInt(nodeLeft.toString()) + Integer.parseInt(nodeRight.toString());
			break;
		case SUB:
			result = Integer.parseInt(nodeLeft.toString()) - Integer.parseInt(nodeRight.toString());
			break;
		case MUL:
			result = Integer.parseInt(nodeLeft.toString()) * Integer.parseInt(nodeRight.toString());
			break;
		default:
			break;
		}
		return result;
	}
	/*
	 * Performs an Arithmetic operation using Doubles converted nodes.
	 */
	public static Object parseDouble(TokenType operator, Object nodeLeft, Object nodeRight) {
		double result = 0;
		switch(operator) {
		case ADD:
			result = Double.parseDouble(nodeLeft.toString()) + Double.parseDouble(nodeRight.toString());
			break;
		case SUB:
			result = Double.parseDouble(nodeLeft.toString()) - Double.parseDouble(nodeRight.toString());
			break;
		case MUL:
			result = Double.parseDouble(nodeLeft.toString()) * Double.parseDouble(nodeRight.toString());
			break;
		case POW:
			result = Math.pow(Double.parseDouble(nodeLeft.toString()), Double.parseDouble(nodeRight.toString()));
			break;
		default:
			break;
		}
		return result;
	}
	/*
	 * Performs a relation operation using Integers or Doubles nodes.
	 */
	public static boolean parseRelation(TokenType type, Object nodeLeft, Object nodeRight) {
		boolean result = false;
		switch(type) {
		case LESS:
			if (isInteger(nodeLeft) && isInteger(nodeRight)) {
				result = Boolean.valueOf(Integer.parseInt(nodeLeft.toString()) < Integer.parseInt(nodeRight.toString()));
			} else if(isDouble(nodeLeft) && isDouble(nodeRight)) {
				result = Boolean.valueOf(Double.parseDouble(nodeLeft.toString()) < Double.parseDouble(nodeRight.toString()));
			} else if (isInteger(nodeLeft) && isDouble(nodeRight)) {
				result = Boolean.valueOf(Integer.parseInt(nodeLeft.toString()) < Double.parseDouble(nodeRight.toString()));
			} else if (isDouble(nodeLeft) && isInteger(nodeRight)) {
				result = Boolean.valueOf(Double.parseDouble(nodeLeft.toString()) < Integer.parseInt(nodeRight.toString()));
			}
			break;
		case GREATER:
			if (isInteger(nodeLeft) && isInteger(nodeRight)) {
				result = Boolean.valueOf(Integer.parseInt(nodeLeft.toString()) > Integer.parseInt(nodeRight.toString()));
			} else if(isDouble(nodeLeft) && isDouble(nodeRight)) {
				result = Boolean.valueOf(Double.parseDouble(nodeLeft.toString()) > Double.parseDouble(nodeRight.toString()));
			} else if (isInteger(nodeLeft) && isDouble(nodeRight)) {
				result = Boolean.valueOf(Integer.parseInt(nodeLeft.toString()) > Double.parseDouble(nodeRight.toString()));
			} else if (isDouble(nodeLeft) && isInteger(nodeRight)) {
				result = Boolean.valueOf(Double.parseDouble(nodeLeft.toString()) > Integer.parseInt(nodeRight.toString()));
			}			
			break;
		case LESSEQUAL:
			if (isInteger(nodeLeft) && isInteger(nodeRight)) {
				result = Boolean.valueOf(Integer.parseInt(nodeLeft.toString()) <= Integer.parseInt(nodeRight.toString()));
			} else if(isDouble(nodeLeft) && isDouble(nodeRight)) {
				result = Boolean.valueOf(Double.parseDouble(nodeLeft.toString()) <= Double.parseDouble(nodeRight.toString()));
			} else if (isInteger(nodeLeft) && isDouble(nodeRight)) {
				result = Boolean.valueOf(Integer.parseInt(nodeLeft.toString()) <= Double.parseDouble(nodeRight.toString()));
			} else if (isDouble(nodeLeft) && isInteger(nodeRight)) {
				result = Boolean.valueOf(Double.parseDouble(nodeLeft.toString()) <= Integer.parseInt(nodeRight.toString()));
			}
			break;
		case GREATEREQUAL:
			if (isInteger(nodeLeft) && isInteger(nodeRight)) {
				result = Boolean.valueOf(Integer.parseInt(nodeLeft.toString()) >= Integer.parseInt(nodeRight.toString()));
			} else if(isDouble(nodeLeft) && isDouble(nodeRight)) {
				result = Boolean.valueOf(Double.parseDouble(nodeLeft.toString()) >= Double.parseDouble(nodeRight.toString()));
			} else if (isInteger(nodeLeft) && isDouble(nodeRight)) {
				result = Boolean.valueOf(Integer.parseInt(nodeLeft.toString()) >= Double.parseDouble(nodeRight.toString()));
			} else if (isDouble(nodeLeft) && isInteger(nodeRight)) {
				result = Boolean.valueOf(Double.parseDouble(nodeLeft.toString()) >= Integer.parseInt(nodeRight.toString()));
			}
			break;
		case EQUAL:
			if (isInteger(nodeLeft) && isInteger(nodeRight)) {
				result = Boolean.valueOf(Integer.parseInt(nodeLeft.toString()) == Integer.parseInt(nodeRight.toString()));
			} else if(isDouble(nodeLeft) && isDouble(nodeRight)) {
				result = Boolean.valueOf(Double.parseDouble(nodeLeft.toString()) == Double.parseDouble(nodeRight.toString()));
			} else if (isInteger(nodeLeft) && isDouble(nodeRight)) {
				result = Boolean.valueOf(Integer.parseInt(nodeLeft.toString()) == Double.parseDouble(nodeRight.toString()));
			} else if (isDouble(nodeLeft) && isInteger(nodeRight)) {
				result = Boolean.valueOf(Double.parseDouble(nodeLeft.toString()) == Integer.parseInt(nodeRight.toString()));
			}
			break;
		case NOTEQUAL:
			if (isInteger(nodeLeft) && isInteger(nodeRight)) {
				result = Boolean.valueOf(Integer.parseInt(nodeLeft.toString()) != Integer.parseInt(nodeRight.toString()));
			} else if(isDouble(nodeLeft) && isDouble(nodeRight)) {
				result = Boolean.valueOf(Double.parseDouble(nodeLeft.toString()) != Double.parseDouble(nodeRight.toString()));
			} else if (isInteger(nodeLeft) && isDouble(nodeRight)) {
				result = Boolean.valueOf(Integer.parseInt(nodeLeft.toString()) != Double.parseDouble(nodeRight.toString()));
			} else if (isDouble(nodeLeft) && isInteger(nodeRight)) {
				result = Boolean.valueOf(Double.parseDouble(nodeLeft.toString()) != Integer.parseInt(nodeRight.toString()));
			}
			break;
		default:
			break;
		}
		return result;
	}
	/*
	 * PrettyPrint: outputs all tokens.
	 */
	public static void prettyPrint(List<Token> tokens) {
		for (Token token: tokens) {
			System.out.println("Type: " + token.type + " Value: <'" + token.value + "'> at " + token.lineNumber + ":" + token.columnNumber);
		}
	}
	/*
	 * isRelOp
	 * checks if the current token type is a:
	 * '<' | '>' | '<=' | '>=' | '==' | '!='
	 */
	public static boolean isRelOp(TokenType type) {
		return type == TokenType.LESS
				|| type == TokenType.GREATER
				|| type == TokenType.LESSEQUAL
				|| type == TokenType.GREATEREQUAL
				|| type == TokenType.EQUAL
				|| type == TokenType.NOTEQUAL;
	}
	/*
	 * isAddOp
	 * checks if the current token type is a: '+' | '-'
	 */
	public static boolean isAddOp(TokenType type) {
		return type == TokenType.ADD
				|| type == TokenType.SUB;
	}
	/*
	 * isMulOp
	 * checks if the current token type is a: '*' | '/'
	 */
	public static boolean isMulOp(TokenType type) {
		return type == TokenType.MUL
				|| type == TokenType.DIV;
	}
	/*
	 * Finds and retrieve the token type.
	 */
	public static TokenType getTokenType(String word, Tokenizer tokenizer) {
		TokenType type = TokenType.IDENTIFIER;
		switch(word) {
		case "iox":
			type = TokenType.IOX;
			break;
		case "end":
			type = TokenType.END;
			break;
		case "true":
			type = TokenType.TRUE;
			break;
		case "false":
			type = TokenType.FALSE;
			break;
		case "and":
			type = TokenType.AND;
			break;
		case "or":
			type = TokenType.OR;
			break;
		case "while":
			type = TokenType.WHILE;
			break;
		case "exit":
			type = TokenType.EXIT;
			break;
		case "if":
			type = TokenType.IF;
			break;
		case "elif":
			type = TokenType.ELIF;
			break;
		case "else":
			type = TokenType.ELSE;
			break;
		case "do":
			type = TokenType.DO;
			break;
		case "case":
			type = TokenType.CASE;
			break;
		case "default":
			type = TokenType.DEFAULT;
			break;
		case "until":
			type = TokenType.UNTIL;
			break;
		case "for":
			type = TokenType.FOR;
			break;
		case "to":
			type = TokenType.TO;
			break;
		case "step":
			type = TokenType.STEP;
			break;
		case "func":
			type = TokenType.FUNC;
			tokenizer.funCounter++;
			break;
		case "return":
			type = TokenType.RETURN;
			break;
		default:
			break;
		}
		return type;
	}
	/*
	 * Create Inline Functions
	 */
	public static List<Node> createInlineFunctions(Parser parser){
		List<Node> inlineFuncBlock = new ArrayList<Node>();
		// abs
		inlineFuncBlock.add(createAbsFunction(parser));
		// addbs
		inlineFuncBlock.add(createAddBsFunction(parser));
		// alltrim
		inlineFuncBlock.add(createAlltrimFunction(parser));
		// asc
		inlineFuncBlock.add(createAscFunction(parser));
		// at
		inlineFuncBlock.add(createAtFunction(parser));
		// between
		inlineFuncBlock.add(createBetweenFunction(parser));
		// date
		inlineFuncBlock.add(createDateFunction(parser));
		// empty
		inlineFuncBlock.add(createEmptyFunction(parser));
		// iif
		inlineFuncBlock.add(createIifFunction(parser));
		// print
		inlineFuncBlock.add(createPrintFunction("print", parser, false));
		// println
		inlineFuncBlock.add(createPrintFunction("println", parser, true));
		// len
		inlineFuncBlock.add(createLenFunction(parser));
		// left
		inlineFuncBlock.add(createLeftFunction(parser));
		// lTrim
		inlineFuncBlock.add(createLtrimFunction(parser));
		// right
		inlineFuncBlock.add(createRightFunction(parser));
		// rTrim
		inlineFuncBlock.add(createRtrimFunction(parser));
		// strTran
		inlineFuncBlock.add(createStrTranFunction(parser));
		// SqlConnectionString
		inlineFuncBlock.add(createSqlConnectionStringFunction(parser));
		// SqlDisconnect
		inlineFuncBlock.add(createSqlDisconnectFunction(parser));
		// padl
		inlineFuncBlock.add(createPadlFunction(parser));
		// padr
		inlineFuncBlock.add(createPadrFunction(parser));
		// replicate
		inlineFuncBlock.add(createReplicateFunction(parser));
		
		return inlineFuncBlock;
	}
	/*
	 * abs = 'abs' '(' expression ')'
	 */
	private static Node createAbsFunction(Parser parser) {
		List<String> paramList = new ArrayList<String>();
		paramList.add("absNodeParameter");
		List<Node> block = new ArrayList<Node>();
		block.add(new AbsNode(parser));
		Node funcStruct = new FunctionStructNode(paramList, new BlockNode(block));
		return new AssignmentNode("abs", funcStruct, parser);
	}	
	/*
	 * createAddBsFunction
	 */
	private static Node createAddBsFunction(Parser parser) {
		List<String> paramList = new ArrayList<String>();
		List<Node> block = new ArrayList<Node>();
		Node funcStruct = null;
		paramList.add("addBsNodeParameter");
		block.add(new AddBsNode(parser));
		funcStruct = new FunctionStructNode(paramList, new BlockNode(block));
		return new AssignmentNode("addbs", funcStruct, parser);
	}
	/*
	 * createAlltrimFunction
	 */
	private static Node createAlltrimFunction(Parser parser) {
		List<String> paramList = new ArrayList<String>();
		List<Node> block = new ArrayList<Node>();
		Node funcStruct = null;
		paramList.add("alltrimNodeParameter");
		block.add(new AlltrimNode(parser));
		funcStruct = new FunctionStructNode(paramList, new BlockNode(block));
		return new AssignmentNode("alltrim", funcStruct, parser);
	}
	/*
	 * createAscFunction
	 */
	 private static Node createAscFunction(Parser parser) {
		 List<String> paramList = new ArrayList<String>();
		 List<Node> block = new ArrayList<Node>();
		 FunctionStructNode funcStruct = null;
		 paramList.add("ascNodeParameter");
		 block.add(new AscNode(parser));
		 funcStruct = new FunctionStructNode(paramList, new BlockNode(block));
		 return new AssignmentNode("asc", funcStruct, parser);
	 }
	 /*
	  * createAtFunction
	  */
	 private static Node createAtFunction(Parser parser) {
		 List<String> paramList = new ArrayList<String>();
		 List<Node> block = new ArrayList<Node>();
		 Node funcStruct = null;
		 paramList.add("atNodeParameter");
		 paramList.add("atNodeParameter2");
		 paramList.add("atNodeParameter3");
		 block.add(new AtNode(parser));
		 funcStruct = new FunctionStructNode(paramList, new BlockNode(block));
		 return new AssignmentNode("at", funcStruct, parser);
	 }
	/*
	 * createBetweenFunction
	 */
	 private static Node createBetweenFunction(Parser parser) {
		 List<String> paramList = new ArrayList<String>();
		 List<Node> block = new ArrayList<Node>();
		 Node funcStruct = null;
		 paramList.add("betweenNodeParameter1");
		 paramList.add("betweenNodeParameter2");
		 paramList.add("betweenNodeParameter3");
		 block.add(new BetweenNode(parser));
		 funcStruct = new FunctionStructNode(paramList, new BlockNode(block));
		 return new AssignmentNode("between", funcStruct, parser);
	 }
	/*
	 * createDateFunction
	 */
	 private static Node createDateFunction(Parser parser) {
		 List<String> paramList = new ArrayList<String>();
		 List<Node> block = new ArrayList<Node>();
		 Node funcStruct = null;
		 paramList.add("dateNodeParameter");
		 paramList.add("dateNodeParameter2");
		 paramList.add("dateNodeParameter3");
		 block.add(new DateNode(parser));
		 funcStruct = new FunctionStructNode(paramList, new BlockNode(block), false);
		 return new AssignmentNode("date", funcStruct, parser);
	 }
	/*
	 * createEmptyFunction
	 */
	 private static Node createEmptyFunction(Parser parser) {
		 List<String> paramList = new ArrayList<String>();
		 List<Node> block = new ArrayList<Node>();
		 Node funcStruct = null;
		 paramList.add("emptyNodeParameter");
		 block.add(new EmptyNode(parser));
		 funcStruct = new FunctionStructNode(paramList, new BlockNode(block));
		 return new AssignmentNode("empty", funcStruct, parser);
	 }
	/*
	 * createIifFunction
	 */
	 private static Node createIifFunction(Parser parser) {
		 List<String> paramList = new ArrayList<String>();
		 List<Node> block = new ArrayList<Node>();
		 Node funcStruct = null;
		 paramList.add("iifNodeParameter");
		 paramList.add("iifNodeParameter2");
		 paramList.add("iifNodeParameter3");
		 block.add(new IifNode(parser));
		 funcStruct = new FunctionStructNode(paramList, new BlockNode(block));
		 return new AssignmentNode("iif", funcStruct, parser);
	 }
	/*
	 * lenStmt = 'len' '(' expression ')'
	 */
	private static Node createLenFunction(Parser parser) {
		List<String> paramList = new ArrayList<String>();
		paramList.add("lenNodeParameter");
		List<Node> block = new ArrayList<Node>();
		block.add(new LenNode(parser));
		Node funcStruct = new FunctionStructNode(paramList, new BlockNode(block));
		return new AssignmentNode("len", funcStruct, parser);
	}
	/*
	 * createLeftFunction
	 */
	private static Node createLeftFunction(Parser parser) {
		List<String> paramList = new ArrayList<String>();
		List<Node> block = new ArrayList<Node>();
		Node funcStruct = null;
		paramList.add("leftNodeParameter");
		paramList.add("leftNodeParameter2");
		block.add(new LeftNode(parser));
		funcStruct = new FunctionStructNode(paramList, new BlockNode(block));
		return new AssignmentNode("left", funcStruct, parser);
	}
	/*
	 * createLtrimFunction
	 */
	private static Node createLtrimFunction(Parser parser) {
		List<String> paramList = new ArrayList<String>();
		List<Node> block = new ArrayList<Node>();
		Node funcStruct = null;
		paramList.add("ltrimNodeParameter");
		block.add(new LtrimNode(parser));
		funcStruct = new FunctionStructNode(paramList, new BlockNode(block));
		return new AssignmentNode("ltrim", funcStruct, parser);
	}
	/*
	 * createRightFunction
	 */
	public static Node createRightFunction(Parser parser) {
		List<String> paramList = new ArrayList<String>();
		List<Node> block = new ArrayList<Node>();
		Node funcStruct = null;
		paramList.add("rightNodeParameter");
		paramList.add("rightNodeParameter2");
		block.add(new RightNode(parser));
		funcStruct = new FunctionStructNode(paramList, new BlockNode(block));
		return new AssignmentNode("right", funcStruct, parser);
	}
	/*
	 * createRtrimFunction
	 */
	private static Node createRtrimFunction(Parser parser) {
		List<String> paramList = new ArrayList<String>();
		List<Node> block = new ArrayList<Node>();
		Node funcStruct = null;
		paramList.add("rtrimNodeParameter");
		block.add(new RtrimNode(parser));
		funcStruct = new FunctionStructNode(paramList, new BlockNode(block));
		return new AssignmentNode("rtrim", funcStruct, parser);
	}	
	/*
	 * createPrintFunction
	 */
	private static Node createPrintFunction(String funcName, Parser parser, boolean newLine) {
		List<String> paramList = new ArrayList<String>();
		
		paramList.add("printNodeParameter");
		List<Node> block = new ArrayList<Node>();
		block.add(new PrintNode(parser, newLine));
		Node funcStruct = new FunctionStructNode(paramList, new BlockNode(block)); 
		return new AssignmentNode(funcName, funcStruct, parser);
	}
	/*
	 * createSqlStringConnectionFunction
	 */
	public static Node createSqlConnectionStringFunction(Parser parser) {
		List<String> paramList = new ArrayList<String>();
		List<Node> block = new ArrayList<Node>();
		Node funcStruct = null;
		paramList.add("sqlConnectionStringNodeParameter");
		paramList.add("sqlConnectionStringNodeParameter2");
		block.add(new SqlConnectionStringNode(parser));
		funcStruct = new FunctionStructNode(paramList, new BlockNode(block));
		return new AssignmentNode("sqlconnectionstring", funcStruct, parser);
	}
	/*
	 * createSqlDisconnectFunction
	 */
	public static Node createSqlDisconnectFunction(Parser parser) {
		List<String> paramList = new ArrayList<String>();
		List<Node> block = new ArrayList<Node>();
		Node funcStruct = null;
		paramList.add("sqlDisconnectNodeParameter");
		block.add(new SqlDisconnectNode(parser));
		funcStruct = new FunctionStructNode(paramList, new BlockNode(block));
		return new AssignmentNode("sqldisconnect", funcStruct, parser);
	}	
	/*
	 * createStrTranFunction
	 */
	public static Node createStrTranFunction(Parser parser) {
		List<String> paramList = new ArrayList<String>();
		List<Node> block = new ArrayList<Node>();
		Node funcStruct = null;
		paramList.add("strTranNodeParameter");
		paramList.add("strTranNodeParameter2");
		paramList.add("strTranNodeParameter3");
		block.add(new StrTranNode(parser));
		funcStruct = new FunctionStructNode(paramList, new BlockNode(block), true);
		return new AssignmentNode("strtran", funcStruct, parser);
	}
	/*
	 * createPadlFunction
	 */
	public static Node createPadlFunction(Parser parser) {
		List<String> paramList = new ArrayList<String>();
		List<Node> block = new ArrayList<Node>();
		Node funcStruct = null;
		paramList.add("padlNodeParameter");
		paramList.add("padlNodeParameter2");
		paramList.add("padlNodeParameter3");
		block.add(new PadlNode(parser));
		funcStruct = new FunctionStructNode(paramList, new BlockNode(block), true);
		return new AssignmentNode("padl", funcStruct, parser);
	}
	/*
	 * createPadrFunction
	 */
	public static Node createPadrFunction(Parser parser) {
		List<String> paramList = new ArrayList<String>();
		List<Node> block = new ArrayList<Node>();
		Node funcStruct = null;
		paramList.add("padrNodeParameter");
		paramList.add("padrNodeParameter2");
		paramList.add("padrNodeParameter3");
		block.add(new PadrNode(parser));
		funcStruct = new FunctionStructNode(paramList, new BlockNode(block), true);
		return new AssignmentNode("padr", funcStruct, parser);
	}
	/*
	 * createReplicateFunction
	 */
	public static Node createReplicateFunction(Parser parser) {
		List<String> paramList = new ArrayList<String>();
		List<Node> block = new ArrayList<Node>();
		Node funcStruct = null;
		paramList.add("replicateNodeParameter");
		paramList.add("replicateNodeParameter2");
		block.add(new ReplicateNode(parser));
		funcStruct = new FunctionStructNode(paramList, new BlockNode(block));
		return new AssignmentNode("replicate", funcStruct, parser);
	}
	/*
	 * Check if the last sentence is a 'return' or 'exit'
	 */
	public static boolean isReturnOrExit(Object lastStmt) {
		return lastStmt != null && (lastStmt.toString().equals("exit") || lastStmt.toString().equals("return"));
	}
	/*
	 * occurs internal function.
	 */
	public static List<Integer> occurs(String wilcard, String str){
		List<Integer> occurs = new ArrayList<Integer>();
		int len = wilcard.length();
		String chunk = "";
		for (int index = 0; index < str.length(); index++) {
			if (index + len > str.length()) {
				break;
			} else {
				chunk = str.substring(index, index + len);
				if (chunk.equals(wilcard)) {
					occurs.add(index + 1);
				}
			}
		}
		return occurs;
	}
	/*
	 * isDefaultValue
	 * checks if the string belongs to IntegerClass
	 * if it is so then is empty by default.
	 */
	public static boolean isDefaultValue(Object str) {
		return str.getClass() == Integer.class && str.toString().equals("0");
	}
}
