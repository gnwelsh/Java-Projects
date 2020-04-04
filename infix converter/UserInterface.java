import java.util.Scanner;
public class UserInterface {

	public static void main(String[] args) {

		Scanner kb= new Scanner(System.in);

		String input = "";//holds infix expression
		String temp = "";

		System.out.println("Enter an Infix Expression: ");
		input = input + kb.nextLine();
		input = input.replace(" ", "");///take out white space

		Deck<Term> operators = new Deck<Term>();//operators Stack
		Deck<Term> infix= new Deck<Term>();//Parsed infix Queue of Terms
		Deck<Term> output = new Deck<Term>();//postfix  Queue of Terms
		Deck<Term> result = new Deck<Term>();

		for(int i = 0; i<input.length(); i++)
		{
				if(temp.isEmpty() && (i == 0 || input.charAt(i-1) != ')')) //when a new operator is found the next character of + or - or ( is allowed.
				{
					if(input.charAt(i) == '(')
					{
						Term cur = new Term(input.charAt(i));
						infix.push(cur);//push Term
						operators.push(cur);//push operator
						
						temp = "";//reset temp
						i++;
					}
					else
					{
						if(input.charAt(i) == '+' || input.charAt(i)=='-')
						{
							temp = temp + input.charAt(i);//add to temp
							i++; //go to the next character
						}

						if(!(Character.isDigit(input.charAt(i)) || input.charAt(i) == '.')) //if the character is not a digit or '.' invalid operand
						{
							System.out.println("Invalid operand");
							return;
						}
					}
				}

				if(Character.isDigit(input.charAt(i))) //if a character is a number add it to the operand.
					temp = temp + input.charAt(i);

				else if(input.charAt(i) == '.') //if a character is a '.'
				{	
					if(temp.indexOf('.') == -1) //and there are no '.' characters in the operand
						temp = temp + input.charAt(i); //add it to the operand.

					else 
					{
						System.out.println("Invalid operand");
						return;
					}
				}
				/**
				 * add operators
				 * handles parenthesis in postfix
				 */
				else if((input.charAt(i)=='+' || input.charAt(i)=='-' || input.charAt(i)=='*' || input.charAt(i)=='/' ||  input.charAt(i)==')') && (i == 0 || input.charAt(i-1) != '(' ))
				{
					if(!temp.isEmpty()) {
						infix.push(new Term(Double.parseDouble(temp)));//parse temp and add to infix
						output.push(new Term(Double.parseDouble(temp)));//parse temp and add to postfix
					}
					
					temp = "";//reset temp
					Term cur = new Term(input.charAt(i));
					infix.push(cur);//add cur to queue
					if(cur.getOperator() == ')')
					{
						while(operators.peekTop().getOperator() != '(')
						{
							output.push(operators.peekTop());
							operators.pop();
							if(operators.getSize() == 0)
							{
								System.out.println("Unbalanced right parenthesis ')'");
								return;
							}
							
						}
						operators.pop();
					}
					else if(operators.getSize()==0)//empty operators
						operators.push(cur);
					else if(cur.getOperator()=='(')
						operators.push(cur);
					else if(cur.getPrecendence() <= operators.peekTop().getPrecendence())
					{
							output.push(operators.peekTop());
							operators.pop();
							operators.push(cur);
					}

					else
					{
						operators.push(cur);
					}
				}

				else if((input.charAt(i) == '+' || input.charAt(i)=='-' ) && (i == 0 || input.charAt(i-1) == '(' )) //catches + and - after a (
				{
					temp = temp + input.charAt(i);
				}

				else if(input.charAt(i)=='(')//handle '('
				{
					Term cur= new Term(input.charAt(i));
					infix.push(cur);
					operators.push(cur);
					temp="";
				}
				
				else //invalid operator error
				{
					System.out.println("invalid operator: " + input.charAt(i));
					return;
				}
		}
		
		if (!temp.isEmpty()) {								//add operand
			infix.push(new Term(Double.parseDouble(temp)));
			output.push(new Term(Double.parseDouble(temp)));
		}
		
		if(infix.peekTop().isOperator() && infix.peekTop().getOperator() != ')')//Missing Operand
		{
			System.out.println("Missing Operand");
			return;
		}

		while(operators.peekTop() != null)
		{
			if(operators.peekTop().getOperator()==')')
				operators.pop();
			else if (operators.peekTop().getOperator()=='(')
			{
				System.out.println("Unbalanced left parenthesis (");
				return;
			}
			else
			{
				output.push(operators.peekTop());
			operators.pop();
			}
		}

		System.out.print("infix Expression: ");//print out infix Expression
		for(int i = 0; i < infix.getSize(); i++)
		{
			if(infix.get(i).isOperator())
				System.out.print(infix.get(i).getOperator() + " ");
			else
				System.out.print(infix.get(i).getOperand() + " ");
		}
		System.out.println();
		System.out.print("Postfix Expression: ");
		for(int i = 0; i < output.getSize(); i++)		//print out postfix Expression
		{
			if(output.get(i).isOperator())
				System.out.print(output.get(i).getOperator() + " ");
			else
				System.out.print(output.get(i).getOperand() + " ");
		}
		System.out.print("\n");
										// evaluate postfix expression
		Term answer= new Term(0);
		for(int i = 0; i < output.getSize(); i++)
		{
			// if peekTop is operator, then evaluate result contents (pop two operands), else push onto result

			if (!output.get(i).isOperator()) {
				//push operand
				result.push(output.get(i));
			}
			
			else {
				char operator = output.get(i).getOperator();
				double num1 = result.peekTop().getOperand();
				result.pop();
				double num2 = result.peekTop().getOperand();
				result.pop();

				if(operator=='+')
				{
					answer = new Term(num2+num1);
					result.push(answer);
				}

				if(operator=='-')
				{
					answer = new Term(num2-num1);
					result.push(answer);
				}

				if(operator=='*')
				{
					answer = new Term(num2*num1);
					result.push(answer);
				}

				if(operator=='/')
				{
					answer = new Term(num2/num1);
					result.push(answer);
				}
			}
		}

		double finalAnswer=result.peekTop().getOperand();//print out answer

		System.out.println("Answer: "+finalAnswer);
	}
}