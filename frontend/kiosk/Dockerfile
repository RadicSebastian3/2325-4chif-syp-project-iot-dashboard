FROM node:18.13-alpine as angular

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

FROM httpd:alpine3.15

COPY --from=angular /app/dist/kiosk/ /usr/local/apache2/htdocs/
