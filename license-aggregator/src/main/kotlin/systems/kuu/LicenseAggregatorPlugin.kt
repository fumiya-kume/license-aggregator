package systems.kuu

import groovy.json.JsonBuilder
import groovy.util.Node
import groovy.util.NodeList
import groovy.xml.XmlParser
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.diagnostics.DependencyReportTask
import org.slf4j.LoggerFactory
import java.io.File

class LicenseAggregatorPlugin : Plugin<Project> {

    private val logger = LoggerFactory.getLogger(DependencyReportTask::class.java)

    override fun apply(target: Project) {
        val generateTask = target.tasks.register("generateLicenseFile") {
            val dependencies = target.configurations
                .map {
                    runCatching {
                        it.copyRecursive().apply {
                            it.isCanBeResolved = true
                        }.resolvedConfiguration.lenientConfiguration.artifacts
                    }
                        .getOrNull()
                }
                .filterNotNull()
                .flatten()
                .map {
                    val dependency = it.run {
                        target.dependencies.create("${moduleVersion.id.group}:${moduleVersion.id.name}:${moduleVersion.id.version}@pom")
                    }
                    target.configurations.detachedConfiguration(dependency)
                }
                .map {
                    it.resolve().toList().first()
                }.map {
                    val xml = XmlParser().parse(it)
                    val groupId = xml.getValue("groupId")
                    val artifactId = xml.getValue("artifactId")
                    val version = xml.getValue("version")
                    val name = xml.getValue("name")
                    val description = xml.getValue("description")
                    val url = xml.getValue("url")
                    val license = xml.getAsNode("licenses")?.getAsNode("license").let { node ->
                        Pom.License(
                            name = node?.getValue("name"),
                            url = node?.getValue("url"),
                            distribution = node?.getValue("distribution"),
                            comments = node?.getValue("comments")
                        )

                    }

                    Pom(
                        name = name,
                        groupId = groupId,
                        artifactId = artifactId,
                        version = version,
                        description = description,
                        url = url,
                        licenses = license
                    )
                }
            val buildDir = target.rootProject.layout.buildDirectory.asFile.get()
            buildDir.mkdir()
            val outputFile = File(buildDir, "dependencies.json")
            outputFile.writeText(JsonBuilder(dependencies).toPrettyString())
        }

        target.tasks.findByPath("build")?.dependsOn(generateTask)
        target.tasks.findByPath("kspKotlin")?.dependsOn(generateTask)
    }
}

fun Node.getValue(name: String): String? {
    return getAsNode(name)?.text()
}

fun Node.getAsNode(name: String): Node? {
    val target = get(name)
    return if (target is NodeList) {
        target.firstOrNull() as Node?
    } else {
        target as Node
    }
}

