# Search Service

![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.6-brightgreen?logo=springboot)
![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Port](https://img.shields.io/badge/port-8087-blue)
![Elasticsearch](https://img.shields.io/badge/Elasticsearch-8.x-005571?logo=elasticsearch)
![Kafka](https://img.shields.io/badge/Kafka-consumer-231F20?logo=apachekafka)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

Elasticsearch-powered full-text search microservice for vacancies and resumes. Read-only — all write operations happen via Kafka consumers.

## Table of Contents

- [Overview](#overview)
- [API Endpoints](#api-endpoints)
- [Query Parameters](#query-parameters)
- [Response Format](#response-format)
- [Index Population](#index-population)
- [Configuration](#configuration)
- [Running Locally](#running-locally)

## Overview

| Property | Value |
|---|---|
| Port | **8087** |
| Base paths | `/api/search/vacancies`, `/api/search/resumes` |
| Storage | Elasticsearch 8.x |
| Swagger UI | `http://localhost:8087/swagger-ui.html` |
| OpenAPI JSON | `http://localhost:8087/v3/api-docs` |
| Prometheus | `http://localhost:8087/actuator/prometheus` |

All endpoints are **public** — no authentication required.

## API Endpoints

| Method | Path | Description |
|---|---|---|
| `GET` | `/api/search/vacancies` | Search vacancies |
| `GET` | `/api/search/resumes` | Search resumes |

## Query Parameters

### GET /api/search/vacancies

| Parameter | Type | Description |
|---|---|---|
| `query` | String | Free-text fuzzy search over title, description, companyName |
| `skills` | Set\<String\> | AND logic — all skills must be present |
| `location` | String | Exact location match |
| `salaryMin` | Double | Minimum salary (inclusive) |
| `salaryMax` | Double | Maximum salary (inclusive) |
| `page` | int | Page number (0-based, default: 0) |
| `size` | int | Page size (1–100, default: 10) |

### GET /api/search/resumes

| Parameter | Type | Description |
|---|---|---|
| `query` | String | Free-text fuzzy search over title, summary, institutions |
| `skills` | Set\<String\> | AND logic — all skills must be present |
| `experienceYearsMin` | Integer | Minimum years of experience (inclusive) |
| `experienceYearsMax` | Integer | Maximum years of experience (inclusive) |
| `page` | int | Page number (0-based, default: 0) |
| `size` | int | Page size (1–100, default: 10) |

## Response Format

Both endpoints return a `PageResponse<T>`:

```json
{
  "content": [...],
  "totalElements": 42,
  "totalPages": 5,
  "currentPage": 0,
  "pageSize": 10,
  "hasNext": true,
  "hasPrevious": false
}
```

### VacancySearchResponse fields

`id`, `title`, `description`, `companyName`, `location`, `salary`, `skills` (Set\<String\>), `createdAt`

### ResumeSearchResponse fields

`id`, `title`, `summary`, `experienceYears`, `skills` (Set\<String\>), `institutions` (Set\<String\>)

## Index Population

Indexes are maintained by consuming Kafka topics:

| Kafka Topic | Published by | Index |
|---|---|---|
| `indexing-vacancy` | VacancyService | `vacancies` |
| `indexing-resume` | ResumeService | `resumes` |

Trigger full reindex via:
- `POST /api/vacancies/reindex` (VacancyService)
- `POST /api/resumes/reindex` (ResumeService)

## Configuration

| Property | Default | Description |
|---|---|---|
| `server.port` | `8087` | HTTP port |
| `spring.elasticsearch.uris` | `http://localhost:9200` | Elasticsearch cluster URL |
| `spring.kafka.bootstrap-servers` | `localhost:9092` | Kafka brokers |

## Running Locally

```bash
./gradlew bootRun
```

Requires Elasticsearch 8.x on port 9200 and Kafka on port 9092.
