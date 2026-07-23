package androidx.constraintlayout.motion.utils;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class ViewSpline extends SplineSet {
    private static final String TAG = "ViewSpline";

    public static class AlphaSet extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public void setProperty(View view, float t) {
            view.setAlpha(get(t));
        }
    }

    public static class CustomSet extends ViewSpline {
        public SparseArray<ConstraintAttribute> mConstraintAttributeList;
        public float[] mTempValues;

        public CustomSet(String attribute, SparseArray<ConstraintAttribute> attrList) {
            String str = attribute.split(",")[1];
            this.mConstraintAttributeList = attrList;
        }

        @Override // androidx.constraintlayout.core.motion.utils.SplineSet
        public void setPoint(int position, float value) {
            throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute)");
        }

        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public void setProperty(View view, float t) {
            this.mCurveFit.getPos(t, this.mTempValues);
            CustomSupport.setInterpolatedValue(this.mConstraintAttributeList.valueAt(0), view, this.mTempValues);
        }

        @Override // androidx.constraintlayout.core.motion.utils.SplineSet
        public void setup(int curveType) {
            int size = this.mConstraintAttributeList.size();
            int iNumberOfInterpolatedValues = this.mConstraintAttributeList.valueAt(0).numberOfInterpolatedValues();
            double[] dArr = new double[size];
            this.mTempValues = new float[iNumberOfInterpolatedValues];
            double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) double.class, size, iNumberOfInterpolatedValues);
            for (int i = 0; i < size; i++) {
                int iKeyAt = this.mConstraintAttributeList.keyAt(i);
                ConstraintAttribute constraintAttributeValueAt = this.mConstraintAttributeList.valueAt(i);
                dArr[i] = ((double) iKeyAt) * 0.01d;
                constraintAttributeValueAt.getValuesToInterpolate(this.mTempValues);
                int i2 = 0;
                while (true) {
                    float[] fArr = this.mTempValues;
                    if (i2 < fArr.length) {
                        dArr2[i][i2] = fArr[i2];
                        i2++;
                    }
                }
            }
            this.mCurveFit = CurveFit.get(curveType, dArr, dArr2);
        }

        public void setPoint(int position, ConstraintAttribute value) {
            this.mConstraintAttributeList.append(position, value);
        }
    }

    public static class ElevationSet extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public void setProperty(View view, float t) {
            view.setElevation(get(t));
        }
    }

    public static class PathRotate extends ViewSpline {
        public void setPathRotate(View view, float t, double dx, double dy) {
            view.setRotation(get(t) + ((float) Math.toDegrees(Math.atan2(dy, dx))));
        }

        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public void setProperty(View view, float t) {
        }
    }

    public static class PivotXset extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public void setProperty(View view, float t) {
            view.setPivotX(get(t));
        }
    }

    public static class PivotYset extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public void setProperty(View view, float t) {
            view.setPivotY(get(t));
        }
    }

    public static class ProgressSet extends ViewSpline {
        public boolean mNoMethod = false;

        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public void setProperty(View view, float t) {
            if (view instanceof MotionLayout) {
                ((MotionLayout) view).setProgress(get(t));
                return;
            }
            if (this.mNoMethod) {
                return;
            }
            Method method = null;
            try {
                method = view.getClass().getMethod("setProgress", Float.TYPE);
            } catch (NoSuchMethodException unused) {
                this.mNoMethod = true;
            }
            if (method != null) {
                try {
                    method.invoke(view, Float.valueOf(get(t)));
                } catch (IllegalAccessException e) {
                    Log.e(ViewSpline.TAG, "unable to setProgress", e);
                } catch (InvocationTargetException e2) {
                    Log.e(ViewSpline.TAG, "unable to setProgress", e2);
                }
            }
        }
    }

    public static class RotationSet extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public void setProperty(View view, float t) {
            view.setRotation(get(t));
        }
    }

    public static class RotationXset extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public void setProperty(View view, float t) {
            view.setRotationX(get(t));
        }
    }

    public static class RotationYset extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public void setProperty(View view, float t) {
            view.setRotationY(get(t));
        }
    }

    public static class ScaleXset extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public void setProperty(View view, float t) {
            view.setScaleX(get(t));
        }
    }

    public static class ScaleYset extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public void setProperty(View view, float t) {
            view.setScaleY(get(t));
        }
    }

    public static class TranslationXset extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public void setProperty(View view, float t) {
            view.setTranslationX(get(t));
        }
    }

    public static class TranslationYset extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public void setProperty(View view, float t) {
            view.setTranslationY(get(t));
        }
    }

    public static class TranslationZset extends ViewSpline {
        @Override // androidx.constraintlayout.motion.utils.ViewSpline
        public void setProperty(View view, float t) {
            view.setTranslationZ(get(t));
        }
    }

    public static ViewSpline makeCustomSpline(String str, SparseArray<ConstraintAttribute> attrList) {
        return new CustomSet(str, attrList);
    }

    public static ViewSpline makeSpline(String str) {
        Objects.requireNonNull(str);
        switch (str) {
            case "rotationX":
                return new RotationXset();
            case "rotationY":
                return new RotationYset();
            case "translationX":
                return new TranslationXset();
            case "translationY":
                return new TranslationYset();
            case "translationZ":
                return new TranslationZset();
            case "progress":
                return new ProgressSet();
            case "scaleX":
                return new ScaleXset();
            case "scaleY":
                return new ScaleYset();
            case "waveVariesBy":
                return new AlphaSet();
            case "transformPivotX":
                return new PivotXset();
            case "transformPivotY":
                return new PivotYset();
            case "rotation":
                return new RotationSet();
            case "elevation":
                return new ElevationSet();
            case "transitionPathRotate":
                return new PathRotate();
            case "alpha":
                return new AlphaSet();
            case "waveOffset":
                return new AlphaSet();
            default:
                return null;
        }
    }

    public abstract void setProperty(View view, float t);
}
