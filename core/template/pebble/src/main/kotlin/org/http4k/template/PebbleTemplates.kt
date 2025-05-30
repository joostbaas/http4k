package org.http4k.template

import io.pebbletemplates.pebble.PebbleEngine
import io.pebbletemplates.pebble.error.LoaderException
import io.pebbletemplates.pebble.loader.ClasspathLoader
import io.pebbletemplates.pebble.loader.FileLoader
import java.io.File
import java.io.StringWriter

class PebbleTemplates(private val configure: (PebbleEngine.Builder) -> PebbleEngine.Builder = { it },
                      private val classLoader: ClassLoader = ClassLoader.getSystemClassLoader()) : Templates {

    private class PebbleTemplateRenderer(private val engine: PebbleEngine) : TemplateRenderer {
        override fun invoke(viewModel: ViewModel): String = try {
            val writer = StringWriter()
            engine.getTemplate(viewModel.template() + ".peb").evaluate(writer, mapOf("model" to viewModel))
            writer.toString()
        } catch (e: LoaderException) {
            throw ViewNotFound(viewModel)
        }
    }

    override fun CachingClasspath(baseClasspathPackage: String): TemplateRenderer {
        val loader = ClasspathLoader(classLoader)
        loader.prefix = if (baseClasspathPackage.isEmpty()) null else baseClasspathPackage.replace('.', File.separatorChar)
        return PebbleTemplateRenderer(configure(PebbleEngine.Builder().loader(loader)).build())
    }

    override fun Caching(baseTemplateDir: String): TemplateRenderer {
        val loader = FileLoader()
        loader.prefix = baseTemplateDir
        return PebbleTemplateRenderer(configure(PebbleEngine.Builder().cacheActive(true).loader(loader)).build())
    }

    override fun HotReload(baseTemplateDir: String): TemplateRenderer {
        val loader = FileLoader()
        loader.prefix = baseTemplateDir
        return PebbleTemplateRenderer(configure(PebbleEngine.Builder().cacheActive(false).loader(loader)).build())
    }
}
