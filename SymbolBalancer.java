/*
	Takes a path to a file as a command-line argument. Uses this argument to parse
	the file in order to check if all (), [], and {} symbols are balanced. If the
	symbols are not balanced, then it reports the line number and the symbol that
	is not balanced. Otherwise, it reports that all symbols are balanced.
*/

import java.io.*;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Stack;

public class SymbolBalancer {

	public static void main(String[]args) {

		BufferedReader lineReader = null;										//declare a BufferedReader
		Stack <Integer>st = new Stack<Integer>();						//create a stack for symbols
		Stack <Integer>lineNum = new Stack<Integer>();			//create a stack for line numbers
		int c, i;
		String readingLine;

		//checks for a lack of arguments passed
		if (args.length == 0)
		{
			System.out.println("Need to pass a file path as an argument");
			return;
		}

		//opens file for reading
		//if the path is invalid, then reports an error
		try
		{
			lineReader = new BufferedReader(new FileReader(args[0]));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File read error");
			return;
		}

		try
		{
			int lineCounter = 1;		//counter for line numbers

			//loops through all lines in a line, reading in a line for each iteration
			while((readingLine = lineReader.readLine()) != null)
			{
				//iterates through each character in the line
				for(int j = 0; j < readingLine.length(); j++)
				{
					c = readingLine.charAt(j);			//current character

					//if the character is an opening symbol, push the character to its stack
					//as well as the line number to its stack
					if(c == '(' || c == '[' || c == '{')
					{
						st.push(c);
						lineNum.push(lineCounter);
					}
					//if the character is a closing symbol, perform some checks
					else if(c == ')' || c == ']' || c == '}')
					{
						//if the stack is empty, a stray/extra closing character has been encountered
						if(st.empty())
						{
							System.out.println("On line " + lineCounter + " there is a " + (char)c + " that has no opener");
							return;
						}

						//pop the top off of the stack and store the value
						int top = st.pop();

						//compare the top and the character
						//if they don't match, report it
						if((top == '(' && c != ')') || (top == '[' && c != ']') || (top == '{' && c != '}') )
						{
							System.out.println("On lines " + lineNum.peek() + " and " + lineCounter
									+ " there is a mismathced pair of a " +  (char)((int)top) + " and a " + (char)c);
							return;
						}
						//if the top and the character match, pop the top off of the line
						//number stack as well
						else if(c == ')' && top == '(')
						{
							lineNum.pop();
						}
						else if(c == ']' && top == '[')
						{
							lineNum.pop();
						}
						else if(c == '}' && top == '{')
						{
							lineNum.pop();
						}
					}
				}
				lineCounter++;		//increment the line number counter
			}

			//after the loop, if the stack isn't empty, report that there is a
			//stray/empty opening symbol. Otherwise, all symbols are balanced
			if(!st.empty())
			{
				System.out.println("On line " + lineNum.peek() + " there is an open " + (char)((int)st.peek()) + " with no closer");
			}
			else
			{
				System.out.println("All symbols are balanced");
			}
		}
		catch(IOException e)
		{		}
	}
}
