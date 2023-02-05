package de.florianmichael.testserverutils.test

import de.florianmichael.testserverutils.TestServerUtils
import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

/**
 * Debug Stuff
 */
class LocationExporter {
    fun uwu(location: Location) {
        val path = File(TestServerUtils.get().dataFolder, "test.yml")
        val test = YamlConfiguration.loadConfiguration(path)
        test.set("position", location)

        test.save(path)
    }
}