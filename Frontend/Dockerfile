FROM node:18-alpine as build

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
ENTRYPOINT  npm run dev

EXPOSE 5173