package com.tencent.shadow.core.gradle

open class ContainerRenameConfig {
    companion object {
        private const val PATTERN_PKG_NAME = "^([a-zA-Z_][a-zA-Z0-9_]*)+([.][a-zA-Z_][a-zA-Z0-9_]*)+\$"
    }
    var newPackageName = ""

    fun isValid(): Boolean {
        val regex = Regex(PATTERN_PKG_NAME)
        return newPackageName.matches(regex)
    }
}