CREATE TABLE IF NOT EXISTS patients (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       firstName VARCHAR(32) NOT NULL,
       lastName VARCHAR(64) NOT NULL,
       dateOfBirth DATE,
       gender VARCHAR(1),
       address VARCHAR(96),
       phoneNumber VARCHAR(16)
);