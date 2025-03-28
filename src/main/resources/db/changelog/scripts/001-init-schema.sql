CREATE TABLE users (
    id UUID PRIMARY KEY,
    user_role ENUM('admin', 'user', 'trainer') NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(50) UNIQUE NOT NULL,
    date_of_birth DATE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE gym_info (
    id UUID PRIMARY KEY,
    address VARCHAR(255) NOT NULL,
    phone_number VARCHAR(50) NOT NULL,
    start_weekend_time TIME NOT NULL,
    end_weekend_time TIME NOT NULL,
    start_week_time TIME NOT NULL,
    end_week_time TIME NOT NULL
);

CREATE TABLE trainer_info (
    id UUID PRIMARY KEY,
    user_id UUID UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    photo VARCHAR(255) NOT NULL,
    description TEXT NOT NULL
);

CREATE TABLE trainer_schedule (
    id UUID PRIMARY KEY,
    start_datetime TIMESTAMP NOT NULL,
    end_datetime TIMESTAMP NOT NULL,
    trainer_info_id UUID REFERENCES trainer_info(id) ON DELETE CASCADE,
    gym_info_id UUID REFERENCES gym_info(id) ON DELETE CASCADE
);

CREATE TABLE appointment (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    trainer_schedule_id UUID REFERENCES trainer_schedule(id) ON DELETE CASCADE
);

CREATE TABLE subscription (
    id UUID PRIMARY KEY,
    amount_of_trainings INT NOT NULL,
    validity_period INT NOT NULL,
    price DECIMAL(10,2) NOT NULL
);

CREATE TABLE user_subscriptions (
    id UUID PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    remaining_trainings INT,
    subscription_id UUID REFERENCES subscription(id) ON DELETE CASCADE,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE visit_history (
    id UUID PRIMARY KEY,
    visit_datetime TIMESTAMP NOT NULL,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE
);

ALTER TABLE users
ADD CONSTRAINT email_format_check
CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$');

ALTER TABLE users
ADD CONSTRAINT phone_format_check
CHECK (phone ~* '/^(\+375)?(44|29|25|33)[0-9]{7}$');

ALTER TABLE subscription
ADD CONSTRAINT price_check
CHECK (price > 0);

ALTER TABLE user_subscriptions
ADD CONSTRAINT remaining_trainings_check
CHECK (remaining_trainings >= 0)