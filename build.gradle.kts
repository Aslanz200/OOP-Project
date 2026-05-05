plugins {
    id("java")
}

group = "kz.kbtu"
version = "1.0-SNAPSHOT"
repositories {
    mavenCentral()
}
val umlDoclet: Configuration by configurations.creating

dependencies {
    umlDoclet("nl.talsmasoftware:umldoclet:2.3.0")
    implementation("at.favre.lib:bcrypt:0.10.2")
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