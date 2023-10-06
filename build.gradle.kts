// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.6.0"
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    filter {
        exclude("**/ExampleInstrumentedTest.kt")
        exclude("**/settings.gradle.kts")
        exclude("**/ktlintKotlinScriptCheck.txt")
    }
}
