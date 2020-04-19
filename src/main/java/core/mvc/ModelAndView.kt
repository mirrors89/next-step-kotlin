package core.mvc

import java.util.*
import kotlin.collections.HashMap

class ModelAndView(val view: View) {
    val model: HashMap<String, Any?> = hashMapOf()

    fun addObject(attributeName: String, attributeValue :Any?): ModelAndView {
        model[attributeName] = attributeValue
        return this
    }

    fun getModel(): MutableMap<String, Any?> = Collections.unmodifiableMap(model)

}