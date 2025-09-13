// import java.sql.*;
// import java.util.*;

// public class Athlete {
//     private int athleteID;
//     private Scanner sc = new Scanner(System.in);

//     public Athlete() { }
//     public Athlete(int athleteID) { this.athleteID = athleteID; }
//     public void setAthleteID(int athleteID) { this.athleteID = athleteID; }

//     // ---------------- Menu ----------------
//     public void menu() {
//         while(true) {
//             System.out.println("\n--- Athlete Menu ---");
//             System.out.println("1. View My Matches");
//             System.out.println("2. View My Group");
//             System.out.println("3. View Performance (Top Scores)");
//             System.out.println("4. Exit");
//             System.out.print("Enter choice: ");
//             int choice = sc.nextInt(); sc.nextLine();

//             switch(choice) {
//                 case 1: viewMatchesDSA(); break;
//                 case 2: viewGroup(); break;
//                 case 3: viewPerformanceDSA(); break;
//                 case 4: return;
//                 default: System.out.println("Invalid choice!");
//             }
//         }
//     }

//     // ---------------- Sign Up ----------------
//     public int signUp() {
//         System.out.print("Enter Name: "); String name = sc.nextLine();
//         System.out.print("Enter Age: "); int age = sc.nextInt(); sc.nextLine();
//         System.out.print("Enter Country: "); String country = sc.nextLine();
//         System.out.print("Enter Sport: "); String sport = sc.nextLine();

//         String sql = "INSERT INTO Athlete(name, age, country, sport) VALUES (?, ?, ?, ?)";
//         try (Connection conn = DBConnection.getConnection();
//              PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//             pstmt.setString(1, name);
//             pstmt.setInt(2, age);
//             pstmt.setString(3, country);
//             pstmt.setString(4, sport);
//             pstmt.executeUpdate();

//             ResultSet rs = pstmt.getGeneratedKeys();
//             if(rs.next()) {
//                 int id = rs.getInt(1);
//                 System.out.println("Sign Up Successful! Athlete ID: " + id);
//                 this.athleteID = id;
//                 return id;
//             }
//         } catch(SQLException e) { e.printStackTrace(); }
//         return -1;
//     }

//     // ---------------- Sign In ----------------
//     public int signIn() {
//         System.out.print("Enter Athlete ID: "); int id = sc.nextInt(); sc.nextLine();

//         String sql = "SELECT * FROM Athlete WHERE athlete_id = ?";
//         try (Connection conn = DBConnection.getConnection();
//              PreparedStatement pstmt = conn.prepareStatement(sql)) {
//             pstmt.setInt(1, id);
//             ResultSet rs = pstmt.executeQuery();
//             if(rs.next()) {
//                 System.out.println("Welcome, " + rs.getString("name") + "!");
//                 this.athleteID = id;
//                 return id;
//             } else System.out.println("Athlete not found!");
//         } catch(SQLException e) { e.printStackTrace(); }
//         return -1;
//     }

//     // ---------------- View Matches with PriorityQueue (Next Upcoming Matches) ----------------
//     private void viewMatchesDSA() {
//         String sql = "SELECT * FROM Matches WHERE athlete_id = ?";
//         try (Connection conn = DBConnection.getConnection();
//              PreparedStatement pstmt = conn.prepareStatement(sql)) {
//             pstmt.setInt(1, athleteID);
//             ResultSet rs = pstmt.executeQuery();

//             // PriorityQueue sorted by match_date ascending
//             PriorityQueue<Match> pq = new PriorityQueue<>(Comparator.comparing(m -> m.matchDate));
//             while(rs.next()) {
//                 pq.add(new Match(
//                     rs.getInt("match_id"),
//                     rs.getString("sport"),
//                     rs.getDate("match_date"),
//                     rs.getString("opponent"),
//                     rs.getString("location"),
//                     rs.getString("status")
//                 ));
//             }

//             if(pq.isEmpty()) { 
//                 System.out.println("No matches found."); 
//                 return; 
//             }

