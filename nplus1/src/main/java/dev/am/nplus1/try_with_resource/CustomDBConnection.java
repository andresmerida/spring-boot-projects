package dev.am.nplus1.try_with_resource;

public class CustomDBConnection implements AutoCloseable {
    private boolean isConnected;

    public CustomDBConnection() {
        isConnected = true;
        IO.println("Connection opened");
    }

    public void executeQuery(String query) {
        if (!isConnected) {
            throw new IllegalStateException("Connection is closed");
        }
        IO.println("Executing query: " + query);
    }

    @Override
    public void close() throws Exception {
        isConnected = false;
        IO.println("Connection closed.");
    }
}
