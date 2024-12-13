FROM node:latest

# Set the working directory
WORKDIR /app

# Copy the script into the container
COPY ./commands.sh /app/commands.sh

# Ensure the script is executable
RUN chmod +x /app/commands.sh

# Set the script as the entrypoint
ENTRYPOINT ["sh", "/app/commands.sh"]