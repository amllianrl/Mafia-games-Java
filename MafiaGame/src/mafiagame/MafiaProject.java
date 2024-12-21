/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mafiagame;

/**
 *
 * @author amili
 */import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class MafiaProject {

    public static void main(String[] args) {
        int players;
        String[] playersname = new String[50];
        String again;
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Mafia Game");

        do {
            do {
                System.out.print("Enter no of players: ");
                players = input.nextInt();

                if (players < 5) {
                    System.out.println("Minimum to play is 5 players.");
                }
                if (players > 15) {
                    System.out.println("Maximum to play is 15 players.");
                }

            } while (players < 5 || players > 15);

            clearOutput();

            playersname = new String[players];
            AssignPlayers(players, playersname);

            System.out.println("confirm player? (y for yes / n for no)");
            again = input.next();

        } while ("n".equals(again));

        char[] playersRole = new char[players];
        AssignRole(players, playersRole);

        int[] playerslife = new int[players];
        for (int j = 0; j < players; j++) {
            playerslife[j] = 1;
        }

        Play(players, playersRole, playerslife, playersname);
        clearOutput();
    }

    public static void AssignPlayers(int players, String[] playersname) {
        Scanner input = new Scanner(System.in);
        for (int i = 0; i < players; i++) {
            System.out.printf("Enter player %d name: ", i + 1);
            playersname[i] = input.next();
        }
    }

    public static void AssignRole(int players, char[] playersRole) {
        int numMafia = players / 5;

        int randMafia1 = (int) (Math.random() * players);
        int randMafia2 = -1; // Initialize to an invalid index for the case when there's only 1 mafia

        if (numMafia == 2) {
            do {
                randMafia2 = (int) (Math.random() * players);
            } while (randMafia1 == randMafia2);
        }

        int randDoctor;
        do {
            randDoctor = (int) (Math.random() * players);
        } while (randMafia1 == randDoctor || randMafia2 == randDoctor);

        int randPolice;
        do {
            randPolice = (int) (Math.random() * players);
        } while (randMafia1 == randPolice || randMafia2 == randPolice || randDoctor == randPolice);

        int randCitizen;
        do {
            randCitizen = (int) (Math.random() * players);
        } while (randMafia1 == randCitizen || randMafia2 == randCitizen || randDoctor == randCitizen
                || randPolice == randCitizen);

        // Assign roles to the playersRole array
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
            Thread.sleep(500);
        } catch (InterruptedException | AWTException e) {
            System.out.println("Error!");
        }
    }

    public static int MafiaKill(int[] playerslife, String[] playersname) {
        Scanner input = new Scanner(System.in);
        System.out.println("You are the Mafia ");

        for (int i = 0; i < playersname.length; i++) {
            if (playerslife[i] != 0) {
                System.out.printf("Player %d : %s\n", i + 1, playersname[i]);
            }
        }

        System.out.println("Choose who to kill tonight:");

        int mafiaChoice = input.nextInt() - 1;

        for (int i = 0; i < playerslife.length; i++) {
            if (i == mafiaChoice) {
                playerslife[i] = 0;
            } else {
                playerslife[i] = 1;
            }
        }
        return mafiaChoice;
    }

    public static void CitizenChooseSuspect(String[] playersname, int players, char[] playersRole, int[] playerslife) {
        int[] suspectCount = new int[players];

        Scanner input = new Scanner(System.in);

        for (int i = 0; i < playersname.length; i++) {
            System.out.printf("Player %d : %s\n", i + 1, playersname[i]);
        }

        System.out.print("Who do you suspect to be Mafia? ");
        int choice = input.nextInt() - 1;

        suspectCount[choice]++;

        System.out.println("Current Suspect Counts:");
        for (int i = 0; i < players; i++) {
            System.out.printf("%s: %d\n", playersname[i], suspectCount[i]);
        }

        int mostSuspectedIndex = 0;
        for (int i = 1; i < players; i++) {
            if (suspectCount[i] > suspectCount[mostSuspectedIndex]) {
                mostSuspectedIndex = i;
            }
        }

        System.out.printf("Player %s is the most suspected.\n", playersname[mostSuspectedIndex]);
    }

    public static int doctorHeal(String[] playersname, int[] playerslife) {
        Scanner input = new Scanner(System.in);

        for (int i = 0; i < playersname.length; i++) {
            System.out.printf("player %d:%s\n", i + 1, playersname[i]);
        }
        System.out.println("you are the doctor");
        System.out.println("choose who to heal: ");

        int doctorChoice = input.nextInt() - 1;
        for (int i = 0; i < playerslife.length; i++) {
            if (i == doctorChoice) {
                playerslife[i] = 1;
            }
        }
        return doctorChoice;
    }

    public static void PoliceReveal(char[] playersRole, int[] playerslife, String[] playersname) {
        Scanner input = new Scanner(System.in);

        for (int i = 0; i < playersname.length; i++) {
            System.out.printf("player %d:%s\n", i + 1, playersname[i]);
        }
        System.out.println("you are the police");
        System.out.println("choose who to reveal roles ");

        int policeChoice = input.nextInt() - 1;

        for (int i = 0; i < playerslife.length; i++) {
            if (i == policeChoice) {
                switch (playersRole[i]) {
                    case 'M':
                        System.out.printf("Player %d is the Mafia", i + 1);
                        break;
                    case 'C':
                        System.out.printf("Player %d is the Citizen", i + 1);
                        break;
                    case 'D':
                        System.out.printf("Player %d is the Doctor", i + 1);
                        break;
                    case 'P':
                        System.out.printf("Player %d is the Police", i + 1);
                        break;
                    default:
                        break;
                }
            }
        }
    }



