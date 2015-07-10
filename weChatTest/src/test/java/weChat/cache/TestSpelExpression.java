package weChat.cache;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class TestSpelExpression {
	class Simple {
	    public List<Boolean> booleanList = new ArrayList<Boolean>();
	}
	@Test
	public void testSpelExpressionParser(){
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("new String('hello world').toUpperCase()");
		String message = exp.getValue(String.class);
		System.out.println(message);
	}
	@Test
	public void testTypeConversion(){
		Simple simple = new Simple();
		simple.booleanList.add(true);

		StandardEvaluationContext simpleContext = new StandardEvaluationContext(simple);
		ExpressionParser parser = new SpelExpressionParser();
		// false is passed in here as a string. SpEL and the conversion service will
		// correctly recognize that it needs to be a Boolean and convert it
		parser.parseExpression("booleanList[0]").setValue(simpleContext, "true");

		// b will be false
		Boolean b = simple.booleanList.get(0);
		System.out.println(b);
	}
	@Test
	public void testType(){
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setVariable("param1", 55);
		context.setVariable("param2", 22);
		String expression = "T(weChat.core.cache.CacheUtils).hash(#param1, #param2)";
		String value = parser.parseExpression(expression).getValue(context,String.class);
		System.out.println(value);
	}
}
