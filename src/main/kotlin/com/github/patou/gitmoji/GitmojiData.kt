package com.github.patou.gitmoji

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon
const val PLUGIN_ID = "com.github.patou"

const val CONFIG_RENDER_COMMIT_LOG: String = "$PLUGIN_ID.render-commit-log"
const val CONFIG_USE_UNICODE: String = "$PLUGIN_ID.gitmoji.use-unicode"
const val CONFIG_DISPLAY_ICON: String = "$PLUGIN_ID.gitmoji.display-icon"
const val CONFIG_AFTER_UNICODE: String = "$PLUGIN_ID.gitmoji.text-after-unicode"
const val CONFIG_LANGUAGE: String = "$PLUGIN_ID.gitmoji.language"
const val CONFIG_INSERT_IN_CURSOR_POSITION: String = "$PLUGIN_ID.gitmoji.insert-in-cursor-position"
const val CONFIG_INCLUDE_GITMOJI_DESCRIPTION: String = "$PLUGIN_ID.gitmoji.include-gitmoji-description"

data class GitmojiData(val code: String, val emoji: String, val description: String, val name: String) {
    private lateinit var _icon: Icon

    fun getIcon(): Icon {
        if (!this::_icon.isInitialized) {
            _icon = try {
                IconLoader.findIcon(
                    "/icons/gitmoji/" + code.replace(":", "") + ".png",
                    GitmojiData::class.java,
                    false,
                    true
                )!!
            } catch (e: Exception) {
                IconLoader.getIcon("/icons/gitmoji/anguished.png", GitmojiData::class.java)
            }
        }
        return _icon
    }

    val localeDescription: String
        get() = GitmojiLocale.t(name, description)
}
