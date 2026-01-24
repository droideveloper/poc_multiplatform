import com.multiplatform.td.conventions.iosTargets
import org.gradle.api.Project
import org.gradle.kotlin.dsl.fileTree
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun KotlinMultiplatformExtension.iosTargets(
    named: String,
    isShared: Boolean = true,
    options: Map<String, String> = emptyMap(),
) {
    // packaged and is not accessible so we need to proxy it
    iosTargets(named, isShared, options)
}

val Project.libsTree get() = fileTree(
    "dir" to "$rootDir/libs",
    "include" to arrayOf("**/*.jar", "*.jar"),
)
