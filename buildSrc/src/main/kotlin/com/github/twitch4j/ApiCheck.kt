package com.github.twitch4j

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.tasks.JavaExec

val API_BASE_FILE = "api.base"

/**
 * Adapted from ktlint
 */
fun Project.configureMetalava() {
	val checkProvider = tasks.register("checkApi", JavaExec::class.java) {
		configureCommonMetalavaArgs(this@configureMetalava)
		description = "Check API compatibility."
		group = "Verification"
		args = listOf("--check-compatibility:api:released", API_BASE_FILE) + args
	}

	afterEvaluate {
		// check task is not available yet, which is why we use afterEvaluate
		project.tasks.named("check").configure {
			dependsOn(checkProvider)
		}
	}

	tasks.register("updateApi", JavaExec::class.java) {
		configureCommonMetalavaArgs(this@configureMetalava)
		description = "Update API base file."
		group = "formatting"
		args = listOf("--api", API_BASE_FILE) + args
	}
}

/**
 * Configures common Metalava parameters
 */
private fun JavaExec.configureCommonMetalavaArgs(
	project: Project
) {
	val jdkHome = org.gradle.internal.jvm.Jvm.current().getJavaHome().absolutePath
	val compileClasspath = project.getCompileClasspath()
	val apiFiles = project.fileTree(project.projectDir).also {
		it.include("**/*.kt")
		it.include("**/*.java")
		it.exclude("**/testData/**")
		it.exclude("**/build/**")
		it.exclude("**/.*/**")
	}
	inputs.files(apiFiles)
	classpath = project.getMetalavaConfiguration()
	mainClass.set("com.android.tools.metalava.Driver")
	args = listOf(
		"--jdk-home", jdkHome,
		"--classpath", compileClasspath,
		"--source-files",
	) + apiFiles.files.map { it.absolutePath }
}

private fun Project.getCompileClasspath(): String =
	configurations.findByName("compileClasspath")!!.files.map { it.absolutePath }.joinToString(":")

private fun Project.getMetalavaConfiguration(): Configuration {
	return configurations.findByName("metalava") ?: configurations.create("metalava") {
		val dependency = this@getMetalavaConfiguration.dependencies.create("com.android.tools.metalava:metalava:1.0.0-alpha04")
		dependencies.add(dependency)
	}
}

