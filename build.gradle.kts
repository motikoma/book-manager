import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("com.arenagod.gradle.MybatisGenerator") version "1.4"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"

	// for OpenAPI
	id("org.openapi.generator") version "5.3.0"

}

group = "com.book.manager"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.0")
	implementation("org.mybatis.dynamic-sql:mybatis-dynamic-sql:1.2.1")
	implementation("mysql:mysql-connector-java:8.0.23")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.session:spring-session-data-redis")
	implementation("redis.clients:jedis")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	mybatisGenerator("org.mybatis.generator:mybatis-generator-core:1.4.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.1")
	testImplementation("org.assertj:assertj-core:3.19.0")
	testImplementation("org.mockito:mockito-core:3.8.0")
	testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register("moveOpenApiGeneratedCode") {

	val controllerPath = "src/main/kotlin/com/book/manager/presentation/generated/controller"
	val modelPath = "src/main/kotlin/com/book/manager/presentation/generated/model"

	doLast {
		// controller
		copy {
			from("$buildDir/generated/src/main/kotlin/com/book/manager/controller")
			into(controllerPath)
		}

		// model
		copy {
			from("$buildDir/generated/src/main/kotlin/com/book/manager/model")
			into(modelPath)
		}
	}
}


// Builds a Kotlin Server by OpenAPI
tasks.withType<org.openapitools.generator.gradle.plugin.tasks.GenerateTask> {
	generatorName.set("kotlin-spring")
	inputSpec.set("$rootDir/openapi/api.yaml")
	outputDir.set("$buildDir/generated")
	apiPackage.set("com.book.manager.controller")
	invokerPackage.set("com.book.manager.invoker")
	modelPackage.set("com.book.manager.model")
	configOptions.set(mapOf(
		"serviceInterface" to "true",
		"serializableModel" to "true",
		"useBeanValidation" to "false",
		"modelMutable" to "true",
		"interfaceOnly" to "false"
	))
	globalProperties.set(mapOf("modelDocs" to "false"))
	skipValidateSpec.set(true)
	logToStderr.set(true)
	generateAliasAsModel.set(false)
	// set to true and set environment variable {LANG}_POST_PROCESS_FILE
	// (e.g. SCALA_POST_PROCESS_FILE) to the linter/formatter to be processed.
	// This command will be passed one file at a time for most supported post processors.
	enablePostProcessFile.set(false)
	finalizedBy("moveOpenApiGeneratedCode")

	outputs.upToDateWhen { false }
	outputs.cacheIf { false }
}

mybatisGenerator {
	verbose = true
	configFile = "${projectDir}/src/main/resources/generatorConfig.xml"
}
