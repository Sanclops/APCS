
/**
 *  WordToolkit.java
 *  Provides utilities for manipulation of words.
 *  Some of the methods include:  A getInput
 *  method that prompts the user to enter a String
 *  of letters, returning that String, a findAllWords
 *  method that will find all words that can be
 *  created from the given String, found in
 *  wordlist.txt, a matchFound method that returns
 *  true if the given word can be created from the
 *  given String of letters (false otherwise).  
 *  Also, a printWords method that prints the
 *  given array of words (Strings) to the terminal
 *  window, a bestWord method that finds the
 *  highest scoring word given a list of words
 *  (Strings) and a letter-scoring table (array),
 *  a getScore method that returns the score for
 *  a word (String) given a letter-scoring table
 *  (array), and an isWordInList method, returning
 *  true if a given word exists in wordlist.txt
 *  (false otherwise).  Finally, the method
 *  alphaOrder returns true when the given
 *  word (a String) has letters in alpha order
 *  (false otherwise).
 *  @author Sankalp Agrawal
 *  @version 1.0
 *  @since 9/17/2015
 */
 
import java.util.Scanner;

public class WordToolkit
{
    public static void main(String [] args)
    {
        boolean done = false;
        do 
        {
            String input = getInput();
            if (input.length() != 0) 
            {
                String [] word = findAllWords(input);
                printWords(word);
				
                int count = 0;
                System.out.print("\nFrom the list above, words with letters in alpha order:");
                for(int i = 0; i < word.length; i++)
                {

                    if(alphaOrder(word[i]))
                    {
                        if(count % 5 == 0)
                        {
                            System.out.println();
                        }
                        count++;
                        System.out.printf("%14s",word[i]);
                    }
                }
		
                // Score table in alphabetic order according to Scrabble
                int [] scoretable = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
                String best = bestWord(word,scoretable);
                System.out.println("\n\nHighest scoring word: " + 
                    getScore(best,scoretable) + "   " + best + "\n");
            }
            else
            {
                done = true;
            }
        } 
        while(!done);
        System.out.println("\n\nGOODBYE!\n\n");
    }
	
    /**
     *  Accepts input for a list of 3 to 12 letters. It also checks
     *  all characters to insure they are letters.
     *
     *  @return  A String of the letters
     */
    public static String getInput ()
    {
    	boolean badInput = true;
    	String input = "";
    	/*while(badInput) {
    		input = Prompt.getString("Please enter a list of characters from 3 to 12 letters long with no spaces: ");
    		for(int i = 0; i < input.length(); i++) {
    			if(input.charAt(i) > 65 && input.charAt(i) < 122) {
    				badInput = false;
    			}
    		}
    	}*/
        input = Prompt.getString("Please enter a list of characters from 3 to 12 letters long with no spaces: ");
        return input;
    }
	
    /**
     *  Finds all words that can be formed by a String of letters.
     *
     *  @param     letters    String list of letters
     *  @return    an array of Strings representing all words found
     */
    public static String [] findAllWords(String letters)
    {		
        int counter = 0;
        boolean counterFound = false;
        String [] word = {};
        String currentLine = "";
        for(int i = 0; i < 2; i++) {
        	Scanner scan = OpenFile.openToRead("Words.txt");
        	while(scan.hasNext()) {
        		currentLine = scan.nextLine();
        		if(matchFound(currentLine, letters)) {
        			if(!counterFound) {
        				counter++;
        			} else {
        				word[counter] = currentLine;
        				counter++;
        			}
        		}
        	}

        	if(!counterFound) {
        		word = new String[counter];	
        	}
        	counterFound = true;
        	counter = 0;
        }
        return word;
    }
	
    /**
     *  Determines if a word can be formed by a list of letters.
     *
     *  @param        word       the word (a String) to be tested
     *  @param        letters    a String of the list of letters
     *  @return       true if word can be formed, false otherwise
     */
    public static boolean matchFound(String word, String letters) 
    {
    	String temp = word;
    	boolean letterMatch = false;

    	for(int i = 0; i < letters.length(); i++) {
    		letterMatch = false;
    		for(int j = 0; j < temp.length(); j++) {
    			if(letters.charAt(i) == temp.charAt(j)) {
    				letterMatch = true;
                    temp = temp.substring(0,j) + temp.substring(j+1);
    			}
    		}
    		if(!letterMatch) {
    			return false;
    		}
    	}
        return true;
    }
	
    /**
     *  Prints the words found to the screen.
     *
     *  @param        word      the String array containing the words
     */
    public static void printWords(String [] word)
    {
    	System.out.println("\n\n\n");
        int counter = 0;
        for(int i = 0; i < word.length; i++) {
        	System.out.printf("%20s, ", word[i]);
        	counter++;
        	if(counter%5 == 0) {
        		System.out.println();
        	}
        }
        System.out.println("\n\n\n");
    }
	
    /**
     *  Finds the highest scoring word according to Scrabble rules.
     *
     *  @param          word          an array of words (Strings) to check
     *  @param          scoretable    an array of 26 integer scores in letter order
     *  @return         the word with the highest score
     */
    public static String bestWord(String [] word, int [] scoretable)
    {
    	String currentWord = "";
        int currentPoints = 0;
    	String currentBestWord = "";
    	int currentTopScore = 0;
    	for(int i = 0; i < word.length; i++) {
    		currentWord = word[i];
            currentWord = currentWord.toLowerCase();
    		for(int j = 0; j < currentWord.length(); j++) {
    			currentPoints += scoretable[currentWord.charAt(j) - 97];
    		}
            if(currentPoints > currentTopScore) {
                currentTopScore = currentPoints;
                currentBestWord = currentWord;
            }
            currentTopScore = 0;
            currentWord = "";
    	}
        return currentBestWord;
    }
	
