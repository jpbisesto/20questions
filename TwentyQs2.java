import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * class TwentyQs will run a twenty questions game for animals. It will load
 * data from a file 20qs.dat. If the program decides that it has no more things
 * to guess then it will ask the user their animal and a question that goes with
 * the animal and add both of these things to list. The user can then decide if
 * they want to play again with these saved results 	from before. At the end of a
 * play the file 20qs.dat will be read from a file to allow for the program to
 * learn
 * 
 * Classes Needed: StringBTNode.java
 * 
 * @author Joseph Bisesto date written: 10/9/13 
 * last updated: 11/22/13
 * 
 */

public class TwentyQs2 {

	static StringBTNode ROOT = new StringBTNode(
			"is it furry? (enter 'yes' or 'no' only)");
	static StringBTNode right = new StringBTNode("a squirrel"); // starting at
																// the right of
																// the ROOT
	static StringBTNode left = new StringBTNode("an armadillo"); // starting at
																	// the left
																	// of the
																	// ROOT

	// declare a runner
	private static StringBTNode runner; // will run through the tree so that it
										// knows where in the tree the user is
										// currently

	public static void main(String[] args) {

		ROOT.right = right; // set runners right pointer to the right node:
		// yes
		ROOT.left = left; // set runners left pointer to the left node: no

		String input; // a string that the user will enter to keep track of yes
						// or no answers, only yes or no

		String animal; // the users animal that they will enter when it is time

		String question; // the distinguishing question that the user will enter

		boolean playing = true; // keep track of if the user is playing or not

		Scanner scanFile; // a scanner to read in file 20qs.dat

		while (playing) { // run the game while the user wants to keep
							// playing

			// load game from file
			try {
				// set up scanner
				scanFile = new Scanner(new File("20qs.dat"));
				// load the file 20qs.dat and set tree created as root
				ROOT = loadGame(ROOT, scanFile);

			} catch (FileNotFoundException e) {
				System.out.println("no file yet");
			}

			runner = ROOT; // set runner = to root, this will allow for pointers
							// to
			// be manipulated

			System.out
					.println("think of an animal and hit enter when you are done");
			input = TextIO.getln();

			while (runner.left != null && runner.right != null) { // if not
																	// equal to
																	// null then
																	// ask
																	// the
																	// appropriate
																	// question

				System.out.println(runner.item); // ask the users the question
													// in the node
				input = TextIO.getln();

				if (input.equals("yes")) {
					runner = runner.right;
				} else if (input.equals("no")) {
					runner = runner.left;
				}

			} // end of inner while

			// asked if it was an animal, get user input
			System.out.println("is it " + runner.item + "?");
			input = TextIO.getln();

			// if input is yes then the computer won. Report it
			if (input.equals("yes")) {
				System.out.println("I WON! YAY! :oD");
			}

			// or else do

			else if (input.equals("no")) {
				// step 1: Ask what the animal was
				System.out
						.println("I give up. What was your animal? (enter in with 'a' or 'an')");
				animal = TextIO.getln();

				// ask for a distinguishing question
				System.out
						.println("what question would distinguish "
								+ animal
								+ " from a "
								+ runner.item
								+ "? (Enter in as question with a question mark on end)");
				question = TextIO.getln();

				// ask for answer to that question if it was their animal
				System.out.println("what is the answer to that for " + animal
						+ "?");
				input = TextIO.getln();

				System.out.println("thanks, I will try and remember that");

				// add all this new information to the tree

				// create and old animal node, containing the string item in
				// runner
				StringBTNode oldAn = new StringBTNode(runner.item);

				StringBTNode animalAdd = new StringBTNode(animal);// a
																	// stringBTNode
																	// with item
																	// being the
																	// users
																	// animal

				if (input.equals("no")) {
					runner.item = question; // set the current runners item to
											// the string, question
					runner.left = animalAdd; // set the left pointer to the
												// users animal
					runner.right = oldAn; // set the right pointer to the oldAn
											// or old animal

				} else if (input.equals("yes")) {
					runner.item = question; // set the current runners item to
											// the string, question
					runner.left = oldAn; // set the left pointer to the old
											// animal node
					runner.right = animalAdd; // set the right pointer to the
												// new animal
				}

			}

			// ask the user if they want to play again and get the answer.
			System.out.println("do you want to play again?");
			playing = TextIO.getlnBoolean();
			// loop will continue if the user says yes

			// save game to file
			try {
				PrintWriter fileName = new PrintWriter(new FileWriter(
						"20qs.dat"));
				createGame(ROOT, fileName);

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

		} // end of outer while loop

		// say goodbye and exit
		System.out.println("Thanks for playing!!!!");
		System.out.println("Goodbye....... :oD");

	}

	/**
	 * createGame() will either create a file that contains the tree or it will
	 * add onto the already existing file that contains all the data in the tree
	 * 
	 * @param r
	 *            is the root of the tree being stored into data
	 * @param pw
	 *            is the printWriter that will write the data to the tree
	 */
	public static void createGame(StringBTNode r, PrintWriter pw) {
		if (r == null) {
			pw.println("---");
			pw.flush();
		} else {
			pw.println(r.item);
			pw.flush();
			createGame(r.left, pw);
			createGame(r.right, pw);
		}

	}

	/**
	 * Method loadGame() will create and return a StringBTNode tree that has
	 * been created by reading from a file
	 * 
	 * @param r
	 *            is a StringBT node being passed in where the top of the tree
	 *            will end up being
	 * @param scanFile
	 *            is a scanner reading a file
	 * @return the StringBTNode that is the root of the tree
	 */
	public static StringBTNode loadGame(StringBTNode r, Scanner scanFile) {
		// get r
		StringBTNode root = r;
		// set the line to set to root as nextLine()
		String toAdd = scanFile.nextLine();

		// base case - recursion will end if the nextLine() is "---"
		if (toAdd.equals("---")) {
			root = null; // set the root to null
			// end recursion
		} else {
			root = new StringBTNode(); // create new StringBTNode
			root.item = toAdd; // add string to this item
			// recursive call for left and right
			root.left = loadGame(root.left, scanFile);
			root.right = loadGame(root.right, scanFile);

		}

		// return the root
		return root;
	}
	//end of class TwentyQs2
}

