package dev.am.nplus1.try_with_resource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomResourceMain {
    static void main() throws Exception {
        try (var dbConnection = new CustomDBConnection();
            var resource = new CustomResource("A");
            var resource2 = new CustomResource("B")) {
            dbConnection.executeQuery("SELECT * FROM customers");
            IO.println();
            resource.doSomething();
            resource2.doSomething();
        } catch (Exception e) {
            log.error("Error occurred", e);
        }
    }
}
