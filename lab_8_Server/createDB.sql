CREATE SEQUENCE sequence_id;

CREATE SEQUENCE sequence_user_id;

CREATE TABLE users (
                       id INT PRIMARY KEY,
                       login VARCHAR(256) NOT NULL,
                       password VARCHAR(256),
                       salt VARCHAR(256)
);

CREATE TABLE flats (
                       key INT,
                       id INT PRIMARY KEY,
                       name VARCHAR(256) NOT NULL,
                       Coordinates_x FLOAT,
                       Coordinates_y INT,
                       creationDate TIMESTAMP,
                       area INT,
                       numberOfRooms INT,
                       furnish VARCHAR(10),
                       view VARCHAR(10),
                       transport VARCHAR(10) NOT NULL,
                       House_name VARCHAR(256) NOT NULL,
                       House_year INT,
                       House_numberOfFloors INT,
                       House_numberOfFlatsOnFloor INT,
                       user_id INT REFERENCES users (id),
                       Color VARCHAR(50)
);