package ui.component

import kotlinx.css.CSSBuilder
import react.RBuilder
import styled.css
import styled.styledImg


fun RBuilder.AddIcon(cssBuilder: CSSBuilder.() -> Unit) {
    styledImg(src = "add-24px.svg") {
        css(cssBuilder)
    }
}