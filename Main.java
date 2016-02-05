/* Program to play the game of Kings in the Corner
** Played against a user and a computer.
** Rounds and turns alternate based on the initial dealer (the computer).
**
** Use of classes and no special java libraries
**
** Marek Rybakiewicz (mrybak3)
** CS 342, UIC 2016
*
 */

import java.io.*;
import java.util.Scanner;
import java.util.Random;




/* Necessary class.
** Implementation of all card types
** Can return rank, suite, and color and value of 1 card
*/
class Card{

    //Default Constructor
    Card(){
        rank  = '0';    //Invalid input
        suite = '0';    //Invalid input
        color = '0';    //Invalid input
        value = 0;      //Invalid input
    }

    //Returns the color of the suite
    //Used in constructor
    private char colorSetter(char suite){
        if (suite == 'c' || suite == 's'){
            return 'b'; //The suite is black
        }
        else if ( suite == 'd' || suite == 'h'){
            return 'r'; //The suite is red
        }
        else{
            return '0'; //Invalid input
        }
    }
    //end of ColorSetter

    //Sets the value of the card based on rank
    private char valueSetter(char rank){
        if (Character.toLowerCase(rank) == 'k'){
            return 13;
        }
        else if (Character.toLowerCase(rank) == 'q'){
            return 12;
        }
        else if (Character.toLowerCase(rank) == 'j'){
            return 11;
        }
        else if (Character.toLowerCase(rank) == 't'){
            return 10;
        }
        else if (Character.toLowerCase(rank) == '9'){
            return 9;
        }
        else if (Character.toLowerCase(rank) == '8'){
            return 8;
        }
        else if (Character.toLowerCase(rank) == '7'){
            return 7;
        }
        else if (Character.toLowerCase(rank) == '6'){
            return 6;
        }
        else if (Character.toLowerCase(rank) == '5'){
            return 5;
        }
        else if (Character.toLowerCase(rank) == '4'){
            return 4;
        }
        else if (Character.toLowerCase(rank) == '3'){
            return 3;
        }
        else if (Character.toLowerCase(rank) == '2'){
            return 2;
        }
        else if (Character.toLowerCase(rank) == 'a'){
            return 1;
        }
        else{
            return 0; //Invalid
        }
    }
    //end of ValueSetter

    //Create a card object of specific type
    public Card(char rank, char suite){
        this.rank = rank;
        this.suite = suite;
        color = colorSetter(suite);
        value = valueSetter(rank);
    }
    //end of Card Constructor

    //Print the card information to the console
    public void print(){
        System.out.println(rank + " " + suite + " " + color + " " + value);
    }


    //Returns the card rank
    public char getRank(){
        return rank;
    }
    //Returns the card suite
    public char getSuite(){
        return suite;
    }
    //Returns the card color
    public char getColor(){
        return color;
    }
    //Returns the card value
    public int getValue() {return value;}

    //Instance Variables
    private char rank;  //Rank of the card (Ace - King)
    private char suite; //Suite of the card (Clubs, Diamonds, Hearts, Spades
    private char color; //Color of the Suite (Clubs, Spades: B(lack). Diamonds, Hearts: R(ed))
    private int value;  //Value of the rank, for easy sorting and processing later
}


/* PlayerHands class.
** Contains information about the aI and user hands,
** including size of the hand and penalty points.
**
** Used to check for a valid card in a hand,
** sort the hand,
** print the hand to the console,
** get a given cards position on the hand,
** and calculate penalty points.
*
** Since the hands can expand when cards are drawn, it is useful
** to set the array to the size of all of the cards so we don't have
** to worry about resizing at any point.
 */
class PlayerHands {

    //Constructor
     PlayerHands(){
        //The hand of the human player
        userHand = new Card[52];

        //fill array with black cards
        int i = 0;
        while(i < 52){
            userHand[i] = new Card();
            i++;
        }

        //The hand of the AI
        aIHand   = new Card[52];

        i = 0;
        while(i < 52){
            aIHand[i] = new Card();
            i++;
        }

        //Each player starts with 7 cards
        userCardNum = 7;
        aICardNum   = 7;

        //Each player starts with 0 penalty points
        userPenalty = 0;
         aIPenalty  = 0;
    }
    //End of Constructor

    //New round constructor, keep penalty points
    PlayerHands(int userPoints, int aIPoints){
        //The hand of the human player
        userHand = new Card[52];

        //fill array with black cards
        int i = 0;
        while(i < 52){
            userHand[i] = new Card();
            i++;
        }

        //The hand of the AI
        aIHand   = new Card[52];

        i = 0;
        while(i < 52){
            aIHand[i] = new Card();
            i++;
        }

        //Each player starts with 7 cards
        userCardNum = 7;
        aICardNum   = 7;

        //Each player starts with 0 penalty points
        userPenalty = userPoints;
        aIPenalty  = aIPoints;
    }
    //End of new round constructor

    //Check if a card is in the users hand (already lowercase)
    protected int validCard(Card[] hand, Card card){
        int i = 0;

        while(i < userCardNum){
            if( hand[i].getRank() == card.getRank() ){
                if( hand[i].getSuite() == card.getSuite()){
                    //Card is in the players hand if the rank and suite match
                    return 0;
                }
            }
            i++;
        }

        return 1;
    }
    //End of validCard

    //Sort the hand
    protected void sortHand(Card[] hand, int count){
        int i = 0;
        int swapCount = 1;

        //Bubble sort
        while(swapCount == 1) {
            swapCount = 0;

            //Count will never be close to the end of the array so we do not have to error check
            while (i < count) {
                Card tempCard  = new Card();


                //Swap
                if (hand[i].getValue() < hand[i+1].getValue()){
                    tempCard = hand[i];

                    hand[i] = hand[i+1];

                    hand[i+1] = tempCard;

                    i++;
                    swapCount = 1;
                }
                //Don't swap
                else{
                    i++;
                }

            }

            i = 0;
        }
    }
    //End of sortHand

    //Print a players hand in sorted order
    //Uses sortHand as a helper
    protected void printHand(Card[] hand, int count){
        int i = 0;

        sortHand(hand, count);

        //Print the hand in uppercase
        while (i < count) {
            System.out.print(Character.toUpperCase(hand[i].getRank()) + "" + Character.toUpperCase(hand[i].getSuite()) + " ");
            i++;
        }
        System.out.println();

    }
    //End of printHand


    //Returns the index in the hand where the specified card is
    protected int cardPosition(Card[] hand, int cardNum, Card card){
        int i = 0;

        while (i < cardNum){
            //If the cards are the same
            if((hand[i].getRank() ==  card.getRank()) && (hand[i].getSuite() == card.getSuite())){
                return i;
            }
            i++;
        }


        //Card not found
        return -1;
    }
    //End of cardPosition

    //Returns the penalty points for the hand
    protected int penaltyCalculator(Card[] hand, int cardNum, int penalty){
         int i = 0;

        while(i < cardNum){
            //If the card is a King, add 10 points
            if(hand[i].getValue() == 13 ){
                penalty = penalty + 10;
            }
            //Otherwise, add 1 points
            else if (hand[i].getValue() != 0){
                penalty = penalty + 1;
            }

            i++;
        }

        return penalty;
    }
    //End of penaltyCalculator

    protected static Card[]    userHand;    //The hand of the user
    protected static Card[]      aIHand;    //The hand of the AI
    protected static int    userCardNum;    //Amount of cards in users hand
    protected static int      aICardNum;    //Amount of cards in AI hand
    protected static int    userPenalty;    //Amount of penalty points the user has
    protected static int      aIPenalty;    //Amount of penalty points the AI has
}
//End of PlayerHands


/* KingPiles class.
** Extension of Piles, holds the king piles (5-8)
** Creates an array of maximum possible cards in a pile
 */
class KingPiles extends Piles{
    KingPiles(){
        //Create a new lay-down pile, that starts with no cards
        kingPile5 = new Card[13];
        kingPile5Count = 0;

        kingPile6 = new Card[13];
        kingPile6Count = 0;

        kingPile7 = new Card[13];
        kingPile7Count = 0;

        kingPile8 = new Card[13];
        kingPile8Count = 0;
    }


    //Numbered beginning at 5 to keep the idea of connection with the 4 other card piles
    protected Card[]      kingPile5;
    protected int    kingPile5Count;

    protected Card[]      kingPile6;
    protected int    kingPile6Count;

    protected Card[]      kingPile7;
    protected int    kingPile7Count;

    protected Card[]      kingPile8;
    protected int    kingPile8Count;

}
//End of KingPiles class

/* Piles Class
** Holds the  piles (1-4)
**The most cards a single pile can have is 13 (A-K)
** therefore that is the size of each pile array so no resizing is needed.
 */

class Piles{
    Piles(){
        //Create a new lay-down pile, that starts with no cards
        pile1 = new Card[13];
        pile1Count = 0;

        pile2 = new Card[13];
        pile2Count = 0;

        pile3 = new Card[13];
        pile3Count = 0;

        pile4 = new Card[13];
        pile4Count = 0;
    }



    protected Card[]      pile1;
    protected int    pile1Count;

    protected Card[]      pile2;
    protected int    pile2Count;

    protected Card[]      pile3;
    protected int    pile3Count;

    protected Card[]      pile4;
    protected int    pile4Count;
}

//End of Piles class



/* CardPile Class
** Treated as the game itself, holding all game related information and data structures.
** Creates a new game, sets up all board, hand, and pile information
** Runs through the user input and AI loops.
** Has the ability to create a new game/round based on previous dealer
 */
class CardPile{

    // Creates the initial game set-up.
    CardPile(){
        //Dealer starts as AI
        dealer = 0;

        //The draw pile starts out without the need to be incremented
        //Since no cards have been removed
        deckIncrementer = 0;

        //Create the base deck of 52 cards
        createDeck();

        //printCardDeck();

        //Shuffle
        shuffle();

        //Create the hands of both players
        hands = new PlayerHands();

        //Create the lay-down piles of the game board
        piles = new Piles();
        kingPiles = new KingPiles();

        //Deals the cards based on the dealer
        //This handles the player hands as well as the lay-down piles
        deal();

    }
    //End of CardPile constructor

    //Creates the next round of the game
    CardPile(int newDealer, int userPenPoints, int aIPenPoints){
        //Dealer starts as AI
        dealer = newDealer;

        //The draw pile starts out without the need to be incremented
        //Since no cards have been removed
        deckIncrementer = 0;

        //Create the base deck of 52 cards
        createDeck();

        //printCardDeck();

        //Shuffle
        shuffle();

        //Create the hands of both players
        hands = new PlayerHands(userPenPoints, aIPenPoints);

        //Create the lay-down piles of the game board
        piles = new Piles();
        kingPiles = new KingPiles();

        //Deals the cards based on the dealer
        //This handles the player hands as well as the lay-down piles
        deal();
    }
    //End of New round CardPile constructor

    //Create the initial 52 card deck
    private void createDeck(){
        char[] rankArray = new char[] {'a', '2', '3', '4', '5', '6', '7', '8', '9', 't', 'j', 'q', 'k'};
        char[] suitArray = new char[] {'c', 'd', 'h', 's'};

        int i = 0;
        int j = 0;

        cardDeck = new Card[52];

        //Create the clubs
        while(i < 13){
            cardDeck[i] = new Card(rankArray[i], suitArray[j]);
            i++;
        }
        j++;

        //Create the diamonds
        while(i < 26){
            cardDeck[i] = new Card(rankArray[i%13], suitArray[j]);
            i++;
        }
        j++;

        //Create the hearts
        while(i < 39){
            cardDeck[i] = new Card(rankArray[i%13], suitArray[j]);
            i++;
        }
        j++;

        //Create the spades
        while(i < 52){
            cardDeck[i] = new Card(rankArray[i%13], suitArray[j]);
            i++;
        }

        i = 0;
        j = 0;
    }
    //End of createDeck

    //Shuffle the initial 52 card deck
    private void shuffle(){
        Card first = new Card();
        Card second = new Card();
        int  i,j,x;

        //Set up for random integer
        Random rand = new Random();

        //For all cards
        for(x = 0; x < 52; x++){

            //Get a random integer between 0 and 51
            i = rand.nextInt(51);
            j = rand.nextInt(51);

            //Get two cards
            first = cardDeck[i];
            second = cardDeck[j];

            //System.out.println(first.getRank() + "" + first.getSuite());

            //Swap
            cardDeck[i] = second;
            cardDeck[j] = first;

        }

//        i = 0;
//        while(i < 52){
//            System.out.println(cardDeck[i].getRank() + "" + cardDeck[i].getSuite() + " " + cardDeck[i].getColor());
//
//            int z = 0;
//
//            //Check if copy was done correctly
//            while(z < 52){
//                if( (i != z) &&  (cardDeck[i].getRank() == cardDeck[z].getRank()) && (cardDeck[i].getSuite() == cardDeck[z].getSuite()) )
//                    System.out.println("ERROR: More than one copy found");;
//
//                z++;
//            }
//
//
//            i++;
//        }

    }
    //End of shuffle

