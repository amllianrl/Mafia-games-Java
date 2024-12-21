package mafiagame;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Player {
   String name;
    boolean isMafia;
    boolean isAlive;
    boolean isMarkedForElimination;
    Player target;

    Player(String name) {
        this.name = name;
        this.isAlive = true;
        this.isMarkedForElimination = false;
    }
}

public class MafiaGame {
    private ArrayList<Player> players;

    public MafiaGame() {
        players = new ArrayList<>();
    }

    public void initializeGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();

        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player("Player" + i));
        }

        assignRoles();
        Collections.shuffle(players);
    }

    private void assignRoles() {
        int numMafia = players.size() / 4;

        for (int i = 0; i < numMafia; i++) {
            int randomIndex = (int) (Math.random() * players.size());
            players.get(randomIndex).isMafia = true;
        }
    }

    public void playGame() {
        while (!isGameOver()) {
            performRound();
        }

        determineWinner();
    }

    private void performRound() {
        System.out.println("=== Round Start ===");

        // Mafia members take action
        mafiaAction();

        // Players discuss
        System.out.println("Players discuss who to vote for...");

        // Voting phase
        conductVoting();

        // Check for eliminated players
        checkEliminations();

        System.out.println("=== Round End ===");
    }

    private void mafiaAction() {
        System.out.println("Mafia members take action...");

        for (Player mafiaPlayer : players) {
            if (mafiaPlayer.isMafia && mafiaPlayer.isAlive) {
                Player target = chooseMafiaTarget();

                if (target != null) {
                    System.out.println(mafiaPlayer.name + " (Mafia) targets " + target.name);

                    // Process the Mafia action
                    processMafiaAction(mafiaPlayer, target);
                } else {
                    System.out.println(mafiaPlayer.name + " (Mafia) couldn't find a target.");
                }
            }
        }
    }

    private Player chooseMafiaTarget() {
        // Filter alive non-Mafia players
        ArrayList<Player> nonMafiaAlivePlayers = new ArrayList<>();
        for (Player player : players) {
            if (!player.isMafia && player.isAlive) {
                nonMafiaAlivePlayers.add(player);
            }
        }

        // Randomly select a target from the alive non-Mafia players
        if (!nonMafiaAlivePlayers.isEmpty()) {
            int randomIndex = (int) (Math.random() * nonMafiaAlivePlayers.size());
            return nonMafiaAlivePlayers.get(randomIndex);
        } else {
            return null; // No valid target available
        }
    }

    private void processMafiaAction(Player mafiaPlayer, Player target) {
        // Implement actions based on Mafia targeting logic
        // For example, mark the target player for elimination or perform other actions
        // For simplicity, let's mark the target player for elimination
        System.out.println("Mafia actions: " + target.name + " marked for elimination.");
        target.isMarkedForElimination = true;
        mafiaPlayer.target = target;  // Remember the target for later reference if needed
    }

    private void conductVoting() {
        System.out.println("Players vote on who to eliminate...");

        for (Player player : players) {
            if (player.isAlive) {
                // For simplicity, players randomly vote
                int randomVoteIndex = (int) (Math.random() * players.size());
                Player voteTarget = players.get(randomVoteIndex);
                System.out.println(player.name + " votes to eliminate " + voteTarget.name);

                // Process the voting action
                processVotingAction(player, voteTarget);
            }
        }
    }

    private void processVotingAction(Player voter, Player voteTarget) {
        // Implement actions based on voting logic
        // For simplicity, no additional action is performed here
    }

    private void checkEliminations() {
        // Check for player elimination based on votes
        for (Player player : players) {
            if (player.isMarkedForElimination) {
                System.out.println(player.name + " has been eliminated by votes!");
                player.isAlive = false;
                player.isMarkedForElimination = false; // Reset the mark
            }
        }

        // Check for player elimination based on Mafia actions
        for (Player player : players) {
            if (player.isMafia && player.isAlive) {
                if (player.target != null && player.target.isAlive) {
                    System.out.println(player.target.name + " has been eliminated by Mafia!");
                    player.target.isAlive = false;
                    player.target = null; // Reset the target
                }
            }
        }
    }

    private boolean isGameOver() {
        int numMafiaAlive = 0;
        int numVillagersAlive = 0;

        for (Player player : players) {
            if (player.isAlive) {
                if (player.isMafia) {
                    numMafiaAlive++;
                } else {
                    numVillagersAlive++;
                }
            }
        }

        // Game is over if all Mafia members are eliminated or if Mafia outnumber villagers
        return numMafiaAlive == 0 || numMafiaAlive >= numVillagersAlive;
    }

    private void determineWinner() {
        boolean mafiaAlive = false;
        boolean villagersAlive = false;

        for (Player player : players) {
            if (player.isAlive) {
                if (player.isMafia) {
                    mafiaAlive = true;
                } else {
                    villagersAlive = true;
                }
            }
        }

        if (mafiaAlive && !villagersAlive) {
            System.out.println("Mafia wins!");
        } else if (!mafiaAlive && villagersAlive) {
            System.out.println("Villagers win!");
        } else {
            System.out.println("It's a draw!");
        }
    }

    public static void main(String[] args) {
        MafiaGame mafiaGame = new MafiaGame();
        mafiaGame.initializeGame();
        mafiaGame.playGame();
    }
}