CREATE TABLE order_status (
                              order_id VARCHAR(50) PRIMARY KEY,
                              status VARCHAR(50) NOT NULL,
                              last_event_payload CLOB NOT NULL,
                              updated_at TIMESTAMP NOT NULL
);