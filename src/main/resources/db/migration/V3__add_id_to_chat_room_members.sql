ALTER TABLE chat_room_members
    ADD COLUMN id UUID DEFAULT gen_random_uuid() PRIMARY KEY;

ALTER TABLE chat_room_members
    ADD CONSTRAINT unique_room_member UNIQUE (room_id, user_id);