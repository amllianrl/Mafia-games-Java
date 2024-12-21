/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mafiagame;

/**
 *
 * @author amili
 */
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Scanner;
import java.util.InputMismatchException;

public class testMafia {



    public static void main(String[] args) {
        
  
        int players;
        String[] playersname=new String[50];
        String again;
        Scanner input = new Scanner (System.in);
        
        System.out.println("Welcome to Mafia Game");
        do{
            players=0;
        do{
           
        System.out.print("Enter no of players: ");  // prompt user for no of players playing in the game
        try{
              players=input.nextInt();
    
             if(players<5)
                 {System.out.println("Minimum to play is 5 players.");}
             if(players>15)
                 {System.out.println("Maximum to play is 15 players.");}
           }catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                input.next(); }
           }while(players<5||players>15); // the program will ask user to enter again if is not in the range of 5 - 15
        
       clearOutput();
         
        playersname = new String[players];
        AssignPlayers(players,playersname);

        
        System.out.println("confirm player? (y for yes / n for no)");
        again=input.next();
        
        }while("n".equals(again)); // checking for char y/n 
        
        
        char[] playersRole = new char[players];
        AssignRole(players, playersRole);  // call method to assign roles to all player
        
        int[] playerslife=new int[players];
        for(int j=0;j<players;j++){  
        playerslife[j]=1;}    // assign all players life to be 1  [(1) alive / (0) dead ]
        
 
        int[] suspectCount = new int[players]; // array to count votes from all players 
 
Play(players,playersRole,playerslife,playersname,suspectCount); // method that call for round playing

 
        
    }
    public static void AssignPlayers(int players,String[] playersname){ // method to insert all players name
        Scanner input=new Scanner(System.in);
        for(int i=0;i<=players-1;i++){
            System.out.printf("Enter player %d name: ",i+1); // loop to save the name of all players in array playersname[] 
            playersname[i]=input.next();
        }
    }
   public static void AssignRole(int players, char[] playersRole) { // method to generate random index for each roles
    int numMafia = players / 5;
    
    int randMafia1 = (int) (Math.random() * players);
    int randMafia2 = -1; // Initialize to an invalid index for the case when there's only 1 mafia

    if (numMafia == 2) {
        do {
            randMafia2 = (int) (Math.random() * players);
        } while (randMafia1 == randMafia2);  // making sure that the index for mafia 1 and mafia 2
    }

    int randDoctor;
    do {
        randDoctor = (int) (Math.random() * players);
    } while (randMafia1 == randDoctor || randMafia2 == randDoctor); //making sure index is not the same with other players

    int randPolice;
    do {
        randPolice = (int) (Math.random() * players);
    } while (randMafia1 == randPolice || randMafia2 == randPolice || randDoctor == randPolice); //making sure index is not the same with other players

    int randCitizen;
    do {
        randCitizen = (int) (Math.random() * players);
    } while (randMafia1 == randCitizen || randMafia2 == randCitizen || randDoctor == randCitizen || randPolice == randCitizen); //making sure index is not the same with other players

    // Assign roles to the playersRole array (M,D,P,C) based on the random index generated for each roles above
    for (int i = 0; i < playersRole.length; i++) { 
        if (i == randMafia1 || i == randMafia2) {
            playersRole[i] = 'M';   
        } else if (i == randDoctor) {
            playersRole[i] = 'D';
        } else if (i == randPolice) {
            playersRole[i] = 'P';
        } else {
            playersRole[i] = 'C';
        }
    }
}
   
   public static void clearOutput() {
        try {
            Robot rob = new Robot();
            Thread.sleep(1000);
            rob.keyPress(KeyEvent.VK_CONTROL);
            rob.keyPress(KeyEvent.VK_L);
            rob.keyRelease(KeyEvent.VK_L);
            rob.keyRelease(KeyEvent.VK_CONTROL);
            Thread.sleep(1500);
        } catch (InterruptedException | AWTException e) {
            System.out.println("Error!");
        }

                
    }
   public static int MafiaKill(int[] playerslife,String[] playersname){ //method call for player role is Mafia
    Scanner input=new Scanner(System.in);
    System.out.println("You are the Mafia ");
    
    for(int i = 0 ; i < playersname.length;i++)
    { 
        if(playerslife[i]!=0)
    {System.out.printf("Player %d : %s\n",i+1, playersname[i]);}
    }
    
    System.out.println("Choose who to kill tonight:");
    
    int mafiaChoice = input.nextInt() - 1; //index for players that will be kill by Mafia

   return mafiaChoice; // return int mafiaChoice to method Play()
}
public static void CitizenChooseSuspect(String[] playersname, int players, char[] playersRole, int[] playerslife,int[] suspectCount) { //Method for player role is Citizen
        
        
        Scanner input = new Scanner(System.in);
      
        for (int i = 0; i < playersname.length; i++) {
            if(playerslife[i]!=0)
            { System.out.printf("Player %d : %s\n", i + 1, playersname[i]);}  //displaying name of players that is still alive
        }

        System.out.print("Who do you suspect to be Mafia? ");
        int choice = input.nextInt() - 1; // index that citizen choose to be the suspect 

        // Increment the suspect count for the chosen player
        suspectCount[choice]++;

        // Display the current suspect counts
        System.out.println("Current Suspect Counts:");
        for (int i = 0; i < players; i++) 
        {
            if(playerslife[i]!=0)
            {  System.out.printf("%s: %d\n", playersname[i], suspectCount[i]);}
        }

        // Find the most suspected player
        int mostSuspectedIndex = 0;
        for (int i = 1; i < players; i++) {
            if (suspectCount[i] > suspectCount[mostSuspectedIndex]) {
                mostSuspectedIndex = i;
            }
        }

        System.out.printf("Player %s is the most suspected.\n", playersname[mostSuspectedIndex]); // display the most suspected players
       
    }

