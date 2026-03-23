package io.github.ulyssesrr.infratoolbox.hash;

import org.junit.jupiter.api.Test;

public class TestJdkHasher32 implements TestStatefulHasher {

    @Override
    public StatefulHasher createStatefulHasher() {
        return JdkHasher32.getDefaultFactory().create();
    }

    @Test
    void testDefaultFactoryCreatesInstance() {
        StatefulHasher hasher = JdkHasher32.getDefaultFactory().create();
        assertThat(hasher).isInstanceOf(JdkHasher32.class);
    }
}
