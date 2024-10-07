mvn io.quarkus:quarkus-maven-plugin:3.2.0.Final:create \
    -DprojectGroupId=com.example \
    -DprojectArtifactId=contact-aggregator \
    -DclassName="com.example.ContactResource" \
    -Dpath="/contacts" \
    -Dextensions="resteasy,resteasy-jsonb"


# Unit Testing and Mocking
# Example task: Write a test for the `getAllContacts()` method by mocking external dependencies (such as REST clients or databases) and asserting that the method behaves correctly under different conditions.

# Integration Testing with Quarkus
# Example task: Write a Quarkus integration test using RestAssured to send HTTP requests and verify that the `ContactResource` or `ContactService` is behaving correctly (e.g., returning proper HTTP status codes and responses).

# Handling and Testing API Errors
# Example task: Modify the `getAllContacts()` method to handle different HTTP error codes (e.g., 404, 500), and write corresponding tests that mock those error scenarios.

# Dependency Injection with Quarkus
# Example task: Write a test that mocks dependencies of the `ContactService` (such as REST clients or configuration values injected via `@ConfigProperty`) and asserts that the service behaves correctly with different injected configurations.

# Testing Asynchronous Code
# Example task: Modify your `ContactService` to perform asynchronous API calls and write tests to ensure that asynchronous responses are handled correctly.

# Testing with Configuration Properties
# Example task: Write tests that validate the behavior of `ContactService` when different values are injected for `kenect.api.url` and `kenect.api.token`, including missing or invalid configurations.

# Edge Case Testing
# What happens when the external API returns an empty contact list?
# How does the service handle pagination, and what if it fetches more pages than expected?
# How does it handle null or malformed responses?
# Example task: Write tests that simulate edge cases, like an empty response or pagination exceeding limits.

# Refactoring for Best Practices
# Example task: Refactor `getAllContacts()` to include retry logic in case of transient failures, and then write tests to validate the retry mechanism.

# Performance and Load Testing
# Example task: Simulate fetching a large number of contacts and verify that the service can handle it efficiently.

# Exception Handling and Custom Exceptions
# Example task: Modify `ContactService` to throw custom exceptDownlions for specific error scenarios (e.g., `InvalidContactException` for bad data), and test that these exceptions are handled correctly in the REST layer.

# curl -v -H "Authorization: Bearer J7ybt6jv6pdJ4gyQP9gNonsY" "https://k-messages-api.herokuapp.com/api/v1/contacts"
