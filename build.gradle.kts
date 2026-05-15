plugins {
    id("java")
    application
}

group = "kz.kbtu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
application {
    mainClass.set("kz.kbtu.Main")
}


val umlDoclet: Configuration by configurations.creating



dependencies {
    umlDoclet("nl.talsmasoftware:umldoclet:2.3.0")

    implementation("at.favre.lib:bcrypt:0.10.2")
    implementation("com.jcabi:jcabi-jdbc:0.19.0")

    implementation("org.xerial:sqlite-jdbc:3.53.1.0")

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
configurations {
    umlDoclet
}

tasks.javadoc {

    source = sourceSets.main.get().allJava
    val docletOptions = options as StandardJavadocDocletOptions
    docletOptions.docletpath = umlDoclet.files.toList()
    docletOptions.doclet = "nl.talsmasoftware.umldoclet.UMLDoclet"
//    docletOptions.addStringOption("additionalParamName", "additionalParamValue")
}

tasks.test {
    useJUnitPlatform()
}