package com.github.patou.gitmoji

import com.intellij.ide.AppLifecycleListener
import com.intellij.ide.util.PropertiesComponent
import javassist.ClassClassPath
import javassist.ClassPool
import javassist.CtNewMethod

const val PlUG_ID = "com.github.patou.gitmoji"

class EmojiCommitLogALL : AppLifecycleListener {

    override fun appFrameCreated(commandLineArgs: MutableList<String>) {
        val projectInstance = PropertiesComponent.getInstance()
        val result =projectInstance.getBoolean(CONFIG_RENDER_COMMIT_LOG)
        if (!result) return

        val classPool = ClassPool.getDefault()
        classPool.appendClassPath(ClassClassPath(EmojiConverter::class.java))
        val ctClass = classPool["com.intellij.vcs.log.ui.render.GraphCommitCell"]
        if (ctClass != null) {
            ctClass.defrost()
            val converter = classPool["$PlUG_ID.EmojiConverter"].getDeclaredMethod("convert")
            ctClass.addMethod(CtNewMethod.copy(converter, ctClass, null))
            val constructor =
                ctClass.getConstructor("(Ljava/lang/String;Ljava/util/Collection;Ljava/util/Collection;)V")
            if (constructor != null) {
                constructor.insertBefore("\$1 = convert(\$1);")
                ctClass.toClass()
            }
        }
    }
}