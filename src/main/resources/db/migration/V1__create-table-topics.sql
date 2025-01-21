CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    pass VARCHAR(255) NOT NULL,
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE topics (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author_id BIGINT REFERENCES users(id),
    course VARCHAR(255) NOT NULL,
    date_of_creation TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE user_messages (
    user_id BIGINT REFERENCES users(id),
    message TEXT NOT NULL
);

CREATE TABLE topic_messages (
    topic_id BIGINT REFERENCES topics(id),
    content TEXT NOT NULL,
    user_id BIGINT REFERENCES users(id),
    date_of_creation TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_topic_author_id ON topics(author_id);
CREATE INDEX idx_user_messages_user_id ON user_messages(user_id);
CREATE INDEX idx_topic_messages_topic_id ON topic_messages(topic_id);
CREATE INDEX idx_topic_messages_user_id ON topic_messages(user_id);
