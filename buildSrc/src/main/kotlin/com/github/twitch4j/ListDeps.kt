package com.github.twitch4j

import org.gradle.api.Project

fun Project.configureListDependencies() {
    task("listDependencies") {
        val configurationDependencies = mutableMapOf<String, String>()
        this@configureListDependencies.configurations.forEach { configuration ->
            if (configuration.isCanBeResolved) {
                configurationDependencies[configuration.name] = configuration.resolve().fold("") { acc, file ->
                    "$acc\n\t${file.absolutePath}"
                }
            }
        }
        doLast {
            configurationDependencies.forEach { (configuration, dependencies) ->
                if (dependencies.isNotEmpty()) {
                    logger.lifecycle("$project:$configuration -> Dependencies:$dependencies")
                }
            }
        }
    }
}
