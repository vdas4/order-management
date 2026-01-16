INSERT INTO order_status (order_id, status, last_event_payload, updated_at) VALUES
                                                                                ('ORD-1001', 'CREATED', '{"eventType":"OrderCreated","orderId":"ORD-1001"}', CURRENT_TIMESTAMP),
                                                                                ('ORD-1002', 'VALIDATED', '{"eventType":"OrderValidated","orderId":"ORD-1002","data":{"valid":true}}', CURRENT_TIMESTAMP),
                                                                                ('ORD-1003', 'REJECTED', '{"eventType":"OrderValidated","orderId":"ORD-1003","data":{"valid":false}}', CURRENT_TIMESTAMP);
