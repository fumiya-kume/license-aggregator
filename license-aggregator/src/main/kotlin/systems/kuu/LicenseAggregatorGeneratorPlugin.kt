package systems.kuu

import groovy.json.JsonSlurper
import org.apache.groovy.json.internal.JsonFastParser
import org.apache.groovy.json.internal.LazyMap
import org.apache.groovy.json.internal.ValueContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.diagnostics.DependencyReportTask
import org.gradle.internal.impldep.org.bouncycastle.asn1.x500.style.RFC4519Style.description
import org.gradle.internal.impldep.org.bouncycastle.asn1.x500.style.RFC4519Style.name
import org.gradle.internal.impldep.org.bouncycastle.openpgp.PGPSignatureGenerator
import org.slf4j.LoggerFactory
import java.io.File
import javax.print.attribute.standard.MediaSize.Other
import kotlin.math.log

class LicenseAggregatorGeneratorPlugin : Plugin<Project> {

    private val logger = LoggerFactory.getLogger(LicenseAggregatorGeneratorPlugin::class.java)
    override fun apply(target: Project) {
        val generateTask = target.tasks.register("generateLicenseFile") {
            runCatching {
                val buildDir = target.rootProject.layout.buildDirectory.asFile.get()
                val dependenciesJsonFile = File(buildDir, "dependencies.json").readLines().joinToString("")
                val pomOriginalList = JsonSlurper().parseText(dependenciesJsonFile) as ArrayList<LazyMap>
                val pomList = pomOriginalList.map { any ->
                    val license = JsonSlurper().parseText(any["license"].toString())
                        .run {
                            Pom.License(
                                name = any["name"].toString(),
                                url = any["url"].toString(),
                                distribution = any["distribution"].toString(),
                                comments = any["comments"].toString()
                            )
                        }
                    Pom(
                        name = any["name"].toString(),
                        groupId = any["groupId"].toString(),
                        artifactId = any["artifactId"].toString(),
                        version = any["version"].toString(),
                        description = any["description"].toString(),
                        url = any["url"].toString(),
                        licenses = license
                    )
                }
            }.getOrNull()
        }
        target.tasks.findByPath("analyzeLicenseFile")?.dependsOn(generateTask)
    }

    private fun Pom.License.toCode(): String {
        return """
        Pom.License(
            name = ${name},
            url = ${url},
            distribution = ${distribution},
            comments = ${comments}
        ),
        """
    }

    private fun Pom.toCode(): String {
        return """
        Pom(
            name = ${name},
            groupId = ${groupId},
            version = ${version},
            description = ${description},
            artifactId = ${artifactId},
            url = ${url},
            licenses = ${licenses.toCode()}
        )
        """
    }
}

