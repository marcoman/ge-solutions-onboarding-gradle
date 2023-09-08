// Step 1 asks us to apply the Gradle Build scan plugin
// Step 1 asks us to apply the custom user data gradle plugin
plugins {
     id("com.gradle.enterprise") version("3.14.1")
     id("com.gradle.common-custom-user-data-gradle-plugin") version("1.11.1")
}

// Step 1 asks us to set a root project name
rootProject.name = "ge-solutions-onboarding-gradle"


include(
    ":common",
    ":auth",
    ":client-websocket",
    ":chat",
    ":eventsub-common",
    ":rest-extensions",
    ":rest-helix",
    ":rest-kraken",
    ":rest-tmi",
    ":pubsub",
    ":graphql",
    ":util",
    ":twitch4j",
    ":kotlin",
)

project(":common").name = "twitch4j-common"
project(":client-websocket").name = "twitch4j-client-websocket"
project(":auth").name = "twitch4j-auth"
project(":chat").name = "twitch4j-chat"
project(":eventsub-common").name = "twitch4j-eventsub-common"
project(":rest-extensions").name = "twitch4j-extensions"
project(":rest-helix").name = "twitch4j-helix"
project(":rest-kraken").name = "twitch4j-kraken"
project(":rest-tmi").name = "twitch4j-messaginginterface"
project(":pubsub").name = "twitch4j-pubsub"
project(":graphql").name = "twitch4j-graphql"
project(":util").name = "twitch4j-util"
project(":twitch4j").name = "twitch4j"
project(":kotlin").name = "twitch4j-kotlin"

gradleEnterprise {
    // Step 1 asks us to point to our gradle server.   Your server may be different.
    // Plain-old http, so no self-signed certificate.  Instead, use an untrusted server.
    server = "http://ec2-44-203-143-70.compute-1.amazonaws.com"
    allowUntrustedServer = true

    // see https://docs.gradle.com/enterprise/gradle-plugin/#publishing_every_build
    buildScan {
        // Step 1 asks to always publish build scans.  This value does not change as it allows us to see the results
        publishAlways()

        // see https://docs.gradle.com/enterprise/gradle-plugin/#capturing_task_input_files
        // for plugin >= 3.7
        // Step 1 asks to enable the capture of task input files for CI and local builds.
        capture {
            isTaskInputFiles = true
        }

        // see https://docs.gradle.com/enterprise/gradle-plugin/#disabling_programmatically
        // Step 1 asks us to disable build scan background upload for all CI builds
        isUploadInBackground = true
//        isUploadInBackground = System.genenv("CI") == null
    }
}

buildCache {
    local {
        isEnabled = true
    }

    remote<HttpBuildCache> {
        isEnabled = true
        isPush = System.getenv("BUILD_URL") != null
        isAllowUntrustedServer = true
        isAllowInsecureProtocol = true
        url = uri("http://ec2-44-203-143-70.compute-1.amazonaws.com/cache/exp4/")
    }
}

