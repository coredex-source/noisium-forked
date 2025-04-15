import earth.terrarium.cloche.api.target.FabricTarget
import earth.terrarium.cloche.api.target.ForgeTarget

plugins {
	id("earth.terrarium.cloche")
    id("maven-publish")
}

val mod_group: String by project
group = mod_group
val mod_version: String by project
version = mod_version

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21

    withSourcesJar()
    withJavadocJar()
}

val mod_id: String by project
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = mod_group
            artifactId = "${mod_group}.${mod_id}"
            version = mod_version

            from(components["java"])
        }
    }

    repositories {
        maven {
            val maven_organization: String by project
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/${maven_organization}/${mod_id}")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

repositories {
    // Workaround for terrarium-earth/cloche #40 ([Bug]: Windows requires mavencentral() before librariesMinecraft())
    // https://github.com/terrarium-earth/cloche/issues/40
    mavenCentral()

    cloche {
        main()
        librariesMinecraft()
        mavenFabric()
        mavenNeoforged()
        mavenForge()
    }

    maven(url = "https://maven.terraformersmc.com/")
}

cloche {
    metadata {
        modId = mod_id
        val mod_name: String by project
        name = mod_name
        val mod_description: String by project
        description = mod_description
        val mod_license: String by project
        license = mod_license
        val mod_authors: String by project
        mod_authors.split(',').forEach(::author)
        version = mod_version
    }

    mappings {
        fabricIntermediary()
    }

    targets.all {
        mixins.from(file("src/common/main/${mod_id}.mixins.json"))

        runs {
            client()
            server()
        }
    }

    val fabricCommon = common("fabric:common") {}

    targets.withType<FabricTarget> {
        dependsOn(fabricCommon)

        val fabric_loader_version: String by project
        loaderVersion = fabric_loader_version

        includedClient()
    }

    targets.withType<ForgeTarget> {
        dependencies {
            val mixin_extras_version: String by project
            val mixin_extras = module("io.github.llamalad7:mixinextras-forge:${mixin_extras_version}")
            annotationProcessor(mixin_extras)
            implementation(mixin_extras)
            include(mixin_extras)
        }
    }

    fabric("fabric:1.20") {
        minecraftVersion.set("1.20")

        dependencies {
            val fabric_api_version: String = "0.83.0+1.20"
            modRuntimeOnly("net.fabricmc.fabric-api:fabric-api:${fabric_api_version}")
            val mod_menu_version: String = "7.0.1"
            modRuntimeOnly("com.terraformersmc:modmenu:${mod_menu_version}")
        }
    }

    fabric("fabric:1.20.1") {
        minecraftVersion.set("1.20.1")

        dependencies {
            val fabric_api_version: String = "0.92.5+1.20.1"
            modRuntimeOnly("net.fabricmc.fabric-api:fabric-api:${fabric_api_version}")
            val mod_menu_version: String = "7.2.2"
            modRuntimeOnly("com.terraformersmc:modmenu:${mod_menu_version}")
        }
    }

    forge("forge:1.20") {
        minecraftVersion.set("1.20")

        loaderVersion = "46.0.14"
    }

    forge("forge:1.20.1") {
        minecraftVersion.set("1.20.1")

        loaderVersion = "47.4.0"
    }

    forge("forge:1.20.2") {
        minecraftVersion.set("1.20.2")

        loaderVersion = "48.1.0"
    }

    forge("forge:1.20.3") {
        minecraftVersion.set("1.20.3")

        loaderVersion = "49.0.2"
    }

    forge("forge:1.20.4") {
        minecraftVersion.set("1.20.4")

        loaderVersion = "49.2.0"
    }

    targets.all {
        when(minecraftVersion.get()) {
            "1.20" -> mappings { yarn("build.1") }
            "1.20.1" -> mappings { yarn("build.10") }
            "1.20.2" -> mappings { yarn("build.4") }
            "1.20.3" -> mappings { yarn("build.1") }
            "1.20.4" -> mappings { yarn("build.3") }
        }
    }
}