    /* Deal cards to players based on who the dealer is
    ** Helper of deal
     */
    private void dealPlayers(){
        //printCardDeck();

        int i;
        int j = 0;
        int k = 0;
        switch (dealer) {
            //AI dealer
            case 0:
                //Deal out the cards with the Top card going to the user
                for (i = 0; i < 14; i++) {
                    //If draw is even (first draw included)
                    if ((i % 2) == 0) {
                        //AI gives card to user
                        hands.userHand[j] = cardDeck[i];

                        //Set to No Card
                        cardDeck[i] = new Card();

                        j++;
                    }
                    //If draw is odd
                    else if ((i % 2) != 0) {
                        //AI gives card to itself
                        hands.aIHand[k] = cardDeck[i];

                        //Set to No Card
                        cardDeck[i] = new Card();

                        k++;
                    }
                }
                break;
            //Human dealer
            case 1:
                //Deal out the cards with the Top card going to the user
                for (i = 0; i < 14; i++) {
                    //If draw is even (first draw included)
                    if ((i % 2) == 0) {
                        //User gives card to AI
                        hands.aIHand[j] = cardDeck[i];

                        //Set to No Card
                        cardDeck[i] = new Card();

                        j++;
                    }
                    //If draw is odd
                    else if ((i % 2) != 0) {
                        //User gives card to itself
                        hands.userHand[k] = cardDeck[i];

                        //Set to No Card
                        cardDeck[i] = new Card();

                        k++;
                    }
                }
                break;
        }

        //printHand(hands.userHand, hands.userCardNum);
        //printHand(hands.aIHand, hands.aICardNum);
        // printCardDeck();

        //14 cards are out of the deck after dealing
        deckIncrementer = 14;
    }
    //End of dealPlayers

    /* Deals 1 card to each pile
    ** Helper of deal
    */
    private void dealPiles(){
        //printCardDeck();

        piles.pile1[0] = cardDeck[deckIncrementer];
        piles.pile1Count++;
        //Remove the card
        cardDeck[deckIncrementer] = new Card();
        deckIncrementer++;

        piles.pile2[0] = cardDeck[deckIncrementer];
        piles.pile2Count++;
        //Remove the card
        cardDeck[deckIncrementer] = new Card();
        deckIncrementer++;

        piles.pile3[0] = cardDeck[deckIncrementer];
        piles.pile3Count++;
        //Remove the card
        cardDeck[deckIncrementer] = new Card();
        deckIncrementer++;

        piles.pile4[0] = cardDeck[deckIncrementer];
        piles.pile4Count++;
        //Remove the card
        cardDeck[deckIncrementer] = new Card();
        deckIncrementer++;

        //printCardDeck();

    }
    //End of dealPiles

    /* Deals based on the dealer to the players and the piles
    ** Assisted by respective helper functions dealPiles and dealPlayers
     */
    private void deal() {
        dealPlayers();

        dealPiles();
    }
    //End of deal


    //Prints the deck of cards
    private void printCardDeck(){
        int i = 0;
        while(i < 13){
            System.out.print(cardDeck[i].getRank() + "" + cardDeck[i].getSuite() + " ");
            i++;
        }
        System.out.println("");

        while(i < 26){
            System.out.print(cardDeck[i].getRank() + "" + cardDeck[i].getSuite() + " ");
            i++;
        }
        System.out.println("");

        while(i < 39){
            System.out.print(cardDeck[i].getRank() + "" + cardDeck[i].getSuite() + " ");
            i++;
        }
        System.out.println("");

        while(i < 52){
            System.out.print(cardDeck[i].getRank() + "" + cardDeck[i].getSuite() + " ");
            i++;
        }
        System.out.println("");
        System.out.println("");


    }
    //End of printCardDeck

    //Prints the user interface, displaying the board and hand information
    private void UIPrint(){
        int i = 0;

        System.out.println("\n\n\n\n");

        System.out.print("Main Pile 1: ");
        while(i < piles.pile1Count){
            System.out.print(Character.toUpperCase(piles.pile1[i].getRank()) + "" + Character.toUpperCase(piles.pile1[i].getSuite()) + " ");
            i++;
        }
        i = 0;
        System.out.println();

        System.out.print("Main Pile 2: ");
        while(i < piles.pile2Count){
            System.out.print(Character.toUpperCase(piles.pile2[i].getRank()) + "" + Character.toUpperCase(piles.pile2[i].getSuite()) + " ");
            i++;
        }
        i = 0;
        System.out.println();

        System.out.print("Main Pile 3: ");
        while(i < piles.pile3Count){
            System.out.print(Character.toUpperCase(piles.pile3[i].getRank()) + "" + Character.toUpperCase(piles.pile3[i].getSuite()) + " ");
            i++;
        }
        i = 0;
        System.out.println();

        System.out.print("Main Pile 4: ");
        while(i < piles.pile4Count){
            System.out.print(Character.toUpperCase(piles.pile4[i].getRank()) + "" + Character.toUpperCase(piles.pile4[i].getSuite()) + " ");
            i++;
        }
        i = 0;
        System.out.println();


        //Print Corner King Piles
        System.out.print("King Pile 5: ");
        while(i < kingPiles.kingPile5Count){
            System.out.print(Character.toUpperCase(kingPiles.kingPile5[i].getRank()) + "" + Character.toUpperCase(kingPiles.kingPile5[i].getSuite()) + " ");
            i++;
        }
        i = 0;
        System.out.println();

        System.out.print("King Pile 6: ");
        while(i < kingPiles.kingPile6Count){
            System.out.print(Character.toUpperCase(kingPiles.kingPile6[i].getRank()) + "" + Character.toUpperCase(kingPiles.kingPile6[i].getSuite()) + " ");
            i++;
        }
        i = 0;
        System.out.println();

        System.out.print("King Pile 7: ");
        while(i < kingPiles.kingPile7Count){
            System.out.print(Character.toUpperCase(kingPiles.kingPile7[i].getRank()) + "" + Character.toUpperCase(kingPiles.kingPile7[i].getSuite()) + " ");
            i++;
        }
        i = 0;
        System.out.println();

        System.out.print("King Pile 8: ");
        while(i < kingPiles.kingPile8Count){
            System.out.print(Character.toUpperCase(kingPiles.kingPile8[i].getRank()) + "" + Character.toUpperCase(kingPiles.kingPile8[i].getSuite()) + " ");
            i++;
        }
        i = 0;
        System.out.println();
        System.out.println();


        System.out.println("Computer Player has " + hands.aICardNum + " cards");
        //hands.printHand(hands.aIHand, hands.aICardNum);

        System.out.print("Your hand: ");
        hands.printHand(hands.userHand, hands.userCardNum);

        System.out.print("Move> ");
    }
    //End of UIPrint

    //Quits the game, helper of userInput
    private int userQuit(String[] finalInput){
        //Check if a single character was given
        if(finalInput.length != 1){
            System.out.println("Invalid input");
            return 1;
        }

        System.out.println("The program will now quit. Thanks for playing!");
        System.exit(0);

        return 1;
    }
    //End of userQuit

    //Prints help information, helper of userInput
    private int userHelp(String[] finalInput){
        //Check if a single character was given
        if(finalInput.length != 1){
            System.out.println("Invalid input");
            return 1;
        }


        System.out.println();

        System.out.println("The following commands are available:");
        System.out.println(" 'Q':");
        System.out.println("  Quits the program.");
        System.out.println();
        System.out.println(" 'H':");
        System.out.println("  Displays a help message detailing available commands.");
        System.out.println();
        System.out.println(" 'A':");
        System.out.println("  Displays information about the program and programmer.");
        System.out.println();
        System.out.println(" 'D':");
        System.out.println("  Draws a card. Takes the top card from the Draw pile, adds it to the users hand,");
        System.out.println("  and ends the users turn.");
        System.out.println();
        System.out.println(" 'L <Card> <Pile>':");
        System.out.println("  Lay a card from the user hand onto a pile. The card must be specified as a two character ");
        System.out.println("  string, rank followed by suite (example: '2S' for two of spades). The pile is specified by numbers 1-8.");
        System.out.println("  If the pile is empty, any card can be laid down on piles 1-4, while only a King can be laid down on piles 5-8.");
        System.out.println();
        System.out.println(" 'M <Pile1> <Pile2>'");
        System.out.println("  Moves Pile1 onto Pile2. The piles are specified by numbers 1-8. Only piles 1-4 can be moved,");
        System.out.println("  while the King piles 5-8 may only be moved onto.");
        System.out.println();


        System.out.print("Press ENTER to continue> ");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        //No card was drawn
        return 1;

    }
    //End of userHelp

    //Prints the about page, helper of userInput
    private int userAbout(String[] finalInput){
        //Check if a single character was given
        if(finalInput.length != 1){
            System.out.println("Invalid input");
            return 1;
        }


        System.out.println();

        System.out.println("About the program:");
        System.out.println(" This program is an implementation of the card game Kings in the Corner. ");
        System.out.println(" The purpose of the game is to lay down all of the cards in the players hand");
        System.out.println(" on one of the 8 piles.");
        System.out.println();
        System.out.println(" There are three main rules for laying down a card:");
        System.out.println("  1. The card must be exactly one rank lower than the top card of the pile.");
        System.out.println("  2. The card must be of opposite color than the top card of the pile.");
        System.out.println("  3. If a pile is full, a card cannot be laid onto it. ");
        System.out.println();
        System.out.println(" Piles 1-4 can have any card at the bottom due to dealing,");
        System.out.println(" while piles 5-8 (King piles) must start with a King being placed.");
        System.out.println();
        System.out.println(" Piles 1-4 can be moved on top of each-other and on top of the King piles 5-8,");
        System.out.println(" as long as the 3 main rules still apply. King piles may not be moved.");
        System.out.println();
        System.out.println(" The computer will deal first, and the user will make the first turn, after");
        System.out.println(" which turns will alternate between the computer and the player.");
        System.out.println();
        System.out.println(" A turn ends when the player draws a card. A player may choose to not place any cards in a given round.");
        System.out.println();
        System.out.println(" The game ends when a player either has no cards in his hand, or no more moves can be made.");
        System.out.println(" Penalty points are then calculated for the round for each remaining card in a players hand: ");
        System.out.println("  A king is worth 10 penalty points, while every other card is worth 1.");
        System.out.println("  A game is played until one player gets 25 penalty points, and that player is declared the loser.");
        System.out.println();
        System.out.println(" At the start of each round, the dealer changes and therefore the user with the starting play alternates.");
        System.out.println(" At the end of the game the user may choose to play again, and the dealer is whoever was not dealing last round.");
        System.out.println();
        System.out.println(" Made by Marek Rybakiewicz (mrybak3)");
        System.out.println(" CS 342, UIC 2016");
        System.out.println();

        System.out.print("Press ENTER to continue> ");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        //No card was drawn
        return 1;

    }
    //End of userAbout

    //Draws a card, helper of userInput
    private int userDraw(String[] finalInput){
        //Check if a single character was given
        if(finalInput.length != 1){
            System.out.println("Invalid input");
            return 1;
        }

        //Draw pile is empty
        if (deckIncrementer == 52) {
            System.out.println("The deck is empty. Moving on to next player");
            return 0;
        }

        //Place the top card of the deck at the end of the user hand
        hands.userCardNum++;
        hands.userHand[hands.userCardNum] = cardDeck[deckIncrementer];

        //The card does not exist in the deck anymore
        cardDeck[deckIncrementer] = new Card();
        deckIncrementer++;

        //A card was drawn
        return 0;
    }
    //End of userDraw

