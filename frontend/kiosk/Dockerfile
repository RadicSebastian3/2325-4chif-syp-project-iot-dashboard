FROM node:18.17.1-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:stable
COPY --from=build /app/dist/kiosk/ /usr/share/nginx/html
EXPOSE 80
