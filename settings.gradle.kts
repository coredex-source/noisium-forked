val mod_id: String by settings
rootProject.name = mod_id

pluginManagement {
	repositories {
        mavenCentral()
        gradlePluginPortal()

        maven(url = "https://maven.msrandom.net/repository/cloche/")
	}

	val kotlin_jvm_plugin_version: String by settings
	val auto_include_plugin_version: String by settings
	val cloche_plugin_version: String by settings
	plugins {
		kotlin("jvm").version(kotlin_jvm_plugin_version)
    	id("com.pablisco.gradle.auto.include").version(auto_include_plugin_version)
		id("earth.terrarium.cloche").version(cloche_plugin_version).apply(false)
	}
}

// Gradle modules are automatically included using com.pablisco.gradle.auto.include
// See https://github.com/pablisco/auto-include/