    //Lays a card onto a pile, helper of userInput
    private int userLayDown(String[] finalInput){
        //Check if 3 strings were given
        if(finalInput.length != 3){
            System.out.println();
            System.out.println("Input must be given in the form L <Card> <Pile>");
            System.out.println("Where card is a 2 character string of rank followed by suite");
            System.out.println("And pile is an integer 1-8");
            return 1;
        }

        //If cards are not 2 character strings, invalid
        if((finalInput[1].length() != 2) || (finalInput[2].length() != 1)){
            System.out.println();
            System.out.println("Input must be given in the form L <Card> <Pile>");
            System.out.println("Where card is a 2 character string of rank followed by suite");
            System.out.println("And pile is an integer 1-8");
            return 1;
        }

        int pileValid = 1;
        //Check if pile is valid
        if((finalInput[2].equals("1")) || (finalInput[2].equals("2")) || (finalInput[2].equals("3")) || (finalInput[2].equals("4")) ||
                (finalInput[2].equals("5")) || (finalInput[2].equals("6")) || (finalInput[2].equals("7")) || (finalInput[2].equals("8")) ) {
            //The pile is valid
            pileValid = 0;
        }
        if(pileValid == 1){
            System.out.println();
            System.out.println("Pile number is not valid. Input must be between 1-8");
            return 1;
        }

        int i = 0;

        //Create the card the user wanted (for checking)
        Card card = new Card(Character.toLowerCase(finalInput[1].charAt(0)), Character.toLowerCase(finalInput[1].charAt(1)));

        //Hold the pile number
        String pileString = finalInput[2];

        //Check if valid card is given (is in the users hand)
        int validCheckValue = hands.validCard(hands.userHand, card);
        if(validCheckValue == 1){
            System.out.println("The card is not in the players hand");
            return  1;
        }

        //Check if card can be laid down on the pile, and lay it down
        switch(pileString){
            //Pile 1
            case "1":
                //If the pile is full, don't lay down
                if(piles.pile1Count == 13){
                    System.out.println();
                    System.out.println("The pile is full!");
                    return 1;
                }
                //If the pile is empty, lay down
                else if( piles.pile1Count == 0){

                    //Get the position of the card in the users hand
                    i = hands.cardPosition(hands.userHand, hands.userCardNum, card);

                    //Put the card in the pile, increment pile card count
                    piles.pile1[piles.pile1Count] = hands.userHand[i];
                    piles.pile1Count++;

                    //Remove card from user hand, decrement user card count
                    hands.userHand[i] = new Card();

                    //Ensures the blank card is at the end of the real cards
                    hands.sortHand(hands.userHand, hands.userCardNum);
                    hands.userCardNum--;

                    //Check if user won the round
                    if (hands.userCardNum == 0){
                        return -1;
                    }

                    return 1;

                }
                //If the card is not a valid placement, (The card is not one less in value, or it is the same color as the top of the pile)
                //Don't place
                else if ( (card.getValue() != (piles.pile1[piles.pile1Count - 1].getValue() - 1))  ||
                        (card.getColor() == (piles.pile1[piles.pile1Count - 1].getColor()))){

                    System.out.println();
                    System.out.println("Card must be exactly one rank less than the top card, and of opposite color");
                    return 1;

                }
                //The card is a valid placement, lay it down,
                //Increment the pile count, decrement user hand count, sort
                else {

                    //Get the position of the card in the users hand
                    i = hands.cardPosition(hands.userHand, hands.userCardNum, card);

                    //Put the card in the pile, increment pile card count
                    piles.pile1[piles.pile1Count] = hands.userHand[i];
                    piles.pile1Count++;

                    //Remove card from user hand, decrement user card count
                    hands.userHand[i] = new Card();

                    //Ensures the blank card is at the end of the real cards
                    hands.sortHand(hands.userHand, hands.userCardNum);
                    hands.userCardNum--;

                    //Check if user won the round
                    if (hands.userCardNum == 0){
                        return -1;
                    }

                    return 1;
                }
                //Pile 2
            case "2":
                //If the pile is full, don't lay down
                if(piles.pile2Count == 13){
                    System.out.println();
                    System.out.println("The pile is full!");
                    return 1;
                }
                //If the pile is empty, lay down
                else if( piles.pile2Count == 0){

                    //Get the position of the card in the users hand
                    i = hands.cardPosition(hands.userHand, hands.userCardNum, card);

                    //Put the card in the pile, increment pile card count
                    piles.pile2[piles.pile2Count] = hands.userHand[i];
                    piles.pile2Count++;

                    //Remove card from user hand, decrement user card count
                    hands.userHand[i] = new Card();

                    //Ensures the blank card is at the end of the real cards
                    hands.sortHand(hands.userHand, hands.userCardNum);
                    hands.userCardNum--;

                    //Check if user won the round
                    if (hands.userCardNum == 0){
                        return -1;
                    }

                    return 1;
                }
                //If the card is not a valid placement, (The card is not one less in value, or it is the same color as the top of the pile)
                //Don't place
                else if ( (card.getValue() != (piles.pile2[piles.pile2Count - 1].getValue() - 1))  ||
                        (card.getColor() == (piles.pile2[piles.pile2Count - 1].getColor()))){

                    System.out.println();
                    System.out.println("Card must be exactly one rank less than the top card, and of opposite color");
                    return 1;

                }
                //The card is a valid placement, lay it down,
                //Increment the pile count, decrement user hand count, sort
                else {

                    //Get the position of the card in the users hand
                    i = hands.cardPosition(hands.userHand, hands.userCardNum, card);

                    //Put the card in the pile, increment pile card count
                    piles.pile2[piles.pile2Count] = hands.userHand[i];
                    piles.pile2Count++;

                    //Remove card from user hand, decrement user card count
                    hands.userHand[i] = new Card();

                    //Ensures the blank card is at the end of the real cards
                    hands.sortHand(hands.userHand, hands.userCardNum);
                    hands.userCardNum--;

                    //Check if user won the round
                    if (hands.userCardNum == 0){
                        return -1;
                    }

                    return 1;
                }
                //Pile 3
            case "3":
                //If the pile is full, don't lay down
                if(piles.pile3Count == 13){
                    System.out.println();
                    System.out.println("The pile is full!");
                    return 1;
                }
                //If the pile is empty, lay down
                else if( piles.pile3Count == 0){

                    //Get the position of the card in the users hand
                    i = hands.cardPosition(hands.userHand, hands.userCardNum, card);

                    //Put the card in the pile, increment pile card count
                    piles.pile3[piles.pile3Count] = hands.userHand[i];
                    piles.pile3Count++;

                    //Remove card from user hand, decrement user card count
                    hands.userHand[i] = new Card();

                    //Ensures the blank card is at the end of the real cards
                    hands.sortHand(hands.userHand, hands.userCardNum);
                    hands.userCardNum--;

                    //Check if user won the round
                    if (hands.userCardNum == 0){
                        return -1;
                    }

                    return 1;

                }
                //If the card is not a valid placement, (The card is not one less in value, or it is the same color as the top of the pile)
                //Don't place
                else if ( (card.getValue() != (piles.pile3[piles.pile3Count - 1].getValue() - 1))  ||
                        (card.getColor() == (piles.pile3[piles.pile3Count - 1].getColor()))){

                    System.out.println();
                    System.out.println("Card must be exactly one rank less than the top card, and of opposite color");
                    return 1;

                }
                //The card is a valid placement, lay it down,
                //Increment the pile count, decrement user hand count, sort
                else {

                    //Get the position of the card in the users hand
                    i = hands.cardPosition(hands.userHand, hands.userCardNum, card);

                    //Put the card in the pile, increment pile card count
                    piles.pile3[piles.pile3Count] = hands.userHand[i];
                    piles.pile3Count++;

                    //Remove card from user hand, decrement user card count
                    hands.userHand[i] = new Card();

                    //Ensures the blank card is at the end of the real cards
                    hands.sortHand(hands.userHand, hands.userCardNum);
                    hands.userCardNum--;

                    //Check if user won the round
                    if (hands.userCardNum == 0){
                        return -1;
                    }

                    return 1;
                }
                //Pile 4
            case "4":
                //If the pile is full, don't lay down
                if(piles.pile4Count == 13){
                    System.out.println();
                    System.out.println("The pile is full!");
                    return 1;
                }
                //If the pile is empty, lay down
                else if( piles.pile4Count == 0){

                    //Get the position of the card in the users hand
                    i = hands.cardPosition(hands.userHand, hands.userCardNum, card);

                    //Put the card in the pile, increment pile card count
                    piles.pile4[piles.pile4Count] = hands.userHand[i];
                    piles.pile4Count++;

                    //Remove card from user hand, decrement user card count
                    hands.userHand[i] = new Card();

                    //Ensures the blank card is at the end of the real cards
                    hands.sortHand(hands.userHand, hands.userCardNum);
                    hands.userCardNum--;

                    //Check if user won the round
                    if (hands.userCardNum == 0){
                        return -1;
                    }

                    return 1;

                }
                //If the card is not a valid placement, (The card is not one less in value, or it is the same color as the top of the pile)
                //Don't place
                else if ( (card.getValue() != (piles.pile4[piles.pile4Count - 1].getValue() - 1))  ||
                        (card.getColor() == (piles.pile4[piles.pile4Count - 1].getColor()))){

                    System.out.println();
                    System.out.println("Card must be exactly one rank less than the top card, and of opposite color");
                    return 1;

                }
                //The card is a valid placement, lay it down,
                //Increment the pile count, decrement user hand count, sort
                else {

                    //Get the position of the card in the users hand
                    i = hands.cardPosition(hands.userHand, hands.userCardNum, card);

                    //Put the card in the pile, increment pile card count
                    piles.pile4[piles.pile4Count] = hands.userHand[i];
                    piles.pile4Count++;

                    //Remove card from user hand, decrement user card count
                    hands.userHand[i] = new Card();

                    //Ensures the blank card is at the end of the real cards
                    hands.sortHand(hands.userHand, hands.userCardNum);
                    hands.userCardNum--;

                    //Check if user won the round
                    if (hands.userCardNum == 0){
                        return -1;
                    }

                    return 1;
                }
                //King Pile 5
            case "5":
                //If the pile is full, don't lay down
                if(kingPiles.kingPile5Count == 13){
                    System.out.println();
                    System.out.println("The pile is full!");
                    return 1;
                }
                //If the pile is empty, and the card is a King, lay down
                else if( kingPiles.kingPile5Count == 0){
                    if(card.getValue() != 13){
                        System.out.println("A king must be placed first on the King piles");
                        return 1;
                    }

                    //Get the position of the card in the users hand
                    i = hands.cardPosition(hands.userHand, hands.userCardNum, card);

                    //Put the card in the pile, increment pile card count
                    kingPiles.kingPile5[kingPiles.kingPile5Count] = hands.userHand[i];
                    kingPiles.kingPile5Count++;

                    //Remove card from user hand, decrement user card count
                    hands.userHand[i] = new Card();

                    //Ensures the blank card is at the end of the real cards
                    hands.sortHand(hands.userHand, hands.userCardNum);
                    hands.userCardNum--;

                    //Check if user won the round
                    if (hands.userCardNum == 0){
                        return -1;
                    }

                    return 1;
                }
                //If the card is not a valid placement, (The card is not one less in value, or it is the same color as the top of the pile)
                //Don't place
                else if ( (card.getValue() != (kingPiles.kingPile5[kingPiles.kingPile5Count - 1].getValue() - 1))  ||
                        (card.getColor() == (kingPiles.kingPile5[kingPiles.kingPile5Count - 1].getColor()))){

                    System.out.println();
                    System.out.println("Card must be exactly one rank less than the top card, and of opposite color");
                    return 1;

                }
                //The card is a valid placement, lay it down,
                //Increment the pile count, decrement user hand count, sort
                else {

                    //Get the position of the card in the users hand
                    i = hands.cardPosition(hands.userHand, hands.userCardNum, card);

                    //Put the card in the pile, increment pile card count
                    kingPiles.kingPile5[kingPiles.kingPile5Count] = hands.userHand[i];
                    kingPiles.kingPile5Count++;

                    //Remove card from user hand, decrement user card count
                    hands.userHand[i] = new Card();

                    //Ensures the blank card is at the end of the real cards
                    hands.sortHand(hands.userHand, hands.userCardNum);
                    hands.userCardNum--;

                    //Check if user won the round
                    if (hands.userCardNum == 0){
                        return -1;
                    }

                    return 1;
                }
                //King Pile 6
            case "6":
                //If the pile is full, don't lay down
                if(kingPiles.kingPile6Count == 13){
                    System.out.println();
                    System.out.println("The pile is full!");
                    return 1;
                }
                //If the pile is empty, and the card is a King, lay down
                else if( kingPiles.kingPile6Count == 0){
                    if(card.getValue() != 13){
                        System.out.println("A king must be placed first on the King piles");
                        return 1;
                    }

                    //Get the position of the card in the users hand
                    i = hands.cardPosition(hands.userHand, hands.userCardNum, card);

                    //Put the card in the pile, increment pile card count
                    kingPiles.kingPile6[kingPiles.kingPile6Count] = hands.userHand[i];
                    kingPiles.kingPile6Count++;

                    //Remove card from user hand, decrement user card count
                    hands.userHand[i] = new Card();

                    //Ensures the blank card is at the end of the real cards
                    hands.sortHand(hands.userHand, hands.userCardNum);
                    hands.userCardNum--;

                    //Check if user won the round
                    if (hands.userCardNum == 0){
                        return -1;
                    }

                    return 1;
                }
                //If the card is not a valid placement, (The card is not one less in value, or it is the same color as the top of the pile)
                //Don't place
                else if ( (card.getValue() != (kingPiles.kingPile6[kingPiles.kingPile6Count - 1].getValue() - 1))  ||
                        (card.getColor() == (kingPiles.kingPile6[kingPiles.kingPile6Count - 1].getColor()))){

                    System.out.println();
                    System.out.println("Card must be exactly one rank less than the top card, and of opposite color");
                    return 1;

                }
                //The card is a valid placement, lay it down,
                //Increment the pile count, decrement user hand count, sort
                else {

                    //Get the position of the card in the users hand
                    i = hands.cardPosition(hands.userHand, hands.userCardNum, card);

                    //Put the card in the pile, increment pile card count
                    kingPiles.kingPile6[kingPiles.kingPile6Count] = hands.userHand[i];
                    kingPiles.kingPile6Count++;

                    //Remove card from user hand, decrement user card count
                    hands.userHand[i] = new Card();

                    //Ensures the blank card is at the end of the real cards
                    hands.sortHand(hands.userHand, hands.userCardNum);
                    hands.userCardNum--;

                    //Check if user won the round
                    if (hands.userCardNum == 0){
                        return -1;
                    }

                    return 1;
                }
                //King Pile 7
            case "7":
                //If the pile is full, don't lay down
                if(kingPiles.kingPile7Count == 13){
                    System.out.println();
                    System.out.println("The pile is full!");
                    return 1;
                }
                //If the pile is empty, and the card is a King, lay down
                else if( kingPiles.kingPile7Count == 0){
                    if(card.getValue() != 13){
                        System.out.println("A king must be placed first on the King piles");
                        return 1;
                    }

                    //Get the position of the card in the users hand
                    i = hands.cardPosition(hands.userHand, hands.userCardNum, card);

                    //Put the card in the pile, increment pile card count
                    kingPiles.kingPile7[kingPiles.kingPile7Count] = hands.userHand[i];
                    kingPiles.kingPile7Count++;

                    //Remove card from user hand, decrement user card count
                    hands.userHand[i] = new Card();

                    //Ensures the blank card is at the end of the real cards
                    hands.sortHand(hands.userHand, hands.userCardNum);
                    hands.userCardNum--;

                    //Check if user won the round
                    if (hands.userCardNum == 0){
                        return -1;
                    }

                    return 1;
                }
                //If the card is not a valid placement, (The card is not one less in value, or it is the same color as the top of the pile)
                //Don't place
                else if ( (card.getValue() != (kingPiles.kingPile7[kingPiles.kingPile7Count - 1].getValue() - 1))  ||
                        (card.getColor() == (kingPiles.kingPile7[kingPiles.kingPile7Count - 1].getColor()))){

                    System.out.println();
                    System.out.println("Card must be exactly one rank less than the top card, and of opposite color");
                    return 1;

                }
                //The card is a valid placement, lay it down,
                //Increment the pile count, decrement user hand count, sort
                else {

                    //Get the position of the card in the users hand
                    i = hands.cardPosition(hands.userHand, hands.userCardNum, card);

                    //Put the card in the pile, increment pile card count
                    kingPiles.kingPile7[kingPiles.kingPile7Count] = hands.userHand[i];
                    kingPiles.kingPile7Count++;

                    //Remove card from user hand, decrement user card count
                    hands.userHand[i] = new Card();

                    //Ensures the blank card is at the end of the real cards
                    hands.sortHand(hands.userHand, hands.userCardNum);
                    hands.userCardNum--;

                    //Check if user won the round
                    if (hands.userCardNum == 0){
                        return -1;
                    }

                    return 1;
                }
                //King Pile 8
            case "8":
                //If the pile is full, don't lay down
                if(kingPiles.kingPile8Count == 13){
                    System.out.println();
                    System.out.println("The pile is full!");
                    return 1;
                }
                //If the pile is empty, and the card is a King, lay down
                else if( kingPiles.kingPile8Count == 0){
                    if(card.getValue() != 13){
                        System.out.println("A king must be placed first on the King piles");
                        return 1;
                    }

                    //Get the position of the card in the users hand
                    i = hands.cardPosition(hands.userHand, hands.userCardNum, card);

                    //Put the card in the pile, increment pile card count
                    kingPiles.kingPile8[kingPiles.kingPile8Count] = hands.userHand[i];
                    kingPiles.kingPile8Count++;

                    //Remove card from user hand, decrement user card count
                    hands.userHand[i] = new Card();

                    //Ensures the blank card is at the end of the real cards
                    hands.sortHand(hands.userHand, hands.userCardNum);
                    hands.userCardNum--;

                    //Check if user won the round
                    if (hands.userCardNum == 0){
                        return -1;
                    }

                    return 1;
                }
                //If the card is not a valid placement, (The card is not one less in value, or it is the same color as the top of the pile)
                //Don't place
                else if ( (card.getValue() != (kingPiles.kingPile8[kingPiles.kingPile8Count - 1].getValue() - 1))  ||
                        (card.getColor() == (kingPiles.kingPile8[kingPiles.kingPile8Count - 1].getColor()))){

                    System.out.println();
                    System.out.println("Card must be exactly one rank less than the top card, and of opposite color");
                    return 1;

                }
                //The card is a valid placement, lay it down,
                //Increment the pile count, decrement user hand count, sort
                else {

                    //Get the position of the card in the users hand
                    i = hands.cardPosition(hands.userHand, hands.userCardNum, card);

                    //Put the card in the pile, increment pile card count
                    kingPiles.kingPile8[kingPiles.kingPile8Count] = hands.userHand[i];
                    kingPiles.kingPile8Count++;

                    //Remove card from user hand, decrement user card count
                    hands.userHand[i] = new Card();

                    //Ensures the blank card is at the end of the real cards
                    hands.sortHand(hands.userHand, hands.userCardNum);
                    hands.userCardNum--;

                    //Check if user won the round
                    if (hands.userCardNum == 0){
                        return -1;
                    }

                    return 1;
                }
            default:
                //Nothing occurred
                System.out.println("Invalid Input");
                return 1;
        }
    }
    //End of userLayDown

