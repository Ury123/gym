CREATE TYPE user_role AS ENUM ('admin', 'user', 'trainer');

CREATE TABLE users (
    id UUID PRIMARY KEY,
    user_role user_role NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(13) UNIQUE NOT NULL,
    date_of_birth DATE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE gym_info (
    id UUID PRIMARY KEY,
    address VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
    description TEXT NOT NULL
);

CREATE TABLE trainer_info (
    id UUID PRIMARY KEY,
    user_id UUID UNIQUE REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    photo VARCHAR(500) NOT NULL,
    description TEXT NOT NULL
);

CREATE TABLE trainer_schedule (
    id UUID PRIMARY KEY,
    start_datetime TIMESTAMP NOT NULL,
    end_datetime TIMESTAMP NOT NULL,
    trainer_info_id UUID REFERENCES trainer_info(id) ON DELETE CASCADE ON UPDATE CASCADE,
    gym_info_id UUID REFERENCES gym_info(id) ON DELETE CASCADE ON UPDATE CASCADE,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE subscription (
    id UUID PRIMARY KEY,
    amount_of_trainings INT NOT NULL,
    validity_period VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL
);

CREATE TABLE user_subscription (
    id UUID PRIMARY KEY,
    start_date DATE NOT NULL,
    remaining_trainings INT,
    subscription_id UUID REFERENCES subscription(id) ON DELETE CASCADE ON UPDATE CASCADE,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE visit_history (
    id UUID PRIMARY KEY,
    visit_datetime TIMESTAMP NOT NULL,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE users
ADD CONSTRAINT email_format_check
CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$');

ALTER TABLE users
ADD CONSTRAINT user_phone_format_check
CHECK (phone_number ~* '^(\+375)?(44|29|25|33)[0-9]{7}$');

ALTER TABLE gym_info
ADD CONSTRAINT gym_phone_format_check
CHECK (phone_number ~* '^\d{2,4}\s?[0-9]{6}$');

ALTER TABLE subscription
ADD CONSTRAINT price_check
CHECK (price > 0);

ALTER TABLE user_subscription
ADD CONSTRAINT remaining_trainings_check
CHECK (remaining_trainings >= 0);

ALTER TABLE subscription
ADD CONSTRAINT validity_period_email_format_check
CHECK (validity_period ~* '^((\d+)\s+years?\s*)?((\d+)\s+months?\s*)?((\d+)\s+days?\s*)?$')