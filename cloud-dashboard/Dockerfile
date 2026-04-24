FROM node:20-alpine AS deps
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm ci

FROM node:20-alpine AS builder
WORKDIR /app
COPY --from=deps /app/node_modules ./node_modules
COPY . .
# ИСПРАВЛЕНО: передаём GATEWAY_URL во время сборки чтобы next.config.ts мог его использовать
ARG GATEWAY_URL=http://gateway:8072
ENV GATEWAY_URL=${GATEWAY_URL}
RUN npm run build

FROM node:20-alpine AS runner
WORKDIR /app
ENV NODE_ENV=production
# ИСПРАВЛЕНО: передаём GATEWAY_URL в runtime контейнер
ARG GATEWAY_URL=http://gateway:8072
ENV GATEWAY_URL=${GATEWAY_URL}
COPY --from=deps /app/node_modules ./node_modules
COPY --from=builder /app/.next ./.next
COPY --from=builder /app/public ./public
COPY --from=builder /app/package.json ./package.json
COPY --from=builder /app/next.config.ts ./next.config.ts
EXPOSE 3000
CMD ["npm", "start"]