    //Helper of userMove, moves a pile if some previous conditions are met
    private int[] userMover(Card[] pile1, Card[] pile2, int pile1Count, int pile2Count){
        int[] returnValue = new int [2];
        returnValue[0] = pile1Count;
        returnValue[1] = pile2Count;

        //Check if the moveTo pile is empty
        if(pile2Count == 0){
            int i = 0;

            //Move piles
            while(i < pile1Count) {
                pile2[i] = pile1[i];
                i++;
            }

            //Set new pile count
            pile2Count = pile1Count;

            //Zero out the original pile
            i = 0;
            while(i < pile1Count){
                pile1[i] = new Card();
                i++;
            }

            //Set old pile count to 0
            pile1Count = 0;

        }
        //else the moveTo pile is not empty
        else{
            //Check if moveFrom bottom card is 1 less than moveTo top card, and of opposite color
            if(((pile1[0].getValue() + 1) == (pile2[pile2Count - 1].getValue())) &&
                    ((pile1[0].getColor()) != (pile2[pile2Count - 1].getColor()))){
                //System.out.println("You can move");

                int i = 0;
                //Move piles
                while(i < pile1Count) {
                    pile2[i + pile2Count] = pile1[i];
                    i++;
                }

                //Set new pile count
                pile2Count = pile2Count + pile1Count;

                //Zero out the original pile
                i = 0;
                while(i < pile1Count){
                    pile1[i] = new Card();
                    i++;
                }

                //Set old pile count to 0
               pile1Count = 0;

            }
            else{
                System.out.println("The bottom card of the pile you are moving must be 1 less in value");
                System.out.println("and of opposite color of the bottom card of the pile you want to place onto");

                //System.out.println( (piles.pile1[0].getValue() + 1) + " " + (piles.pile2[piles.pile2Count - 1].getValue())   );
                //System.out.println( (piles.pile1[0].getColor()) + " " + (piles.pile2[piles.pile2Count - 1].getColor()) );

            }
        }

        returnValue[0] = pile1Count;
        returnValue[1] = pile2Count;
        return returnValue;
    }
    //End of userMover

    //Moves a pile of cards onto another
    private int userMove(String[] finalInput){
        //Check if 3 strings were given
        if (finalInput.length != 3) {
            System.out.println();
            System.out.println("Input must be given in the form M <Pile1> <Pile2>");
            System.out.println("Where piles are numbered 1-8,");
            System.out.println("with Pile1 being the pile that is moved, and Pile2 being the pile that is moved to.");
            return 1;
        }
        //If piles are not single characters, invalid
        if ((finalInput[1].length() != 1) || (finalInput[2].length() != 1)) {
            System.out.println();
            System.out.println("Input must be given in the form L <Card> <Pile>");
            System.out.println("Where card is a 2 character string of rank followed by suite");
            System.out.println("And pile is an integer 1-8");
            return 1;
        }

        int pileValid = 1;
        int pileValid2 = 1;
        //Check if pile 1 is valid
        if ((finalInput[1].equals("1")) || (finalInput[1].equals("2")) || (finalInput[1].equals("3")) || (finalInput[1].equals("4"))) {
            //Check if the pile is empty
            switch(finalInput[1]){
                case "1":
                    if(piles.pile1Count == 0){
                        System.out.println("Cannot move from an empty pile");
                        return 1;
                    }
                    break;
                case "2":
                    if(piles.pile2Count == 0){
                        System.out.println("Cannot move from an empty pile");
                        return 1;
                    }
                    break;
                case "3":
                    if(piles.pile3Count == 0){
                        System.out.println("Cannot move from an empty pile");
                        return 1;
                    }
                    break;
                case "4":
                    if(piles.pile4Count == 0){
                        System.out.println("Cannot move from an empty pile");
                        return 1;
                    }
                    break;
            }
            //The pile is valid
            pileValid = 0;
        }
        //Check if pile 2 is valid
        if ((finalInput[2].equals("1")) || (finalInput[2].equals("2")) || (finalInput[2].equals("3")) || (finalInput[2].equals("4")) ||
                (finalInput[2].equals("5")) || (finalInput[2].equals("6")) || (finalInput[2].equals("7")) || (finalInput[2].equals("8"))) {
            //The pile is valid
            pileValid2 = 0;
        }
        //If the user attempts to move a pile onto itself
        if( (finalInput[1].equals(finalInput[2])) ){
            System.out.println("Cannot move a pile onto itself");
            return 1;
        }
        //If the user attempts to move a King pile, invalid
        if( (finalInput[1].equals("5")) || (finalInput[1].equals("6")) || (finalInput[1].equals("7")) || (finalInput[1].equals("8")) ){
            System.out.println("Cannot move a King pile onto another pile");
            return 1;
        }
        //If the pile to be moved to is an empty King pile, and the bottom card of the MoveFrom pile is not a King, invalid
        if((finalInput[2].equals("5") && (kingPiles.kingPile5Count == 0) ) || (finalInput[2].equals("6") && (kingPiles.kingPile6Count == 0) ) ||
                (finalInput[2].equals("7") && (kingPiles.kingPile7Count == 0) ) || (finalInput[2].equals("8") && (kingPiles.kingPile8Count == 0) ) ){

            //Check the 4 possible movement piles
            switch(finalInput[1]){
                case "1":
                    //If not a King, invalid
                    if( piles.pile1[0].getValue() != 13 ){
                        System.out.println("You must first move a King onto an empty King pile");
                        return 1;
                    }
                    break;
                case "2":
                    //If not a King, invalid
                    if( piles.pile2[0].getValue() != 13 ){
                        System.out.println("You must first move a King onto an empty King pile");
                        return 1;
                    }
                    break;
                case "3":
                    //If not a King, invalid
                    if( piles.pile3[0].getValue() != 13 ){
                        System.out.println("You must first move a King onto an empty King pile");
                        return 1;
                    }
                    break;
                case "4":
                    //If not a King, invalid
                    if( piles.pile4[0].getValue() != 13 ){
                        System.out.println("You must first move a King onto an empty King pile");
                        return 1;
                    }
                    break;
                default:
                    //Invalid input
                    return 1;
            }


        }

        //Check if the pile turned out to be valid
        if ((pileValid == 1) || (pileValid2 == 1)) {
            System.out.println();
            System.out.println("Pile number is not valid. Input must be between 1-8");
            return 1;
        }

        //Hold the pile number
        String pileFromString = finalInput[1];
        String pileToString = finalInput[2];

        int[] newValues = new int[2];

        //Attempt to move the pile (Knowing that there is a King being moved if there is an empty King pile)
        //Check if numbers are 1 below and other color
        switch(pileFromString){
            case "1":
                //Move from pile is 1, for all possible move-to piles
                switch(pileToString){
                    case "1":
                        //Should never move to same pile
                        return 1;
                    case "2":

                        newValues =  userMover(piles.pile1, piles.pile2, piles.pile1Count, piles.pile2Count);

                        piles.pile1Count = newValues[0];
                        piles.pile2Count = newValues[1];
                        return 1;
                    case "3":

                        newValues =  userMover(piles.pile1, piles.pile3, piles.pile1Count, piles.pile3Count);

                        piles.pile1Count = newValues[0];
                        piles.pile3Count = newValues[1];

                        return 1;

                    case "4":

                        newValues =  userMover(piles.pile1, piles.pile4, piles.pile1Count, piles.pile4Count);

                        piles.pile1Count = newValues[0];
                        piles.pile4Count = newValues[1];

                        return 1;

                    case "5":

                        newValues =  userMover(piles.pile1, kingPiles.kingPile5, piles.pile1Count, kingPiles.kingPile5Count);

                        piles.pile1Count = newValues[0];
                        kingPiles.kingPile5Count = newValues[1];

                        return 1;

                    case "6":

                        newValues =  userMover(piles.pile1, kingPiles.kingPile6, piles.pile1Count, kingPiles.kingPile6Count);

                        piles.pile1Count = newValues[0];
                        kingPiles.kingPile6Count = newValues[1];

                        return 1;

                    case "7":

                        newValues =  userMover(piles.pile1, kingPiles.kingPile7, piles.pile1Count, kingPiles.kingPile7Count);

                        piles.pile1Count = newValues[0];
                        kingPiles.kingPile7Count = newValues[1];

                        return 1;

                    case "8":
                        newValues =  userMover(piles.pile1, kingPiles.kingPile8, piles.pile1Count, kingPiles.kingPile8Count);

                        piles.pile1Count = newValues[0];
                        kingPiles.kingPile8Count = newValues[1];

                        return 1;

                    default:
                        //Nothing occurred
                        System.out.println("Invalid input");
                        return 1;
                }
            case "2":
                //Move from pile is 1, for all possible move-to piles
                switch(pileToString){
                    case "1":

                        newValues =  userMover(piles.pile2, piles.pile1, piles.pile2Count, piles.pile1Count);

                        piles.pile2Count = newValues[0];
                        piles.pile1Count = newValues[1];
                        return 1;

                    case "2":
                        //Should never move to same pile
                        return 1;

                    case "3":

                        newValues =  userMover(piles.pile2, piles.pile3, piles.pile2Count, piles.pile3Count);

                        piles.pile2Count = newValues[0];
                        piles.pile3Count = newValues[1];
                        return 1;

                    case "4":

                        newValues =  userMover(piles.pile2, piles.pile4, piles.pile2Count, piles.pile4Count);

                        piles.pile2Count = newValues[0];
                        piles.pile4Count = newValues[1];
                        return 1;

                    case "5":

                        newValues =  userMover(piles.pile2, kingPiles.kingPile5, piles.pile2Count, kingPiles.kingPile5Count);

                        piles.pile2Count = newValues[0];
                        kingPiles.kingPile5Count = newValues[1];
                        return 1;

                    case "6":

                        newValues =  userMover(piles.pile2, kingPiles.kingPile6, piles.pile2Count, kingPiles.kingPile6Count);

                        piles.pile2Count = newValues[0];
                        kingPiles.kingPile6Count = newValues[1];
                        return 1;

                    case "7":

                        newValues =  userMover(piles.pile2, kingPiles.kingPile7, piles.pile2Count, kingPiles.kingPile7Count);

                        piles.pile2Count = newValues[0];
                        kingPiles.kingPile7Count = newValues[1];
                        return 1;

                    case "8":
                        newValues =  userMover(piles.pile2, kingPiles.kingPile8, piles.pile2Count, kingPiles.kingPile8Count);

                        piles.pile2Count = newValues[0];
                        kingPiles.kingPile8Count = newValues[1];
                        return 1;

                    default:
                        //Nothing occurred
                        System.out.println("Invalid input");
                        return 1;
                }
            case "3":
                //Move from pile is 1, for all possible move-to piles
                switch(pileToString){
                    case "1":

                        newValues =  userMover(piles.pile3, piles.pile1, piles.pile3Count, piles.pile1Count);

                        piles.pile3Count = newValues[0];
                        piles.pile1Count = newValues[1];
                        return 1;

                    case "2":

                        newValues =  userMover(piles.pile3, piles.pile2, piles.pile3Count, piles.pile2Count);

                        piles.pile3Count = newValues[0];
                        piles.pile2Count = newValues[1];
                        return 1;

                    case "3":

                        //Should never move to same pile
                        return 1;

                    case "4":

                        newValues =  userMover(piles.pile3, piles.pile4, piles.pile3Count, piles.pile4Count);

                        piles.pile3Count = newValues[0];
                        piles.pile4Count = newValues[1];
                        return 1;

                    case "5":

                        newValues =  userMover(piles.pile3, kingPiles.kingPile5, piles.pile3Count, kingPiles.kingPile5Count);

                        piles.pile3Count = newValues[0];
                        kingPiles.kingPile5Count = newValues[1];
                        return 1;

                    case "6":

                        newValues =  userMover(piles.pile3, kingPiles.kingPile6, piles.pile3Count, kingPiles.kingPile6Count);

                        piles.pile3Count = newValues[0];
                        kingPiles.kingPile6Count = newValues[1];
                        return 1;

                    case "7":

                        newValues =  userMover(piles.pile3, kingPiles.kingPile7, piles.pile3Count, kingPiles.kingPile7Count);

                        piles.pile3Count = newValues[0];
                        kingPiles.kingPile7Count = newValues[1];
                        return 1;

                    case "8":
                        newValues =  userMover(piles.pile3, kingPiles.kingPile8, piles.pile3Count, kingPiles.kingPile8Count);

                        piles.pile3Count = newValues[0];
                        kingPiles.kingPile8Count = newValues[1];
                        return 1;

                    default:
                        //Nothing occurred
                        System.out.println("Invalid input");
                        return 1;
                }
            case "4":
                //Move from pile is 1, for all possible move-to piles
                switch(pileToString){
                    case "1":

                        newValues =  userMover(piles.pile4, piles.pile1, piles.pile4Count, piles.pile1Count);

                        piles.pile4Count = newValues[0];
                        piles.pile1Count = newValues[1];
                        return 1;

                    case "2":

                        newValues =  userMover(piles.pile4, piles.pile2, piles.pile4Count, piles.pile2Count);

                        piles.pile4Count = newValues[0];
                        piles.pile2Count = newValues[1];
                        return 1;

                    case "3":

                        newValues =  userMover(piles.pile4, piles.pile2, piles.pile4Count, piles.pile3Count);

                        piles.pile4Count = newValues[0];
                        piles.pile3Count = newValues[1];
                        return 1;

                    case "4":

                        //Should never move to same pile
                        return 1;

                    case "5":

                        newValues =  userMover(piles.pile4, kingPiles.kingPile5, piles.pile4Count, kingPiles.kingPile5Count);

                        piles.pile4Count = newValues[0];
                        kingPiles.kingPile5Count = newValues[1];
                        return 1;

                    case "6":

                        newValues =  userMover(piles.pile4, kingPiles.kingPile6, piles.pile4Count, kingPiles.kingPile6Count);

                        piles.pile4Count = newValues[0];
                        kingPiles.kingPile6Count = newValues[1];
                        return 1;

                    case "7":

                        newValues =  userMover(piles.pile4, kingPiles.kingPile7, piles.pile4Count, kingPiles.kingPile7Count);

                        piles.pile4Count = newValues[0];
                        kingPiles.kingPile7Count = newValues[1];
                        return 1;

                    case "8":
                        newValues =  userMover(piles.pile4, kingPiles.kingPile8, piles.pile4Count, kingPiles.kingPile8Count);

                        piles.pile4Count = newValues[0];
                        kingPiles.kingPile8Count = newValues[1];
                        return 1;

                    default:
                        //Nothing occurred
                        System.out.println("Invalid input");
                        return 1;
                }
            default:
                //Nothing occurred
                System.out.println("Invalid input");
                return 1;
        }
    }
    //End of userMove

