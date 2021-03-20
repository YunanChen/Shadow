package com.tencent.shadow.core.gradle

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.isAccessible

class ContainerRenamePlugin : Plugin<Project> {
    companion object {
        private const val EXTENSION_NAME = "containerRenameConfig"
    }

    override fun apply(project: Project) {
        val config = project.extensions.create(EXTENSION_NAME, ContainerRenameConfig::class.java)
        if (config.newPackageName.isNotEmpty()) {
            if (!config.isValid()) {
                throw IllegalArgumentException("ContainerRenameConfig is invalid!!!")
            }
            getBaseExtension(project).run {
                registerTransform(TransformCreateHelper.create(project, config))
            }
        }
    }

    private fun getBaseExtension(project: Project): BaseExtension {
        val plugin = project.plugins.getPlugin(AppPlugin::class.java)
        return if (com.android.builder.model.Version.ANDROID_GRADLE_PLUGIN_VERSION == "3.0.0") {
            val method = BasePlugin::class.declaredFunctions.first { it.name == "getExtension" }
            method.isAccessible = true
            method.call(plugin) as BaseExtension
        } else {
            project.extensions.getByName("android") as BaseExtension
        }
    }
}