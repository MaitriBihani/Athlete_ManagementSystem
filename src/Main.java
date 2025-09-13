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





// Welcome to Athlete Management System!

// Select Role:
// 1. Coach
// 2. Athlete
// 3. Exit
// Enter choice: 1
// 1. Sign Up
// 2. Sign In
// Enter choice: 1
// Enter Name: Maitri
// Enter Sport: Cricket
// Enter Experience (years): 3
// Sign Up Successful! Coach ID: 4

// Select Role:
// 1. Coach
// 2. Athlete
// 3. Exit
// Enter choice: 1
// 1. Sign Up
// 2. Sign In
// Enter choice: 2
// Enter Coach ID: 4
// Welcome, Maitri!

// --- Coach Menu ---
// 1. View My Athletes
// 2. Assign Match
// 3. View Athlete Performance
// 4. Log Training Session
// 5. Show Training History
// 6. Show Athletes Sorted by Name
// 7. View Sports Handled
// 8. Add Notification & Show Recent Alerts
// 9. Exit
// Enter choice: 1
// No athletes assigned.

// --- Coach Menu ---
// 1. View My Athletes
// 2. Assign Match
// 3. View Athlete Performance
// 4. Log Training Session
// 5. Show Training History
// 6. Show Athletes Sorted by Name
// 7. View Sports Handled
// 8. Add Notification & Show Recent Alerts
// 9. Exit
// Enter choice: 3
// Enter Athlete ID: 1
// --- Top Performances ---
// Event: Asian Games 2023, Score: 88.17, Date: 2023-10-01

// --- Coach Menu ---
// 1. View My Athletes
// 2. Assign Match
// 3. View Athlete Performance
// 4. Log Training Session
// 5. Show Training History
// 6. Show Athletes Sorted by Name
// 7. View Sports Handled
// 8. Add Notification & Show Recent Alerts
// 9. Exit
// Enter choice: 4
// Enter Training Session Detail: 12am to 6 pm
// Training session logged!

// --- Coach Menu ---
// 1. View My Athletes
// 2. Assign Match
// 3. View Athlete Performance
// 4. Log Training Session
// 5. Show Training History
// 6. Show Athletes Sorted by Name
// 7. View Sports Handled
// 8. Add Notification & Show Recent Alerts
// 9. Exit
// Enter choice: 5
// --- Training History ---
// 12am to 6 pm

// --- Coach Menu ---
// 1. View My Athletes
// 2. Assign Match
// 3. View Athlete Performance
// 4. Log Training Session
// 5. Show Training History
// 6. Show Athletes Sorted by Name
// 7. View Sports Handled
// 8. Add Notification & Show Recent Alerts
// 9. Exit
// Enter choice: 6
// No athletes loaded yet.

// --- Coach Menu ---
// 1. View My Athletes
// 2. Assign Match
// 3. View Athlete Performance
// 4. Log Training Session
// 5. Show Training History
// 6. Show Athletes Sorted by Name
// 7. View Sports Handled
// 8. Add Notification & Show Recent Alerts
// 9. Exit
// Enter choice: 7
// --- Sports Handled by Coach ---
// Cricket

// --- Coach Menu ---
// 1. View My Athletes
// 2. Assign Match
// 3. View Athlete Performance
// 4. Log Training Session
// 5. Show Training History
// 6. Show Athletes Sorted by Name
// 7. View Sports Handled
// 8. Add Notification & Show Recent Alerts
// 9. Exit
// Enter choice: 8
// Enter Notification Message: Come fast
// Notification added!
// --- Recent Notifications ---
// Come fast

// --- Coach Menu ---
// 1. View My Athletes
// 2. Assign Match
// 3. View Athlete Performance
// 4. Log Training Session
// 5. Show Training History
// 6. Show Athletes Sorted by Name
// 7. View Sports Handled
// 8. Add Notification & Show Recent Alerts
// 9. Exit
// Enter choice: 8
// Enter Notification Message: Great Work today!!
// Notification added!
// --- Recent Notifications ---
// Great Work today!!
// Come fast

// --- Coach Menu ---
// 1. View My Athletes
// 2. Assign Match
// 3. View Athlete Performance
// 4. Log Training Session
// 5. Show Training History
// 6. Show Athletes Sorted by Name
// 7. View Sports Handled
// 8. Add Notification & Show Recent Alerts
// 9. Exit
// Enter choice: 9

