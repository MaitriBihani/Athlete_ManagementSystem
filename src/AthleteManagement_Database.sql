-- Use the database
CREATE DATABASE IF NOT EXISTS athletedb;
USE athletedb;

-- =========================
-- Table: Athlete
-- =========================
CREATE TABLE IF NOT EXISTS Athlete (
    athlete_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    country VARCHAR(50),
    sport VARCHAR(50)
);

-- Sample data
INSERT INTO Athlete (name, age, country, sport)
VALUES 
('Neeraj Chopra', 26, 'India', 'Javelin Throw'),
('Usain Bolt', 37, 'Jamaica', 'Sprinting'),
('Simone Biles', 27, 'USA', 'Gymnastics');

-- =========================
-- Table: Performance
-- =========================
CREATE TABLE IF NOT EXISTS Performance (
    performance_id INT AUTO_INCREMENT PRIMARY KEY,
    athlete_id INT,
    event_name VARCHAR(100),
    score DECIMAL(10,2),
    event_date DATE,
    FOREIGN KEY (athlete_id) REFERENCES Athlete(athlete_id)
);

-- Sample data
INSERT INTO Performance (athlete_id, event_name, score, event_date)
VALUES
(1, 'Asian Games 2023', 88.17, '2023-10-01'),
(2, 'Olympics 2016', 9.81, '2016-08-14'),
(3, 'World Championship 2019', 15.50, '2019-10-05');

-- =========================
-- Table: Matches
-- =========================
CREATE TABLE IF NOT EXISTS Matches (
    match_id INT AUTO_INCREMENT PRIMARY KEY,
    athlete_id INT NOT NULL,
    sport VARCHAR(50) NOT NULL,
    match_date DATE NOT NULL,
    opponent VARCHAR(100),
    location VARCHAR(100),
    status VARCHAR(20) DEFAULT 'Scheduled',
    FOREIGN KEY (athlete_id) REFERENCES Athlete(athlete_id)
);

-- Sample data
INSERT INTO Matches (athlete_id, sport, match_date, opponent, location, status)
VALUES
(1, 'Chess', '2025-09-01', 'John Doe', 'Delhi Arena', 'Scheduled'),
(2, 'Football', '2025-09-05', 'City Tigers', 'Stadium A', 'Scheduled'),
(1, 'Chess', '2025-09-10', 'Jane Smith', 'Delhi Arena', 'Scheduled'),
(1, 'Javelin Throw', '2025-09-12', 'N/A', 'Pune', 'Scheduled');

-- =========================
-- Table: Coach
-- =========================
CREATE TABLE IF NOT EXISTS Coach (
    coach_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    sport VARCHAR(50),
    experience INT
);

-- Sample data
INSERT INTO Coach (name, sport, experience)
VALUES
('Coach A', 'Chess', 10),
('Coach B', 'Football', 8),
('Maitri', 'Football', 3);

-- =========================
-- Table: Athlete_Groups
-- =========================
CREATE TABLE IF NOT EXISTS Athlete_Groups (
    group_id INT AUTO_INCREMENT PRIMARY KEY,
    athlete_id INT NOT NULL,
    coach_id INT NOT NULL,
    sport VARCHAR(50) NOT NULL,
    group_name VARCHAR(50),
    FOREIGN KEY (athlete_id) REFERENCES Athlete(athlete_id),
    FOREIGN KEY (coach_id) REFERENCES Coach(coach_id)
);

-- Sample data
INSERT INTO Athlete_Groups (athlete_id, coach_id, sport, group_name)
VALUES
(1, 1, 'Chess', 'Delhi Chess Club'),
(2, 2, 'Football', 'City Tigers Squad'),
(3, 1, 'Chess', 'Delhi Chess Club');

-- =========================
-- Optional: View all tables
-- =========================
-- SHOW TABLES;