    /* User input loop
    ** Processes the input of the user and, using helper functions,
    ** makes the appropriate actions occur
    */
    private int userInput(){

        int i;

        //Read from user input, split strings by whitespace
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        String[] finalInput = input.split("\\s+");

        //Check if Null input
        if (finalInput.length == 0) {
            System.out.println("No input");
            return 1;
        }

        //INPUT CASES START HERE//

        //Convert strings to lowercase
        for (i = 0; i < finalInput.length; i++) {
            finalInput[i] = finalInput[i].toLowerCase();
        }

        i = 0;

        //The user wants to quit the program
        if (finalInput[0].equals("q")) {
            return userQuit(finalInput);
        }
        //The user wants a help message displayed
        else if (finalInput[0].equals("h")) {
            return userHelp(finalInput);
        }
        //The user wants an about message displayed
        else if (finalInput[0].equals("a")) {
            return userAbout(finalInput);
        }
        //The user wants to draw a card
        else if (finalInput[0].equals("d")) {
            return userDraw(finalInput);
        }
        // The user wants to lay down a card on a Pile
        else if(finalInput[0].equals("l")){
            return userLayDown(finalInput);
        }
        // The user wants to move a pile onto a pile
        else if(finalInput[0].equals("m")) {
            return userMove(finalInput);
        }
        //Unknown input
        else {
            System.out.println("Invalid input");
            //Invalid
            return 1;
        }
    }
    //End of userInput















    //Puts a king from the aI hand onto an empty pile, helper of aIKings
    private int[] aIKingPutter(Card[] hand, Card[] pile, int handCount, int pileCount){
        int[] newValue = new int[2];

        int i = 0;
        //Copy the card
        Card card = new Card('k',hand[i].getSuite());

        //Get the position of the card in the AI hand
        i = hands.cardPosition(hand, handCount, card);

        //Put the card in the pile, increment pile card count
        pile[pileCount] = hand[i];
        pileCount++;

        //Remove card from user hand, decrement user card count
        hand[i] = new Card();

        //Ensures the blank card is at the end of the real cards
        hands.sortHand(hand, handCount);
        handCount--;

        //Check if user won the round
        if (handCount == 0){
            newValue[0] = -1;
            newValue[1] = -1;
            return newValue;
        }

        newValue[0] = handCount;
        newValue[1] = pileCount;

        return newValue;
    }
    //End of aIKingPutter

    //Puts a king from the hand of the AI onto an empty pile
    private int aIKings(){
        int i = 0;

        int[] newValue = new int[2];

        //Check if AI has a king
        while(i < hands.aICardNum){
            //If the card is a king
            if(hands.aIHand[i].getValue() == 13){
                //Check which king pile is empty
                //KingPile5
                if(kingPiles.kingPile5Count == 0){
                    newValue =  aIKingPutter(hands.aIHand, kingPiles.kingPile5, hands.aICardNum, kingPiles.kingPile5Count);

                    hands.aICardNum = newValue[0];
                    kingPiles.kingPile5Count = newValue[1];

                    if(newValue[0] == -1){
                        return -1;
                    }

                    return 0;
                }
                //KingPile6
                if(kingPiles.kingPile6Count == 0){
                    newValue =  aIKingPutter(hands.aIHand, kingPiles.kingPile6, hands.aICardNum, kingPiles.kingPile6Count);

                    hands.aICardNum = newValue[0];
                    kingPiles.kingPile6Count = newValue[1];

                    if(newValue[0] == -1){
                        return -1;
                    }

                    return 0;
                }
                //KingPile7
                if(kingPiles.kingPile7Count == 0){
                    newValue =  aIKingPutter(hands.aIHand, kingPiles.kingPile7, hands.aICardNum, kingPiles.kingPile7Count);

                    hands.aICardNum = newValue[0];
                    kingPiles.kingPile7Count = newValue[1];

                    if(newValue[0] == -1){
                        return -1;
                    }

                    return 0;
                }
                //KingPile8
                if(kingPiles.kingPile8Count == 0){
                    newValue =  aIKingPutter(hands.aIHand, kingPiles.kingPile8, hands.aICardNum, kingPiles.kingPile8Count);

                    hands.aICardNum = newValue[0];
                    kingPiles.kingPile8Count = newValue[1];

                    if(newValue[0] == -1){
                        return -1;
                    }

                    return 0;
                }

            }
            i++;
        }
        return 1;
    }
    //End of aIKings

    //Moves a pile with a king at the bottom to a blank king pile
    private int aIKingPileMove(){
        int i = 0;

        int[] newValue = new int[2];

        //Check if there is a king at the bottom of the pile
        //Pile 1
        if( (piles.pile1Count != 0)  && (piles.pile1[0].getValue() == 13)){
            //There is a king at the bottom of pile 1

            //Check which king pile is empty, move
            if(kingPiles.kingPile5Count == 0){
                newValue =  userMover(piles.pile1, kingPiles.kingPile5, piles.pile1Count, kingPiles.kingPile5Count);

                piles.pile1Count = newValue[0];
                kingPiles.kingPile5Count = newValue[1];

                return 0;
            }
            else if (kingPiles.kingPile6Count == 0){
                newValue =  userMover(piles.pile1, kingPiles.kingPile6, piles.pile1Count, kingPiles.kingPile6Count);

                piles.pile1Count = newValue[0];
                kingPiles.kingPile6Count = newValue[1];

                return 0;

            }
            else if (kingPiles.kingPile7Count == 0){
                newValue =  userMover(piles.pile1, kingPiles.kingPile7, piles.pile1Count, kingPiles.kingPile7Count);

                piles.pile1Count = newValue[0];
                kingPiles.kingPile7Count = newValue[1];

                return 0;

            }
            else if (kingPiles.kingPile8Count == 0){
                newValue =  userMover(piles.pile1, kingPiles.kingPile8, piles.pile1Count, kingPiles.kingPile8Count);

                piles.pile1Count = newValue[0];
                kingPiles.kingPile8Count = newValue[1];

                return 0;

            }
        }
        //Check pile 2
        else if( (piles.pile2Count != 0)  && (piles.pile2[0].getValue() == 13)){

            //Check which king pile is empty, move
            if(kingPiles.kingPile5Count == 0){
                newValue =  userMover(piles.pile2, kingPiles.kingPile5, piles.pile2Count, kingPiles.kingPile5Count);

                piles.pile2Count = newValue[0];
                kingPiles.kingPile5Count = newValue[1];

                return 0;

            }
            else if (kingPiles.kingPile6Count == 0){
                newValue =  userMover(piles.pile2, kingPiles.kingPile6, piles.pile2Count, kingPiles.kingPile6Count);

                piles.pile2Count = newValue[0];
                kingPiles.kingPile6Count = newValue[1];

                return 0;

            }
            else if (kingPiles.kingPile7Count == 0){
                newValue =  userMover(piles.pile2, kingPiles.kingPile7, piles.pile2Count, kingPiles.kingPile7Count);

                piles.pile2Count = newValue[0];
                kingPiles.kingPile7Count = newValue[1];

                return 0;

            }
            else if (kingPiles.kingPile8Count == 0){
                newValue =  userMover(piles.pile2, kingPiles.kingPile8, piles.pile2Count, kingPiles.kingPile8Count);

                piles.pile2Count = newValue[0];
                kingPiles.kingPile8Count = newValue[1];

                return 0;

            }
        }
        //Check pile 3
        if( (piles.pile3Count != 0)  && (piles.pile3[0].getValue() == 13)){
            //There is a king at the bottom of pile 1

            //Check which king pile is empty, move
            if(kingPiles.kingPile5Count == 0){
                newValue =  userMover(piles.pile3, kingPiles.kingPile5, piles.pile3Count, kingPiles.kingPile5Count);

                piles.pile3Count = newValue[0];
                kingPiles.kingPile5Count = newValue[1];

                return 0;

            }
            else if (kingPiles.kingPile6Count == 0){
                newValue =  userMover(piles.pile3, kingPiles.kingPile6, piles.pile3Count, kingPiles.kingPile6Count);

                piles.pile3Count = newValue[0];
                kingPiles.kingPile6Count = newValue[1];

                return 0;

            }
            else if (kingPiles.kingPile7Count == 0){
                newValue =  userMover(piles.pile3, kingPiles.kingPile7, piles.pile3Count, kingPiles.kingPile7Count);

                piles.pile3Count = newValue[0];
                kingPiles.kingPile7Count = newValue[1];

                return 0;

            }
            else if (kingPiles.kingPile8Count == 0){
                newValue =  userMover(piles.pile3, kingPiles.kingPile8, piles.pile3Count, kingPiles.kingPile8Count);

                piles.pile3Count = newValue[0];
                kingPiles.kingPile8Count = newValue[1];

                return 0;

            }
        }
        //Check pile 4
        if( (piles.pile4Count != 0)  && (piles.pile4[0].getValue() == 13)){
            //Check which king pile is empty, move
            if(kingPiles.kingPile5Count == 0){
                newValue =  userMover(piles.pile4, kingPiles.kingPile5, piles.pile4Count, kingPiles.kingPile5Count);

                piles.pile4Count = newValue[0];
                kingPiles.kingPile5Count = newValue[1];

                return 0;

            }
            else if (kingPiles.kingPile6Count == 0){
                newValue =  userMover(piles.pile4, kingPiles.kingPile6, piles.pile4Count, kingPiles.kingPile6Count);

                piles.pile4Count = newValue[0];
                kingPiles.kingPile6Count = newValue[1];

                return 0;
            }
            else if (kingPiles.kingPile7Count == 0){
                newValue =  userMover(piles.pile4, kingPiles.kingPile7, piles.pile4Count, kingPiles.kingPile7Count);

                piles.pile4Count = newValue[0];
                kingPiles.kingPile7Count = newValue[1];

                return 0;

            }
            else if (kingPiles.kingPile8Count == 0){
                newValue =  userMover(piles.pile4, kingPiles.kingPile8, piles.pile4Count, kingPiles.kingPile8Count);

                piles.pile4Count = newValue[0];
                kingPiles.kingPile8Count = newValue[1];

                return 0;

            }
        }

        //No move was made
        return 1;
    }
    //End of aIKingPileMove