//             System.out.println("\n--- Upcoming Matches ---");
//             while(!pq.isEmpty()) {
//                 Match m = pq.poll();
//                 System.out.println("ID:" + m.matchID + ", Sport:" + m.sport + ", Date:" + m.matchDate +
//                                    ", Opponent:" + m.opponent + ", Location:" + m.location + ", Status:" + m.status);
//             }

//         } catch(SQLException e) { e.printStackTrace(); }
//     }

//     // ---------------- View Group ----------------
//     private void viewGroup() {
//         String sql = "SELECT ag.group_id, c.name AS coach_name, ag.sport, ag.group_name " +
//                      "FROM Athlete_Groups ag JOIN Coach c ON ag.coach_id=c.coach_id WHERE ag.athlete_id=?";
//         try (Connection conn = DBConnection.getConnection();
//              PreparedStatement pstmt = conn.prepareStatement(sql)) {
//             pstmt.setInt(1, athleteID);
//             ResultSet rs = pstmt.executeQuery();

//             boolean hasGroup = false;
//             while(rs.next()) {
//                 hasGroup = true;
//                 System.out.println("GroupID:" + rs.getInt("group_id") +
//                                    ", Coach:" + rs.getString("coach_name") +
//                                    ", Sport:" + rs.getString("sport") +
//                                    ", Group:" + rs.getString("group_name"));
//             }
//             if(!hasGroup) System.out.println("No group assigned.");

//         } catch(SQLException e) { e.printStackTrace(); }
//     }

//     // ---------------- View Performance (Top Scores using PriorityQueue) ----------------
//     private void viewPerformanceDSA() {
//         String sql = "SELECT * FROM Performance WHERE athlete_id=?";
//         try (Connection conn = DBConnection.getConnection();
//              PreparedStatement pstmt = conn.prepareStatement(sql)) {
//             pstmt.setInt(1, athleteID);
//             ResultSet rs = pstmt.executeQuery();

//             // Max-heap for top scores
//             PriorityQueue<Performance> pq = new PriorityQueue<>((a,b) -> Double.compare(b.score, a.score));
//             while(rs.next()) {
//                 pq.add(new Performance(
//                     rs.getInt("performance_id"),
//                     rs.getString("event_name"),
//                     rs.getDouble("score"),
//                     rs.getDate("event_date")
//                 ));
//             }

//             if(pq.isEmpty()) { 
//                 System.out.println("No performance data found."); 
//                 return; 
//             }

//             System.out.println("\n--- Top Performances ---");
//             while(!pq.isEmpty()) {
//                 Performance p = pq.poll();
//                 System.out.println("Event:" + p.eventName + ", Score:" + p.score + ", Date:" + p.eventDate);
//             }

//         } catch(SQLException e) { e.printStackTrace(); }
//     }

//     // ---------------- Inner Classes ----------------
//     private static class Match {
//         int matchID; String sport; java.sql.Date matchDate; String opponent, location, status;
//         public Match(int id, String sport, java.sql.Date date, String opp, String loc, String stat) {
//             this.matchID = id; this.sport = sport; this.matchDate = date;
//             this.opponent = opp; this.location = loc; this.status = stat;
//         }
//     }

//     private static class Performance {
//         int perfID; String eventName; double score; java.sql.Date eventDate;
//         public Performance(int id, String name, double s, java.sql.Date date) {
//             this.perfID = id; this.eventName = name; this.score = s; this.eventDate = date;
//         }
//     }
// }
import java.sql.*;
import java.util.*;

public class Athlete {
    private int athleteID;
    private Scanner sc = new Scanner(System.in);

    // -------- New Data Structures --------
    private Stack<String> activityLog = new Stack<>(); 
    private Queue<Double> performanceTrend = new LinkedList<>(); 
    private Map<Integer, TreeSet<String>> achievements = new HashMap<>();

    public Athlete() { }
    public Athlete(int athleteID) { this.athleteID = athleteID; }
    public void setAthleteID(int athleteID) { this.athleteID = athleteID; }

