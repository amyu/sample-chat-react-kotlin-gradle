import kotlinx.browser.document
import kotlinx.browser.window
import react.dom.render
import router.Router
import ui.error.ErrorHandler

fun main() {
    window.onload = {
        render(document.getElementById("root")) {
            ErrorHandler {
                Router()
            }
        }
    }
}
