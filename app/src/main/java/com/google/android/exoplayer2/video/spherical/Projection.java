package com.google.android.exoplayer2.video.spherical;

import com.google.android.exoplayer2.util.Assertions;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: classes.dex */
final class Projection {
    public static final int DRAW_MODE_TRIANGLES = 0;
    public static final int DRAW_MODE_TRIANGLES_FAN = 2;
    public static final int DRAW_MODE_TRIANGLES_STRIP = 1;
    public static final int POSITION_COORDS_PER_VERTEX = 3;
    public static final int TEXTURE_COORDS_PER_VERTEX = 2;
    public final Mesh leftMesh;
    public final Mesh rightMesh;
    public final boolean singleMesh;
    public final int stereoMode;

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface DrawMode {
    }

    public static final class Mesh {
        private final SubMesh[] subMeshes;

        public Mesh(SubMesh... subMeshArr) {
            this.subMeshes = subMeshArr;
        }

        public SubMesh getSubMesh(int i) {
            return this.subMeshes[i];
        }

        public int getSubMeshCount() {
            return this.subMeshes.length;
        }
    }

    public static final class SubMesh {
        public static final int VIDEO_TEXTURE_ID = 0;
        public final int mode;
        public final float[] textureCoords;
        public final int textureId;
        public final float[] vertices;

        public SubMesh(int i, float[] fArr, float[] fArr2, int i2) {
            this.textureId = i;
            Assertions.checkArgument(((long) fArr.length) * 2 == ((long) fArr2.length) * 3);
            this.vertices = fArr;
            this.textureCoords = fArr2;
            this.mode = i2;
        }

        public int getVertexCount() {
            return this.vertices.length / 3;
        }
    }

    public Projection(Mesh mesh, int i) {
        this(mesh, mesh, i);
    }

    public static Projection createEquirectangular(int i) {
        return createEquirectangular(50.0f, 36, 72, 180.0f, 360.0f, i);
    }

    public Projection(Mesh mesh, Mesh mesh2, int i) {
        this.leftMesh = mesh;
        this.rightMesh = mesh2;
        this.stereoMode = i;
        this.singleMesh = mesh == mesh2;
    }

    public static Projection createEquirectangular(float f, int i, int i2, float f2, float f3, int i3) {
        int i4;
        int i5;
        int i6;
        float[] fArr;
        int i7 = i;
        int i8 = i2;
        Assertions.checkArgument(f > 0.0f);
        Assertions.checkArgument(i7 >= 1);
        Assertions.checkArgument(i8 >= 1);
        Assertions.checkArgument(f2 > 0.0f && f2 <= 180.0f);
        Assertions.checkArgument(f3 > 0.0f && f3 <= 360.0f);
        float radians = (float) Math.toRadians(f2);
        float radians2 = (float) Math.toRadians(f3);
        float f4 = radians / i7;
        float f5 = radians2 / i8;
        int i9 = i8 + 1;
        int i10 = ((i9 * 2) + 2) * i7;
        float[] fArr2 = new float[i10 * 3];
        float[] fArr3 = new float[i10 * 2];
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        while (i11 < i7) {
            float f6 = radians / 2.0f;
            float f7 = (i11 * f4) - f6;
            int i14 = i11 + 1;
            float f8 = (i14 * f4) - f6;
            int i15 = 0;
            while (i15 < i9) {
                float f9 = f7;
                int i16 = i14;
                int i17 = 0;
                while (i17 < 2) {
                    float f10 = i15 * f5;
                    float f11 = f5;
                    int i18 = i12 + 1;
                    int i19 = i15;
                    double d = f;
                    float f12 = f4;
                    int i20 = i17;
                    double d2 = (f10 + 3.1415927f) - (radians2 / 2.0f);
                    double d3 = i17 == 0 ? f9 : f8;
                    float[] fArr4 = fArr3;
                    float f13 = f8;
                    fArr2[i12] = -((float) (Math.cos(d3) * Math.sin(d2) * d));
                    int i21 = i18 + 1;
                    int i22 = i11;
                    fArr2[i18] = (float) (Math.sin(d3) * d);
                    int i23 = i21 + 1;
                    fArr2[i21] = (float) (Math.cos(d3) * Math.cos(d2) * d);
                    int i24 = i13 + 1;
                    fArr4[i13] = f10 / radians2;
                    int i25 = i24 + 1;
                    fArr4[i24] = ((i22 + i20) * f12) / radians;
                    if (i19 == 0 && i20 == 0) {
                        i4 = i2;
                        i5 = i19;
                        i6 = i20;
                    } else {
                        i4 = i2;
                        i5 = i19;
                        i6 = i20;
                        if (i5 != i4 || i6 != 1) {
                            fArr = fArr4;
                        }
                        i13 = i25;
                        i12 = i23;
                        i17 = i6 + 1;
                        i8 = i4;
                        i15 = i5;
                        fArr3 = fArr;
                        i11 = i22;
                        i9 = i9;
                        f5 = f11;
                        f4 = f12;
                        f8 = f13;
                    }
                    System.arraycopy(fArr2, i23 - 3, fArr2, i23, 3);
                    i23 += 3;
                    fArr = fArr4;
                    System.arraycopy(fArr, i25 - 2, fArr, i25, 2);
                    i25 += 2;
                    i13 = i25;
                    i12 = i23;
                    i17 = i6 + 1;
                    i8 = i4;
                    i15 = i5;
                    fArr3 = fArr;
                    i11 = i22;
                    i9 = i9;
                    f5 = f11;
                    f4 = f12;
                    f8 = f13;
                }
                float f14 = f4;
                int i26 = i15;
                int i27 = i8;
                int i28 = i26 + 1;
                f7 = f9;
                i14 = i16;
                i9 = i9;
                f5 = f5;
                f4 = f14;
                f8 = f8;
                i8 = i27;
                i15 = i28;
            }
            i7 = i;
            i11 = i14;
        }
        return new Projection(new Mesh(new SubMesh(0, fArr2, fArr3, 1)), i3);
    }
}
