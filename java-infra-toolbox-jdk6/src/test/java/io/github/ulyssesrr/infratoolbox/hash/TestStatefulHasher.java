package io.github.ulyssesrr.infratoolbox.hash;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

public interface TestStatefulHasher extends WithAssertions {

    StatefulHasher createStatefulHasher();

    @Test
    default void testPutByte() {
        String hash = createStatefulHasher().putByte((byte) 42).hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    default void testSameByteProduceSameHash() {
        byte value = 42;
        String hash1 = createStatefulHasher().putByte(value).hash();
        String hash2 = createStatefulHasher().putByte(value).hash();
        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    default void testDifferentByteProduceDifferentHash() {
        String hash1 = createStatefulHasher().putByte((byte) 42).hash();
        String hash2 = createStatefulHasher().putByte((byte) 24).hash();
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    default void testPutBytes() {
        byte[] bytes = new byte[] { 1, 2, 3, 4 };
        String hash = createStatefulHasher().putBytes(bytes).hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    default void testSameBytesProduceSameHash() {
        byte[] bytes1 = new byte[] { 1, 2, 3, 4 };
        byte[] bytes2 = new byte[] { 1, 2, 3, 4 };
        String hash1 = createStatefulHasher().putBytes(bytes1).hash();
        String hash2 = createStatefulHasher().putBytes(bytes2).hash();
        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    default void testDifferentBytesProduceDifferentHash() {
        byte[] bytes1 = new byte[] { 1, 2, 3, 4 };
        byte[] bytes2 = new byte[] { 1, 2, 3, 5 };
        String hash1 = createStatefulHasher().putBytes(bytes1).hash();
        String hash2 = createStatefulHasher().putBytes(bytes2).hash();
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    default void testPutShort() {
        String hash = createStatefulHasher().putShort((short) 100).hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    default void testSameShortProduceSameHash() {
        short value = 100;
        String hash1 = createStatefulHasher().putShort(value).hash();
        String hash2 = createStatefulHasher().putShort(value).hash();
        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    default void testDifferentShortProduceDifferentHash() {
        String hash1 = createStatefulHasher().putShort((short) 100).hash();
        String hash2 = createStatefulHasher().putShort((short) 200).hash();
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    default void testPutInt() {
        String hash = createStatefulHasher().putInt(12345).hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    default void testSameIntProduceSameHash() {
        int value = 12345;
        String hash1 = createStatefulHasher().putInt(value).hash();
        String hash2 = createStatefulHasher().putInt(value).hash();
        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    default void testDifferentIntProduceDifferentHash() {
        String hash1 = createStatefulHasher().putInt(100).hash();
        String hash2 = createStatefulHasher().putInt(200).hash();
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    default void testPutLong() {
        String hash = createStatefulHasher().putLong(9876543210L).hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    default void testSameLongProduceSameHash() {
        long value = 9876543210L;
        String hash1 = createStatefulHasher().putLong(value).hash();
        String hash2 = createStatefulHasher().putLong(value).hash();
        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    default void testDifferentLongProduceDifferentHash() {
        String hash1 = createStatefulHasher().putLong(100L).hash();
        String hash2 = createStatefulHasher().putLong(200L).hash();
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    default void testPutFloat() {
        String hash = createStatefulHasher().putFloat(3.14f).hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    default void testSameFloatProduceSameHash() {
        float value = 3.14f;
        String hash1 = createStatefulHasher().putFloat(value).hash();
        String hash2 = createStatefulHasher().putFloat(value).hash();
        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    default void testDifferentFloatProduceDifferentHash() {
        String hash1 = createStatefulHasher().putFloat(3.14f).hash();
        String hash2 = createStatefulHasher().putFloat(2.71f).hash();
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    default void testPutDouble() {
        String hash = createStatefulHasher().putDouble(2.718281828).hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    default void testSameDoubleProduceSameHash() {
        double value = 2.718281828;
        String hash1 = createStatefulHasher().putDouble(value).hash();
        String hash2 = createStatefulHasher().putDouble(value).hash();
        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    default void testDifferentDoubleProduceDifferentHash() {
        String hash1 = createStatefulHasher().putDouble(2.7).hash();
        String hash2 = createStatefulHasher().putDouble(3.1).hash();
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    default void testPutBoolean() {
        String hashTrue = createStatefulHasher().putBoolean(true).hash();
        String hashFalse = createStatefulHasher().putBoolean(false).hash();
        assertThat(hashTrue).isNotEmpty();
        assertThat(hashFalse).isNotEmpty();
        assertThat(hashTrue).isNotEqualTo(hashFalse);
    }

    @Test
    default void testPutChar() {
        String hash = createStatefulHasher().putChar('A').hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    default void testSameCharProduceSameHash() {
        char value = 'A';
        String hash1 = createStatefulHasher().putChar(value).hash();
        String hash2 = createStatefulHasher().putChar(value).hash();
        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    default void testDifferentCharProduceDifferentHash() {
        String hash1 = createStatefulHasher().putChar('A').hash();
        String hash2 = createStatefulHasher().putChar('B').hash();
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    default void testPutString() {
        String hash = createStatefulHasher().putString("test").hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    default void testSameStringProduceSameHash() {
        String value = "hello world";
        String hash1 = createStatefulHasher().putString(value).hash();
        String hash2 = createStatefulHasher().putString(value).hash();
        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    default void testDifferentStringProduceDifferentHash() {
        String hash1 = createStatefulHasher().putString("hello").hash();
        String hash2 = createStatefulHasher().putString("world").hash();
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    default void testConsistentHashForSameInput() {
        StatefulHasher hasher1 = createStatefulHasher().putInt(12345).putString("hello");
        StatefulHasher hasher2 = createStatefulHasher().putInt(12345).putString("hello");
        assertThat(hasher1.hash()).isEqualTo(hasher2.hash());
    }

    @Test
    default void testDifferentHashForDifferentInput() {
        String hash1 = createStatefulHasher().putInt(100).hash();
        String hash2 = createStatefulHasher().putInt(200).hash();
        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    default void testChainingPutMethods() {
        String hash1 = createStatefulHasher()
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
        String hash2 = createStatefulHasher()
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
    default void testPutBytesWithEmptyArray() {
        byte[] emptyBytes = new byte[0];
        String hash = createStatefulHasher().putBytes(emptyBytes).hash();
        assertThat(hash).isNotEmpty();
    }

    @Test
    default void testNullByteProducesDifferentHashThanZero() {
        String hashNull = createStatefulHasher().putByte(null).hash();
        String hashZero = createStatefulHasher().putByte((byte) 0).hash();
        assertThat(hashNull).isNotEqualTo(hashZero);
    }

    @Test
    default void testNullBytesProducesDifferentHashThanZeroLength() {
        String hashNull = createStatefulHasher().putBytes(null).hash();
        String hashEmpty = createStatefulHasher().putBytes(new byte[0]).hash();
        assertThat(hashNull).isNotEqualTo(hashEmpty);
    }

    @Test
    default void testNullShortProducesDifferentHashThanZero() {
        String hashNull = createStatefulHasher().putShort(null).hash();
        String hashZero = createStatefulHasher().putShort((short) 0).hash();
        assertThat(hashNull).isNotEqualTo(hashZero);
    }

    @Test
    default void testNullIntProducesDifferentHashThanZero() {
        String hashNull = createStatefulHasher().putInt(null).hash();
        String hashZero = createStatefulHasher().putInt(0).hash();
        assertThat(hashNull).isNotEqualTo(hashZero);
    }

    @Test
    default void testNullLongProducesDifferentHashThanZero() {
        String hashNull = createStatefulHasher().putLong(null).hash();
        String hashZero = createStatefulHasher().putLong(0L).hash();
        assertThat(hashNull).isNotEqualTo(hashZero);
    }

    @Test
    default void testNullFloatProducesDifferentHashThanZero() {
        String hashNull = createStatefulHasher().putFloat(null).hash();
        String hashZero = createStatefulHasher().putFloat(0.0f).hash();
        assertThat(hashNull).isNotEqualTo(hashZero);
    }

    @Test
    default void testNullDoubleProducesDifferentHashThanZero() {
        String hashNull = createStatefulHasher().putDouble(null).hash();
        String hashZero = createStatefulHasher().putDouble(0.0).hash();
        assertThat(hashNull).isNotEqualTo(hashZero);
    }

    @Test
    default void testNullBooleanProducesDifferentHashThanFalse() {
        String hashNull = createStatefulHasher().putBoolean(null).hash();
        String hashFalse = createStatefulHasher().putBoolean(false).hash();
        assertThat(hashNull).isNotEqualTo(hashFalse);
    }

    @Test
    default void testNullCharProducesDifferentHashThanZeroChar() {
        String hashNull = createStatefulHasher().putChar(null).hash();
        String hashZero = createStatefulHasher().putChar('\0').hash();
        assertThat(hashNull).isNotEqualTo(hashZero);
    }

    @Test
    default void testNullStringProducesDifferentHashThanEmptyString() {
        String hashNull = createStatefulHasher().putString(null).hash();
        String hashEmpty = createStatefulHasher().putString("").hash();
        assertThat(hashNull).isNotEqualTo(hashEmpty);
    }

    @Test
    default void testNullValuesProduceSameHash() {
        String hash1 = createStatefulHasher()
                .putByte(null)
                .putShort(null)
                .putInt(null)
                .putLong(null)
                .putFloat(null)
                .putDouble(null)
                .putBoolean(null)
                .putChar(null)
                .putString(null)
                .hash();

        String hash2 = createStatefulHasher()
                .putByte(null)
                .putShort(null)
                .putInt(null)
                .putLong(null)
                .putFloat(null)
                .putDouble(null)
                .putBoolean(null)
                .putChar(null)
                .putString(null)
                .hash();
        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    default void testMultipleHashesReturnSameValue() {
        StatefulHasher hasher = createStatefulHasher().putInt(42);
        String hash1 = hasher.hash();
        String hash2 = hasher.hash();
        assertThat(hash1).isEqualTo(hash2);
    }
}
