package util

import java.nio.file.Files
import java.nio.file.Paths


class FileIoUtils {
    companion object {
        fun loadFileFromClasspath(filePath: String?): ByteArray? {
            val path = Paths.get(FileIoUtils::class.java.classLoader.getResource(filePath).toURI())
            return Files.readAllBytes(path)
        }
    }
}