// Select Role:
// 1. Coach
// 2. Athlete
// 3. Exit
// Enter choice: 2
// 1. Sign Up
// 2. Sign In
// Enter choice: 1
// Enter Name: Asmita Ahire
// Enter Age: 20
// Enter Country: India
// Enter Sport: Yoga
// Sign Up Successful! Athlete ID: 4

// Select Role:
// 1. Coach
// 2. Athlete
// 3. Exit
// Enter choice: 2
// 1. Sign Up
// 2. Sign In
// Enter choice: 2
// Enter Athlete ID: 4
// Welcome, Asmita Ahire!

// --- Athlete Menu ---
// 1. View My Matches
// 2. View My Group
// 3. View Performance (Top Scores)
// 4. View Recent Activities (Stack)
// 5. View Performance Trend (Queue)
// 6. Add / View Achievements (HashMap+TreeSet)
// 7. Exit
// Enter choice: 1
// No matches found.

// --- Athlete Menu ---
// 1. View My Matches
// 2. View My Group
// 3. View Performance (Top Scores)
// 4. View Recent Activities (Stack)
// 5. View Performance Trend (Queue)
// 6. Add / View Achievements (HashMap+TreeSet)
// 7. Exit
// Enter choice: 2
// No group assigned.

// --- Athlete Menu ---
// 1. View My Matches
// 2. View My Group
// 3. View Performance (Top Scores)
// 4. View Recent Activities (Stack)
// 5. View Performance Trend (Queue)
// 6. Add / View Achievements (HashMap+TreeSet)
// 7. Exit
// Enter choice: 3
// No performance data found.

// --- Athlete Menu ---
// 1. View My Matches
// 2. View My Group
// 3. View Performance (Top Scores)
// 4. View Recent Activities (Stack)
// 5. View Performance Trend (Queue)
// 6. Add / View Achievements (HashMap+TreeSet)
// 7. Exit
// Enter choice: 4

// --- Recent Activities (Last to First) ---
// Viewed Group
// Signed in as Athlete with ID 4

// --- Athlete Menu ---
// 1. View My Matches
// 2. View My Group
// 3. View Performance (Top Scores)
// 4. View Recent Activities (Stack)
// 5. View Performance Trend (Queue)
// 6. Add / View Achievements (HashMap+TreeSet)
// 7. Exit
// Enter choice: 5
// No performance trend data available.

// --- Athlete Menu ---
// 1. View My Matches
// 2. View My Group
// 3. View Performance (Top Scores)
// 4. View Recent Activities (Stack)
// 5. View Performance Trend (Queue)
// 6. Add / View Achievements (HashMap+TreeSet)
// 7. Exit
// Enter choice: 6

// --- Achievements ---
// 1. Add Achievement
// 2. View Achievements by Year
// 3. Back
// Enter choice: 2
// Enter Year to View: 2024
// No achievements for 2024

// --- Achievements ---
// 1. Add Achievement
// 2. View Achievements by Year
// 3. Back
// Enter choice: 1
// Enter Year: 2025
// Enter Achievement: College Level competeion
// Achievement added!

// --- Achievements ---
// 1. Add Achievement
// 2. View Achievements by Year
// 3. Back
// Enter choice: 2
// Enter Year to View: 2025
// Achievements in 2025:
// - College Level competeion

// --- Achievements ---
// 1. Add Achievement
// 2. View Achievements by Year
// 3. Back
// Enter choice: 3

// --- Athlete Menu ---
// 1. View My Matches
// 2. View My Group
// 3. View Performance (Top Scores)
// 4. View Recent Activities (Stack)
// 5. View Performance Trend (Queue)
// 6. Add / View Achievements (HashMap+TreeSet)
// 7. Exit
// Enter choice: 7

// Select Role:
// 1. Coach
// 2. Athlete
// 3. Exit
// Enter choice: 3
// Exiting...



// PS C:\Users\bihan\OneDrive\Desktop\Myproject\Athlete_ManagementSystem-main\src> java -cp ".;..\lib\mysql-connector-j-9.4.0\mysql-connector-j-9.4.0.jar" Main

// javac *.java command inside the src 