//import com.google.devtools.ksp.KspExperimental
//import com.google.devtools.ksp.getKotlinClassByName
//import com.google.devtools.ksp.processing.CodeGenerator
//import com.google.devtools.ksp.processing.Dependencies
//import com.google.devtools.ksp.processing.KSPLogger
//import com.google.devtools.ksp.processing.Resolver
//import com.google.devtools.ksp.processing.SymbolProcessor
//import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
//import com.google.devtools.ksp.processing.SymbolProcessorProvider
//import com.google.devtools.ksp.symbol.KSAnnotated
//import groovy.json.JsonBuilder
//import groovy.json.JsonParser
//import org.apache.groovy.json.internal.JsonFastParser
//import org.apache.tools.ant.property.GetProperty
//import org.gradle.api.Project
//import org.gradle.api.file.Directory
//import org.gradle.api.invocation.Gradle
//import org.gradle.api.tasks.diagnostics.DependencyReportTask
//import org.gradle.configurationcache.problems.PropertyTrace
//import org.gradle.initialization.Environment
//import org.gradle.initialization.layout.BuildLayout
//import org.gradle.initialization.layout.BuildLayoutConfiguration
//import org.gradle.internal.impldep.org.bouncycastle.asn1.x500.style.RFC4519Style
//import org.gradle.internal.impldep.org.bouncycastle.asn1.x500.style.RFC4519Style.name
//import org.gradle.tooling.model.gradle.GradleBuild
//import org.slf4j.LoggerFactory
//import systems.kuu.Pom
//import java.io.File
//import java.io.FileInputStream
//import kotlin.jvm.internal.Intrinsics.Kotlin
//
//class LicenseAggregatorSymbolProcessor(
//    private val codeGenerator: CodeGenerator,
//    private val logger:KSPLogger,
//) : SymbolProcessor {
//
//    var executed: Boolean = false
//
//    @OptIn(KspExperimental::class)
//    override fun process(resolver: Resolver): List<KSAnnotated> {
////        System.getProperty("GRADLE_USER_HOME").forEach {
////            logger.warn("${ikey}:${it.value}")
////        }
//
////        val dependenciesJsonFile = FileInputStream("${GradleBuild}/generated/dependencies.json")
////        val pomList = JsonFastParser().parse(dependenciesJsonFile) as List<Pom>
//
//        if (executed) return emptyList()
////        return emptyList()
//
//        val file = codeGenerator.createNewFileByPath(
//            dependencies = Dependencies.ALL_FILES,
//            "systems/kuu/sample.kt",
//        )
////        val code = pomList.map {
////            it.toCode()
////        }.joinToString ()
//
////        file.write(code.toByteArray())
//        file.close()
//        executed = true
//
//        return emptyList()
//    }
//}
//
//private fun Pom.License.toCode(): String {
//    return """
//        Pom.License(
//            name = ${name},
//            url = ${url},
//            distribution = ${distribution},
//            comments = ${comments}
//        ),
//        """
//}
//
//private fun Pom.toCode(): String {
//    return """
//        Pom(
//            name = ${name},
//            groupId = ${groupId},
//            version = ${version},
//            description = ${description},
//            artifactId = ${artifactId},
//            url = ${url},
//            licenses = ${licenses.toCode()}
//        )
//        """
//}
//
//class LicenseAggregatorSymbolProcessorProvider : SymbolProcessorProvider {
//    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
//        return LicenseAggregatorSymbolProcessor(environment.codeGenerator, environment.logger)
//    }
//}