FROM node:18-alpine AS builder

WORKDIR /app

COPY . .

RUN npm install && npm run build

FROM node:18-alpine

WORKDIR /app

COPY --from=builder /app ./

RUN npm install -g serve

CMD ["serve", "-s", "dist", "-l", "8081"]
