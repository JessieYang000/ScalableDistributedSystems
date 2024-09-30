# Stage 1: Compile the Java code
FROM bellsoft/liberica-openjdk-alpine-musl:11 AS build
# Set the working directory
WORKDIR /usr/src/myapp
# Copy the source files
COPY . .
# Compile the Java code
RUN javac server/*.java client/*.java

# Stage 2: Run the compiled Java program
FROM bellsoft/liberica-openjdk-alpine-musl:11
# Set the working directory
WORKDIR /usr/app
# Copy the compiled files from the build stage
COPY --from=build /usr/src/myapp /usr/app
# Expose TCP and UDP ports
EXPOSE 1111/tcp 5555/udp

# Define the default command for the server. For TCP and UDP.
CMD ["java", "server.ServerApp", "1111", "5555"]