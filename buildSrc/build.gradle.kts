plugins {
	`kotlin-dsl`
}

kotlin {
	jvmToolchain {
		languageVersion.set(JavaLanguageVersion.of(11))
	}
}

repositories {
	mavenCentral()
}
