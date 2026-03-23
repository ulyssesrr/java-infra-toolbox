package io.github.ulyssesrr.infratoolbox.hash;

import org.junit.jupiter.api.Test;

public class TestGuavaHasher implements TestStatefulHasher {

    @Override
    public StatefulHasher createStatefulHasher() {
        return GuavaHasher.getDefaultFactory().create();
    }

    @Test
    void testDefaultFactoryCreatesInstance() {
        StatefulHasher hasher = GuavaHasher.getDefaultFactory().create();
        assertThat(hasher).isInstanceOf(GuavaHasher.class);
    }
}
