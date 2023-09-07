import groovy.namespace.QName
import groovy.xml.XmlParser
import org.gradle.api.Plugin
import org.gradle.api.Project

class LicenseAggregatorPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val dependencies = target.configurations.map {
            it.resolvedConfiguration.lenientConfiguration.allModuleDependencies
        }.flatten()

        val dependencyFileList = dependencies
            .map {
                it.allModuleArtifacts
            }
            .flatten()
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


    }
}

data class Pom(
    val name: String,
    val url: String,
    val licenses: List<Pair<String, String>>
)