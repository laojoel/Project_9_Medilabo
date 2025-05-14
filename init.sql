CREATE TABLE IF NOT EXISTS patients (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        firstName VARCHAR(32) NOT NULL,
                                        lastName VARCHAR(64) NULL,
                                        dateOfBirth DATE,
                                        gender CHAR(1),
                                        address VARCHAR(96),
                                        phoneNumber VARCHAR(16)
);

INSERT INTO patients (firstName, lastName, dateOfBirth, gender, address, phoneNumber) VALUES
                                                                                         ('Test', 'TestNone', '1966-12-31', 'F', '1 Brookside St', '1002223333'),
                                                                                         ('Test', 'TestBorderline', '1945-06-24', 'M', '2 High St', '2003334444'),
                                                                                         ('Test', 'TestInDanger', '2004-06-18', 'M', '3 Club Road', '3004445555'),
                                                                                         ('Test', 'TestEarlyOnset', '2002-06-28', 'F', '4 Valley Dr', '4005556666');