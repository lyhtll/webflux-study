-- users 테이블 (R2DBC)
CREATE TABLE IF NOT EXISTS users (
                                     id         VARCHAR(36)  PRIMARY KEY,
    username   VARCHAR(50)  UNIQUE NOT NULL,
    password   VARCHAR(100) NOT NULL,
    created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
    );

-- chat_rooms 테이블 (R2DBC)
CREATE TABLE IF NOT EXISTS chat_rooms (
                                          id         VARCHAR(36)  PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    owner_id   VARCHAR(36)  NOT NULL,
    created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
    );