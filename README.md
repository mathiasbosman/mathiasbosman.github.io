# Mathias Bosman - CV
![CI](https://github.com/mathiasbosman/cv/workflows/CI/badge.svg)

A simple website with a bit of Github automatisation magic containing my personal resume
on [mathiasbosman.be](http://mathiasbosman.be).

## Building

The website uses some a Spring Boot backend and React.js frontend.

To install just run

```bash
mvn clean install
```

This will build both back- and frontend after which you can start the application and visit the
website at http://localhost:8081 (mind the port here)

## Local development

### OAuth2

For local development it is required to copy and adjust the [properties file][application_props] for
Spring Boot in a seperate profile. The application uses OAuth2 for authorization which (sadly) cannot be disabled via a
simple property. So make sure you edit properties as below:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          github:
            clientId: someClientId
            clientSecret: someClientSecret
            redirectUri: "{baseUrl}/oauth2/callback/{registrationId}"
```

On the provider side make sure that the callback Url goes to:
```text
yourHost.com/oauth2/callback/PROVIDER_ID
```
In the example of GitHub:
```text
yourHost.com/oauth2/callback/github
```

Locally add the port:
```text
http://localhost:8081/oauth2/callback/github
```

### React.js

Developping the frontend is as simple as running

```bash
npm start
```

in the "webapp" folder while the backend application is running. By default, the requests will be proxied to the backend.
See the [package.json][package_json] file for the proxy setting.

## Publishing
The application is published to Google Cloud Platform via the maven command when a new release is published on GitHub.
See [the GitHub workflow][publish_workflow].

[publish_workflow]:.github/workflows/publish.yml
[package_json]:src/main/webapp/package.json
[application_props]:src/main/resources/application.yml