    /**
     *  Calculates the score of a word according to Scrabble rules.
     *
     *  @param           word           the word (a String) to score
     *  @param           scoretable     the array of 26 scores for alphabet
     *  @return          the integer score of the word
     */
    public static int getScore(String word, int [] scoretable)
    {
        int currentScore = 0;

        for(int i = 0; i < word.length(); i++) {
            currentScore += scoretable[word.charAt(i) - 97];
        }
        return currentScore;
    }
	
    /**
     *  Determines if word is in word list.
     *
     *  @param            wordToMatch      the word (a String) to search in the word list
     *  @return           true if the word is found, otherwise false
     */
    public static boolean isWordInList(String wordToMatch) 
    {
        String currentWord = "";
        String temp = "";
        boolean matched = false;
        Scanner scan = OpenFile.openToRead("Words.txt");
        while(scan.hasNext()) {
            currentWord = scan.nextLine();
            temp = currentWord;
            for(int i = 0; i < wordToMatch.length(); i++) {
                matched = false;
                for(int j = 0; j < temp.length(); j++) {
                    if(wordToMatch.charAt(i) == temp.charAt(j)) {
                        temp = temp.substring(0,j) + temp.substring(j+1);
                        matched = true;
                    }
                }
                if(!matched) {
                    return false;
                }
            }
        }
        return true;
    }
	
    /**
     *  Determines if word has letters in alpha order.
     *
     *  @param            word      the word (a String) to be checked for alpha order
     *  @return           true if the word has letters in alpha order, otherwise false
     */
    public static boolean alphaOrder(String word)
    {
        int currentLetterValue = 0;
        for(int i = 0; i < word.length(); i++) {
            if(word.toLowerCase().charAt(i) >= currentLetterValue) {
                currentLetterValue = (int)word.toLowerCase().charAt(i);
            } else {
                return false;
            }
        }
        return true;
    }
}
/*

C:\Java\Words>java WordToolkit




Please enter a list of letters, from 3 to 12 letters long, without spaces: nwbpnobeoe


            be         bebop           bee          been          beep
           ben          bene         benne            bo           bob
        bonbon          bone         bonne           boo          boob
          boon           bop           bow           ebb          ebon
            en          enow           eon           ewe            ne
           neb           nee          neep          nene          neon
           new            no           nob          none           noo
          noon          nope           now           obe          oboe
            oe            on           one            op           ope
          open            ow           owe           own            pe
           pee          peen           pen         penne          peon
           pew          pone          poon           pow            we
           web           wee          ween          weep           wen
            wo           woe           won           woo           wop


From the list above, words with letters in alpha order:
            be           bee          been          beep           ben
            bo           boo           bop           bow            en
          enow            no           noo           now            op
            ow

Highest scoring word: 11   bebop





Please enter a list of letters, from 3 to 12 letters long, without spaces: aaownbanpwr


            aa            ab           aba           abo            an
           ana          anna          anoa          anon         apron
            ar           arb            aw           awa           awn
            ba           baa           ban        banana           bap
           bar          barn         baron            bo           boa
          boar           bop          bora          born           bow
           bra          bran          braw         brawn           bro
          brow         brown         bwana            na          naan
           nab           nan          nana           nap           naw
         nawab            no           nob          nona        nonpar
        nonwar           nor           now           oar            on
            op            or           ora           orb            ow
           own            pa           pan           par          para
           paw          pawn        pawnor          porn           pow
          prao         prawn           pro          proa          prow
           ran           rap           raw          roan           rob
           row         rowan           wab           wan           wap
           war          warn          warp           waw            wo
           won           wop          worn           wow          wrap


From the list above, words with letters in alpha order:
            aa            ab           abo            an            ar
            aw            bo           bop           bow            no
           nor           now            op            or            ow

Highest scoring word: 11   pawnor





Please enter a list of letters, from 3 to 12 letters long, without spaces: spnotespo


            en           ens           eon          eons          epos
            es           ess         estop        estops            et
            ne          ness          nest         nests           net
         netop        netops          nets            no          noes
           noo         noose        nooses          nope           nos
          nose         noses           not          note         notes
            oe           oes            on           one          ones
           ons         onset        onsets          onto          oops
           oot          oots            op           ope          open
         opens          opes        oppose       opposes           ops
           opt          opts            os           ose          oses
            pe           pen          pens          pent          peon
         peons           pep          pepo         pepos          peps
           pes          peso         pesos          pest         pesto
        pestos         pests           pet          pets          poet
         poets          pone         pones          pons        pontes
          poon         poons          poop         poops           pop
          pope         popes          pops          pose         poses
         posse        posset          post      postpone     postpones
         posts           pot          pots          psst        ptoses
           sen          sent          sept         septs           set
         seton        setons          sets         snoop        snoops
         snoot        snoots          snot         snots            so
           son          sone         sones          sons          soon
       soonest          soot         soots           sop          sops
           sos           sot          sots         spent         spoon
        spoons          spot         spots         steno        stenos
          step         steps       stepson         stone        stones
         stoop        stoops          stop         stope        stopes
         stops           ten          tens            to           toe
          toes           ton          tone         tones          tons
           too          toon         toons           top          tope
         topes         topos          tops          toss


From the list above, words with letters in alpha order:
            en           ens            es           ess            et
            no           noo           nos           not          oops
           oot            op           ops           opt            os
          psst

Highest scoring word: 13   postpones





Please enter a list of letters, from 3 to 12 letters long, without spaces:

GOODBYE!


C:\Java\Words>
//LOL
*/
