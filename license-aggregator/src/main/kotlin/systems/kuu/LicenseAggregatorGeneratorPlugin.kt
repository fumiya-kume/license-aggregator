package systems.kuu

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import groovy.json.JsonSlurper
import org.apache.groovy.json.internal.LazyMap
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.plugins.ide.idea.model.IdeaModel
import org.slf4j.LoggerFactory
import java.io.File

class LicenseAggregatorGeneratorPlugin : Plugin<Project> {

    private val logger = LoggerFactory.getLogger(LicenseAggregatorGeneratorPlugin::class.java)
    override fun apply(target: Project) {
        val generateTask = target.tasks.register("generateLicenseFile") {
            runCatching {
                val buildDir = target.rootProject.layout.buildDirectory.asFile.get()
                val dependenciesJsonFile = File(buildDir, "/generated/res/raw/dependencies.json").readLines().joinToString("")
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
//                val generateDir = "${target.buildDir}/generated/sources/main/kotlin/systems.kuu"
                val generateDir = "${target.buildDir}/generated/sources/main"

                target.layout.buildDirectory.dir("$generateDir/generated/res/raw")

                val ideaModel = target.extensions.findByType(IdeaModel::class.java)
                val generatedSourceDirs = ideaModel?.module?.generatedSourceDirs
                ideaModel?.module?.generatedSourceDirs = generatedSourceDirs.apply {
                    this?.add(target.file(generateDir))
                }

                val targetFolder = File("$generateDir/kotlin/systems.kuu").apply {
                    this.mkdirs()
                }

                val file = File(targetFolder.absolutePath, "LicenseInformation.kt")
//                with(target){
//                    android {
//                        this.libraryVariants.map {variant ->
//                            val files = target.files(targetFolder).builtBy("generateLicenseFile")
//                            variant.registerGeneratedResFolders (files)
//                        }
//                    }
//                }
                val code = pomList.toCode()
                file.writeText(code)

                val pomFile = File(targetFolder.absolutePath, "Pom.kt")
                pomFile.writeText(pomFileContent())

                logger.warn("Done")
            }.getOrElse {
                logger.warn(it.toString())
            }
        }
        target.tasks.findByPath("analyzeLicenseFile")?.dependsOn(generateTask)
    }

    private fun Pom.License.toCode(): String {
        return """Pom.License(
            name = "$name",
            url = "$url",
            distribution = "$distribution",
            comments = "$comments",
        ),
        """.trimIndent()
    }

    private fun Pom.toCode(): String {
        return """
        Pom(
            name = "$name",
            groupId = "$groupId",
            version = "$version",
            description = "$description",
            artifactId = "$artifactId",
            url = "$url",
            licenses = ${licenses.toCode()}
        ),
        """
    }

    private fun List<Pom>.toCode(): String {
        return """
            package systems.kuu
            
            object LicenseInformation{
                val data:List<Pom> = listOf(
                    ${joinToString("") { it.toCode() }}
                )
            }
        """.trimIndent()
    }

    private fun pomFileContent():String{
        return """
            package systems.kuu

            data class Pom(
                val name: String?,
                val groupId: String?,
                val version:String?,
                val description:String?,
                val artifactId: String?,
                val url: String?,
                val licenses: License
            ) {
                data class License(
                    val name: String?,
                    val url: String?,
                    val distribution:String?,
                    val comments:String?,
                )
            }
        """.trimIndent()
    }
}


fun Project.android(action: LibraryExtension.() -> Unit) {
    extensions.configure(action)
}