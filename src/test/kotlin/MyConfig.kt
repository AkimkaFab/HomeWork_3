import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension


object MyConfig : AbstractProjectConfig() {
    override fun extensions(): List<Extension> = listOf(RepeatOnFailureExtension)
}