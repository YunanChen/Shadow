package com.tencent.shadow.core.gradle

import com.android.build.api.transform.TransformInvocation
import com.tencent.shadow.core.transform.specific.SimpleRenameTransform
import com.tencent.shadow.core.transform_kit.AbstractTransform
import com.tencent.shadow.core.transform_kit.AbstractTransformManager
import com.tencent.shadow.core.transform_kit.ClassPoolBuilder
import com.tencent.shadow.core.transform_kit.SpecificTransform
import org.gradle.api.Project

class CustomRenameTransform(
        project: Project,
        classPoolBuilder: ClassPoolBuilder,
        private val renameMap: Map<String, String>
) : AbstractTransform(project, classPoolBuilder) {

    private lateinit var _mTransformManager: AbstractTransformManager

    override val mTransformManager: AbstractTransformManager
        get() = _mTransformManager

    override fun beforeTransform(invocation: TransformInvocation) {
        super.beforeTransform(invocation)
        _mTransformManager = object : AbstractTransformManager(mCtClassInputMap, classPool) {
            override val mTransformList: List<SpecificTransform>
                get() = listOf(SimpleRenameTransform(renameMap))
        }
    }

    override fun getName(): String = "ContainerRenameTransform"
}