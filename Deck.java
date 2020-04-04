
public class Deck<E> 
{
		private E[] array;
		private int size = 0;
		private int top = -1;
		private int bottom = 0;

		public Deck()
		{
			array = (E[]) new Object[20];
			size = 20;
		}

		public void push(E value)
		{
			if(top == size - 1)
			{
				E[] temp = (E[]) new Object[size*2];
				for(int i = 0; i < size; i++)
				{
					temp[i] = array[i];
					array = temp;
				}
			}

			top++;
			array[top] = value;
		}

		public void pop()
		{
			if(top<bottom)
				return;
			else {
				top--;
			}				
		}

		public void popBottom()
		{	if(bottom>top)
				return;
			bottom++;
		}

		public E peekTop()
		{
			if(top >= 0)
			{
				return array[top];
			}
			return null;
		}

		public E peekBottom()
		{
			if(bottom >= 0)
			{
				return array[bottom];
			}
			return null;
		}

		public int getSize()
		{
			return top + 1;
		}
		
		public E get(int index)
		{
			return array[index];
		}
}