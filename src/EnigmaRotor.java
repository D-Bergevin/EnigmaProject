/**
 * Program Name: EnigmaRotor.java
 * Purpose: This is a class that represents the rotors on the enigma machine.
 * 
 * PROJECT CODER(S): Dominic Bergevin - Section 02
 * Date: August 5th, 2025
 */

public class EnigmaRotor {
	private String permutation;
	private int offset;
	
	public EnigmaRotor(String p)
	{
		permutation = p;
		offset = 0;
	}

	public String getPermutation()
	{
		return permutation;
	}
	
	public void invertKey()
	{
		String inverse = "";
		
		for (int i = 0; i < EnigmaConstants.ALPHABET.length(); i++)
        {
			inverse += EnigmaConstants.ALPHABET.charAt(permutation.indexOf(EnigmaConstants.ALPHABET.charAt(i)));
        }
		
		permutation = inverse;
	}
	
	public int getOffset()
	{
		return offset;
	}
	
	public boolean advance()
	{
		if (++offset == EnigmaConstants.ALPHABET.length())
		{
			offset = 0;
			return true;
		}
		return false;
		
	}
	
	public static int applyPermutation(int index, String perm, int off)
	{
		int shiftedIndex = (index+off) % perm.length();
		
		String permutationShift = Character.toString(perm.charAt(shiftedIndex));
		
		return (EnigmaConstants.ALPHABET.indexOf(permutationShift)-off + EnigmaConstants.ALPHABET.length()) % EnigmaConstants.ALPHABET.length();
		
		
	}
	
}
