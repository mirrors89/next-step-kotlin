package core.annotation

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Service(val value: String = "")