    //Moves a pile, helper of aIMove
    private int[] aIMover(Card[] pile1, Card[] pile2, int pile1Count, int pile2Count){
        int[] returnValue = new int [2];
        returnValue[0] = pile1Count;
        returnValue[1] = pile2Count;

        //Check if the moveTo pile is empty
        if(pile2Count == 0){
            int i = 0;

            //Move piles
            while(i < pile1Count) {
                pile2[i] = pile1[i];
                i++;
            }

            //Set new pile count
            pile2Count = pile1Count;

            //Zero out the original pile
            i = 0;
            while(i < pile1Count){
                pile1[i] = new Card();
                i++;
            }

            //Set old pile count to 0
            pile1Count = 0;

        }
        //else the moveTo pile is not empty
        else{
            //Check if moveFrom bottom card is 1 less than moveTo top card, and of opposite color
            if(((pile1[0].getValue() + 1) == (pile2[pile2Count - 1].getValue())) &&
                    ((pile1[0].getColor()) != (pile2[pile2Count - 1].getColor()))){
                //System.out.println("You can move");

                int i = 0;
                //Move piles
                while(i < pile1Count) {
                    pile2[i + pile2Count] = pile1[i];
                    i++;
                }

                //Set new pile count
                pile2Count = pile2Count + pile1Count;

                //Zero out the original pile
                i = 0;
                while(i < pile1Count){
                    pile1[i] = new Card();
                    i++;
                }

                //Set old pile count to 0
                pile1Count = 0;

            }
            else{
                returnValue[0] = pile1Count;
                returnValue[1] = pile2Count;
            }
        }

        returnValue[0] = pile1Count;
        returnValue[1] = pile2Count;
        return returnValue;
    }
    //End of aIMover

