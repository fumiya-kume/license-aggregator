package systems.kuu

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.diagnostics.DependencyReportTask
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register
import org.slf4j.LoggerFactory

class LicenseAggregatorAnalyzerPlugin : Plugin<Project> {

    private val logger = LoggerFactory.getLogger(DependencyReportTask::class.java)

    override fun apply(target: Project) {
        val analyzeTask = target.tasks.register<LicenseAggregatorAnalyzeTask>("licenseAggregatorGenerateTask",).get()
        target.tasks.getByPath("build").dependsOn(analyzeTask)
    }
}



