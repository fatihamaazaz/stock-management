FROM node:latest

# Set the working directory
WORKDIR /app

# Copy the script into the container
COPY ./commandes.sh /app/commandes.sh

# Ensure the script is executable
RUN chmod +x /app/commandes.sh

# Set the script as the entrypoint
ENTRYPOINT ["sh", "/app/commandes.sh"]