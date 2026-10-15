package me.theabab2333.harvestheritage.api.integration;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

// 是的，这块 integration 由 ai 生成，似乎还蛮好用的
/**
 * 一条待加载的联动记录，负责延迟解析联动类并调用它的入口方法。
 * <p>
 * 类型解析被刻意推迟到 {@link #newInstance()}，这样在目标 mod 不存在时，本类自身也不会触发目标类的加载。
 * 入口方法通过 {@link MethodHandle} 缓存，避免每次调用都重复做反射查找。
 */
@Getter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Slf4j
public final class IntegrationInstance {

    /**
     * 通用端入口方法名。
     */
    public static final String LOADER_METHOD = "apply";

    /**
     * 客户端入口方法名。
     */
    public static final String CLIENT_LOADER_METHOD = "applyClient";

    @ToString.Include
    @EqualsAndHashCode.Include
    private final String modid;

    @ToString.Include
    @EqualsAndHashCode.Include
    private final String className;

    private @Nullable Class<?> clazz;

    @ToString.Include
    private @Nullable Object instance;

    private @Nullable MethodHandle constructor;
    private @Nullable MethodHandle loader;
    private @Nullable MethodHandle clientLoader;

    @Setter
    private boolean loaded;

    @Setter
    private boolean clientLoaded;

    public IntegrationInstance(String modid, String className) {
        this.modid = modid;
        this.className = className;
    }

    /**
     * 解析联动类并创建实例，重复调用是安全的。
     */
    @SneakyThrows
    public void newInstance() {
        if (this.clazz == null) {
            this.resolve();
        }
        if (this.instance == null) {
            MethodHandle constructor = Objects.requireNonNull(this.constructor, "constructor");
            this.instance = constructor.invoke();
        }
    }

    /**
     * 调用通用端入口方法 {@link #LOADER_METHOD}，没有声明时什么也不做。
     */
    @SneakyThrows
    public void invoke() {
        if (this.loader != null) {
            this.loader.invoke(this.instance);
        }
    }

    /**
     * 调用客户端入口方法 {@link #CLIENT_LOADER_METHOD}，没有声明时什么也不做。
     */
    @SneakyThrows
    public void invokeClient() {
        if (this.clientLoader != null) {
            this.clientLoader.invoke(this.instance);
        }
    }

    @SneakyThrows
    private void resolve() {
        Class<?> clazz = Class.forName(this.className);
        this.clazz = clazz;

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        this.constructor = lookup.findConstructor(clazz, MethodType.methodType(void.class));
        this.loader = findLoader(lookup, clazz, LOADER_METHOD);
        this.clientLoader = findLoader(lookup, clazz, CLIENT_LOADER_METHOD);

        if (this.loader == null && this.clientLoader == null) {
            log.warn(
                "Integration {} does not declare any loader method ({} / {}).",
                this.className,
                LOADER_METHOD,
                CLIENT_LOADER_METHOD
            );
        }
    }

    private static @Nullable MethodHandle findLoader(MethodHandles.Lookup lookup, Class<?> clazz, String name) {
        try {
            return lookup.findVirtual(clazz, name, MethodType.methodType(void.class));
        } catch (NoSuchMethodException | IllegalAccessException ignored) {
            return null;
        }
    }

}
