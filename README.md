# Advanced Slime World Manager
This is a modified version to be used in Genshin Craft
https://www.github.com/GedStudio/Genshin-Craft

---

## How to use
You should be sure you invoke these methods in your plugin main class:
```java
import net.deechael.genshin.lib.impl.world.SlimeManagerImpl;
import org.bukkit.plugin.java.JavaPlugin;

public class YourPluginMainClass extends JavaPlugin {

    @Override
    public void onLoad() {
        SlimeManagerImpl.getInstance().loading(this);
        // ... attention here, you need to add something later
    }

    @Override
    public void onEnable() {
        SlimeManagerImpl.getInstance().enabling();
    }

    @Override
    public void onDisable() {
        SlimeManagerImpl.getInstance().disabling();
    }
}
```
This is to initialize the SWM\
Then you need to add source provider
```java
// This should be in the onLoad() method after SlimeManagerImpl.getInstance().loading(this);

// File
FileConfig config = new FileConfig("slime-worlds");
try {
    SlimeManager.getManager().registerLoader(config);
} catch (SQLException e) {
    throw new RuntimeException(e);
}

// MySQL
MysqlConfig config = new MysqlConfig("127.0.0.1", 3306, "root", "123456", "database");
try {
    SlimeManager.getManager().registerLoader(config);
} catch (SQLException e) {
    throw new RuntimeException(e);
}

// MongoDB
MongoDBConfig config = new MongoDBConfig("127.0.0.1", 27017, "authSource", "root", "123456", "database", "collection");
try {
    SlimeManager.getManager().registerLoader(config);
} catch (SQLException e) {
    throw new RuntimeException(e);
}

// Redis
RedisConfig config = new RedisConfig("redis://127.0.0.1/");
try {
    SlimeManager.getManager().registerLoader(config);
} catch (SQLException e) {
    throw new RuntimeException(e);
}

```
If all these things have been done, you can use swm!\
This two classes are the main part of modified swm:
```java
SlimeManager slimeManager = SlimeManager.getManager(); // Original SWMPlugin.class
WorldManager worldManager = WorldManager.getManager(); // Added by DeeChael
```

#### Don't forget to use the modified classmodifier in gs-world-clsm module!

## Credits
Thanks to:

All the contributors that actively maintain ASWM and added features to SWM.
Paul19988 - ASWM Creator.
ComputerNerd100 - Large Contributor & Maintainer.
b0ykoe - Provider of build services & repositories.
Owen1212055 - Large Contributor & Maintainer.
Gerolmed - Contributor & Maintainer.
Grinderwolf - The original creator.
Glare - Providing the original Maven repository.
Minikloon and all the Hypixel team for developing the SRF.
