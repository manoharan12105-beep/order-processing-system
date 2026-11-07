# My Learnings

## Day 01 — 06/11/2026

1. `@Transactional`
2. Hibernate Dirty Checking
   - How Hibernate automatically detects entity changes.
   - Methods to prevent unwanted updates.

## Day 02 — 07/11/2026

1. **AWS RDS – Inbound Security Group Rules**
   - Configured an inbound rule for PostgreSQL.
   - Protocol: TCP
   - Port: `5432`
   - Source: My Public IPv4 Address (`/32`).
   - Understood how security groups control incoming database connections.
   - Learned how to allow access from different IP addresses.

2. **Important Networking Concepts**
   - `My IP`: Allows connections from my current public IP address.
   - `0.0.0.0/0`: Allows connection attempts from any IPv4 address.
   - Valid database credentials are still required for PostgreSQL authentication.
   - Avoid exposing database ports to the entire internet in production.