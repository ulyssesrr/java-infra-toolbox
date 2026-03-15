package io.github.ulyssesrr.infratoolbox.hash;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TestJdkHasher32 {

    @Test
    void testDefaultFactoryCreatesInstance() {
        StatefulHasher hasher = JdkHasher32.getDefaultFactory().create();
        assertThat(hasher).isInstanceOf(JdkHasher32.class);
    }

    @Test
    void testPutByte() {
        String hash = new JdkHasher32().putByte((byte) 42).hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    void testSameByteProduceSameHash() {
        byte value = 42;
        String hash1 = new JdkHasher32().putByte(value).hash();
        String hash2 = new JdkHasher32().putByte(value).hash();
        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    void testDifferentByteProduceDifferentHash() {
        String hash1 = new JdkHasher32().putByte((byte) 42).hash();
        String hash2 = new JdkHasher32().putByte((byte) 24).hash();
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    void testPutBytes() {
        byte[] bytes = new byte[] { 1, 2, 3, 4 };
        String hash = new JdkHasher32().putBytes(bytes).hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    void testSameBytesProduceSameHash() {
        byte[] bytes1 = new byte[] { 1, 2, 3, 4 };
        byte[] bytes2 = new byte[] { 1, 2, 3, 4 };
        String hash1 = new JdkHasher32().putBytes(bytes1).hash();
        String hash2 = new JdkHasher32().putBytes(bytes2).hash();
        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    void testDifferentBytesProduceDifferentHash() {
        byte[] bytes1 = new byte[] { 1, 2, 3, 4 };
        byte[] bytes2 = new byte[] { 1, 2, 3, 5 };
        String hash1 = new JdkHasher32().putBytes(bytes1).hash();
        String hash2 = new JdkHasher32().putBytes(bytes2).hash();
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    void testPutShort() {
        String hash = new JdkHasher32().putShort((short) 100).hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    void testSameShortProduceSameHash() {
        short value = 100;
        String hash1 = new JdkHasher32().putShort(value).hash();
        String hash2 = new JdkHasher32().putShort(value).hash();
        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    void testDifferentShortProduceDifferentHash() {
        String hash1 = new JdkHasher32().putShort((short) 100).hash();
        String hash2 = new JdkHasher32().putShort((short) 200).hash();
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    void testPutInt() {
        String hash = new JdkHasher32().putInt(12345).hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    void testSameIntProduceSameHash() {
        int value = 12345;
        String hash1 = new JdkHasher32().putInt(value).hash();
        String hash2 = new JdkHasher32().putInt(value).hash();
        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    void testDifferentIntProduceDifferentHash() {
        String hash1 = new JdkHasher32().putInt(100).hash();
        String hash2 = new JdkHasher32().putInt(200).hash();
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    void testPutLong() {
        String hash = new JdkHasher32().putLong(9876543210L).hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    void testSameLongProduceSameHash() {
        long value = 9876543210L;
        String hash1 = new JdkHasher32().putLong(value).hash();
        String hash2 = new JdkHasher32().putLong(value).hash();
        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    void testDifferentLongProduceDifferentHash() {
        String hash1 = new JdkHasher32().putLong(100L).hash();
        String hash2 = new JdkHasher32().putLong(200L).hash();
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    void testPutFloat() {
        String hash = new JdkHasher32().putFloat(3.14f).hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    void testSameFloatProduceSameHash() {
        float value = 3.14f;
        String hash1 = new JdkHasher32().putFloat(value).hash();
        String hash2 = new JdkHasher32().putFloat(value).hash();
        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    void testDifferentFloatProduceDifferentHash() {
        String hash1 = new JdkHasher32().putFloat(3.14f).hash();
        String hash2 = new JdkHasher32().putFloat(2.71f).hash();
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    void testPutDouble() {
        String hash = new JdkHasher32().putDouble(2.718281828).hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    void testSameDoubleProduceSameHash() {
        double value = 2.718281828;
        String hash1 = new JdkHasher32().putDouble(value).hash();
        String hash2 = new JdkHasher32().putDouble(value).hash();
        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    void testDifferentDoubleProduceDifferentHash() {
        String hash1 = new JdkHasher32().putDouble(2.7).hash();
        String hash2 = new JdkHasher32().putDouble(3.1).hash();
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    void testPutBoolean() {
        String hashTrue = new JdkHasher32().putBoolean(true).hash();
        String hashFalse = new JdkHasher32().putBoolean(false).hash();
        assertThat(hashTrue).isNotEmpty();
        assertThat(hashFalse).isNotEmpty();
        assertThat(hashTrue).isNotEqualTo(hashFalse);
    }

    @Test
    void testPutChar() {
        String hash = new JdkHasher32().putChar('A').hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    void testSameCharProduceSameHash() {
        char value = 'A';
        String hash1 = new JdkHasher32().putChar(value).hash();
        String hash2 = new JdkHasher32().putChar(value).hash();
        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    void testDifferentCharProduceDifferentHash() {
        String hash1 = new JdkHasher32().putChar('A').hash();
        String hash2 = new JdkHasher32().putChar('B').hash();
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    void testPutUnencodedChars() {
        String hash = new JdkHasher32().putString("test").hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    void testPutString() {
        String hash = new JdkHasher32().putString("test").hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    void testSameStringProduceSameHash() {
        String value = "hello world";
        String hash1 = new JdkHasher32().putString(value).hash();
        String hash2 = new JdkHasher32().putString(value).hash();
        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    void testDifferentStringProduceDifferentHash() {
        String hash1 = new JdkHasher32().putString("hello").hash();
        String hash2 = new JdkHasher32().putString("world").hash();
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    void testConsistentHashForSameInput() {
        StatefulHasher hasher1 = new JdkHasher32().putInt(12345).putString("hello");
        StatefulHasher hasher2 = new JdkHasher32().putInt(12345).putString("hello");
        assertThat(hasher1.hash()).isEqualTo(hasher2.hash());
    }

    @Test
    void testDifferentHashForDifferentInput() {
        String hash1 = new JdkHasher32().putInt(100).hash();
        String hash2 = new JdkHasher32().putInt(200).hash();
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    void testChainingPutMethods() {
        String hash1 = new JdkHasher32()
                .putByte((byte) 1)
                .putShort((short) 2)
                .putInt(3)
                .putLong(4L)
                .putFloat(5.0f)
                .putDouble(6.0)
                .putBoolean(true)
                .putChar('Z')
                .putString("abc")
                .hash();
        String hash2 = new JdkHasher32()
                .putByte((byte) 1)
                .putShort((short) 2)
                .putInt(3)
                .putLong(4L)
                .putFloat(5.0f)
                .putDouble(6.0)
                .putBoolean(true)
                .putChar('Z')
                .putString("abc")
                .hash();
        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    void testPutBytesWithEmptyArray() {
        byte[] emptyBytes = new byte[0];
        String hash = new JdkHasher32().putBytes(emptyBytes).hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    void testHashReturnsHexString() {
        String hash = new JdkHasher32().putInt(1).hash();
        assertThat(hash).matches("[0-9a-f]+");
    }

    @Test
    void testMultipleHashesReturnSameValue() {
        StatefulHasher hasher = new JdkHasher32().putInt(42);
        String hash1 = hasher.hash();
        String hash2 = hasher.hash();
        assertThat(hash1).isEqualTo(hash2);
    }
}
