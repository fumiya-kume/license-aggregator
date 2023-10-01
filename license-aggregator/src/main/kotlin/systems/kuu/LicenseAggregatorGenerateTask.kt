package systems.kuu

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.diagnostics.DependencyReportTask
import org.slf4j.LoggerFactory
import java.io.File

open class LicenseAggregatorGenerateTask : DefaultTask() {

    private val logger = LoggerFactory.getLogger(DependencyReportTask::class.java)

    @InputDirectory
    var target: String? = null

    @TaskAction
    fun action() {
        runCatching {
            val buildDir = project.rootProject.layout.buildDirectory.asFile.get()
            val dependencyJsonFile = File(buildDir, "/generated/dependencies.json")
            val destinationFolder = File(target, "raw")
            destinationFolder.mkdirs()
            val destinationFile = File(destinationFolder, "/dependencies.json")
            File(dependencyJsonFile.absolutePath).copyTo(target = destinationFile, overwrite = true)
        }.getOrElse {
            logger.warn(it.toString())
        }
    }
}