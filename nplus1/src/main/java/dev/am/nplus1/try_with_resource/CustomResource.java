package dev.am.nplus1.try_with_resource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomResource implements AutoCloseable {
    private final String resourceName;
    private boolean isClosed = false;

    public CustomResource(String resourceName) {
        this.resourceName = resourceName;
    }

    public void doSomething() {
        if (isClosed) {
            throw new IllegalStateException("Resource is closed");
        }
        log.info("Doing something with {}", resourceName);
    }

    @Override
    public void close() throws Exception {
        isClosed = true;
        log.info("Resource {} closed.", resourceName);
    }
}