public static int doctorHeal(String[] playersname, int[] playerslife){
        Scanner input=new Scanner(System.in);
        

        for(int i=0; i<playersname.length; i++){
         if(playerslife[i]!=0)
         { System.out.printf("player %d:%s\n", i+1,playersname[i]);}  //displaying name of players that is still alive
            
        }
        System.out.println("you are the doctor");
        System.out.println("choose who to heal: "); // prompting doctor to choose who they want to heal
        int doctorChoice = input.nextInt() -1;  // index of player that doctor choose to be healed 
        
        for(int i=0; i<playerslife.length; i++){
            if (i == doctorChoice) {
              playerslife[i]=1; // index of player that doctor choose to be healed will be 1
            }
        }
return doctorChoice; // return int doctorChoice to method Play()
    }
public static void PoliceReveal(char[] playersRole,int[] playerslife,String[] playersname)
{
     Scanner input=new Scanner(System.in);
        

        for(int i=0; i<playersname.length; i++){
         if(playerslife[i]!=0)
         {  System.out.printf("player %d:%s\n", i+1,playersname[i]);}  //displaying name of players that is still alive
            
        }
        System.out.println("you are the police");
        System.out.println("choose who to reveal roles ");
        
        int policeChoice = input.nextInt() -1;   // index that police choose to reveal roles
        
         for(int i=0; i<playerslife.length; i++){ // revealing roles based on the index that doctor choose
            if (i == policeChoice) {
              switch(playersRole[i])
              { case 'M': System.out.printf("Player %d is the Mafia",i+1);break;
                case 'C': System.out.printf("Player %d is the Citizen",i+1);break;
                case 'D': System.out.printf("Player %d is the Doctor",i+1);break;
                case 'P': System.out.printf("Player %d is the Police",i+1);break;
                default:break;}
            }
        }
        
        
}


public static void Play(int players, char[] playersRole, int[] playerslife, String[] playersname,int[] suspectCount) {
clearOutput();
    boolean isGameOver;

    do {
        isGameOver = false; // initializing boolean to be false to do loop for every round if the game hasnt ended yet
        int mafiaChoice = -1; // initializing to reset the index for mafiachoice before the loop
        int doctorChoice = -1; // initializing to reset the index for doctorchoice before the loop
        boolean[] hasTakenTurn = new boolean[players]; // Track whether a player has taken a turn

        
        for (int i = 0; i < playersRole.length; i++) {
clearOutput();
            if(playerslife[i]!=0)
                {
                 System.out.printf("\nPass this screen to %s\n",playersname[i]); 
                    clearOutput();
                System.out.printf("Player %d turn\n", i + 1);
               

                switch (playersRole[i]) { // switching case based on the player role and call each role method 
                    case 'M':
                        mafiaChoice = MafiaKill(playerslife, playersname); // get index mafiaChoice from method MafiaKill()
                        
                        break;
                    case 'C':
                        CitizenChooseSuspect(playersname, players, playersRole, playerslife,suspectCount); 

                        break;
                    case 'D':
                        doctorChoice = doctorHeal(playersname, playerslife); //get index doctorChoice from method doctorHeal()
                     
                        break;
                    case 'P':
                        PoliceReveal(playersRole, playerslife, playersname);
           
                        break;
                    default:
                        break;
                }
                }
             clearOutput();
        }
                
                clearOutput();
       

        checkAlive(playersname, playerslife, mafiaChoice, doctorChoice); // call method to check who got killed after all roles has taken their turn in the round
        VotingRound(playersname, players, playerslife,playersRole); // call method to run voting round to votes for mafia 
        isGameOver = determineWinner(playersRole, playerslife); //  get boolean isGameOver and display whether citizen/mafia is the winner else the boolean will not return any value and the game will goes for the next round
    } while (isGameOver); // if boolean = true the the game ended
}



