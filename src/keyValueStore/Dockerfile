# Stage 1: Compile the Java code
FROM bellsoft/liberica-openjdk-alpine-musl:11 AS build
WORKDIR /usr/src/myapp
COPY . .
# Compile the Java files in the correct package
RUN javac keyValueStore/server/*.java keyValueStore/client/*.java

# Stage 2: Run the compiled Java program
FROM bellsoft/liberica-openjdk-alpine-musl:11
WORKDIR /usr/app
COPY --from=build /usr/src/myapp /usr/app
EXPOSE 1111/tcp
EXPOSE 5555/udp
# Use the fully qualified name for the ServerApp class
CMD ["java", "keyValueStore.server.ServerApp", "1111", "5555"]
