# AGENTS.md

## Project Overview

This project is a learning-oriented but real Spring Boot backend called **OrderIntelligence Agent**.

The long-term goal is to learn and implement:
- agentic programming
- ReAct-style workflows
- Spring AI with Ollama
- MongoDB
- microservices with Spring Boot
- Docker
- Jenkins CI/CD
- K3s / Kubernetes
- Kafka
- Saga pattern

The business use case is:
an intelligent agent that analyzes an order portfolio and answers natural-language questions about customers, products, orders, anomalies, churn risk, and operational signals.

## Current Environment

Already available and working:
- Ubuntu Server with Docker
- MongoDB running
- Ollama running
- IntelliJ with Maven-based Spring Boot project
- GitHub repository configured
- MongoDB integration tested
- Ollama integration tested
- OpenCode Terminal installed

## Important Distinction

Do not confuse these two concepts:

- **OpenCode** = the coding agent / development assistant
- **Ollama** = the local LLM runtime used by the Spring application at runtime through Spring AI

OpenCode helps build the codebase.
Ollama is part of the application architecture.

## Development Philosophy

Always follow these principles:

1. Start simple.
2. Prefer a working vertical slice over premature architecture.
3. Keep the initial system as a single Spring Boot application.
4. Delay microservices until the core monolith is stable.
5. Delay Kafka until distributed workflows are actually needed.
6. Delay Saga until there are multiple services participating in one workflow.
7. Delay Kubernetes until Docker and CI/CD are stable.
8. Avoid overengineering.
9. Favor readable, testable, pragmatic code.
10. Be explicit about tradeoffs.

## Current Roadmap

### Phase 1 — Foundations
1. OpenCode workflow setup
2. Domain model in one Spring Boot project
3. Realistic seed dataset

### Phase 2 — Agent Core
4. Spring AI + Ollama + ChatClient + first tool
5. ReAct minimum with multiple tools
6. Conversation memory and audit in MongoDB

### Phase 3 — Monolith Consolidation
7. Stabilization and realistic use cases
8. Docker containerization

### Phase 4 — Distributed System
9. Split into microservices:
    - order-service
    - agent-service
    - gateway-service
10. Introduce Saga pattern
11. Introduce Kafka and event-driven communication

### Phase 5 — Platform
12. Jenkins CI/CD
13. K3s / Kubernetes
14. Observability and hardening

## Architectural Rules

### Rule 1 — Single Project First
Until explicitly requested otherwise, assume the codebase should remain a single Spring Boot application.

### Rule 2 — Saga Timing
Do not introduce Saga before the microservice split.
Saga belongs after microservices, before or together with Kafka-based distributed workflows.

### Rule 3 — Kafka Timing
Do not introduce Kafka in the monolith phase unless there is a very strong didactic reason.

### Rule 4 — Spring AI Goal
The first important AI milestone is:
user prompt -> ChatClient -> tool call -> MongoDB -> answer

### Rule 5 — Observability
Agent behavior should be easy to inspect.
Whenever relevant, prefer designs that make tool calls, tool inputs, tool outputs, and session history visible and storable.

## Preferred Initial Domain

The first domain entities are:
- Customer
- Product
- Order

These should be modeled in a way that supports future AI tools such as:
- find orders by customer
- summarize customer spending
- detect anomalies
- estimate churn risk
- compute simple portfolio statistics

## Coding Style

- Java + Spring Boot + Maven
- clear package naming
- minimal but clean layering
- avoid unnecessary abstractions
- prefer explicit names over clever names
- code should be easy to evolve later into microservices
- temporary shortcuts are acceptable only if clearly marked as temporary

## Response Style for This Project

When working on a task, prefer this structure:
1. task understanding
2. minimal architectural decisions
3. files to create/modify
4. proposed implementation
5. how to test
6. next micro-step

## Practical Expectations

When asked to implement something:
- do not redesign the whole system unnecessarily
- do not jump ahead in the roadmap
- do not introduce future-phase concerns too early
- keep the current step aligned with the roadmap

When there are multiple options:
- recommend the most pragmatic option for now
- briefly explain why the more advanced options should wait

## Current Immediate Focus

We are in the early monolith phase.

The immediate implementation order is:
1. package structure
2. MongoDB entities
3. Spring Data repositories
4. seed data
5. simple validation endpoint/service
6. prepare for Spring AI integration later

## Useful Output Format

For implementation tasks, provide:
- short rationale
- file tree
- code
- test/run instructions
- next task suggestion

## Anti-Goals For Now

Do NOT prematurely add:
- microservice split
- Kafka
- Kubernetes manifests
- Saga orchestration framework
- advanced distributed patterns
- excessive DDD complexity
- unnecessary interfaces for every class