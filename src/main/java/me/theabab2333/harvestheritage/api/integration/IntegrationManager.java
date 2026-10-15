package me.theabab2333.harvestheritage.api.integration;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import java.lang.annotation.ElementType;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;
import net.neoforged.fml.loading.progress.ProgressMeter;
import net.neoforged.fml.loading.progress.StartupNotificationManager;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.language.ModFileScanData;

// 是的，这块 integration 由 ai 生成，似乎还蛮好用的
/**
 * 收集并加载所有 {@link Integration}。
 * <p>
 * 典型用法（已经接在 {@code HarvestHeritage} 与 {@code HarvestHeritageClient} 上）：
 * <pre>{@code
 * // 通用端：先扫描注解，再加载所有目标 mod 已存在的联动
 * INTEGRATIONS.compileContent();
 * INTEGRATIONS.loadAllIntegrations();
 *
 * // 客户端：额外加载仅客户端的联动
 * HarvestHeritage.getIntegrationManager().loadAllClientIntegrations();
 * }</pre>
 * {@link #compileContent()} 依赖 {@link FMLLoader} 的扫描结果，因此必须在 mod 构造阶段或之后调用。
 */
@Slf4j
@Getter
public class IntegrationManager {

    private final Multimap<String, IntegrationInstance> instances =
        MultimapBuilder.hashKeys().linkedHashSetValues().build();

    /**
     * 扫描所有已加载 mod 的字节码，收集标有 {@link Integration} 的类。
     * <p>
     * 重复调用是安全的：同一个类只会被记录一次。
     */
    public void compileContent() {
        FMLLoader loader = FMLLoader.getCurrentOrNull();
        if (loader == null) {
            log.warn("FMLLoader is not available yet, skipping the integration scan.");
            return;
        }

        List<ModFileInfo> modFiles = loader.getLoadingModList().getModFiles();
        ProgressMeter meter = StartupNotificationManager.addProgressBar("Load Integrations", modFiles.size());
        try {
            for (ModFileInfo modFile : modFiles) {
                meter.increment();
                ModFileScanData scanData = modFile.getFile().getScanResult();
                scanData.getAnnotatedBy(Integration.class, ElementType.TYPE).forEach(this::consider);
            }
        } finally {
            StartupNotificationManager.popBar(meter);
        }
    }

    /**
     * 加载指定 modid 对应的全部联动，已加载过的联动会被跳过。
     *
     * @param modid 目标 mod 的 id
     */
    public void load(String modid) {
        for (IntegrationInstance instance : this.instances.get(modid)) {
            if (instance.isLoaded()) {
                continue;
            }
            instance.newInstance();
            log.info("Loading integration {} for {}.", instance.getInstance(), modid);
            instance.invoke();
            instance.setLoaded(true);
        }
    }

    /**
     * 加载指定 modid 对应的全部客户端联动，已加载过的联动会被跳过。
     *
     * @param modid 目标 mod 的 id
     */
    public void loadClient(String modid) {
        for (IntegrationInstance instance : this.instances.get(modid)) {
            if (instance.isClientLoaded()) {
                continue;
            }
            instance.newInstance();
            log.info("Loading client integration {} for {}.", instance.getInstance(), modid);
            instance.invokeClient();
            instance.setClientLoaded(true);
        }
    }

    /**
     * 加载所有目标 mod 已存在的通用端联动。
     */
    public void loadAllIntegrations() {
        for (String modid : Set.copyOf(this.instances.keySet())) {
            if (isModLoaded(modid)) {
                this.load(modid);
            }
        }
    }

    /**
     * 加载所有目标 mod 已存在的客户端联动。
     */
    public void loadAllClientIntegrations() {
        for (String modid : Set.copyOf(this.instances.keySet())) {
            if (isModLoaded(modid)) {
                this.loadClient(modid);
            }
        }
    }

    /**
     * 判断某个 mod 是否已经被加载，可在联动类外部做条件判断。
     */
    public static boolean isModLoaded(String modid) {
        FMLLoader loader = FMLLoader.getCurrentOrNull();
        if (loader == null) {
            return false;
        }
        return loader.getLoadingModList()
            .getMods()
            .stream()
            .map(IModInfo::getModId)
            .anyMatch(modid::equals);
    }

    private void consider(ModFileScanData.AnnotationData annotation) {
        Object value = annotation.annotationData().get("value");
        if (!(value instanceof String modid)) {
            log.warn("Integration {} does not declare a valid modid and will be skipped.", annotation.memberName());
            return;
        }

        log.debug("Considering integration {} for {}.", annotation.memberName(), modid);
        this.instances.put(modid, new IntegrationInstance(modid, annotation.memberName()));
    }
}
