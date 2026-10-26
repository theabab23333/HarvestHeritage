package me.theabab2333.harvestheritage.common.seed.requirement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Objects;
import java.util.Optional;

public record SeedLightRequirement(Optional<Integer> minLight, Optional<Integer> maxLight) {
    public static final int MIN_LIGHT_LEVEL = 0;
    public static final int MAX_LIGHT_LEVEL = 15;

    public static final Codec<SeedLightRequirement> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Codec.INT.optionalFieldOf("min").forGetter(SeedLightRequirement::minLight),
        Codec.INT.optionalFieldOf("max").forGetter(SeedLightRequirement::maxLight)
    ).apply(inst, SeedLightRequirement::new));

    public SeedLightRequirement {
        Objects.requireNonNull(minLight, "minLight");
        Objects.requireNonNull(maxLight, "maxLight");
        minLight.ifPresent(level -> checkLightLevel("min", level));
        maxLight.ifPresent(level -> checkLightLevel("max", level));

        int min = minLight.orElse(MIN_LIGHT_LEVEL);
        int max = maxLight.orElse(MAX_LIGHT_LEVEL);
        if (min > max) {
            throw new IllegalArgumentException("Seed light requirement min must not exceed max");
        }
    }

    public static SeedLightRequirement any() {
        return new SeedLightRequirement(Optional.empty(), Optional.empty());
    }

    public static SeedLightRequirement atLeast(int min) {
        return new SeedLightRequirement(Optional.of(min), Optional.empty());
    }

    public static SeedLightRequirement atMost(int max) {
        return new SeedLightRequirement(Optional.empty(), Optional.of(max));
    }

    public static SeedLightRequirement between(int min, int max) {
        return new SeedLightRequirement(Optional.of(min), Optional.of(max));
    }

    public static SeedLightRequirement of(int level) {
        return between(level, level);
    }

    public boolean matches(int lightLevel) {
        return this.minLight.map(min -> lightLevel >= min).orElse(true)
            && this.maxLight.map(max -> lightLevel <= max).orElse(true);
    }

    public boolean isUnrestricted() {
        return this.minLight.isEmpty() && this.maxLight.isEmpty();
    }

    private static void checkLightLevel(String fieldName, int level) {
        if (level < MIN_LIGHT_LEVEL || level > MAX_LIGHT_LEVEL) {
            throw new IllegalArgumentException(
                "Seed light requirement " + fieldName + " must be between "
                    + MIN_LIGHT_LEVEL + " and " + MAX_LIGHT_LEVEL
            );
        }
    }
}
