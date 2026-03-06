DROP SCHEMA IF EXISTS raw CASCADE;
CREATE SCHEMA raw;

CREATE TABLE raw.accounts (
                              account_id TEXT PRIMARY KEY,
                              email TEXT,
                              created_at TIMESTAMPTZ
);

CREATE TABLE raw.events (
                            event_id TEXT PRIMARY KEY,
                            name TEXT,
                            slug TEXT
);

CREATE TABLE raw.showtimes (
                               showtime_id TEXT PRIMARY KEY,
                               event_id TEXT references raw.events(event_id) on delete cascade,
                               start_at TIMESTAMPTZ
);

CREATE TABLE raw.orders (
                            order_id TEXT PRIMARY KEY,
                            account_id TEXT references raw.accounts(account_id) on delete cascade,
                            showtime_id TEXT references raw.showtimes(showtime_id) on delete cascade,
                            created_at TIMESTAMPTZ,
                            total_amount TEXT
);