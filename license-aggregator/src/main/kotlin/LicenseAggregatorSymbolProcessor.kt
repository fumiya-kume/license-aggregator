import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated

class LicenseAggregatorSymbolProcessor(
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {

    var executed: Boolean = false

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (executed) return emptyList()
        val code = """
            package systems.kuu
            class Sample {
                fun helloKuu() {
                }
            }
        """.trimIndent()
        val file = codeGenerator.createNewFileByPath(
            dependencies = Dependencies.ALL_FILES,
            "systems/kuu/sample.kt",
        )
        file.write(code.toByteArray())
        file.close()
        executed = true

        return emptyList()
    }
}

class LicenseAggregatorSymbolProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return LicenseAggregatorSymbolProcessor(environment.codeGenerator)
    }
}