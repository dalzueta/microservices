# Etapa de construcción
FROM golang:1.18-alpine AS builder
WORKDIR /app
COPY go.mod go.sum ./
RUN go mod download
COPY . .
RUN go build -o auth-api ./cmd/main.go

# Etapa de ejecución
FROM alpine:latest
WORKDIR /root/
COPY --from=builder /app/auth-api .
EXPOSE 8080
CMD ["./auth-api"]
