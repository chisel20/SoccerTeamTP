DROP TABLE IF EXISTS footballer;

DROP TABLE IF EXISTS team;

CREATE TABLE team
(
    team_id            int         NOT NULL auto_increment,
    team_name          varchar(50) NOT NULL UNIQUE,
    CONSTRAINT team_pk PRIMARY KEY (team_id)
);

CREATE TABLE footballer
(
    footballer_id int          NOT NULL auto_increment,
    firstname     varchar(50) NOT NULL,
    lastname      varchar(50) NOT NULL,
    age           int          NOT NULL,
    salary        float          NOT NULL,
    team_id       int          NOT NULL,
    joining_date  date          NOT NULL,
    CONSTRAINT footballer_pk PRIMARY KEY (footballer_id),
    CONSTRAINT footballer_team_fk FOREIGN KEY (team_id) REFERENCES team (team_id)
);