    //Moves a pile onto another if conditions are met
    private int aIMove(){
        int i = 0;

        int[] newValues = new int[2];

        //Check if a move has actually been made
        int tempPileCount = 0;

        //Move from, move to
        //Check if there is a pile you can move from
        if(piles.pile1Count != 0){
            //Check if there is a nonempty pile to move to
            if(piles.pile2Count != 0){
                tempPileCount = piles.pile2Count;

                newValues =  aIMover(piles.pile1, piles.pile2, piles.pile1Count, piles.pile2Count);

                piles.pile1Count = newValues[0];
                piles.pile2Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(piles.pile3Count != 0){
                tempPileCount = piles.pile3Count;

                newValues =  aIMover(piles.pile1, piles.pile3, piles.pile1Count, piles.pile3Count);

                piles.pile1Count = newValues[0];
                piles.pile3Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(piles.pile4Count != 0){
                tempPileCount = piles.pile4Count;

                newValues =  aIMover(piles.pile1, piles.pile4, piles.pile1Count, piles.pile4Count);

                piles.pile1Count = newValues[0];
                piles.pile4Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(kingPiles.kingPile5Count != 0){
                tempPileCount = kingPiles.kingPile5Count;

                newValues =  aIMover(piles.pile1, kingPiles.kingPile5, piles.pile1Count, kingPiles.kingPile5Count);

                piles.pile1Count = newValues[0];
                kingPiles.kingPile5Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(kingPiles.kingPile6Count != 0){
                tempPileCount = kingPiles.kingPile6Count;

                newValues =  aIMover(piles.pile1, kingPiles.kingPile6, piles.pile1Count, kingPiles.kingPile6Count);

                piles.pile1Count = newValues[0];
                kingPiles.kingPile6Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(kingPiles.kingPile7Count != 0){
                tempPileCount = kingPiles.kingPile7Count;

                newValues =  aIMover(piles.pile1, kingPiles.kingPile7, piles.pile1Count, kingPiles.kingPile7Count);

                piles.pile1Count = newValues[0];
                kingPiles.kingPile7Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(kingPiles.kingPile8Count != 0){
                tempPileCount = kingPiles.kingPile8Count;

                newValues =  aIMover(piles.pile1, kingPiles.kingPile8, piles.pile1Count, kingPiles.kingPile8Count);

                piles.pile1Count = newValues[0];
                kingPiles.kingPile8Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }

        }
        if(piles.pile2Count != 0){
            if(piles.pile1Count != 0){
                tempPileCount = piles.pile1Count;

                newValues =  aIMover(piles.pile2, piles.pile1, piles.pile2Count, piles.pile1Count);

                piles.pile2Count = newValues[0];
                piles.pile1Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(piles.pile3Count != 0){
                tempPileCount = piles.pile3Count;

                newValues =  aIMover(piles.pile2, piles.pile3, piles.pile2Count, piles.pile3Count);

                piles.pile2Count = newValues[0];
                piles.pile3Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(piles.pile4Count != 0){
                tempPileCount = piles.pile4Count;

                newValues =  aIMover(piles.pile2, piles.pile4, piles.pile2Count, piles.pile4Count);

                piles.pile2Count = newValues[0];
                piles.pile4Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(kingPiles.kingPile5Count != 0){
                tempPileCount = kingPiles.kingPile5Count;

                newValues =  aIMover(piles.pile2, kingPiles.kingPile5, piles.pile2Count, kingPiles.kingPile5Count);

                piles.pile2Count = newValues[0];
                kingPiles.kingPile5Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(kingPiles.kingPile6Count != 0){
                tempPileCount = kingPiles.kingPile6Count;

                newValues =  aIMover(piles.pile2, kingPiles.kingPile6, piles.pile2Count, kingPiles.kingPile6Count);

                piles.pile2Count = newValues[0];
                kingPiles.kingPile6Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(kingPiles.kingPile7Count != 0){
                tempPileCount = kingPiles.kingPile7Count;

                newValues =  aIMover(piles.pile2, kingPiles.kingPile7, piles.pile2Count, kingPiles.kingPile7Count);

                piles.pile2Count = newValues[0];
                kingPiles.kingPile7Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(kingPiles.kingPile8Count != 0){
                tempPileCount = kingPiles.kingPile8Count;

                newValues =  aIMover(piles.pile2, kingPiles.kingPile8, piles.pile2Count, kingPiles.kingPile8Count);

                piles.pile2Count = newValues[0];
                kingPiles.kingPile8Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
        }
        if(piles.pile3Count != 0){
            if(piles.pile1Count != 0){
                tempPileCount = piles.pile1Count;

                newValues =  aIMover(piles.pile3, piles.pile1, piles.pile3Count, piles.pile1Count);

                piles.pile3Count = newValues[0];
                piles.pile1Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(piles.pile2Count != 0){
                tempPileCount = piles.pile2Count;

                newValues =  aIMover(piles.pile3, piles.pile2, piles.pile3Count, piles.pile2Count);

                piles.pile3Count = newValues[0];
                piles.pile2Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(piles.pile4Count != 0){
                tempPileCount = piles.pile4Count;

                newValues =  aIMover(piles.pile3, piles.pile4, piles.pile3Count, piles.pile4Count);

                piles.pile3Count = newValues[0];
                piles.pile4Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(kingPiles.kingPile5Count != 0){
                tempPileCount = kingPiles.kingPile5Count;

                newValues =  aIMover(piles.pile3, kingPiles.kingPile5, piles.pile3Count, kingPiles.kingPile5Count);

                piles.pile3Count = newValues[0];
                kingPiles.kingPile5Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(kingPiles.kingPile6Count != 0){
                tempPileCount = kingPiles.kingPile6Count;

                newValues =  aIMover(piles.pile3, kingPiles.kingPile6, piles.pile3Count, kingPiles.kingPile6Count);

                piles.pile3Count = newValues[0];
                kingPiles.kingPile6Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(kingPiles.kingPile7Count != 0){
                tempPileCount = kingPiles.kingPile7Count;

                newValues =  aIMover(piles.pile3, kingPiles.kingPile7, piles.pile3Count, kingPiles.kingPile7Count);

                piles.pile3Count = newValues[0];
                kingPiles.kingPile7Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(kingPiles.kingPile8Count != 0){
                tempPileCount = kingPiles.kingPile8Count;

                newValues =  aIMover(piles.pile3, kingPiles.kingPile8, piles.pile3Count, kingPiles.kingPile8Count);

                piles.pile3Count = newValues[0];
                kingPiles.kingPile8Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }

        }
        if(piles.pile4Count != 0){
            if(piles.pile1Count != 0){
                tempPileCount = piles.pile1Count;

                newValues =  aIMover(piles.pile4, piles.pile1, piles.pile4Count, piles.pile1Count);

                piles.pile4Count = newValues[0];
                piles.pile1Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(piles.pile2Count != 0){
                tempPileCount = piles.pile2Count;

                newValues =  aIMover(piles.pile4, piles.pile2, piles.pile4Count, piles.pile2Count);

                piles.pile4Count = newValues[0];
                piles.pile2Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(piles.pile3Count != 0){
                tempPileCount = piles.pile3Count;

                newValues =  aIMover(piles.pile4, piles.pile3, piles.pile4Count, piles.pile3Count);

                piles.pile4Count = newValues[0];
                piles.pile3Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(kingPiles.kingPile5Count != 0){
                tempPileCount = kingPiles.kingPile5Count;

                newValues =  aIMover(piles.pile4, kingPiles.kingPile5, piles.pile4Count, kingPiles.kingPile5Count);

                piles.pile4Count = newValues[0];
                kingPiles.kingPile5Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(kingPiles.kingPile6Count != 0){
                tempPileCount = kingPiles.kingPile6Count;

                newValues =  aIMover(piles.pile4, kingPiles.kingPile6, piles.pile4Count, kingPiles.kingPile6Count);

                piles.pile4Count = newValues[0];
                kingPiles.kingPile6Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(kingPiles.kingPile7Count != 0){
                tempPileCount = kingPiles.kingPile7Count;

                newValues =  aIMover(piles.pile4, kingPiles.kingPile7, piles.pile4Count, kingPiles.kingPile7Count);

                piles.pile4Count = newValues[0];
                kingPiles.kingPile7Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }
            if(kingPiles.kingPile8Count != 0){
                tempPileCount = kingPiles.kingPile8Count;

                newValues =  aIMover(piles.pile4, kingPiles.kingPile8, piles.pile4Count, kingPiles.kingPile8Count);

                piles.pile4Count = newValues[0];
                kingPiles.kingPile8Count = newValues[1];

                if(newValues[1] > tempPileCount){
                    // move
                    return 0;
                }
            }

        }

        return 1;
    }
    //End of aIMove

    //Lays a card down onto a non-empty pile, after checking for rank and suite
    private int aILayNonEmpty(){
        int i = 0;

        //Int to place in checks with no return
        int doNothing = 1;
        //Loop through all cards in AI's hand
        while(i < hands.aICardNum){
            //Pile1
            //If the pile is full, don't lay down
            if(piles.pile1Count == 13){
                doNothing = 0;
            }
            //If the pile is empty, do nothing
            else if( piles.pile1Count == 0){
                doNothing = 0;
            }
            //If the card is not a valid placement, (The card is not one less in value, or it is the same color as the top of the pile)
            //Don't place
            else if ( (hands.aIHand[i].getValue() != (piles.pile1[piles.pile1Count - 1].getValue() - 1))  ||
                    (hands.aIHand[i].getColor() == (piles.pile1[piles.pile1Count - 1].getColor()))){

                doNothing = 0;
            }
            //The card is a valid placement, lay it down,
            //Increment the pile count, decrement user hand count, sort
            else {

                //Get the position of the card in the users hand
                i = hands.cardPosition(hands.aIHand, hands.aICardNum, hands.aIHand[i]);

                //Put the card in the pile, increment pile card count
                piles.pile1[piles.pile1Count] = hands.aIHand[i];
                piles.pile1Count++;

                //Remove card from user hand, decrement user card count
                hands.aIHand[i] = new Card();

                //Ensures the blank card is at the end of the real cards
                hands.sortHand(hands.aIHand, hands.aICardNum);
                hands.aICardNum--;

                //Check if user won the round
                if (hands.aICardNum == 0){
                    return -1;
                }
                //Success
                return 0;
            }

            //Pile2
            //If the pile is full, don't lay down
            if(piles.pile2Count == 13){
                doNothing = 0;
            }
            //If the pile is empty, do nothing
            else if( piles.pile2Count == 0){
                doNothing = 0;
            }
            //If the card is not a valid placement, (The card is not one less in value, or it is the same color as the top of the pile)
            //Don't place
            else if ( (hands.aIHand[i].getValue() != (piles.pile2[piles.pile2Count - 1].getValue() - 1))  ||
                    (hands.aIHand[i].getColor() == (piles.pile2[piles.pile2Count - 1].getColor()))){

                doNothing = 0;
            }
            //The card is a valid placement, lay it down,
            //Increment the pile count, decrement user hand count, sort
            else {

                //Get the position of the card in the users hand
                i = hands.cardPosition(hands.aIHand, hands.aICardNum, hands.aIHand[i]);

                //Put the card in the pile, increment pile card count
                piles.pile2[piles.pile2Count] = hands.aIHand[i];
                piles.pile2Count++;

                //Remove card from user hand, decrement user card count
                hands.aIHand[i] = new Card();

                //Ensures the blank card is at the end of the real cards
                hands.sortHand(hands.aIHand, hands.aICardNum);
                hands.aICardNum--;

                //Check if user won the round
                if (hands.aICardNum == 0){
                    return -1;
                }
                //Success
                return 0;
            }

            //Pile3
            //If the pile is full, don't lay down
            if(piles.pile3Count == 13){
                doNothing = 0;
            }
            //If the pile is empty, do nothing
            else if( piles.pile3Count == 0){
                doNothing = 0;
            }
            //If the card is not a valid placement, (The card is not one less in value, or it is the same color as the top of the pile)
            //Don't place
            else if ( (hands.aIHand[i].getValue() != (piles.pile3[piles.pile3Count - 1].getValue() - 1))  ||
                    (hands.aIHand[i].getColor() == (piles.pile3[piles.pile3Count - 1].getColor()))){

                doNothing = 0;
            }
            //The card is a valid placement, lay it down,
            //Increment the pile count, decrement user hand count, sort
            else {

                //Get the position of the card in the users hand
                i = hands.cardPosition(hands.aIHand, hands.aICardNum, hands.aIHand[i]);

                //Put the card in the pile, increment pile card count
                piles.pile3[piles.pile3Count] = hands.aIHand[i];
                piles.pile3Count++;

                //Remove card from user hand, decrement user card count
                hands.aIHand[i] = new Card();

                //Ensures the blank card is at the end of the real cards
                hands.sortHand(hands.aIHand, hands.aICardNum);
                hands.aICardNum--;

                //Check if user won the round
                if (hands.aICardNum == 0){
                    return -1;
                }
                //Success
                return 0;
            }

            //Pile4
            //If the pile is full, don't lay down
            if(piles.pile4Count == 13){
                doNothing = 0;
            }
            //If the pile is empty, do nothing
            else if( piles.pile4Count == 0){
                doNothing = 0;
            }
            //If the card is not a valid placement, (The card is not one less in value, or it is the same color as the top of the pile)
            //Don't place
            else if ( (hands.aIHand[i].getValue() != (piles.pile4[piles.pile4Count - 1].getValue() - 1))  ||
                    (hands.aIHand[i].getColor() == (piles.pile4[piles.pile4Count - 1].getColor()))){

                doNothing = 0;
            }
            //The card is a valid placement, lay it down,
            //Increment the pile count, decrement user hand count, sort
            else {

                //Get the position of the card in the users hand
                i = hands.cardPosition(hands.aIHand, hands.aICardNum, hands.aIHand[i]);

                //Put the card in the pile, increment pile card count
                piles.pile4[piles.pile4Count] = hands.aIHand[i];
                piles.pile4Count++;

                //Remove card from user hand, decrement user card count
                hands.aIHand[i] = new Card();

                //Ensures the blank card is at the end of the real cards
                hands.sortHand(hands.aIHand, hands.aICardNum);
                hands.aICardNum--;

                //Check if user won the round
                if (hands.aICardNum == 0){
                    return -1;
                }
                //Success
                return 0;
            }

            //KingPile5
            //If the pile is full, don't lay down
            if(kingPiles.kingPile5Count == 13){
                doNothing = 0;
            }
            //If the pile is empty, do nothing
            else if( kingPiles.kingPile5Count == 0){
                doNothing = 0;
            }
            //If the card is not a valid placement, (The card is not one less in value, or it is the same color as the top of the pile)
            //Don't place
            else if ( (hands.aIHand[i].getValue() != (kingPiles.kingPile5[kingPiles.kingPile5Count - 1].getValue() - 1))  ||
                    (hands.aIHand[i].getColor() == (kingPiles.kingPile5[kingPiles.kingPile5Count - 1].getColor()))){

                doNothing = 0;
            }
            //The card is a valid placement, lay it down,
            //Increment the pile count, decrement user hand count, sort
            else {

                //Get the position of the card in the users hand
                i = hands.cardPosition(hands.aIHand, hands.aICardNum, hands.aIHand[i]);

                //Put the card in the pile, increment pile card count
                kingPiles.kingPile5[kingPiles.kingPile5Count] = hands.aIHand[i];
                kingPiles.kingPile5Count++;

                //Remove card from user hand, decrement user card count
                hands.aIHand[i] = new Card();

                //Ensures the blank card is at the end of the real cards
                hands.sortHand(hands.aIHand, hands.aICardNum);
                hands.aICardNum--;

                //Check if user won the round
                if (hands.aICardNum == 0){
                    return -1;
                }
                //Success
                return 0;
            }

            //KingPile6
            //If the pile is full, don't lay down
            if(kingPiles.kingPile6Count == 13){
                doNothing = 0;
            }
            //If the pile is empty, do nothing
            else if( kingPiles.kingPile6Count == 0){
                doNothing = 0;
            }
            //If the card is not a valid placement, (The card is not one less in value, or it is the same color as the top of the pile)
            //Don't place
            else if ( (hands.aIHand[i].getValue() != (kingPiles.kingPile6[kingPiles.kingPile6Count - 1].getValue() - 1))  ||
                    (hands.aIHand[i].getColor() == (kingPiles.kingPile6[kingPiles.kingPile6Count - 1].getColor()))){

                doNothing = 0;
            }
            //The card is a valid placement, lay it down,
            //Increment the pile count, decrement user hand count, sort
            else {

                //Get the position of the card in the users hand
                i = hands.cardPosition(hands.aIHand, hands.aICardNum, hands.aIHand[i]);

                //Put the card in the pile, increment pile card count
                kingPiles.kingPile6[kingPiles.kingPile6Count] = hands.aIHand[i];
                kingPiles.kingPile6Count++;

                //Remove card from user hand, decrement user card count
                hands.aIHand[i] = new Card();

                //Ensures the blank card is at the end of the real cards
                hands.sortHand(hands.aIHand, hands.aICardNum);
                hands.aICardNum--;

                //Check if user won the round
                if (hands.aICardNum == 0){
                    return -1;
                }
                //Success
                return 0;
            }

            //KingPile7
            //If the pile is full, don't lay down
            if(kingPiles.kingPile7Count == 13){
                doNothing = 0;
            }
            //If the pile is empty, do nothing
            else if( kingPiles.kingPile7Count == 0){
                doNothing = 0;
            }
            //If the card is not a valid placement, (The card is not one less in value, or it is the same color as the top of the pile)
            //Don't place
            else if ( (hands.aIHand[i].getValue() != (kingPiles.kingPile7[kingPiles.kingPile7Count - 1].getValue() - 1))  ||
                    (hands.aIHand[i].getColor() == (kingPiles.kingPile7[kingPiles.kingPile7Count - 1].getColor()))){

                doNothing = 0;
            }
            //The card is a valid placement, lay it down,
            //Increment the pile count, decrement user hand count, sort
            else {

                //Get the position of the card in the users hand
                i = hands.cardPosition(hands.aIHand, hands.aICardNum, hands.aIHand[i]);

                //Put the card in the pile, increment pile card count
                kingPiles.kingPile7[kingPiles.kingPile7Count] = hands.aIHand[i];
                kingPiles.kingPile7Count++;

                //Remove card from user hand, decrement user card count
                hands.aIHand[i] = new Card();

                //Ensures the blank card is at the end of the real cards
                hands.sortHand(hands.aIHand, hands.aICardNum);
                hands.aICardNum--;

                //Check if user won the round
                if (hands.aICardNum == 0){
                    return -1;
                }
                //Success
                return 0;
            }

            //KingPile8
            //If the pile is full, don't lay down
            if(kingPiles.kingPile8Count == 13){
                doNothing = 0;
            }
            //If the pile is empty, do nothing
            else if( kingPiles.kingPile8Count == 0){
                doNothing = 0;
            }
            //If the card is not a valid placement, (The card is not one less in value, or it is the same color as the top of the pile)
            //Don't place
            else if ( (hands.aIHand[i].getValue() != (kingPiles.kingPile8[kingPiles.kingPile8Count - 1].getValue() - 1))  ||
                    (hands.aIHand[i].getColor() == (kingPiles.kingPile8[kingPiles.kingPile8Count - 1].getColor()))){

                doNothing = 0;
            }
            //The card is a valid placement, lay it down,
            //Increment the pile count, decrement user hand count, sort
            else {

                //Get the position of the card in the users hand
                i = hands.cardPosition(hands.aIHand, hands.aICardNum, hands.aIHand[i]);

                //Put the card in the pile, increment pile card count
                kingPiles.kingPile8[kingPiles.kingPile8Count] = hands.aIHand[i];
                kingPiles.kingPile8Count++;

                //Remove card from user hand, decrement user card count
                hands.aIHand[i] = new Card();

                //Ensures the blank card is at the end of the real cards
                hands.sortHand(hands.aIHand, hands.aICardNum);
                hands.aICardNum--;

                //Check if user won the round
                if (hands.aICardNum == 0){
                    return -1;
                }
                //Success
                return 0;
            }

            i++;
        }




        //Nothing was done
        return 1;
    }
    //End of aILayNonEmpty

    //Lays a card down onto an empty pile
    private int aILayEmpty(){
        int i = 0;

        //Int to place in checks with no return
        int doNothing = 1;
        //Loop through all cards in AI's hand
        while(i < hands.aICardNum) {
            //Pile1
            //If the pile is empty, lay down
            if (piles.pile1Count == 0) {

                //Get the position of the card in the users hand
                i = hands.cardPosition(hands.aIHand, hands.aICardNum, hands.aIHand[i]);

                //Put the card in the pile, increment pile card count
                piles.pile1[piles.pile1Count] = hands.aIHand[i];
                piles.pile1Count++;

                //Remove card from user hand, decrement user card count
                hands.aIHand[i] = new Card();

                //Ensures the blank card is at the end of the real cards
                hands.sortHand(hands.aIHand, hands.aICardNum);
                hands.aICardNum--;

                //Check if user won the round
                if (hands.aICardNum == 0) {
                    return -1;
                }

                //Success
                return 0;

            }

            //Pile2
            //If the pile is empty, lay down
            if (piles.pile2Count == 0) {

                //Get the position of the card in the users hand
                i = hands.cardPosition(hands.aIHand, hands.aICardNum, hands.aIHand[i]);

                //Put the card in the pile, increment pile card count
                piles.pile2[piles.pile2Count] = hands.aIHand[i];
                piles.pile2Count++;

                //Remove card from user hand, decrement user card count
                hands.aIHand[i] = new Card();

                //Ensures the blank card is at the end of the real cards
                hands.sortHand(hands.aIHand, hands.aICardNum);
                hands.aICardNum--;

                //Check if user won the round
                if (hands.aICardNum == 0) {
                    return -1;
                }

                //Success
                return 0;

            }

            //Pile3
            //If the pile is empty, lay down
            if (piles.pile3Count == 0) {

                //Get the position of the card in the users hand
                i = hands.cardPosition(hands.aIHand, hands.aICardNum, hands.aIHand[i]);

                //Put the card in the pile, increment pile card count
                piles.pile3[piles.pile3Count] = hands.aIHand[i];
                piles.pile3Count++;

                //Remove card from user hand, decrement user card count
                hands.aIHand[i] = new Card();

                //Ensures the blank card is at the end of the real cards
                hands.sortHand(hands.aIHand, hands.aICardNum);
                hands.aICardNum--;

                //Check if user won the round
                if (hands.aICardNum == 0) {
                    return -1;
                }

                //Success
                return 0;

            }

            //Pile4
            //If the pile is empty, lay down
            if (piles.pile4Count == 0) {

                //Get the position of the card in the users hand
                i = hands.cardPosition(hands.aIHand, hands.aICardNum, hands.aIHand[i]);

                //Put the card in the pile, increment pile card count
                piles.pile4[piles.pile4Count] = hands.aIHand[i];
                piles.pile4Count++;

                //Remove card from user hand, decrement user card count
                hands.aIHand[i] = new Card();

                //Ensures the blank card is at the end of the real cards
                hands.sortHand(hands.aIHand, hands.aICardNum);
                hands.aICardNum--;

                //Check if user won the round
                if (hands.aICardNum == 0) {
                    return -1;
                }

                //Success
                return 0;

            }

            //Pile5
            //If the pile is empty, lay down if it is a King
            if(hands.aIHand[i].getValue() == 13) {
                System.out.println("Got here");
                //KingPile5
                if (kingPiles.kingPile5Count == 0) {

                    //Get the position of the card in the users hand
                    i = hands.cardPosition(hands.aIHand, hands.aICardNum, hands.aIHand[i]);

                    //Put the card in the pile, increment pile card count
                    kingPiles.kingPile5[kingPiles.kingPile5Count] = hands.aIHand[i];
                    kingPiles.kingPile5Count++;

                    //Remove card from user hand, decrement user card count
                    hands.aIHand[i] = new Card();

                    //Ensures the blank card is at the end of the real cards
                    hands.sortHand(hands.aIHand, hands.aICardNum);
                    hands.aICardNum--;

                    //Check if user won the round
                    if (hands.aICardNum == 0) {
                        return -1;
                    }

                    //Success
                    return 0;

                }

                //KingPile6
                if (kingPiles.kingPile6Count == 0) {

                    //Get the position of the card in the users hand
                    i = hands.cardPosition(hands.aIHand, hands.aICardNum, hands.aIHand[i]);

                    //Put the card in the pile, increment pile card count
                    kingPiles.kingPile6[kingPiles.kingPile6Count] = hands.aIHand[i];
                    kingPiles.kingPile6Count++;

                    //Remove card from user hand, decrement user card count
                    hands.aIHand[i] = new Card();

                    //Ensures the blank card is at the end of the real cards
                    hands.sortHand(hands.aIHand, hands.aICardNum);
                    hands.aICardNum--;

                    //Check if user won the round
                    if (hands.aICardNum == 0) {
                        return -1;
                    }

                    //Success
                    return 0;

                }

                //KingPile7
                if (kingPiles.kingPile7Count == 0) {

                    //Get the position of the card in the users hand
                    i = hands.cardPosition(hands.aIHand, hands.aICardNum, hands.aIHand[i]);

                    //Put the card in the pile, increment pile card count
                    kingPiles.kingPile7[kingPiles.kingPile7Count] = hands.aIHand[i];
                    kingPiles.kingPile7Count++;

                    //Remove card from user hand, decrement user card count
                    hands.aIHand[i] = new Card();

                    //Ensures the blank card is at the end of the real cards
                    hands.sortHand(hands.aIHand, hands.aICardNum);
                    hands.aICardNum--;

                    //Check if user won the round
                    if (hands.aICardNum == 0) {
                        return -1;
                    }

                    //Success
                    return 0;

                }

                //KingPile8
                if (kingPiles.kingPile8Count == 0) {

                    //Get the position of the card in the users hand
                    i = hands.cardPosition(hands.aIHand, hands.aICardNum, hands.aIHand[i]);

                    //Put the card in the pile, increment pile card count
                    kingPiles.kingPile8[kingPiles.kingPile8Count] = hands.aIHand[i];
                    kingPiles.kingPile8Count++;

                    //Remove card from user hand, decrement user card count
                    hands.aIHand[i] = new Card();

                    //Ensures the blank card is at the end of the real cards
                    hands.sortHand(hands.aIHand, hands.aICardNum);
                    hands.aICardNum--;

                    //Check if user won the round
                    if (hands.aICardNum == 0) {
                        return -1;
                    }

                    //Success
                    return 0;

                }

            }

        i++;
        }
        return 1;
    }
    //End of aILayEmpty

    //Draws a card
    private int aIDraw(){

        //Draw pile is empty
        if (deckIncrementer == 52) {
            System.out.println("The deck is empty. Moving on to next player");
            return 0;
        }

        //Place the top card of the deck at the end of the user hand
        hands.aICardNum++;
        hands.aIHand[hands.aICardNum] = cardDeck[deckIncrementer];

        //The card does not exist in the deck anymore
        cardDeck[deckIncrementer] = new Card();
        deckIncrementer++;

        //A card was drawn
        return 0;

    }
    //End of aIDraw

    //Handles AI input in the proper order of operations
    private int aIInput(){
        int success = 0;

        //Lay down all kings in a computer players hand
        while(success == 0){
            success = aIKings();

            if (success == 0) {
                System.out.println("The computer placed a King from its hand onto a King Pile");
            }

            //The computer won
            if(success == -1){
                return -1;
            }
        }
        success = 0;

        //Move card piles with kings to a king pile
        while(success == 0){
            success = aIKingPileMove();

            if(success == 0) {
                System.out.println("The computer moved a pile with a King to a King Pile");
            }
        }
        success = 0;

        //Move pile onto another
        while(success == 0){
            success = aIMove();

            if (success == 0) {
                System.out.println("The computer moved a pile onto another");
            }

        }
        success = 0;

        //Remembers if the action was successfully performed, since success changes
        int successHolder = 0;
        //Move card onto non-empty pile
        while(successHolder == 0){
            success = aILayNonEmpty();
            successHolder = success;

            //The computer won
            if(success == -1){
                return -1;
            }

            if (success == 0) {
                System.out.println("The computer moved a card onto a non-empty pile");

                //Move pile onto another
                while(success == 0){
                    success = aIMove();

                    if (success == 0) {
                        System.out.println("The computer moved a pile onto another");
                    }

                }

            }


        }
        success = 0;
        successHolder = 0;

        int successHolderLast = 0;
        //Move card onto empty pile
        while(successHolderLast == 0){
            success = aILayEmpty();
            successHolder = success;
            successHolderLast = success;

            //The computer won
            if(success == -1){
                return -1;
            }

            while(successHolder == 0){
                System.out.println("The computer moved a card onto an empty pile");

                success = aILayNonEmpty();
                successHolder = success;

                if (success == 0) {
                    System.out.println("The computer moved a card onto a non-empty pile");

                    //Move pile onto another
                    while(success == 0){
                        success = aIMove();

                        if (success == 0) {
                            System.out.println("The computer moved a pile onto another");
                        }

                    }

                }

            }

        }
        success = 0;
        successHolder = 0;

        //Draw a card
        while(success == 0){
            success = aIDraw();

            if (success == 0 && (deckIncrementer != 52)) {
                System.out.println("The computer drew a card");
            }

            if (success == 0){
                return 0;
            }
        }
        success = 0;

        //Should never occur
        return 1;

    }
    //End of aIInput


    /* Main user interface loop
    ** Checks the dealer, then proceeds with allowing a player to make a turn, until a card is drawn
    ** Once a card is drawn the next player makes a turn
    ** Goes on until a player wins the round, at which point a new round begins (if less than 25 penalty points)
    ** or the user is asked if they want to start a brand new game
    */
    public int gameLoop(){

        //The user input was not processed
        int returnValue = 1;
        int returnValue2 = 1;

        if(dealer == 0){
            System.out.println("The computer is now dealing..");
        }
        else {
            System.out.println("The user is now dealing..");
        }

        //AI is the dealer, loop until round end
        while(dealer == 0) {
            //While the user input was not processed
            while (returnValue == 1 && returnValue2 == 1) {
                while(returnValue == 1) {
                    //Print the interface
                    UIPrint();

                    //If the user draws a card, the returnValue is 0, otherwise 1
                    returnValue = userInput();
                }

                if(returnValue != -1) {
                    //AI loop
                    returnValue2 = aIInput();
                }

                //Check if user won (is out of cards). NO OTHER WIN CASE IS POSSIBLE since players play after draw pile is empty
                //If so, calculate penalty points, check if next round needs to be played.
                if(returnValue == -1 || returnValue2 == -1){
                    //Calculate penalty points

                    hands.userPenalty = hands.penaltyCalculator(hands.userHand,hands.userCardNum, hands.userPenalty);

                    hands.aIPenalty   = hands.penaltyCalculator(hands.aIHand, hands.aICardNum, hands.aIPenalty);


                    //System.out.println("The penalties are: " + hands.userPenalty + " " + hands.aIPenalty);



                    if(hands.userPenalty >= 25){
                        System.out.println("The computer wins! Would you like to play again?");
                        //If so switch dealers (return), new functions etc, else system exit

                        int i;

                        //Read from user input, split strings by whitespace
                        Scanner scan = new Scanner(System.in);
                        String input = scan.nextLine();
                        String[] finalInput = input.split("\\s+");

                        //Check if Null input
                        if (finalInput.length == 0) {
                            System.out.println("No input");
                            return 1;
                        }

                        //Convert strings to lowercase
                        for (i = 0; i < finalInput.length; i++) {
                            finalInput[i] = finalInput[i].toLowerCase();
                        }

                        i = 0;


                        if (finalInput[0].equals("y")) {
                            System.out.println("Starting a brand new game");
                            return 2;
                        }
                        else{
                            System.out.println("GoodBye");
                            System.exit(0);
                        }

                    }
                    else if(hands.aIPenalty >= 25){
                        System.out.println("The user wins! Would you like to play again?");
                        System.out.println("Y/N");

                        //If so, switch dealers (return), new functions etc, else system exit

                        int i;

                        //Read from user input, split strings by whitespace
                        Scanner scan = new Scanner(System.in);
                        String input = scan.nextLine();
                        String[] finalInput = input.split("\\s+");

                        //Check if Null input
                        if (finalInput.length == 0) {
                            System.out.println("No input");
                            return 1;
                        }

                        //Convert strings to lowercase
                        for (i = 0; i < finalInput.length; i++) {
                            finalInput[i] = finalInput[i].toLowerCase();
                        }

                        i = 0;

                        //The user wants to quit the program
                        if (finalInput[0].equals("y")) {
                            System.out.println("Starting a brand new game");
                            return 2;
                        }
                        else{
                            System.out.println("GoodBye");
                            System.exit(0);
                        }

                    }
                    //Next round
                    else{
                        System.out.println("Calculating penalty points: ");
                        System.out.println("User:     " + hands.userPenalty);
                        System.out.println("Computer: " + hands.aIPenalty);
                        System.out.println("Going to the next round!");

                        return 0;

                    }
                }
            }

            //Reset user input loop value
            returnValue = 1;
            returnValue2 = 1;

        }


        //Human is the dealer
        while(dealer == 1) {

            //While the user input was not processed
            while (returnValue == 1 && returnValue2 == 1) {
                while(returnValue2 == 1) {
                    //AI loop
                    returnValue2 = aIInput();

                }

                //Print the interface
                UIPrint();


                if(returnValue2 != -1) {
                    //user Loop
                    returnValue = userInput();
                }

                //Check if user won (is out of cards). NO OTHER WIN CASE IS POSSIBLE since players play after draw pile is empty
                //If so, calculate penalty points, check if next round needs to be played.
                if(returnValue == -1 || returnValue2 == -1){
                    //Calculate penalty points

                    hands.userPenalty = hands.penaltyCalculator(hands.userHand,hands.userCardNum, hands.userPenalty);

                    hands.aIPenalty   = hands.penaltyCalculator(hands.aIHand, hands.aICardNum, hands.aIPenalty);


                    //System.out.println("The penalties are: " + hands.userPenalty + " " + hands.aIPenalty);



                    if(hands.userPenalty >= 25){
                        System.out.println("The computer wins! Would you like to play again?");

                        //If so switch dealers (return), new functions etc, else system exit

                        int i;

                        //Read from user input, split strings by whitespace
                        Scanner scan = new Scanner(System.in);
                        String input = scan.nextLine();
                        String[] finalInput = input.split("\\s+");

                        //Check if Null input
                        if (finalInput.length == 0) {
                            System.out.println("No input");
                            return 1;
                        }

                        //Convert strings to lowercase
                        for (i = 0; i < finalInput.length; i++) {
                            finalInput[i] = finalInput[i].toLowerCase();
                        }

                        i = 0;

                        //The user wants to quit the program
                        if (finalInput[0].equals("y")) {
                            System.out.println("Starting a brand new game");
                            return 0;
                        }
                        else{
                            System.out.println("GoodBye");
                            System.exit(0);
                        }

                    }
                    else if(hands.aIPenalty >= 25){
                        System.out.println("The user wins! Would you like to play again?");
                        System.out.println("Y/N");

                        //If so, switch dealers (return), new functions etc, else system exit
                        int i;

                        //Read from user input, split strings by whitespace
                        Scanner scan = new Scanner(System.in);
                        String input = scan.nextLine();
                        String[] finalInput = input.split("\\s+");

                        //Check if Null input
                        if (finalInput.length == 0) {
                            System.out.println("No input");
                            return 1;
                        }

                        //Convert strings to lowercase
                        for (i = 0; i < finalInput.length; i++) {
                            finalInput[i] = finalInput[i].toLowerCase();
                        }

                        i = 0;

                        //The user wants to quit the program
                        if (finalInput[0].equals("y")) {
                            System.out.println("Starting a brand new game");
                            return 0;
                        }
                        else{
                            System.out.println("GoodBye");
                            System.exit(0);
                        }

                    }
                    //Next round
                    else{
                        System.out.println("Calculating penalty points: ");
                        System.out.println("User:     " + hands.userPenalty);
                        System.out.println("Computer: " + hands.aIPenalty);
                        System.out.println("Going to the next round!");

                        return 0;

                    }
                }
            }

            //Reset input loop
            returnValue = 1;
            returnValue2 = 1;

        }

        return 1;

    }
    //End of gameLoop



    protected static KingPiles        kingPiles;    //Contains the king lay-down piles
    protected static Piles                piles;    //Contains the lay-down piles
    protected static PlayerHands          hands;    //Contains the hands of the players
    protected static Card[]            cardDeck;    //Starts with 52 cards
    protected static int                 dealer;    //0 = AI, 1 = Human
    protected static int        deckIncrementer;    //Used to offset draw pile instead
                                                    //of always moving all elements of the array

}
//End of CardPile Class




/* Main class
** Creates an instance of CardPile, which sets up all of the information for the game
** before any moves are made. Then, a call to gameLoop is made, which is the user input
** loop handling all commands and processing game moves.
 */
public class Main {

    public static void main(String[] args) {
        int i = 2;

        //Loop for a brand new game
        while (i == 2) {
            System.out.println("** Welcome to Kings in the Corner **");
            //Create a new game
            CardPile newGame = new CardPile();

            //Start the game
            int GameChecker = newGame.gameLoop();

            //Loop, changing the dealer based on a round ending
            while (GameChecker == 0) {
                int newDealer = newGame.dealer;
                int newuserPen = newGame.hands.userPenalty;
                int newaIPen = newGame.hands.aIPenalty;

                //Set the new dealer
                if (newDealer == 0) {
                    newDealer = 1;
                } else {
                    newDealer = 0;
                }

                newGame = new CardPile(newDealer, newuserPen, newaIPen);
                GameChecker = newGame.gameLoop();
            }

            i = GameChecker;
        }
    }
}
//End of Main class
