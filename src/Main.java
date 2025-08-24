import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Athlete Management System!");
        while (true) {
            System.out.println("\nSelect Role:");
            System.out.println("1. Coach");
            System.out.println("2. Athlete");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int roleChoice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch(roleChoice) {
                case 1: // Coach
                    Coach coach = new Coach(sc);
                    System.out.println("1. Sign Up\n2. Sign In");
                    System.out.print("Enter choice: ");
                    int cChoice = sc.nextInt(); sc.nextLine();
                    if(cChoice == 1) coach.signUp();
                    else if(cChoice == 2) {
                        int coachID = coach.signIn();
                        if(coachID != -1) coach.menu();
                    }
                    break;

                case 2: // Athlete
                    Athlete athlete = new Athlete();
                    System.out.println("1. Sign Up\n2. Sign In");
                    System.out.print("Enter choice: ");
                    int aChoice = sc.nextInt(); sc.nextLine();
                    if(aChoice == 1) athlete.signUp();
                    else if(aChoice == 2) {
                        int athleteID = athlete.signIn();
                        if(athleteID != -1) athlete.menu();
                    }
                    break;

                case 3: 
                    System.out.println("Exiting..."); 
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
