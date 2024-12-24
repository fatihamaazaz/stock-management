CREATE DATABASE mydb;
CREATE DATABASE sonar;
CREATE DATABASE history;

\connect history

CREATE TABLE events (
    id SERIAL PRIMARY KEY,
    employee VARCHAR(255),
    product VARCHAR(255),
    quantity INT,
    eventType VARCHAR(255)
);