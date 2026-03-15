package io.github.ulyssesrr.infratoolbox.logging;

import java.io.Closeable;
import java.util.LinkedHashSet;
import java.util.Set;

import io.github.ulyssesrr.infratoolbox.logging.adapter.LoggerAdapter;

public class MdcScope implements Closeable {

    private final LoggerAdapter adapter;
    private final Set<String> keys = new LinkedHashSet<String>();

    public MdcScope(LoggerAdapter adapter) {
        this.adapter = adapter;
    }

    public void put(String key, Object value) {
        adapter.putMdc(key, value);
        keys.add(key);
    }

    public void close() {
        for (String k : keys) {
            adapter.removeMdc(k);
        }
    }

}
