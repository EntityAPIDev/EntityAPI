/*
 * This file is part of EntityAPI.
 *
 * EntityAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EntityAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EntityAPI.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.entityapi.api.plugin;

import org.bukkit.Bukkit;
import org.entityapi.api.utils.LogicUtil;
import org.entityapi.api.utils.StringUtil;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ModuleLogger extends Logger {

    private final String[] modulePath;
    private final String prefix;

    public ModuleLogger(String... modulePath) {
        this(Bukkit.getLogger(), modulePath);
    }

    public ModuleLogger(Logger parent, String... modulePath) {
        super(StringUtil.join(modulePath, "."), null);
        this.setParent(parent);
        this.setLevel(Level.ALL);
        this.modulePath = modulePath;
        StringBuilder builder = new StringBuilder();
        for (String module : modulePath) {
            builder.append("[").append(module).append("] ");
        }
        this.prefix = builder.toString();
    }

    public ModuleLogger getModule(String... path) {
        return new ModuleLogger(this.getParent(), LogicUtil.appendArray(this.modulePath, path));
    }

    @Override
    public void log(LogRecord logRecord) {
        logRecord.setMessage(this.prefix + logRecord.getMessage());
        super.log(logRecord);
    }
}
