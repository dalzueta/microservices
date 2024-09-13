# Usamos una imagen base de Node.js
FROM node:16-alpine

# Creamos un directorio de trabajo
WORKDIR /app

# Copiamos los archivos del proyecto
COPY package*.json ./
COPY . .

# Instalamos dependencias
RUN npm install

# Construimos la aplicación
RUN npm run build

# Exponemos el puerto
EXPOSE 3000

# Iniciamos la aplicación
CMD ["npm", "start"]
