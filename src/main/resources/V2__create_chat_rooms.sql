CREATE TABLE IF NOT EXISTS chat_rooms (
                                          id         UUID         PRIMARY KEY,
                                          name       VARCHAR(100) NOT NULL,
    owner_id   UUID         NOT NULL,
    created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
    );