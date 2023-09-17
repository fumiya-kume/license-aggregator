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