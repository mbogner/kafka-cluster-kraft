buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

plugins {
    // https://github.com/JetBrains/kotlin/releases
    val versionKotlinPlugins = "1.7.20"
    // https://spring.io/projects/spring-boot
    id("org.springframework.boot") version "2.7.5"
    // https://plugins.gradle.org/plugin/io.spring.dependency-management
    id("io.spring.dependency-management") version "1.1.0"

    kotlin("jvm") version versionKotlinPlugins
    kotlin("plugin.spring") version versionKotlinPlugins
    kotlin("plugin.noarg") version versionKotlinPlugins
    kotlin("kapt") version versionKotlinPlugins
}
val javaVersion = JavaVersion.VERSION_17

repositories {
    mavenLocal()
    mavenCentral()
    google()
}

the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
    resolutionStrategy {
        cacheChangingModulesFor(0, "seconds")
    }
    dependencies {

    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.kafka:spring-kafka")
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = javaVersion.toString()
        targetCompatibility = javaVersion.toString()
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = javaVersion.toString()
        }
    }

    withType<JavaCompile> {
        options.isIncremental = true
    }

    withType<Test> {
        useJUnitPlatform()
    }

    withType<JavaCompile> {
        options.isIncremental = true
    }

    withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
        archiveFileName.set("${project.name}.jar")
    }

    withType<Copy> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    withType<Wrapper> {
        // https://gradle.org/releases/
        gradleVersion = "7.5.1"
        distributionType = Wrapper.DistributionType.BIN
    }
}
