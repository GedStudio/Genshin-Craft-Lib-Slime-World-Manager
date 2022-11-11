package net.deechael.genshin.lib.open.world.config;

import lombok.Getter;
import lombok.Setter;
import net.deechael.genshin.lib.open.world.DataSource;

public class MysqlConfig implements DataSourceConfig {

    @Getter
    @Setter
    private String host = "127.0.0.1";

    @Getter
    @Setter
    private int port = 3306;

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
    private boolean useSsl = false;

    @Getter
    @Setter
    private String sqlUrl = "jdbc:mysql://{host}:{port}/{database}?autoReconnect=true&allowMultiQueries=true&useSSL={usessl}";

    public MysqlConfig() {
    }

    public MysqlConfig(String host, int port, String username, String password, String database) {
        this(host, port, username, password, database, false);
    }

    public MysqlConfig(String host, int port, String username, String password, String database, boolean useSsl) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
        this.useSsl = useSsl;
    }

    @Override
    public final DataSource getType() {
        return DataSource.MYSQL;
    }

}
