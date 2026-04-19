CREATE TABLE IF NOT EXISTS chat_rooms (
                                          id            UUID         PRIMARY KEY,
                                          type          VARCHAR(10)  NOT NULL,
    owner_id      UUID         NOT NULL,
    name          VARCHAR(100),
    first_user_id UUID,
    second_user_id UUID,
    created_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_direct_room UNIQUE (first_user_id, second_user_id)
    );

CREATE TABLE IF NOT EXISTS chat_room_members (
                                                 room_id   UUID      NOT NULL,
                                                 user_id   UUID      NOT NULL,
                                                 joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                 PRIMARY KEY (room_id, user_id)
    );
