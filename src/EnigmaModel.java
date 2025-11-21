 /**
 * Program Name: EnigmaModel.java
 * Purpose: This class defines the starter version of the EnigmaModel class,
 * but it doesn't completely implement any of the methods. That will be your job.
 * NOTE: this class holds the main method for the application.
 * BASE CODE Coders: Eric Roberts and Jed Rembold, Willamette University, OR
 * 
 * PROJECT CODER(S): Dominic Bergevin - Section 02
 * Date: August 5th, 2025
 */

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;

public class EnigmaModel 
{
	/* Private instance variables */

  private ArrayList<EnigmaView> views;
  private HashMap<String,Boolean> keys;
  private HashMap<String,Boolean> lights;
  private ArrayList<EnigmaRotor> rotors;

    public EnigmaModel() 
    {
        views = new ArrayList<EnigmaView>();
        keys = new HashMap<String, Boolean>();
        lights = new HashMap<String, Boolean>();
        for (int i = 0; i < EnigmaConstants.ALPHABET.length(); i++)
        {
            String ch = Character.toString((char) ('A' + i));
            keys.put(ch, false);
            lights.put(ch, false);
        }
        rotors = new ArrayList<EnigmaRotor>();
        for (int i = 0; i < EnigmaConstants.N_ROTORS; i++)
        {
        	rotors.add(new EnigmaRotor(EnigmaConstants.ROTOR_PERMUTATIONS[i]));
        }
    }

/**
 * Adds a view to this model.
 *
 * @param view The view being added
 */

    public void addView(EnigmaView view)
    {
        views.add(view);
    }

/**
 * Sends an update request to all the views.
 */

    public void update() 
    {
        for (EnigmaView view : views)
        {
            view.update();
        }
    }

/**
 * Returns true if the specified letter key is pressed.
 *
 * @param letter The letter key being tested as a one-character string.
 */

    public boolean isKeyDown(String letter)
    {
    	return keys.get(letter);
    }

/**
 * Returns true if the specified lamp is lit.
 *
 * @param letter The lamp being tested as a one-character string.
 */

    public boolean isLampOn(String letter) 
    {
    	return lights.get(letter);
    }

/**
 * Returns the letter visible through the rotor at the specified inded.
 *
 * @param index The index of the rotor (0-2)
 * @return The letter visible in the indicated rotor
 */

    public String getRotorLetter(int index)
    {
    	return Character.toString('A' + rotors.get(index).getOffset());
    }

/**
 * Called automatically by the view when the specified key is pressed.
 *
 * @param key The key the user pressed as a one-character string
 */

    public void keyPressed(String key)
    {
    	
    	for (int i = EnigmaConstants.N_ROTORS-1; i >= 0; i--)
    	{
    		if (!rotors.get(i).advance())
    		{
    			break;
    		}
    	}
    	int keyToPress = EnigmaRotor.applyPermutation(EnigmaConstants.ALPHABET.indexOf(key),rotors.get(2).getPermutation(),rotors.get(2).getOffset());
    	keyToPress = EnigmaRotor.applyPermutation(keyToPress,rotors.get(1).getPermutation(),rotors.get(1).getOffset());
    	keyToPress = EnigmaRotor.applyPermutation(keyToPress,rotors.get(0).getPermutation(),rotors.get(0).getOffset());
    	keyToPress = EnigmaRotor.applyPermutation(keyToPress,EnigmaConstants.REFLECTOR_PERMUTATION,0);
    	
    	for (int i = 0; i < EnigmaConstants.N_ROTORS; i++)
    	{
    		rotors.get(i).invertKey();
    	}
    	
    	keyToPress = EnigmaRotor.applyPermutation(keyToPress,rotors.get(0).getPermutation(),rotors.get(0).getOffset());
    	keyToPress = EnigmaRotor.applyPermutation(keyToPress,rotors.get(1).getPermutation(),rotors.get(1).getOffset());
    	keyToPress = EnigmaRotor.applyPermutation(keyToPress,rotors.get(2).getPermutation(),rotors.get(2).getOffset());
    	
    	for (int i = 0; i < EnigmaConstants.N_ROTORS; i++)
    	{
    		rotors.get(i).invertKey();
    	}
    	
    	String keyPressed = Character.toString(EnigmaConstants.ALPHABET.charAt(keyToPress));
    	keys.replace(key, true);
    	lights.replace(keyPressed, true);
    	update();
    }

/**
 * Called automatically by the view when the specified key is released.
 *
 * @param key The key the user released as a one-character string
 */

    public void keyReleased(String key)
    {
    	int keyToRelease = EnigmaRotor.applyPermutation(EnigmaConstants.ALPHABET.indexOf(key),rotors.get(2).getPermutation(),rotors.get(2).getOffset());
    	keyToRelease = EnigmaRotor.applyPermutation(keyToRelease,rotors.get(1).getPermutation(),rotors.get(1).getOffset());
    	keyToRelease = EnigmaRotor.applyPermutation(keyToRelease,rotors.get(0).getPermutation(),rotors.get(0).getOffset());
    	keyToRelease = EnigmaRotor.applyPermutation(keyToRelease,EnigmaConstants.REFLECTOR_PERMUTATION,0);
    	
    	for (int i = 0; i < EnigmaConstants.N_ROTORS; i++)
    	{
    		rotors.get(i).invertKey();
    	}
    	
    	keyToRelease = EnigmaRotor.applyPermutation(keyToRelease,rotors.get(0).getPermutation(),rotors.get(0).getOffset());
    	keyToRelease = EnigmaRotor.applyPermutation(keyToRelease,rotors.get(1).getPermutation(),rotors.get(1).getOffset());
    	keyToRelease = EnigmaRotor.applyPermutation(keyToRelease,rotors.get(2).getPermutation(),rotors.get(2).getOffset());
    	
    	for (int i = 0; i < EnigmaConstants.N_ROTORS; i++)
    	{
    		rotors.get(i).invertKey();
    	}
    	
    	String keyReleased = Character.toString(EnigmaConstants.ALPHABET.charAt(keyToRelease));
    	keys.replace(key, false);
    	lights.replace(keyReleased, false);
    	update();
    }

/**
 * Called automatically by the view when the rotor at the specified
 * index (0-2) is clicked.
 *
 * @param index The index of the rotor that was clicked
 */

    public void rotorClicked(int index) 
    {
        // Write the code to run when the specified rotor is clicked
    	rotors.get(index).advance();
    	update();
    }

/* Main program */

    public static void main(String[] args)
    {
        EnigmaModel model = new EnigmaModel();
        EnigmaView view = new EnigmaView(model);
        model.addView(view);
    }
  
}//end class EnigmaModel
