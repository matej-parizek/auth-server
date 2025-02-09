# auth-server
Auth Server

This project is built using Ktor and leverages Koin for dependency injection and Kotest for testing. Argon2 is used for password hashing to enhance security. It was created using the [Ktor Project Generator](https://start.ktor.io).

Here are some useful links to get you started:

- [Ktor Documentation](https://ktor.io/docs/home.html)
- [Ktor GitHub page](https://github.com/ktorio/ktor)
- The [Ktor Slack chat](https://app.slack.com/client/T09229ZC6/C0A974TJ9). You'll need
  to [request an invite](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up) to join.

## Features

Here's a list of features included in this project:

| Name                                                                   | Description                                                                        |
|------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| [Routing](https://start.ktor.io/p/routing)                             | Provides a structured routing DSL                                                  |
| [Authentication](https://start.ktor.io/p/auth)                         | Provides extension points for handling the Authorization header                    |
| [Authentication JWT](https://start.ktor.io/p/auth-jwt)                 | Handles JSON Web Token (JWT) bearer authentication scheme                          |
| [Content Negotiation](https://start.ktor.io/p/content-negotiation)     | Provides automatic content conversion based on Content-Type and Accept headers     |
| [kotlinx.serialization](https://start.ktor.io/p/kotlinx-serialization) | Facilitates JSON serialization using the kotlinx.serialization library             |
| [Dependency Injection with Koin](https://insert-link-here)             | Manages dependencies using the Koin library                                        |
| [Testing with Kotest](https://insert-link-here)                        | Implements unit and integration tests using the Kotest framework                   |
| [Password Hashing with Argon2](https://insert-link-here)               | Securely hashes passwords using the Argon2 algorithm                               |

## Building & Running

To build or run the project, use one of the following tasks:

| Task                          | Description                                                          |
|-------------------------------|----------------------------------------------------------------------|
| `./gradlew test`              | Run the tests using Kotest                                           |
| `./gradlew build`             | Build the application including all tests                            |
| `buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `buildImage`                  | Build the Docker image to use with the fat JAR                       |
| `publishImageToLocalRegistry`    | Publish the Docker image locally                                     |
| `run`                         | Run the server                                                       |
| `runDocker`                   | Run using the local Docker image                                     |

If the server starts successfully, you'll see the following output:

