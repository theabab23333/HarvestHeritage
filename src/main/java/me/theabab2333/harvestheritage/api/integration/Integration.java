package me.theabab2333.harvestheritage.api.integration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 是的，这块 integration 由 ai 生成，似乎还蛮好用的
/**
 * 标记一个可选联动（integration）的入口类。
 * <p>
 * 被标注的类在加载期不会被直接引用，{@link IntegrationManager} 只会在确认 {@link #value()} 指定的 mod
 * 已经加载后，才通过 {@code Class.forName} 解析并实例化它。因此联动类内部可以放心地直接引用目标 mod 的类
 * 与 API，不会因为目标 mod 缺失而在启动阶段抛 {@code NoClassDefFoundError}。
 * <p>
 * 联动类需要满足：
 * <ul>
 *     <li>{@code public}，并且拥有 {@code public} 的无参构造器；</li>
 *     <li>可选地声明 {@code public void apply()}，在通用端加载时被调用；</li>
 *     <li>可选地声明 {@code public void applyClient()}，仅在客户端加载时被调用。</li>
 * </ul>
 * 两个入口方法都可以省略，但都没有声明时加载会打印一条警告。
 * <p>
 * 示例：
 * <pre>{@code
 * @Integration("jei")
 * public class ModJeiIntegration {
 *
 *     public void apply() {
 *         // 通用端逻辑
 *     }
 *
 *     public void applyClient() {
 *         // 仅客户端逻辑
 *     }
 * }
 * }</pre>
 *
 * @see IntegrationInstance
 * @see IntegrationManager
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Integration {

    /**
     * 目标 mod 的 id，只有该 mod 已加载时对应的联动类才会被实例化并调用。
     */
    String value();
}
