package com.tencent.shadow.core.gradle

import com.android.build.api.transform.Transform
import com.tencent.shadow.core.transform_kit.ClassPoolBuilder
import javassist.ClassPool
import org.gradle.api.Project

object TransformCreateHelper {
    /**
     * Runtime.apk 打包进去的 activity-container 模块中的类
     */
    private val CONTAINER_CLASS_LIST = arrayOf(
            "com.tencent.shadow.core.runtime.container.DelegateProvider",
            "com.tencent.shadow.core.runtime.container.DelegateProviderHolder",
            "com.tencent.shadow.core.runtime.container.HostActivity",
            "com.tencent.shadow.core.runtime.container.HostActivityDelegate",
            "com.tencent.shadow.core.runtime.container.HostActivityDelegator",
            "com.tencent.shadow.core.runtime.container.PluginContainerActivity",
    )

    fun create(project: Project, config: ContainerRenameConfig): Transform {
        return CustomRenameTransform(
                project,
                buildClassPoolBuilder(),
                buildRenameMap(config)
        )
    }

    private fun buildClassPoolBuilder(): ClassPoolBuilder {
        return object : ClassPoolBuilder {
            override fun build() = ClassPool.getDefault()
        }
    }

    private fun buildRenameMap(config: ContainerRenameConfig): Map<String, String> {
        val ret = mutableMapOf<String, String>()
        for (originClassName in CONTAINER_CLASS_LIST) {
            val newClassName = originClassName.replaceBeforeLast('.', config.newPackageName)
            ret[originClassName] = newClassName
        }
        return ret
    }
}