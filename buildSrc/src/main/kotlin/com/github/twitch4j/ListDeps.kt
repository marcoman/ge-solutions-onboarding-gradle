package com.github.twitch4j

import org.gradle.api.Project
import org.gradle.api.artifacts.ResolvedDependency
import org.gradle.api.tasks.PathSensitivity

fun Project.configureListDependencies() {
    task("listDependencies") {
        doLast {
            val configurationDependencies = this@configureListDependencies
                .getConfigurationsDependencies()
                .getDependenciesPathsMap()
            configurationDependencies.forEach { (configuration, dependencies) ->
                if (dependencies.isNotEmpty()) {
                    logger.lifecycle("$project:$configuration -> Dependencies:$dependencies")
                }
            }
        }
    }
}

fun Project.configureDependencyReport() {
    val dependencyReport = task("dependencyReport") {
        group = "documentation"
        val report = rootProject.layout.buildDirectory.file("reports/dependencies/dependencyReport.md")
        val buildGradleFiles = rootProject.fileTree(rootDir).also {
            it.include("**/*.gradle")
            it.include("**/*.gradle.kts")
        }.files.map { it.toRelativeString(projectDir) }
        inputs.property("project", project.toString())
        inputs.files(buildGradleFiles).withPropertyName("buildGradleFiles")
            .withPathSensitivity(PathSensitivity.RELATIVE)
        outputs.files(report).withPropertyName("dependencyReport")
        outputs.cacheIf { true }

        doLast {
            val configurationDependencies = this@configureDependencyReport
                .getConfigurationsDependencies()
                .getDependenciesPathsMap()

            val dependenciesReport = configurationDependencies.map { (configuration, dependencies) ->
                "## $configuration -> Dependencies:$dependencies"
            }.fold("# Dependencies for $project") { acc, configurationReport ->
                acc + "\n\n" + configurationReport
            }
            report.get().asFile.writeText(dependenciesReport)
        }
    }
    afterEvaluate {
        // check task is not available yet, which is why we use afterEvaluate
        project.tasks.named("check").configure {
            dependsOn(dependencyReport)
        }
    }
}

private fun Project.getConfigurationsDependencies(): Map<String, Set<ResolvedDependency>> {
    return configurations.filter { it.isCanBeResolved }.associate { configuration ->
        configuration.name to configuration.resolvedConfiguration.firstLevelModuleDependencies
    }
}

private fun Map<String, Set<ResolvedDependency>>.getDependenciesPathsMap(): Map<String, String> {
    return this.mapValues {
        it.value.sortedBy { it.name }.fold("") { acc, dep ->
            "$acc\n\t${dep.name}"
        }
    }
}