    // ---------------- Menu ----------------
    public void menu() {
        while(true) {
            System.out.println("\n--- Athlete Menu ---");
            System.out.println("1. View My Matches");
            System.out.println("2. View My Group");
            System.out.println("3. View Performance (Top Scores)");
            System.out.println("4. View Recent Activities (Stack)");
            System.out.println("5. View Performance Trend (Queue)");
            System.out.println("6. Add / View Achievements (HashMap+TreeSet)");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch(choice) {
                case 1: viewMatchesDSA(); break;
                case 2: viewGroup(); break;
                case 3: viewPerformanceDSA(); break;
                case 4: viewRecentActivities(); break;
                case 5: viewPerformanceTrend(); break;
                case 6: achievementsMenu(); break;
                case 7: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    // ---------------- Sign Up ----------------
    public int signUp() {
        System.out.print("Enter Name: "); String name = sc.nextLine();
        System.out.print("Enter Age: "); int age = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Country: "); String country = sc.nextLine();
        System.out.print("Enter Sport: "); String sport = sc.nextLine();

        String sql = "INSERT INTO Athlete(name, age, country, sport) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, country);
            pstmt.setString(4, sport);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                int id = rs.getInt(1);
                System.out.println("Sign Up Successful! Athlete ID: " + id);
                this.athleteID = id;
                activityLog.push("Signed up as Athlete with ID " + id);
                return id;
            }
        } catch(SQLException e) { e.printStackTrace(); }
        return -1;
    }

    // ---------------- Sign In ----------------
    public int signIn() {
        System.out.print("Enter Athlete ID: "); int id = sc.nextInt(); sc.nextLine();

        String sql = "SELECT * FROM Athlete WHERE athlete_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                System.out.println("Welcome, " + rs.getString("name") + "!");
                this.athleteID = id;
                activityLog.push("Signed in as Athlete with ID " + id);
                return id;
            } else System.out.println("Athlete not found!");
        } catch(SQLException e) { e.printStackTrace(); }
        return -1;
    }

    // ---------------- View Matches with PriorityQueue ----------------
    private void viewMatchesDSA() {
        String sql = "SELECT * FROM Matches WHERE athlete_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, athleteID);
            ResultSet rs = pstmt.executeQuery();

            PriorityQueue<Match> pq = new PriorityQueue<>(Comparator.comparing(m -> m.matchDate));
            while(rs.next()) {
                pq.add(new Match(
                    rs.getInt("match_id"),
                    rs.getString("sport"),
                    rs.getDate("match_date"),
                    rs.getString("opponent"),
                    rs.getString("location"),
                    rs.getString("status")
                ));
            }

            if(pq.isEmpty()) { 
                System.out.println("No matches found."); 
                return; 
            }

            System.out.println("\n--- Upcoming Matches ---");
            while(!pq.isEmpty()) {
                Match m = pq.poll();
                System.out.println("ID:" + m.matchID + ", Sport:" + m.sport + ", Date:" + m.matchDate +
                                   ", Opponent:" + m.opponent + ", Location:" + m.location + ", Status:" + m.status);
            }
            activityLog.push("Viewed Matches");

        } catch(SQLException e) { e.printStackTrace(); }
    }

    // ---------------- View Group ----------------
    private void viewGroup() {
        String sql = "SELECT ag.group_id, c.name AS coach_name, ag.sport, ag.group_name " +
                     "FROM Athlete_Groups ag JOIN Coach c ON ag.coach_id=c.coach_id WHERE ag.athlete_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, athleteID);
            ResultSet rs = pstmt.executeQuery();

            boolean hasGroup = false;
            while(rs.next()) {
                hasGroup = true;
                System.out.println("GroupID:" + rs.getInt("group_id") +
                                   ", Coach:" + rs.getString("coach_name") +
                                   ", Sport:" + rs.getString("sport") +
                                   ", Group:" + rs.getString("group_name"));
            }
            if(!hasGroup) System.out.println("No group assigned.");
            activityLog.push("Viewed Group");

        } catch(SQLException e) { e.printStackTrace(); }
    }

    // ---------------- View Performance (PriorityQueue + Queue trend) ----------------
    private void viewPerformanceDSA() {
        String sql = "SELECT * FROM Performance WHERE athlete_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, athleteID);
            ResultSet rs = pstmt.executeQuery();

            PriorityQueue<Performance> pq = new PriorityQueue<>((a,b) -> Double.compare(b.score, a.score));
            performanceTrend.clear(); // refresh queue
            while(rs.next()) {
                double score = rs.getDouble("score");
                pq.add(new Performance(
                    rs.getInt("performance_id"),
                    rs.getString("event_name"),
                    score,
                    rs.getDate("event_date")
                ));
                performanceTrend.add(score); // maintain order of insertion
            }

            if(pq.isEmpty()) { 
                System.out.println("No performance data found."); 
                return; 
            }

            System.out.println("\n--- Top Performances ---");
            while(!pq.isEmpty()) {
                Performance p = pq.poll();
                System.out.println("Event:" + p.eventName + ", Score:" + p.score + ", Date:" + p.eventDate);
            }
            activityLog.push("Viewed Performance");

        } catch(SQLException e) { e.printStackTrace(); }
    }

    // ---------------- Extra Feature: Stack ----------------
    private void viewRecentActivities() {
        if(activityLog.isEmpty()) {
            System.out.println("No activities yet.");
            return;
        }
        System.out.println("\n--- Recent Activities (Last to First) ---");
        ListIterator<String> it = activityLog.listIterator(activityLog.size());
        while(it.hasPrevious()) {
            System.out.println(it.previous());
        }
    }

    // ---------------- Extra Feature: Queue ----------------
    private void viewPerformanceTrend() {
        if(performanceTrend.isEmpty()) {
            System.out.println("No performance trend data available.");
            return;
        }
        System.out.println("\n--- Performance Trend (Oldest to Newest) ---");
        for(Double score : performanceTrend) {
            System.out.print(score + " -> ");
        }
        System.out.println("END");
    }

    // ---------------- Extra Feature: HashMap + TreeSet ----------------
    private void achievementsMenu() {
        while(true) {
            System.out.println("\n--- Achievements ---");
            System.out.println("1. Add Achievement");
            System.out.println("2. View Achievements by Year");
            System.out.println("3. Back");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt(); sc.nextLine();

            switch(ch) {
                case 1: addAchievement(); break;
                case 2: viewAchievements(); break;
                case 3: return;
                default: System.out.println("Invalid!");
            }
        }
    }

    private void addAchievement() {
        System.out.print("Enter Year: ");
        int year = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Achievement: ");
        String ach = sc.nextLine();

        achievements.putIfAbsent(year, new TreeSet<>());
        achievements.get(year).add(ach);

        System.out.println("Achievement added!");
        activityLog.push("Added achievement: " + ach + " (" + year + ")");
    }

    private void viewAchievements() {
        System.out.print("Enter Year to View: ");
        int year = sc.nextInt(); sc.nextLine();

        if(!achievements.containsKey(year) || achievements.get(year).isEmpty()) {
            System.out.println("No achievements for " + year);
            return;
        }

        System.out.println("Achievements in " + year + ":");
        for(String a : achievements.get(year)) {
            System.out.println("- " + a);
        }
    }

    // ---------------- Inner Classes ----------------
    private static class Match {
        int matchID; String sport; java.sql.Date matchDate; String opponent, location, status;
        public Match(int id, String sport, java.sql.Date date, String opp, String loc, String stat) {
            this.matchID = id; this.sport = sport; this.matchDate = date;
            this.opponent = opp; this.location = loc; this.status = stat;
        }
    }

    private static class Performance {
        int perfID; String eventName; double score; java.sql.Date eventDate;
        public Performance(int id, String name, double s, java.sql.Date date) {
            this.perfID = id; this.eventName = name; this.score = s; this.eventDate = date;
        }
    }
}
