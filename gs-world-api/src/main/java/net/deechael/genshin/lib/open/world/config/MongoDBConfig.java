package net.deechael.genshin.lib.open.world.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.deechael.genshin.lib.open.world.DataSource;

@NoArgsConstructor
public class MongoDBConfig implements DataSourceConfig {

    @Getter
    @Setter
    private String host = "127.0.0.1";

    @Getter
    @Setter
    private int port = 27017;

    @Getter
    @Setter
    private String authSource = "admin";

    @Getter
    @Setter
    private String username = "root";

    @Getter
    @Setter
    private String password = "";

    @Getter
    @Setter
    private String database = "minecraft";

    @Getter
    @Setter
    private String collection = "worlds";

    @Getter
    @Setter
    private String uri = "";

    public MongoDBConfig(String host, int port, String authSource, String username, String password, String database, String collection) {
        this.host = host;
        this.port = port;
        this.authSource = authSource;
        this.username = username;
        this.password = password;
        this.database = database;
        this.collection = collection;
    }

    @Override
    public final DataSource getType() {
        return DataSource.MONGO;
    }

}
