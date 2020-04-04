
public class VigenereCipher {

	private int p = 0;
	private String phrase = null;
	public void setPhrase (String pPhrase)
	{
		phrase = pPhrase;
		p = 0;
	}
	
	private char encryptChar (char c, char pc)
	{
		return  (char) ((((c-'A')+(pc-'A'))%26)+'A');
	}
	
	private char decryptChar (char c, char pc)
	{
		int i = (c-'A') - (pc-'A');
		if (i<0)
			return (char) (26+i+'A');
		else
				return (char) (i+'A');
	}
	
	public String encrypt (String s)
	{
		String outline = "";
		for (int i=0; i<s.length(); i++)
		{
			char c = s.charAt(i);
			if (Character.isLetter(c)) {
				c = Character.toUpperCase(c);
				c = encryptChar(c,phrase.charAt(p));
				outline += c;
				p = (++p) % phrase.length();
			}
		}
		return outline;
	}
	
	public String decrypt (String s)
	{
		String outline = "";
		for (int i=0; i<s.length(); i++)
		{
			char c = s.charAt(i);
			if (Character.isLetter(c)) {
				c = decryptChar(c,phrase.charAt(p));
				p = (++p) % phrase.length();
			}
			outline += c;
		}
		return outline;
	}
}