public static void checkAlive(String[] playersname, int[] playerslife, int mafiaChoice, int doctorChoice)
{   
    clearOutput();
    System.out.printf("Last round...\n");
    playerslife[mafiaChoice] = 0 ; // assign 0 to playerslife that has been choose Mafia to be kill
    if (playerslife[mafiaChoice] == 0) {
            System.out.printf("Player %s was killed by the Mafia.\n", playersname[mafiaChoice]);
            if (doctorChoice == mafiaChoice) {
                System.out.printf("But the Doctor healed Player %s, so they survived!\n", playersname[mafiaChoice]);
               playerslife[mafiaChoice] = 1; // assign 1 to playerslife that has been choose Mafia to be kill and has been chose by Doctor to be healed
            }
        }
}

public static void VotingRound(String[] playersname, int players, int[] playerslife, char[] playersRole) {
    int[] votes = new int[players];
    Scanner input = new Scanner(System.in);

    for (int i = 0; i < playersname.length; i++) {
        if (playerslife[i] != 0) {
            System.out.printf("Player %d : %s\n", i + 1, playersname[i]);
        }
    }

    System.out.println("Vote for the player you suspect to be the mafia:");
    for (int i = 0; i < players; i++) {
        if (playerslife[i] != 0) {
            System.out.printf("%s, who do you suspect to be the mafia? Enter the number of the suspected player: ", playersname[i]); // calling all players that is alive to vote who they think is the Mafia
            int vote = input.nextInt() - 1; // index for chosen players in array vote
            votes[vote]++; // increment for each index chosen by players
        }
    }

    System.out.println("Current Votes:");
    for (int i = 0; i < players; i++) {
        System.out.printf("%s: %d\n", playersname[i], votes[i]); // display current votes
    }

    int maxVotesIndex = 0;
    boolean tie = false; 
    for (int i = 1; i < players; i++) {
        if (votes[i] > votes[maxVotesIndex]) {
            maxVotesIndex = i;
            tie = false;
        } else if (votes[i] == votes[maxVotesIndex]) { // finding if there is any tie in votes if yes then 
            tie = true;                                        // return boolean tie TRUE
        }
    }

    if (tie) {
        System.out.println("There is a tie between the following players:"); // telling users there is a tie 
        for (int i = 0; i < players; i++) {
            if (votes[i] == votes[maxVotesIndex]) {
                System.out.println(playersname[i]); // displaying players that have the sae votes
            }
        }
    } else {
        System.out.printf("Player %s has the most votes.\n", playersname[maxVotesIndex]);
        if (playersRole[maxVotesIndex] == 'M') { // if player succeed to find mafia 
            System.out.printf("THE MAFIA HAS BEEN CAUGHT.\n"); 
            playerslife[maxVotesIndex] = 0; // set mafia players life to 0
        } else {
            System.out.printf("Player %s is not the mafia.\n", playersname[maxVotesIndex]);
        }
    }
}



    public static boolean determineWinner(char[] playersRole, int[] playerslife) {
        int mafiaCount = 0;
        int citizenCount = 0;
        boolean isGameOver = false;
        for (int i = 0; i < playerslife.length; i++) {
            if (playerslife[i] == 1) {
                if (playersRole[i] == 'M'&& (!(playerslife[i]==0))) {
                    mafiaCount++; //check how many mafia is still alive
                } else if (!(playersRole[i] == 'M')&& (!(playerslife[i]==0))) {
                    citizenCount++; //check how many roles other than mafia is still alive
                }
            }
        }
        if (mafiaCount == 0) { // if there is no more mafia in the game
            System.out.println("Citizens win!"); // citizen wins the game
        } else if (mafiaCount >= citizenCount) { // mafia count is the same / more than other roles
            System.out.println("Mafia win!"); // mafia wins the game
        } else {
             clearOutput();
            System.out.println("Game continues to the next round."); //display this before the next round
             isGameOver=true; // initialize boolean to true to create a loop 
        }
      return isGameOver ;  // return isGameOver to method Play()
    }

}
