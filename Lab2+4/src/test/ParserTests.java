package test;

import static org.junit.Assert.fail;

import java.io.StringReader;

import lexer.Lexer;

import org.junit.Test;

import parser.Parser;

public class ParserTests {
	private void runtest(String src) {
		runtest(src, true);
	}

	private void runtest(String src, boolean succeed) {
		Parser parser = new Parser();
		try {
			parser.parse(new Lexer(new StringReader(src)));
			if(!succeed) {
				fail("Test was supposed to fail, but succeeded");
			}
		} catch (beaver.Parser.Exception e) {
			if(succeed) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		} catch (Throwable e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testEmptyModule() {
		runtest("module Test { }");
	}

	
	@Test
	public void testImportDeclaration() {
		runtest("module Test {"+
			"import ImportTest;"+
			"int aVariable;"+
			"public void aFuncs() {}"+
			"public type aVariable = \"test\";"+
		"}");
	}

	@Test
	public void testFunctionParam() {
		runtest("module Test {"+
			"public void aFuncs() {}"+
			"public void aFuncs(int p1) {}"+
			"public void aFuncs(int p1, float p2) {}"+
		"}");
	}

	@Test
	public void testFunctionStatementList() {
		runtest("module Test {"+
			"public void aFuncs() {"+
			"boolean LocalVarDeclar;"+
			"{}"+ // empty block list
			"if(a<b) {}"+
			"return;"+
			"}"+
		"}");
	}

	@Test
	public void testWhile() {
		runtest("module Test {"+
			"public void aFuncs() {"+
			"while(e>=f) {break;}"+
			"}"+
		"}");
	}

	@Test
	public void testIfElse() {
		runtest("module Test {"+
			"public void aFuncs() {"+
			"if(e>=f) {bool x;}"+
			"else {int x;}"+
			"}"+
		"}");
	}

	@Test
	public void testFunctionCondition() {
		runtest("module Test {"+
			"public void aFuncs() {"+
			"if(c==d) {int x;}"+
			"else {x=20;}"+
			"while(e>=f) {break;}"+
			"}"+
		"}");
	}

	@Test
	public void testArrayinFunc() {
		runtest("module Test {"+
			"public void aFunc() {"+
			"bool a;"+
			"a = TRUE;"+
			"int b;"+
			"b = 10;"+
			"int[] c;"+
			"b = c[10];"+
			"}"+
		"}");
	}

	@Test
	public void arrayAccTest() {
		runtest("module Test {"+
			"public void arrayF() {" +
			"int[] aArray;"+
			"int a;"+
			"arrayAccess = aArray[a];"+
			"}"+
			"}");
	}

}