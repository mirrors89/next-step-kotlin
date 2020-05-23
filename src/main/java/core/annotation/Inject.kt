package core.annotation

@Target(AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Inject(val required: Boolean = true)