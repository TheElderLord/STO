CREATE TABLE IF NOT EXISTS requests (
                                        id          UUID PRIMARY KEY,
                                        client_id   UUID NOT NULL,
                                        car_vin     VARCHAR(17) NOT NULL,
    description TEXT,
    status      VARCHAR(20) NOT NULL DEFAULT 'NEW',
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT now()
    );

CREATE TABLE IF NOT EXISTS status_history (
                                              id          SERIAL PRIMARY KEY,
                                              request_id  UUID REFERENCES requests(id) ON DELETE CASCADE,
    old_status  VARCHAR(20),
    new_status  VARCHAR(20),
    changed_by  VARCHAR(100),
    reason      TEXT,
    changed_at  TIMESTAMP WITH TIME ZONE DEFAULT now()
    );

CREATE INDEX IF NOT EXISTS idx_status_history_request
    ON status_history(request_id);

CREATE TABLE IF NOT EXISTS users (
                                     id       UUID PRIMARY KEY,
                                     username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled  BOOLEAN NOT NULL DEFAULT true,
    role     VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER'
    );

CREATE TABLE IF NOT EXISTS refresh_tokens (
                                              token       UUID PRIMARY KEY,
                                              user_id     UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                              expires_at  TIMESTAMP WITH TIME ZONE NOT NULL
                                                                                                 );

CREATE INDEX IF NOT EXISTS idx_refresh_tokens_user
    ON refresh_tokens(user_id);
