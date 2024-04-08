plugins {
    java
}

group = "alantheknight.lab6"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:32.1.1-jre")
    implementation("org.ini4j:ini4j:0.5.4")
    implementation("org.apache.commons:commons-lang3:3.14.0")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}