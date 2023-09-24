package systems.kuu


import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.TestedExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.diagnostics.DependencyReportTask
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register
import org.slf4j.LoggerFactory

class LicenseAggregatorGeneratorPlugin : Plugin<Project> {

    private val logger = LoggerFactory.getLogger(DependencyReportTask::class.java)
    override fun apply(target: Project) {
        val generateTask = target.tasks.register<LicenseAggregatorGenerateTask>("licenseAggregatorGenerate").get()

        target.tasks.findByPath("build")?.dependsOn(generateTask)


        val androidExtension: TestedExtension = target.extensions.findByName("android").let {
            when (it) {
                is ApplicationExtension -> {
                    it as ApplicationExtension
                }

                is LibraryExtension -> {
                    it as LibraryExtension
                }

                else -> {
                    logger.warn(it!!::class.java.name)
                    throw Exception("")
                }
            }
        } as TestedExtension



        target.android {

//                this. .map { variant ->
//                val destinationFolder = File("${target.buildDir}/generated/aggregator/res")
//                logger.warn("hoge")
//                val files = target.files(destinationFolder).builtBy(generateTask)
//                variant.registerGeneratedResFolders (files)

        }
    }
}


fun Project.android(action: LibraryExtension.() -> Unit) {
    extensions.configure(action)
}