package net.deechael.genshin.lib.open.world.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.deechael.genshin.lib.open.world.DataSource;

@NoArgsConstructor
@AllArgsConstructor
public class FileConfig implements DataSourceConfig {

    @Getter
    @Setter
    private String path = "slime_worlds";

    @Override
    public final DataSource getType() {
        return DataSource.FILE;
    }

}