public static void Play(int players, char[] playersRole, int[] playerslife, String[] playersname) {
    clearOutput();
    boolean isGameOver;

    List<Integer> killedPlayersList = new ArrayList<>();
    int[] healedPlayers = new int[playersname.length];
    int[] suspectCount = new int[playersname.length];

    do {
        isGameOver = false;

        for (int i = 0; i < playersRole.length; i++) {
            if (playerslife[i] == 0 || killedPlayersList.contains(i)) {
                continue;
            }

            System.out.printf("Player %d turn\n", i + 1);
            clearOutput();

            switch (playersRole[i]) {
                case 'D':
                    healedPlayers[doctorHeal(playersname, playerslife)] = 1;
                    break;
                case 'M':
                    mafiaKill(playerslife, playersname, healedPlayers);
                    break;
                case 'P':
                    PoliceReveal(playersRole, playerslife, playersname);
                    break;
                case 'C':
                    CitizenChooseSuspect(playersname, players, playersRole, playerslife);
                    break;
            }

            clearOutput();
        }

        checkAlive(playersname, playerslife, healedPlayers);
        VotingRound(playersname, players, playerslife);

        for (int i = 0; i < playerslife.length; i++) {
            if (playerslife[i] == 0) {
                killedPlayersList.add(i);
            }
        }

        isGameOver = determineWinner(playersRole, playerslife, suspectCount);

        // Save the game state after each round
        saveGameToFile("savedGame.txt", players, playersRole, playerslife, playersname);
    } while (!isGameOver);
}


    public static void mafiaKill(int[] playerslife, String[] playersname, int[] healedPlayers) {
        Scanner input = new Scanner(System.in);
        System.out.println("You are the Mafia ");

        for (int i = 0; i < playersname.length; i++) {
            if (playerslife[i] != 0) {
                System.out.printf("Player %d : %s\n", i + 1, playersname[i]);
            }
        }

        System.out.println("Choose who to kill tonight:");

        int mafiaChoice = input.nextInt() - 1;

        for (int i = 0; i < playerslife.length; i++) {
            if (i == mafiaChoice && healedPlayers[i] == 0) {
                playerslife[i] = 0;
            } else {
                playerslife[i] = 1;
            }
        }
    }

    public static void checkAlive(String[] playersname, int[] playerslife, int[] healedPlayers) {
        clearOutput();
        System.out.printf("Last round...\n");

        for (int i = 0; i < playersname.length; i++) {
            if (playerslife[i] == 0) {
                System.out.printf("Player %s was killed.\n", playersname[i]);
                if (healedPlayers[i] == 1) {
                    System.out.printf("But the Doctor healed Player %s, so they survived!\n", playersname[i]);
                }
            }
        }
    }

    public static void VotingRound(String[] playersname, int players, int[] playerslife) {
        int[] votes = new int[players];
        Scanner input = new Scanner(System.in);

        for (int i = 0; i < playersname.length; i++) {
            if (playerslife[i] != 0) {
                System.out.printf("Player %d : %s\n", i + 1, playersname[i]);
            }
        }

        System.out.println("Vote for the player you suspect:");
        int vote = input.nextInt() - 1;

        votes[vote]++;

        System.out.println("Current Votes:");
        for (int i = 0; i < players; i++) {
            System.out.printf("%s: %d\n", playersname[i], votes[i]);
        }

        int maxVotesIndex = 0;
        for (int i = 1; i < players; i++) {
            if (votes[i] > votes[maxVotesIndex]) {
                maxVotesIndex = i;
            }
        }

        System.out.printf("Player %s has the most votes.\n", playersname[maxVotesIndex]);
    }

    public static boolean determineWinner(char[] playersRole, int[] playerslife, int[] suspectCount) {
    int mafiaCount = 0;
    int citizenCount = 0;
    boolean isGameOver = false;

    for (int i = 0; i < playerslife.length; i++) {
        if (playerslife[i] == 1) {
            if (playersRole[i] == 'M') {
                mafiaCount++;
            } else if (playersRole[i] == 'C') {
                citizenCount++;
            }
        }
    }

    if (mafiaCount == 0) {
        System.out.println("Citizens win!");
    } else if (mafiaCount >= citizenCount) {
        System.out.println("Mafia win!");
    } else if (suspectCount[findMostSuspectedIndex(suspectCount)] == mafiaCount) {
        System.out.println("Mafia is the most suspected. Game ends!");
        isGameOver = true;
    } else {
        System.out.println("Game continues to the next round.");
    }
    return isGameOver;
}

public static int findMostSuspectedIndex(int[] suspectCount) {
    int mostSuspectedIndex = 0;
    for (int i = 1; i < suspectCount.length; i++) {
        if (suspectCount[i] > suspectCount[mostSuspectedIndex]) {
            mostSuspectedIndex = i;
        }
    }
    return mostSuspectedIndex;
}


    public static void saveGameToFile(String filename, int players, char[] playersRole, int[] playerslife,
            String[] playersname) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            // Write game data to the file
            writer.println("Players: " + players);
            writer.println("Players Role: " + new String(playersRole));
            writer.println("Players Life: " + Arrays.toString(playerslife));
            writer.println("Players Names: " + Arrays.toString(playersname));

            System.out.println("Game data saved to " + filename);
        } catch (FileNotFoundException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
