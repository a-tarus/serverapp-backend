package net.network.server.enumeration;

public enum Status {
    SERVER_UP("SERVER UP"),
    SERVER_DOWN("SERVER DOWN");
    private final String status;

    Status(String status) {
        this.status = status;
    }

    private String getStatus() {
        return this.status;
    }
}