import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;  // For all DS

public class Coach {
    private int coachID;
    private Scanner sc;

    // 🔹 Extra data structures
    private LinkedList<String> trainingSessions = new LinkedList<>();
    private TreeMap<String, Integer> athletesByName = new TreeMap<>();
    private HashSet<String> sportsHandled = new HashSet<>();
    private Deque<String> notifications = new ArrayDeque<>();

    public Coach(Scanner scanner) {
        this.sc = scanner;
    }

    public void setCoachID(int id) { this.coachID = id; }

    public void menu() {
        while(true) {
            System.out.println("\n--- Coach Menu ---");
            System.out.println("1. View My Athletes");
            System.out.println("2. Assign Match");
            System.out.println("3. View Athlete Performance");
            System.out.println("4. Log Training Session");
            System.out.println("5. Show Training History");
            System.out.println("6. Show Athletes Sorted by Name");
            System.out.println("7. View Sports Handled");
            System.out.println("8. Add Notification & Show Recent Alerts");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch(choice) {
                case 1: viewAthletesDSA(); break;
                case 2: assignMatch(); break;
                case 3: viewTopPerformancesDSA(); break;
                case 4: logTrainingSession(); break;
                case 5: showTrainingHistory(); break;
                case 6: showAthletesSorted(); break;
                case 7: showSportsHandled(); break;
                case 8: addNotification(); break;
                case 9: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    public int signUp() {
        try {
            System.out.print("Enter Name: "); String name = sc.nextLine();
            System.out.print("Enter Sport: "); String sport = sc.nextLine();
            System.out.print("Enter Experience (years): "); int exp = sc.nextInt(); sc.nextLine();

            String sql = "INSERT INTO coach(name, sport, experience) VALUES (?, ?, ?)";
            try(Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setString(1, name);
                pstmt.setString(2, sport);
                pstmt.setInt(3, exp);
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Sign Up Successful! Coach ID: " + id);
                    this.coachID = id;

                    // track sport handled
                    sportsHandled.add(sport);
                    return id;
                }
            }
        } catch(SQLException e) {
            System.out.println("Error during Sign Up: " + e.getMessage());
        }
        return -1;
    }

    public int signIn() {
        try {
            System.out.print("Enter Coach ID: "); int id = sc.nextInt(); sc.nextLine();

            String sql = "SELECT * FROM coach WHERE coach_id=?";
            try(Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                if(rs.next()) {
                    System.out.println("Welcome, " + rs.getString("name") + "!");
                    this.coachID = id;
                    sportsHandled.add(rs.getString("sport")); // add sport
                    return id;
                } else System.out.println("Coach not found!");
            }
        } catch(SQLException e) {
            System.out.println("Error during Sign In: " + e.getMessage());
        }
        return -1;
    }

    private void viewAthletesDSA() {
        String sql = "SELECT a.athlete_id, a.name, a.sport, ag.group_name " +
                     "FROM athlete a JOIN athlete_groups ag ON a.athlete_id=ag.athlete_id " +
                     "WHERE ag.coach_id=?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, coachID);
            ResultSet rs = pstmt.executeQuery();

            HashMap<Integer,String> athletes = new HashMap<>();
            while(rs.next()) {
                String athleteName = rs.getString("name");
                athletes.put(rs.getInt("athlete_id"), athleteName + " (" + rs.getString("sport") + ")");
                athletesByName.put(athleteName, rs.getInt("athlete_id")); // track in TreeMap
            }

            if(athletes.isEmpty()) {
                System.out.println("No athletes assigned.");
                return;
            }

            System.out.println("--- My Athletes ---");
            athletes.forEach((id, info) -> System.out.println("ID:" + id + ", Info:" + info));

        } catch(SQLException e) {
            System.out.println("Error fetching athletes: " + e.getMessage());
        }
    }

    private void assignMatch() {
        try {
            System.out.print("Enter Athlete ID: "); int athleteID = sc.nextInt(); sc.nextLine();
            System.out.print("Enter Sport: "); String sport = sc.nextLine();
            System.out.print("Enter Match Date (YYYY-MM-DD): "); String date = sc.nextLine();
            System.out.print("Enter Opponent/Team: "); String opponent = sc.nextLine();
            System.out.print("Enter Location: "); String location = sc.nextLine();

            String sql = "INSERT INTO matches(athlete_id, sport, match_date, opponent, location) VALUES (?, ?, ?, ?, ?)";
            try(Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, athleteID);
                pstmt.setString(2, sport);
                pstmt.setDate(3, java.sql.Date.valueOf(date));
                pstmt.setString(4, opponent);
                pstmt.setString(5, location);
                pstmt.executeUpdate();

                System.out.println("Match assigned successfully!");
            }
        } catch(SQLException e) {
            System.out.println("Error assigning match: " + e.getMessage());
        }
    }

    private void viewTopPerformancesDSA() {
        try {
            System.out.print("Enter Athlete ID: "); int athleteID = sc.nextInt(); sc.nextLine();

            String sql = "SELECT * FROM performance WHERE athlete_id=?";
            try(Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, athleteID);
                ResultSet rs = pstmt.executeQuery();

                PriorityQueue<Performance> pq = new PriorityQueue<>((a,b) -> Double.compare(b.score, a.score));
                while(rs.next()) {
                    pq.add(new Performance(
                        rs.getInt("performance_id"),
                        rs.getString("event_name"),
                        rs.getDouble("score"),
                        rs.getDate("event_date")
                    ));
                }

                if(pq.isEmpty()) {
                    System.out.println("No performance data.");
                    return;
                }

                System.out.println("--- Top Performances ---");
                while(!pq.isEmpty()) {
                    Performance p = pq.poll();
                    System.out.println("Event: " + p.eventName + ", Score: " + p.score + ", Date: " + p.eventDate);
                }
            }
        } catch(SQLException e) {
            System.out.println("Error fetching performance: " + e.getMessage());
        }
    }

    // 🔹 New Features with Different DS

    private void logTrainingSession() {
        System.out.print("Enter Training Session Detail: ");
        String session = sc.nextLine();
        trainingSessions.add(session);
        System.out.println("Training session logged!");
    }

    private void showTrainingHistory() {
        if(trainingSessions.isEmpty()) {
            System.out.println("No training sessions logged yet.");
            return;
        }
        System.out.println("--- Training History ---");
        trainingSessions.forEach(System.out::println);
    }

    private void showAthletesSorted() {
        if(athletesByName.isEmpty()) {
            System.out.println("No athletes loaded yet.");
            return;
        }
        System.out.println("--- Athletes Sorted by Name ---");
        athletesByName.forEach((name, id) -> System.out.println("Name: " + name + ", ID: " + id));
    }

    private void showSportsHandled() {
        if(sportsHandled.isEmpty()) {
            System.out.println("No sports assigned yet.");
            return;
        }
        System.out.println("--- Sports Handled by Coach ---");
        sportsHandled.forEach(System.out::println);
    }

    private void addNotification() {
        System.out.print("Enter Notification Message: ");
        String note = sc.nextLine();
        notifications.addFirst(note);
        if(notifications.size() > 5) notifications.removeLast(); // keep only last 5
        System.out.println("Notification added!");

        System.out.println("--- Recent Notifications ---");
        notifications.forEach(System.out::println);
    }

    // Existing inner class
    private static class Performance {
        int perfID;
        String eventName;
        double score;
        java.sql.Date eventDate;
        public Performance(int id, String name, double s, java.sql.Date date) {
            this.perfID = id;
            this.eventName = name;
            this.score = s;
            this.eventDate = date;
        }
    }
}
