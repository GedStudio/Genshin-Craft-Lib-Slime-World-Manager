package net.deechael.genshin.lib.open.world.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.deechael.genshin.lib.open.world.DataSource;

import java.io.File;

@NoArgsConstructor
public class SqliteConfig implements DataSourceConfig {

    @Getter
    private File database = null;

    @Getter
    @Setter
    private String sqlUrl = "jdbc:sqlite:{path}";

    public SqliteConfig(String path) {
        this(new File(path));
    }

    public SqliteConfig(File database) {
        this.database = database;
    }

    public void setDatabase(File database) {
        this.database = database;
    }

    public void setDatabase(String path) {
        this.database = new File(path);
    }

    @Override
    public DataSource getType() {
        return DataSource.SQLITE;
    }

}
