package io.github.steveplays28.noisium.compat.c2me;

import io.github.steveplays28.noisium.util.ModUtil;

public class NoisiumC2MECompat {
    public static final String C2ME_MOD_ID = "c2me";

    /**
     * @return If C2ME with OpenCL acceleration is loaded.
     */
    public static boolean isC2MELoaded() {
        if (ModUtil.isModPresent(C2ME_MOD_ID)) {
            try {
                Class.forName("com.ishland.c2me.opts.accel.opencl.mixin.deobf.MixinNoiseChunkGenerator");
                return true;
            } catch (ClassNotFoundException e) {
                // If it uses the non-CL version, return false to use the Noisium's default mixins.
                return false;
            }
        }
        return false;
    }
}
