package systems.kuu


import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.diagnostics.DependencyReportTask
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register
import org.slf4j.LoggerFactory
import java.io.File

class LicenseAggregatorGeneratorPlugin : Plugin<Project> {

    private val logger = LoggerFactory.getLogger(DependencyReportTask::class.java)
    override fun apply(target: Project) {

        target.android {
            libraryVariants.all {
                val generateTask = target.tasks.register<LicenseAggregatorGenerateTask>("licenseAggregator${this.name}Generate").get()
                target.tasks.findByPath("build")?.dependsOn(generateTask)
                val destination = "${target.buildDir}/generated/aggregator/${this.name}/res"
                File(destination).mkdirs()
                generateTask.target = destination
                val destinationFolder = File(destination, "raw")
                val files = target.files(destinationFolder).builtBy(generateTask)
                this.registerGeneratedResFolders(files)
                return@all
            }
        }
    }

    private fun Project.android(action: LibraryExtension.() -> Unit) {
        extensions.configure(action)
    }
}