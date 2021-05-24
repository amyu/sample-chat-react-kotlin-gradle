package ui.component

import domain.user.User
import kotlinx.css.*
import kotlinx.css.properties.lh
import react.RBuilder
import styled.css
import styled.styledDiv


fun RBuilder.UserIconComponent(user: User) {
    styledDiv {
        css {
            val iconSize = 50.px
            width = iconSize
            height = iconSize
            borderRadius = 50.pct
            backgroundColor = Color("#2C2D2E")
            textAlign = TextAlign.center
            lineHeight = iconSize.lh
            fontWeight = FontWeight.bold
        }

        +"${user.name[0]}${user.name[1]}"
    }
}
