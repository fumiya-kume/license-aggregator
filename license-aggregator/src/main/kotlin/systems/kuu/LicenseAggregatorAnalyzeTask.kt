package systems.kuu

import groovy.json.JsonBuilder
import groovy.util.Node
import groovy.util.NodeList
import groovy.xml.XmlParser
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class LicenseAggregatorAnalyzeTask : DefaultTask() {
    @TaskAction
    fun action(){
        val dependencies = project.configurations
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
                    project.dependencies.create("${moduleVersion.id.group}:${moduleVersion.id.name}:${moduleVersion.id.version}@pom")
                }
                project.configurations.detachedConfiguration(dependency)
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
        val buildDir = File(project.rootProject.layout.buildDirectory.asFile.get(),"/generated")
        buildDir.mkdirs()
        val outputFile = File(buildDir, "dependencies.json")
        outputFile.writeText(JsonBuilder(dependencies).toPrettyString())
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
}