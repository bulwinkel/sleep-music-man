// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Deps.kotlin_version}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks {
    create<Delete>("clean") {
        delete = setOf(rootProject.buildDir)
    }
}
