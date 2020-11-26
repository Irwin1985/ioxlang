package com.iox.parser;
/*
 * Parser: analyze all iox grammar
 * and generates one AST Node type.
 */

import java.util.List;
import com.iox.lexer.Token;
import com.iox.lexer.TokenType;
import com.iox.util.Util;
import com.iox.ast.*;
import com.iox.ast.func.SayNode;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
	private int currentTokenPosition = 0;
	private List<Token> tokens;
	private HashMap<String, Object> symbolTable = new HashMap<String, Object>();
	/*
	 * default constructor.
	 */
	public Parser() {};
	/*
	 * Single constructor.
	 */
	public Parser(List<Token> tokens) {
		this.tokens = tokens;
	}
	/*
	 * parseUserDefinedFunctions
	 * create new tokens without function definition
	 * return List<Node> with functions parsed already.
	 */
	public List<Node> parseUserDefinedFunctions(){
		List<Node> funcListNode = new ArrayList<Node>();
		List<Token> newTokens = new ArrayList<Token>();
		while (currentToken().type != TokenType.EOF) {
			if (currentToken().type == TokenType.FUNC) {				
				funcListNode.add(functionDefinition());
			} else {
				newTokens.add(currentToken());
				eatToken(1);
			}
		}
		// assign new tokens without functions.
		setTokens(newTokens);
		return funcListNode;
	}	
	/*
	 * Restart the parser.
	 */
	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
		this.currentTokenPosition = 0;
	}
	/*
	 * if is not EOF then returns
	 * the requested token.
	 */
	private Token getToken(int offset) {
		Token token = new Token(TokenType.EOF, "");
		if (currentTokenPosition + offset < tokens.size()) {
			token = tokens.get(currentTokenPosition + offset);
		}
		return token;
	}
	/*
	 * Returns current token.
	 */
	private Token currentToken() {
		return getToken(0);
	}
	/*
	 * Returns next token.
	 */
	private Token nextToken() {
		return getToken(1);
	}
	/*
	 * matchAndEat
	 */
	public Token matchAndEat(TokenType type) {
		Token token = currentToken();
		if (token.type == type) {
			eatToken(1);
		} else {
			System.out.println("Parse Error: expected '" + type + "' and got '" + token.type + "'");
			System.exit(-1);
		}
		return token;
	}
	/*
	 * eatToken
	 */
	private void eatToken(int offset) {
		currentTokenPosition += offset;
	}
	/*
	 * setVariable = add a variable into the 'symbolTable'
	 */
	public void setVariable(String varName, Object varValue) {
		symbolTable.put(varName, varValue);
	}
	/*
	 * getVariable = get a variable value from 'symbolTable'
	 */
	public Object getVariable(String varName) {
		return symbolTable.get(varName);
	}
	/*
	 * removeVariable
	 */
	public void removeVariable(String varName) {
		symbolTable.remove(varName);
	}
	//***************************************************************************************//
	//******************************** expression parsing starts ****************************//
	//***************************************************************************************//
	/*
	 * expression = booleanExpression
	 */
	private Node expression() {
		return booleanExpression();
	}
	/*
	 * booleanExpression = booleanTerm { 'OR' booleanTerm }
	 */
	private Node booleanExpression() {
		Node nodeTerm = booleanTerm();
		if (currentToken().type == TokenType.OR) {
			matchAndEat(TokenType.OR);
			nodeTerm = new BinaryOpNode(TokenType.OR, nodeTerm, booleanTerm()); 
		}
		return nodeTerm;
	}
	/*
	 * booleanTerm = booleanFactor { 'AND' booleanFactor }
	 */
	private Node booleanTerm() {
		Node nodeFactor = negatedBooleanFactor();
		if (currentToken().type == TokenType.AND) {
			matchAndEat(TokenType.AND);
			nodeFactor = new BinaryOpNode(TokenType.AND, nodeFactor, negatedBooleanFactor());
		}
		return nodeFactor;
	}
	/*
	 * negatedBooleanFactor = [NOT] booleanFactor
	 */
	private Node negatedBooleanFactor() {
		Node negatedNode = null;
		if (currentToken().type == TokenType.NOT) {
			matchAndEat(TokenType.NOT);
			negatedNode = new NegatedBooleanNode(booleanFactor());
		} else {
			negatedNode = booleanFactor();
		}
		return negatedNode;
	}
	/*
	 * booleanFactor = TRUE | FALSE | Relation
	 */
	private Node booleanFactor() {
		Node nodeAtomic = null;
		switch(currentToken().type) {
		case TRUE:
			matchAndEat(TokenType.TRUE);
			nodeAtomic = new BooleanNode(true);
			break;
		case FALSE:
			matchAndEat(TokenType.FALSE);
			nodeAtomic = new BooleanNode(false);
			break;
		default:
			nodeAtomic = relation();
			break;
		}
		return nodeAtomic;
	}
	/*
	 * relation = arithmeticExpression relOp arithmeticExpression
	 * relOp    = '<' | '>' | '<=' | '>=' | '==' | '!='
	 */
	private Node relation() {
		Node arithNode = arithmeticExpression();
		if (Util.isRelOp(currentToken().type)) {
			switch (currentToken().type) {
			case LESS:
				matchAndEat(TokenType.LESS);
				arithNode = new BinaryOpNode(TokenType.LESS, arithNode, arithmeticExpression());
				break;
			case GREATER:
				matchAndEat(TokenType.GREATER);
				arithNode = new BinaryOpNode(TokenType.GREATER, arithNode, arithmeticExpression());
				break;
			case LESSEQUAL:
				matchAndEat(TokenType.LESSEQUAL);
				arithNode = new BinaryOpNode(TokenType.LESSEQUAL, arithNode, arithmeticExpression());
				break;
			case GREATEREQUAL:
				matchAndEat(TokenType.GREATEREQUAL);
				arithNode = new BinaryOpNode(TokenType.GREATEREQUAL, arithNode, arithmeticExpression());
				break;
			case EQUAL:
				matchAndEat(TokenType.EQUAL);
				arithNode = new BinaryOpNode(TokenType.EQUAL, arithNode, arithmeticExpression());
				break;
			case NOTEQUAL:
				matchAndEat(TokenType.NOTEQUAL);
				arithNode = new BinaryOpNode(TokenType.NOTEQUAL, arithNode, arithmeticExpression());
				break;
			default:
				break;
			}
		}
		return arithNode;
	}
	/*
	 * arithmeticExpression = term { ('-' | '+') term }
	 */
	private Node arithmeticExpression() {
		Node termNode = term();
		while (Util.isAddOp(currentToken().type)) {
			switch(currentToken().type) {
			case ADD:
				matchAndEat(TokenType.ADD);
				termNode = new BinaryOpNode(TokenType.ADD, termNode, term());
				break;
			case SUB:
				matchAndEat(TokenType.SUB);
				termNode = new BinaryOpNode(TokenType.SUB, termNode, term());
				break;
			default:
				break;
			}
		}
		return termNode;
	}
	/*
	 * term = factor { ('*' | '/') factor }
	 */
	private Node term() {
		Node factorNode = signedFactor();
		while (Util.isMulOp(currentToken().type)) {
			switch (currentToken().type) {
			case MUL:
				matchAndEat(TokenType.MUL);
				factorNode = new BinaryOpNode(TokenType.MUL, factorNode, signedFactor());
				break;
			case DIV:
				matchAndEat(TokenType.DIV);
				factorNode = new BinaryOpNode(TokenType.DIV, factorNode, signedFactor());
				break;
			default:
				break;
			}
		}
		return factorNode;
	}
	/*
	 * signedFactor = ['-'] factor
	 */
	private Node signedFactor() {
		Node signedNode = null;
		if (currentToken().type == TokenType.SUB) {
			matchAndEat(TokenType.SUB);
			switch (currentToken().type) {
			case INTEGER:
				signedNode = new IntegerNode(Integer.parseInt(matchAndEat(TokenType.INTEGER).value), true);
				break;
			case DOUBLE:
				signedNode = new DoubleNode(Double.parseDouble(matchAndEat(TokenType.DOUBLE).value), true);
				break;
			default:
				Util.Writeln("Parse Error: expedted INTEGER | DOUBLE but found '" + currentToken().type);
				System.exit(-1);
			}
		} else {
			signedNode = factor();
		}
		return signedNode;
	}
	/*
	 * factor = '(' expression ')' | NUMBER | STRING | VARIABLE
	 */
	private Node factor() {
		Node atomicFactorNode = null;
		switch (currentToken().type){
		case LPAREN:
			matchAndEat(TokenType.LPAREN);
			atomicFactorNode = expression();
			matchAndEat(TokenType.RPAREN);
			break;
		case INTEGER:
			atomicFactorNode = new IntegerNode(Integer.parseInt(matchAndEat(TokenType.INTEGER).value));
			break;
		case DOUBLE:
			atomicFactorNode = new DoubleNode(Double.parseDouble(matchAndEat(TokenType.DOUBLE).value));
			break;
		case STRING:
			atomicFactorNode = new StringNode(matchAndEat(TokenType.STRING).value);
			break;
		case IDENTIFIER:
			if (nextToken().type == TokenType.LPAREN) {
				// function call.
				atomicFactorNode = functionCall();
			} else if (nextToken().type == TokenType.LBRACKET) {
				// array access.
				atomicFactorNode = arrayAccess();
			} else {
				// variable access.
				atomicFactorNode = variableAccess();
			}
			break;
		default:
			atomicFactorNode = expression();
			break;
		}
		return atomicFactorNode;
	}
	//***************************************************************************************//
	//*********************************** statement starts **********************************//
	//***************************************************************************************//
	private Node statement() {
		Node stmtNode = null;
		switch(currentToken().type) {
		case WHILE:
			stmtNode = whileStmt();
			break;
		case EXIT:
			matchAndEat(TokenType.EXIT);
			stmtNode = new ExitNode();
			break;
		case IF:
			stmtNode = ifStmt();
			break;
		case DO:
			if (nextToken().type == TokenType.CASE) {
				stmtNode = doCaseStmt();
			} else {
				stmtNode = doWhileStmt();
			}
			break;
		case FOR:
			stmtNode = forStmt();
			break;
		case RETURN:
			matchAndEat(TokenType.RETURN);
			stmtNode = new ReturnNode();
			break;
		case SAY:
			stmtNode = sayStmt();
			break;
		default:
			if (currentToken().type == TokenType.IDENTIFIER && nextToken().type == TokenType.ASSIGNMENT) {
				stmtNode = assignment();
			} else if (currentToken().type == TokenType.IDENTIFIER && nextToken().type == TokenType.LPAREN) {
				stmtNode = functionCall();
			} else {
				stmtNode = expression();
			}
			break;
		}
		return stmtNode;
	}
	/*
	 * block = statement { statement }
	 */
	public Node block() {
		List<Node> statements = new ArrayList<Node>();
		while (notEndingBlock()) {
			statements.add(statement());
		}

		if (currentToken().type == TokenType.END) {
			matchAndEat(TokenType.END);
		}
		return new BlockNode(statements);
	}
	/*
	 * notEndingBlock is a method helper. 
	 */
	private boolean notEndingBlock() {
		TokenType type = currentToken().type;
		return type != TokenType.END 
				&& type != TokenType.ELSE 
				&& type != TokenType.ELIF
				&& type != TokenType.CASE
				&& type != TokenType.DEFAULT
				&& type != TokenType.UNTIL;
	}
	/*
	 * assignment = variableDefinition | arrayDefinition
	 */
	private Node assignment() {
		Token thirdToken = getToken(2);		
		Node assignmentNode = null;
		
		if (thirdToken.type == TokenType.LBRACKET) {
			assignmentNode = arrayDefinition();
		} else {
			assignmentNode = variableDefinition();
		}
		return assignmentNode;
	}
	/*
	 * variableDefinition = identifier '=' expression
	 */
	private Node variableDefinition() {
		String varName = matchAndEat(TokenType.IDENTIFIER).value;
		matchAndEat(TokenType.ASSIGNMENT);
		Node varValue = expression();

		return new AssignmentNode(varName, varValue, this);	
	}
	/*
	 * arrayDefinition = identifier '=' '[' expression {',' expression } ']'
	 */
	private Node arrayDefinition() {
		String varName = matchAndEat(TokenType.IDENTIFIER).value;		
		List<Node> elements = new ArrayList<Node>();
		
		matchAndEat(TokenType.ASSIGNMENT);
		matchAndEat(TokenType.LBRACKET);
		
		elements.add(expression());
		while (currentToken().type == TokenType.COMMA) {
			matchAndEat(TokenType.COMMA);
			elements.add(expression());
		}
		
		matchAndEat(TokenType.RBRACKET);
		Node arrayElements = new ArrayElementsNode(elements);
		
		return new AssignmentNode(varName, arrayElements, this);
	}
	/*
	 * functionDefinition = 'func' identifier '(' [paramList] ')' block
	 */
	private Node functionDefinition() {
		matchAndEat(TokenType.FUNC);
		String funName = matchAndEat(TokenType.IDENTIFIER).value;
		List<String> funParamList = null;
		Node funBlock = null;
		if (currentToken().type == TokenType.LPAREN) {
			funParamList = paramListDefinition();
		}
		funBlock = block();
		FunctionStructNode funcStruct = new FunctionStructNode(funParamList, funBlock);

		return new AssignmentNode(funName, funcStruct, this);
	}
	/*
	 * paramList = '(' parameter {',' parameter} ')'
	 * parameter = identifier ['=' expression]
	 */
	private List<String> paramListDefinition() {
		List<String> paramList = new ArrayList<String>();
		matchAndEat(TokenType.LPAREN);
		if (currentToken().type != TokenType.RPAREN) {			
			paramList.add(matchAndEat(TokenType.IDENTIFIER).value);
			while (currentToken().type == TokenType.COMMA) {
				matchAndEat(TokenType.COMMA);
				paramList.add(matchAndEat(TokenType.IDENTIFIER).value);
			}
		}
		matchAndEat(TokenType.RPAREN);

		return paramList;
	}
	/*
	 * functionCall = identifier '(' [paramList] {',' paramList} ')' 
	 */
	private Node functionCall() {
		String funName = matchAndEat(TokenType.IDENTIFIER).value;
		List<Node> paramListValues = new ArrayList<Node>();
		matchAndEat(TokenType.LPAREN);
		if (currentToken().type != TokenType.RPAREN) {
			paramListValues.add(expression());
			while (currentToken().type == TokenType.COMMA) {
				matchAndEat(TokenType.COMMA);
				paramListValues.add(expression());
			}
		}
		matchAndEat(TokenType.RPAREN);
		return new FunctionCallNode(funName, paramListValues, this);
	}
	/*
	 * variableAccess
	 */
	private Node variableAccess() {
		String varName = matchAndEat(TokenType.IDENTIFIER).value;
		return new VariableAccessNode(varName, this);
	}
	/*
	 * arrayAccess
	 */
	private Node arrayAccess() {
		Token positionToken = currentToken();
		Node variableNode = variableAccess();
		matchAndEat(TokenType.LBRACKET);
		Node arrayIndex = expression();
		matchAndEat(TokenType.RBRACKET);
		return new ArrayNode(variableNode, arrayIndex, positionToken);
	}
	/*
	 * whileStmt = 'while' expression block
	 */
	private Node whileStmt() {
		matchAndEat(TokenType.WHILE);
		return new WhileNode(expression(), block());
	}
	/*
	 * ifStmt = 'if' expression block [elIf]
	 * elIf = 'else' block | 'elif' expression block
	 */
	private Node ifStmt() {
		Node condition = null;
		Node blockIf = null;
		List<BranchNode> elifNode = new ArrayList<BranchNode>();
		Node blockElse = null;
		
		matchAndEat(TokenType.IF);
		
		condition = expression();
		blockIf = block();

		while (currentToken().type == TokenType.ELIF) {
			matchAndEat(TokenType.ELIF);
			elifNode.add(new BranchNode(expression(), block()));			
		}
		
		if (currentToken().type == TokenType.ELSE) {
			matchAndEat(TokenType.ELSE);
			blockElse = block();
		}
		return new IfNode(condition, blockIf, elifNode, blockElse);
	}
	/*
	 * doCaseStmt = 'do case' caseBranch ['default' block]
	 * caseBranch = {'case' condition block}
	 */
	private Node doCaseStmt() {
		List<BranchNode> caseNodes = new ArrayList<BranchNode>();
		Node defaultNode = null;
		
		matchAndEat(TokenType.DO);
		matchAndEat(TokenType.CASE);
		
		while (currentToken().type == TokenType.CASE) {
			matchAndEat(TokenType.CASE);
			caseNodes.add(new BranchNode(expression(), block()));
		}
		
		if (currentToken().type == TokenType.DEFAULT) {
			matchAndEat(TokenType.DEFAULT);
			defaultNode = block();
		} else {
			matchAndEat(TokenType.END);
		}
		return new DoCaseNode(caseNodes, defaultNode);
	}
	/*
	 * doWhileStmt = 'do' block 'while' expression
	 */
	private Node doWhileStmt() {
		Node conditionNode = null;
		Node blockNodes = null;
		
		matchAndEat(TokenType.DO);
		blockNodes = block();
		
		matchAndEat(TokenType.UNTIL);
		conditionNode = expression();
		
		return new DoWhileNode(conditionNode, blockNodes);
	}
	/*
	 * forStmt = 'for' identifier '=' assignment 'to' factor ['step' factor]
	 */
	private Node forStmt() {
		String varName = "";
		Node assignment = null;
		Node finalValue = new IntegerNode(0);
		Node stepValue = new IntegerNode(1);
		Node blockNodes = null;
		
		matchAndEat(TokenType.FOR);
		varName = currentToken().value;
		assignment = assignment();
		
		matchAndEat(TokenType.TO);
		finalValue = factor();
		
		if (currentToken().type == TokenType.STEP) {
			matchAndEat(TokenType.STEP);
			stepValue = factor();
		}
		blockNodes = block();
		return new ForNode(varName, assignment, finalValue, stepValue, blockNodes, this);
	}
	/*
	 * sayStmt = '?' expression {',' expression}
	 */
	private Node sayStmt() {
		matchAndEat(TokenType.SAY);
		List<Node> expressionList = new ArrayList<Node>();
		expressionList.add(expression());
		while (currentToken().type == TokenType.COMMA) {
			matchAndEat(TokenType.COMMA);
			expressionList.add(expression());
		}
		return new SayNode(expressionList);
	}
	
}
