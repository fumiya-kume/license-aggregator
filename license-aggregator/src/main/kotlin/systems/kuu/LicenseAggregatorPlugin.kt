package systems.kuu

import groovy.json.JsonBuilder
import groovy.namespace.QName
import groovy.xml.XmlParser
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ResolvedArtifact
import org.gradle.api.artifacts.ResolvedDependency
import java.io.File

class LicenseAggregatorPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.task("generateLicenseFile") {
            val dependencies:List<ResolvedArtifact> = target.configurations
                .filter { it.isCanBeResolved }
                .map {
//                val lenientConfiguration = it.resolvedConfiguration.lenientConfiguration
                it.resolvedConfiguration.resolvedArtifacts

//                val lenientConfiguration = it.resolvedConfiguration.lenientConfiguration
//                lenientConfiguration.allModuleDependencies
            }.flatten()
            return@task

            val output = dependencies
//                .map {
//                    it.allModuleArtifacts
//                }
//                .flatten()
                .map {
                    val moduleVersionid = it.moduleVersion.id
                    target.dependencies.create("${moduleVersionid.group}:${moduleVersionid.name}:${moduleVersionid.version}@pom")
                        .run {
                            target.configurations.detachedConfiguration(this).resolve()
                        }
                }
                .flatten()
                .map { it ->
                    val xml = XmlParser().parse(it)
                    val name = xml.get("name").toString()
                    val url = xml.get("url").toString()
                    val licenses = xml.getAt(QName("licenses")).map { licenseNode ->
                        val node = licenseNode as groovy.util.Node
                        node.get("name").toString() to node.get("url").toString()
                    }
                    Pom(
                        name = name,
                        url = url,
                        licenses = licenses
                    )
                }

            val directly = File(target.buildDir, "generated")
            directly.mkdir()
//            val outputFile = File(directly, "dependencies.json")
//            outputFile.writeText(JsonBuilder(output).toPrettyString())
        }
    }
}


data class Pom(
    val name: String,
    val url: String,
    val licenses: List<Pair<String, String>>
)