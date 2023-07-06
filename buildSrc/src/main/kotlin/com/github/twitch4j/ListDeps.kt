package com.github.twitch4j

import org.gradle.api.Project
import java.io.File

fun Project.configureListDependencies() {
    task("listDependencies") {
        val configurationDependencies = project.getConfigurationsDependencies().getDependenciesPathsMap()
        doLast {
            configurationDependencies.forEach { (configuration, dependencies) ->
                if (dependencies.isNotEmpty()) {
                    logger.lifecycle("$project:$configuration -> Dependencies:$dependencies")
                }
            }
        }
    }
}

fun Project.configureDependencyReport() {
    task("dependencyReport") {
        group = "documentation"
        val report = rootProject.layout.buildDirectory.file("reports/dependencies/dependencyReport.md")
        inputs.files(configurations.filter { it.isCanBeResolved }).withPropertyName("dependencyReportInputs")
        outputs.files(report).withPropertyName("dependencyReport")
        outputs.cacheIf { true }

        doLast {
            val configurationDependencies = project.getConfigurationsDependencies().getDependenciesPathsMap()
            val dependenciesReport = configurationDependencies.map { (configuration, dependencies) ->
                "## $configuration -> Dependencies:$dependencies"
            }.fold("# Dependencies for $project") { acc, configurationReport ->
                acc + "\n\n" + configurationReport
            }
            report.get().asFile.writeText(dependenciesReport)
        }
    }
}

private fun Project.getConfigurationsDependencies(): Map<String, Set<File>> {
    return configurations.filter { it.isCanBeResolved }.associate { it.name to it.resolve() }
}

private fun Map<String, Set<File>>.getDependenciesPathsMap(): Map<String, String> {
    return this.mapValues {
        it.value.fold("") { acc, file ->
            "$acc\n\t${file.absolutePath}"
        }
    }
}
