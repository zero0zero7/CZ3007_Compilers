package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import lexer.Lexer;

import org.junit.Test;

import frontend.Token;
import frontend.Token.Type;
import static frontend.Token.Type.*;

/**
 * This class contains unit tests for your lexer. Currently, there is only one
 * test, but you
 * are strongly encouraged to write your own tests.
 */
public class LexerTests {
	// helper method to run tests; no need to change this
	private final void runtest(String input, Token... output) {
		Lexer lexer = new Lexer(new StringReader(input));
		int i = 0;
		Token actual = new Token(MODULE, 0, 0, ""), expected;
		try {
			do {
				assertTrue(i < output.length);
				expected = output[i++];
				try {
					actual = lexer.nextToken();
					assertEquals(expected, actual);
				} catch (Error e) {
					if (expected != null)
						fail(e.getMessage());
					/* return; */
				}
			} while (!actual.isEOF());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/** Example unit test. */
	@Test
	public void testKWs() {
		// first argument to runtest is the string to lex; the remaining arguments
		// are the expected tokens
		runtest("module false return while",
				new Token(MODULE, 0, 0, "module"),
				new Token(FALSE, 0, 7, "false"),
				new Token(RETURN, 0, 13, "return"),
				new Token(WHILE, 0, 20, "while"),
				new Token(EOF, 0, 25, ""));
	}

	@Test
	public void testPunc() {
		// first argument to runtest is the string to lex; the remaining arguments
		// are the expected tokens
		runtest("; [ { ) ] ,",
				new Token(SEMICOLON, 0, 0, ";"),
				new Token(LBRACKET, 0, 2, "["),
				new Token(LCURLY, 0, 4, "{"),
				new Token(RPAREN, 0, 6, ")"),
				new Token(RBRACKET, 0, 8, "]"),
				new Token(COMMA, 0, 10, ","),
				new Token(EOF, 0, 11, ""));
	}

	@Test
	public void testOperator() {
		// first argument to runtest is the string to lex; the remaining arguments
		// are the expected tokens
		runtest("== * >= /",
				new Token(EQEQ, 0, 0, "=="),
				new Token(TIMES, 0, 3, "*"),
				new Token(GEQ, 0, 5, ">="),
				new Token(DIV, 0, 8, "/"),
				new Token(EOF, 0, 9, ""));
	}

	@Test
	public void testIdentifier() {
		// first argument to runtest is the string to lex; the remaining arguments
		// are the expected tokens
		runtest("abc fgh a",
				new Token(ID, 0, 0, "abc"),
				new Token(ID, 0, 4, "fgh"),
				new Token(ID, 0, 8, "a"),
				new Token(EOF, 0, 9, ""));
	}

	@Test
	public void testPositioningAndLiteral() {
		// first argument to runtest is the string to lex; the remaining arguments
		// are the expected tokens
		runtest("\"abc\" \n1",
				new Token(STRING_LITERAL, 0, 0, "abc"),
				new Token(INT_LITERAL, 1, 0, "1"),
				new Token(EOF, 1, 1, ""));
	}

	@Test
	public void testAll() {
		// first argument to runtest is the string to lex; the remaining arguments
		// are the expected tokens
		runtest("> \"bcd\" , break",
				new Token(GT, 0, 0, ">"),
				new Token(STRING_LITERAL, 0, 2, "bcd"),
				new Token(COMMA, 0, 8, ","),
				new Token(BREAK, 0, 10, "break"),
				new Token(EOF, 0, 15, ""));
	}

	@Test
	public void testStringLiteralWithDoubleQuote() {
		runtest("\"\"\"",
				new Token(STRING_LITERAL, 0, 0, ""),
				(Token) null,
				new Token(EOF, 0, 3, ""));
	}

	@Test
	public void testStringLiteral() {
		runtest("\"\\n\"",
				new Token(STRING_LITERAL, 0, 0, "\\n"),
				new Token(EOF, 0, 4, ""));
	}

}
