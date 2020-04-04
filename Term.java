
public class Term {
		private char operator;
		private int precedence;
		private double operand;
		private boolean type;

		public Term(char data)
		{
			operator = data;
			if (operator == '-' || operator == '+')
				precedence = 2;
			if (operator == '/' || operator == '*')
				precedence = 3;
			if (operator == '(')
				precedence = 1;
			type = true;
		}

		public Term(double data)
		{
			operand = data;
			type = false;
		}

		public int getPrecendence()
		{
			return precedence;
		}

		public char getOperator()
		{
			return operator;
		}

		public double getOperand()
		{
			return operand;
		}

		public boolean isOperator()
		{
			return type;
		}
}