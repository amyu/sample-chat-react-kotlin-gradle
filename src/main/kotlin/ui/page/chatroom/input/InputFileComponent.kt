package ui.page.chatroom.input

import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.files.File
import org.w3c.files.get
import react.RBuilder
import react.dom.label
import styled.css
import styled.styledInput
import ui.component.AddIcon


fun RBuilder.InputFileComponent(
    onChangeFile: (File) -> Unit
) {
    label {
        val inputFileId = "upload_image"
        attrs {
            htmlFor = inputFileId
        }
        AddIcon {
            width = 30.px
            height = 30.px
            backgroundColor = Color("#2C2D2E")
            borderRadius = 30.px
            color = Color.white
            padding(4.px)
            cursor = Cursor.pointer
            hover {
                opacity = 0.7
            }
        }
        styledInput(type = InputType.file) {
            css {
                display = Display.none
            }
            attrs {
                accept = "image/jpeg, image/png"
                id = inputFileId
                onChangeFunction = {
                    (it.target as? HTMLInputElement)?.files?.get(0)?.let {
                        onChangeFile(it)
                    }
                }
            }
        }
    }
}
