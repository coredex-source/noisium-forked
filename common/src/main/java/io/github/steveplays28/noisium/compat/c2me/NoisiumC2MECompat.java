package io.github.steveplays28.noisium.compat.c2me;

import io.github.steveplays28.noisium.Noisium;
import io.github.steveplays28.noisium.util.ModUtil;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

public final class NoisiumC2MECompat {
    public static final String C2ME_MOD_ID = "c2me";
    /**
     * @return true if C2ME is loaded with OpenCL acceleration enabled
     */
    public static boolean isC2MELoaded() {
        if (!ModUtil.isModPresent(C2ME_MOD_ID)) {
            return false;
        }
        boolean hasOpenCL = ClassInfo.forName("com.ishland.c2me.opts.accel.opencl.mixin.deobf.MixinNoiseChunkGenerator") != null;
        return hasOpenCL;
    }
}
