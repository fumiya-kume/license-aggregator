package systems.kuu

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.diagnostics.DependencyReportTask
import org.slf4j.LoggerFactory
import java.io.File

open class LicenseAggregatorGenerateTask : DefaultTask() {

    private val logger = LoggerFactory.getLogger(DependencyReportTask::class.java)

//    var libraryExtension:LibraryExtension? = null

    @TaskAction
    fun action(){
        runCatching {

            val buildDir = project.rootProject.layout.buildDirectory.asFile.get()
            val dependencyJsonFile = File(buildDir, "/generated/res/raw/dependencies.json")
            val destinationFolder = File("${project.buildDir}/generated/aggregator/res/raw")
            destinationFolder.mkdirs()
            val destinationFile = File(destinationFolder,"/dependencies.json")
            File(dependencyJsonFile.absolutePath).copyTo(target = destinationFile, overwrite = true)
//            val pomOriginalList = JsonSlurper().parseText(dependenciesJsonFile) as ArrayList<LazyMap>
//            val pomList = pomOriginalList.map { any ->
//                val license = JsonSlurper().parseText(any["license"].toString())
//                    .run {
//                        Pom.License(
//                            name = any["name"].toString(),
//                            url = any["url"].toString(),
//                            distribution = any["distribution"].toString(),
//                            comments = any["comments"].toString()
//                        )
//                    }
//                Pom(
//                    name = any["name"].toString(),
//                    groupId = any["groupId"].toString(),
//                    artifactId = any["artifactId"].toString(),
//                    version = any["version"].toString(),
//                    description = any["description"].toString(),
//                    url = any["url"].toString(),
//                    licenses = license
//                )
//            }
//                val generateDir = "${target.buildDir}/generated/sources/main/kotlin/systems.kuu"
//            val generateDir = "${project.buildDir}/generated/sources/main"
//
//            project.layout.buildDirectory.dir("$generateDir/generated/res/raw")
//
//            val ideaModel = project.extensions.findByType(IdeaModel::class.java)
//            val generatedSourceDirs = ideaModel?.module?.generatedSourceDirs
//            ideaModel?.module?.generatedSourceDirs = generatedSourceDirs.apply {
//                this?.add(project.file(generateDir))
//            }
//
//            val targetFolder = File("$generateDir/kotlin/systems.kuu").apply {
//                this.mkdirs()
//            }

//            val file = File(targetFolder.absolutePath, "LicenseInformation.kt")
//                with(target){
//                    android {
//                        this.libraryVariants.map {variant ->
//                            val files = target.files(targetFolder).builtBy("generateLicenseFile")
//                            variant.registerGeneratedResFolders (files)
//                        }
//                    }
//                }
//            val code = pomList.toCode()
//            file.writeText(code)
//
//            val pomFile = File(targetFolder.absolutePath, "Pom.kt")
//            pomFile.writeText(pomFileContent())

        }.getOrElse {
            logger.warn(it.toString())
        }
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