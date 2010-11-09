CREATE TABLE users
(
    username        varchar(20) PRIMARY KEY,
    password        varchar(20) NOT NULL,
    isAdmin         bit NOT NULL,
    games_played    int,
    games_won       int,
    rounds_played   int,
    thumb_count     int,
    index_count     int,
    middle_count    int,
    ring_count      int,
    pinkie_count    int,
    round_percent_sum   decimal(10,8)
);

CREATE TABLE rounds
(
    roundNum        int,
    gameID          int,
    leader          int NOT NULL,
    endtime         datetime,
    PRIMARY KEY (gameID, roundNum)
);

CREATE TABLE games
(
    gameID          int PRIMARY KEY,
    numRounds       int NOT NULL,
    winner          varchar(20) NOT NULL,
    startTime       datetime,
    endTime         datetime
);

CREATE TABLE players
(
    username        varchar(20),
    gameID          int,
    roundNum        int,
    finger          int,

    PRIMARY KEY (username, gameID, roundNum)
);