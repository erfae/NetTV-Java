package com.google.android.material.color.utilities;

import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class HctSolver {
    public static final double[][] SCALED_DISCOUNT_FROM_LINRGB = {new double[]{0.001200833568784504d, 0.002389694492170889d, 2.795742885861124E-4d}, new double[]{5.891086651375999E-4d, 0.0029785502573438758d, 3.270666104008398E-4d}, new double[]{1.0146692491640572E-4d, 5.364214359186694E-4d, 0.0032979401770712076d}};
    public static final double[][] LINRGB_FROM_SCALED_DISCOUNT = {new double[]{1373.2198709594231d, -1100.4251190754821d, -7.278681089101213d}, new double[]{-271.815969077903d, 559.6580465940733d, -32.46047482791194d}, new double[]{1.9622899599665666d, -57.173814538844006d, 308.7233197812385d}};
    public static final double[] Y_FROM_LINRGB = {0.2126d, 0.7152d, 0.0722d};
    public static final double[] CRITICAL_PLANES = {0.015176349177441876d, 0.045529047532325624d, 0.07588174588720938d, 0.10623444424209313d, 0.13658714259697685d, 0.16693984095186062d, 0.19729253930674434d, 0.2276452376616281d, 0.2579979360165119d, 0.28835063437139563d, 0.3188300904430532d, 0.350925934958123d, 0.3848314933096426d, 0.42057480301049466d, 0.458183274052838d, 0.4976837250274023d, 0.5391024159806381d, 0.5824650784040898d, 0.6277969426914107d, 0.6751227633498623d, 0.7244668422128921d, 0.775853049866786d, 0.829304845476233d, 0.8848452951698498d, 0.942497089126609d, 1.0022825574869039d, 1.0642236851973577d, 1.1283421258858297d, 1.1946592148522128d, 1.2631959812511864d, 1.3339731595349034d, 1.407011200216447d, 1.4823302800086415d, 1.5599503113873272d, 1.6398909516233677d, 1.7221716113234105d, 1.8068114625156377d, 1.8938294463134073d, 1.9832442801866852d, 2.075074464868551d, 2.1693382909216234d, 2.2660538449872063d, 2.36523901573795d, 2.4669114995532007d, 2.5710888059345764d, 2.6777882626779785d, 2.7870270208169257d, 2.898822059350997d, 3.0131901897720907d, 3.1301480604002863d, 3.2497121605402226d, 3.3718988244681087d, 3.4967242352587946d, 3.624204428461639d, 3.754355295633311d, 3.887192587735158d, 4.022731918402185d, 4.160988767090289d, 4.301978482107941d, 4.445716283538092d, 4.592217266055746d, 4.741496401646282d, 4.893568542229298d, 5.048448422192488d, 5.20615066083972d, 5.3666897647573375d, 5.5300801301023865d, 5.696336044816294d, 5.865471690767354d, 6.037501145825082d, 6.212438385869475d, 6.390297286737924d, 6.571091626112461d, 6.7548350853498045d, 6.941541251256611d, 7.131223617812143d, 7.323895587840543d, 7.5195704746346665d, 7.7182615035334345d, 7.919981813454504d, 8.124744458384042d, 8.332562408825165d, 8.543448553206703d, 8.757415699253682d, 8.974476575321063d, 9.194643831691977d, 9.417930041841839d, 9.644347703669503d, 9.873909240696694d, 10.106627003236781d, 10.342513269534024d, 10.58158024687427d, 10.8238400726681d, 11.069304815507364d, 11.317986476196008d, 11.569896988756009d, 11.825048221409341d, 12.083451977536606d, 12.345119996613247d, 12.610063955123938d, 12.878295467455942d, 13.149826086772048d, 13.42466730586372d, 13.702830557985108d, 13.984327217668513d, 14.269168601521828d, 14.55736596900856d, 14.848930523210871d, 15.143873411576273d, 15.44220572664832d, 15.743938506781891d, 16.04908273684337d, 16.35764934889634d, 16.66964922287304d, 16.985093187232053d, 17.30399201960269d, 17.62635644741625d, 17.95219714852476d, 18.281524751807332d, 18.614349837764564d, 18.95068293910138d, 19.290534541298456d, 19.633915083172692d, 19.98083495742689d, 20.331304511189067d, 20.685334046541502d, 21.042933821039977d, 21.404114048223256d, 21.76888489811322d, 22.137256497705877d, 22.50923893145328d, 22.884842241736916d, 23.264076429332462d, 23.6469514538663d, 24.033477234264016d, 24.42366364919083d, 24.817520537484558d, 25.21505769858089d, 25.61628489293138d, 26.021211842414342d, 26.429848230738664d, 26.842203703840827d, 27.258287870275353d, 27.678110301598522d, 28.10168053274597d, 28.529008062403893d, 28.96010235337422d, 29.39497283293396d, 29.83362889318845d, 30.276079891419332d, 30.722335150426627d, 31.172403958865512d, 31.62629557157785d, 32.08401920991837d, 32.54558406207592d, 33.010999283389665d, 33.4802739966603d, 33.953417292456834d, 34.430438229418264d, 34.911345834551085d, 35.39614910352207d, 35.88485700094671d, 36.37747846067349d, 36.87402238606382d, 37.37449765026789d, 37.87891309649659d, 38.38727753828926d, 38.89959975977785d, 39.41588851594697d, 39.93615253289054d, 40.460400508064545d, 40.98864111053629d, 41.520882981230194d, 42.05713473317016d, 42.597404951718396d, 43.141702194811224d, 43.6900349931913d, 44.24241185063697d, 44.798841244188324d, 45.35933162437017d, 45.92389141541209d, 46.49252901546552d, 47.065252796817916d, 47.64207110610409d, 48.22299226451468d, 48.808024568002054d, 49.3971762874833d, 49.9904556690408d, 50.587870934119984d, 51.189430279724725d, 51.79514187861014d, 52.40501387947288d, 53.0190544071392d, 53.637271562750364d, 54.259673423945976d, 54.88626804504493d, 55.517063457223934d, 56.15206766869424d, 56.79128866487574d, 57.43473440856916d, 58.08241284012621d, 58.734331877617365d, 59.39049941699807d, 60.05092333227251d, 60.715611475655585d, 61.38457167773311d, 62.057811747619894d, 62.7353394731159d, 63.417162620860914d, 64.10328893648692d, 64.79372614476921d, 65.48848194977529d, 66.18756403501224d, 66.89098006357258d, 67.59873767827808d, 68.31084450182222d, 69.02730813691093d, 69.74813616640164d, 70.47333615344107d, 71.20291564160104d, 71.93688215501312d, 72.67524319850172d, 73.41800625771542d, 74.16517879925733d, 74.9167682708136d, 75.67278210128072d, 76.43322770089146d, 77.1981124613393d, 77.96744375590167d, 78.74122893956174d, 79.51947534912904d, 80.30219030335869d, 81.08938110306934d, 81.88105503125999d, 82.67721935322541d, 83.4778813166706d, 84.28304815182372d, 85.09272707154808d, 85.90692527145302d, 86.72564993000343d, 87.54890820862819d, 88.3767072518277d, 89.2090541872801d, 90.04595612594655d, 90.88742016217518d, 91.73345337380438d, 92.58406282226491d, 93.43925555268066d, 94.29903859396902d, 95.16341895893969d, 96.03240364439274d, 96.9059996312159d, 97.78421388448044d, 98.6670533535366d, 99.55452497210776d};

    private HctSolver() {
    }

    public static boolean areInCyclicOrder(double d, double d2, double d3) {
        return ((d2 - d) + 25.132741228718345d) % 6.283185307179586d < ((d3 - d) + 25.132741228718345d) % 6.283185307179586d;
    }

    public static double chromaticAdaptation(double d) {
        double dPow = Math.pow(Math.abs(d), 0.42d);
        return ((((double) MathUtils.signum(d)) * 400.0d) * dPow) / (dPow + 27.13d);
    }

    public static double hueOf(double[] dArr) {
        double[] dArrMatrixMultiply = MathUtils.matrixMultiply(dArr, SCALED_DISCOUNT_FROM_LINRGB);
        double dChromaticAdaptation = chromaticAdaptation(dArrMatrixMultiply[0]);
        double dChromaticAdaptation2 = chromaticAdaptation(dArrMatrixMultiply[1]);
        double dChromaticAdaptation3 = chromaticAdaptation(dArrMatrixMultiply[2]);
        return Math.atan2(((dChromaticAdaptation + dChromaticAdaptation2) - (dChromaticAdaptation3 * 2.0d)) / 9.0d, ((((-12.0d) * dChromaticAdaptation2) + (dChromaticAdaptation * 11.0d)) + dChromaticAdaptation3) / 11.0d);
    }

    public static double inverseChromaticAdaptation(double d) {
        double dAbs = Math.abs(d);
        double dMax = Math.max(0.0d, (27.13d * dAbs) / (400.0d - dAbs));
        return Math.pow(dMax, 2.380952380952381d) * ((double) MathUtils.signum(d));
    }

    public static boolean isBounded(double d) {
        return 0.0d <= d && d <= 100.0d;
    }

    public static Cam16 solveToCam(double d, double d2, double d3) {
        return Cam16.fromInt(solveToInt(d, d2, d3));
    }

    /* JADX WARN: Code duplicated, block: B:106:0x0365  */
    /* JADX WARN: Code duplicated, block: B:107:0x0368  */
    /* JADX WARN: Code duplicated, block: B:109:0x036e  */
    /* JADX WARN: Code duplicated, block: B:45:0x01cd A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:46:0x01ce  */
    /* JADX WARN: Code duplicated, block: B:49:0x01df  */
    /* JADX WARN: Code duplicated, block: B:51:0x01ee  */
    /* JADX WARN: Code duplicated, block: B:52:0x01f1  */
    /* JADX WARN: Code duplicated, block: B:55:0x01f7  */
    /* JADX WARN: Code duplicated, block: B:56:0x01fa  */
    /* JADX WARN: Code duplicated, block: B:59:0x01ff  */
    /* JADX WARN: Code duplicated, block: B:61:0x0214  */
    /* JADX WARN: Code duplicated, block: B:62:0x021e  */
    /* JADX WARN: Code duplicated, block: B:63:0x0222  */
    /* JADX WARN: Code duplicated, block: B:65:0x0229  */
    /* JADX WARN: Code duplicated, block: B:67:0x0239  */
    /* JADX WARN: Code duplicated, block: B:68:0x0246  */
    /* JADX WARN: Code duplicated, block: B:69:0x024c  */
    /* JADX WARN: Code duplicated, block: B:71:0x025c  */
    /* JADX WARN: Code duplicated, block: B:72:0x0267  */
    /* JADX WARN: Code duplicated, block: B:76:0x0273  */
    /* JADX WARN: Code duplicated, block: B:78:0x0279  */
    /* JADX WARN: Code duplicated, block: B:79:0x027e  */
    /* JADX WARN: Code duplicated, block: B:83:0x028d  */
    /* JADX WARN: Code duplicated, block: B:86:0x029c  */
    /* JADX WARN: Code duplicated, block: B:87:0x029e  */
    /* JADX WARN: Code duplicated, block: B:93:0x02bd  */
    /* JADX WARN: Code duplicated, block: B:95:0x02c5  */
    /* JADX WARN: Code duplicated, block: B:97:0x02cf  */
    /* JADX WARN: Code duplicated, block: B:98:0x02e7  */
    public static int solveToInt(double d, double d2, double d3) {
        int iArgbFromLinrgb;
        double[] dArr;
        double[] dArr2;
        double d4;
        double d5;
        boolean z;
        boolean z2;
        int i;
        double[] dArr3;
        double dHueOf;
        double[] dArr4;
        int i2;
        int iCeil;
        double dFloor;
        int i3;
        int i4;
        int iFloor;
        double[] dArr5;
        double dHueOf2;
        double d6;
        double d7;
        double d8;
        double d9;
        double d10;
        double[] dArr6;
        double d11;
        double d12;
        double dHueOf3;
        double d13;
        if (d2 < 1.0E-4d || d3 < 1.0E-4d || d3 > 99.9999d) {
            return ColorUtils.argbFromLstar(d3);
        }
        double dSanitizeDegreesDouble = (MathUtils.sanitizeDegreesDouble(d) / 180.0d) * 3.141592653589793d;
        double dYFromLstar = ColorUtils.yFromLstar(d3);
        double dSqrt = Math.sqrt(dYFromLstar) * 11.0d;
        ViewingConditions viewingConditions = ViewingConditions.DEFAULT;
        double dPow = 1.0d / Math.pow(1.64d - Math.pow(0.29d, viewingConditions.getN()), 0.73d);
        double ncb = viewingConditions.getNcb() * viewingConditions.getNc() * (Math.cos(dSanitizeDegreesDouble + 2.0d) + 3.8d) * 0.25d * 3846.153846153846d;
        double dSin = Math.sin(dSanitizeDegreesDouble);
        double dCos = Math.cos(dSanitizeDegreesDouble);
        int i5 = 0;
        while (true) {
            if (i5 < 5) {
                double d14 = dSqrt / 100.0d;
                double dPow2 = Math.pow(((d2 == 0.0d || dSqrt == 0.0d) ? 0.0d : d2 / Math.sqrt(d14)) * dPow, 1.1111111111111112d);
                double d15 = dPow;
                double dPow3 = (Math.pow(d14, (1.0d / viewingConditions.getC()) / viewingConditions.getZ()) * viewingConditions.getAw()) / viewingConditions.getNbb();
                double d16 = ncb;
                double d17 = (((0.305d + dPow3) * 23.0d) * dPow2) / (((dPow2 * 108.0d) * dSin) + (((dPow2 * 11.0d) * dCos) + (23.0d * d16)));
                double d18 = d17 * dCos;
                double d19 = d17 * dSin;
                double d20 = dPow3 * 460.0d;
                double[] dArrMatrixMultiply = MathUtils.matrixMultiply(new double[]{inverseChromaticAdaptation(((288.0d * d19) + ((451.0d * d18) + d20)) / 1403.0d), inverseChromaticAdaptation(((d20 - (891.0d * d18)) - (261.0d * d19)) / 1403.0d), inverseChromaticAdaptation(((d20 - (d18 * 220.0d)) - (d19 * 6300.0d)) / 1403.0d)}, LINRGB_FROM_SCALED_DISCOUNT);
                if (dArrMatrixMultiply[0] >= 0.0d && dArrMatrixMultiply[1] >= 0.0d && dArrMatrixMultiply[2] >= 0.0d) {
                    double[] dArr7 = Y_FROM_LINRGB;
                    double d21 = (dArr7[2] * dArrMatrixMultiply[2]) + (dArr7[1] * dArrMatrixMultiply[1]) + (dArr7[0] * dArrMatrixMultiply[0]);
                    if (d21 > 0.0d) {
                        if (i5 != 4) {
                            double d22 = d21 - dYFromLstar;
                            if (Math.abs(d22) >= 0.002d) {
                                dSqrt -= (d22 * dSqrt) / (d21 * 2.0d);
                                i5++;
                                ncb = d16;
                                dPow = d15;
                            }
                        }
                        if (dArrMatrixMultiply[0] <= 100.01d && dArrMatrixMultiply[1] <= 100.01d && dArrMatrixMultiply[2] <= 100.01d) {
                            iArgbFromLinrgb = ColorUtils.argbFromLinrgb(dArrMatrixMultiply);
                        }
                        if (iArgbFromLinrgb != 0) {
                            return iArgbFromLinrgb;
                        }
                        dArr = new double[]{-1.0d, -1.0d, -1.0d};
                        dArr2 = dArr;
                        d4 = 0.0d;
                        d5 = 0.0d;
                        z = false;
                        z2 = true;
                        for (i = 0; i < 12; i++) {
                            double[] dArr8 = Y_FROM_LINRGB;
                            d6 = dArr8[0];
                            d7 = dArr8[1];
                            d8 = dArr8[2];
                            if (i % 4 <= 1) {
                                d9 = 0.0d;
                            } else {
                                d9 = 100.0d;
                            }
                            if (i % 2 == 0) {
                                d10 = 0.0d;
                            } else {
                                d10 = 100.0d;
                            }
                            if (i < 4) {
                                d13 = ((dYFromLstar - (d7 * d9)) - (d8 * d10)) / d6;
                                dArr6 = dArr2;
                                dArr2 = new double[]{-1.0d, -1.0d, -1.0d};
                                if (isBounded(d13)) {
                                    dArr2[0] = d13;
                                    dArr2[1] = d9;
                                    dArr2[2] = d10;
                                }
                            } else {
                                dArr6 = dArr2;
                                if (i < 8) {
                                    d12 = ((dYFromLstar - (d6 * d10)) - (d8 * d9)) / d7;
                                    if (isBounded(d12)) {
                                        dArr2 = new double[]{d10, d12, d9};
                                    } else {
                                        dArr2 = new double[]{-1.0d, -1.0d, -1.0d};
                                    }
                                } else {
                                    d11 = ((dYFromLstar - (d6 * d9)) - (d7 * d10)) / d8;
                                    if (isBounded(d11)) {
                                        dArr2 = new double[]{d9, d10, d11};
                                    } else {
                                        dArr2 = new double[]{-1.0d, -1.0d, -1.0d};
                                    }
                                }
                            }
                            if (dArr2[0] < 0.0d) {
                                dArr2 = dArr6;
                            } else {
                                dHueOf3 = hueOf(dArr2);
                                if (!z) {
                                    dArr = dArr2;
                                    d4 = dHueOf3;
                                    d5 = d4;
                                    z = true;
                                } else if (!z2 || areInCyclicOrder(d4, dHueOf3, d5)) {
                                    if (areInCyclicOrder(d4, dSanitizeDegreesDouble, dHueOf3)) {
                                        d5 = dHueOf3;
                                    } else {
                                        dArr = dArr2;
                                        d4 = dHueOf3;
                                        dArr2 = dArr6;
                                    }
                                    z2 = false;
                                } else {
                                    dArr2 = dArr6;
                                }
                            }
                        }
                        double[][] dArr9 = {dArr, dArr2};
                        dArr3 = dArr9[0];
                        dHueOf = hueOf(dArr3);
                        dArr4 = dArr9[1];
                        for (i2 = 0; i2 < 3; i2++) {
                            if (dArr3[i2] == dArr4[i2]) {
                                if (dArr3[i2] < dArr4[i2]) {
                                    iCeil = (int) Math.floor(trueDelinearized(dArr3[i2]) - 0.5d);
                                    dFloor = Math.ceil(trueDelinearized(dArr4[i2]) - 0.5d);
                                } else {
                                    iCeil = (int) Math.ceil(trueDelinearized(dArr3[i2]) - 0.5d);
                                    dFloor = Math.floor(trueDelinearized(dArr4[i2]) - 0.5d);
                                }
                                i3 = (int) dFloor;
                                for (i4 = 0; i4 < 8 && Math.abs(i3 - iCeil) > 1; i4++) {
                                    iFloor = (int) Math.floor(((double) (iCeil + i3)) / 2.0d);
                                    double d23 = CRITICAL_PLANES[iFloor];
                                    double d24 = dArr3[i2];
                                    double d25 = (d23 - d24) / (dArr4[i2] - d24);
                                    dArr5 = new double[]{((dArr4[0] - dArr3[0]) * d25) + dArr3[0], ((dArr4[1] - dArr3[1]) * d25) + dArr3[1], ((dArr4[2] - dArr3[2]) * d25) + dArr3[2]};
                                    dHueOf2 = hueOf(dArr5);
                                    if (areInCyclicOrder(dHueOf, dSanitizeDegreesDouble, dHueOf2)) {
                                        i3 = iFloor;
                                        dArr4 = dArr5;
                                    } else {
                                        iCeil = iFloor;
                                        dArr3 = dArr5;
                                        dHueOf = dHueOf2;
                                    }
                                }
                            }
                        }
                        return ColorUtils.argbFromLinrgb(new double[]{(dArr3[0] + dArr4[0]) / 2.0d, (dArr3[1] + dArr4[1]) / 2.0d, (dArr3[2] + dArr4[2]) / 2.0d});
                    }
                }
            }
            iArgbFromLinrgb = 0;
            if (iArgbFromLinrgb != 0) {
                return iArgbFromLinrgb;
            }
            dArr = new double[]{-1.0d, -1.0d, -1.0d};
            dArr2 = dArr;
            d4 = 0.0d;
            d5 = 0.0d;
            z = false;
            z2 = true;
            while (i < 12) {
                double[] dArr10 = Y_FROM_LINRGB;
                d6 = dArr10[0];
                d7 = dArr10[1];
                d8 = dArr10[2];
                if (i % 4 <= 1) {
                    d9 = 0.0d;
                } else {
                    d9 = 100.0d;
                }
                if (i % 2 == 0) {
                    d10 = 0.0d;
                } else {
                    d10 = 100.0d;
                }
                if (i < 4) {
                    d13 = ((dYFromLstar - (d7 * d9)) - (d8 * d10)) / d6;
                    dArr6 = dArr2;
                    dArr2 = new double[]{-1.0d, -1.0d, -1.0d};
                    if (isBounded(d13)) {
                        dArr2[0] = d13;
                        dArr2[1] = d9;
                        dArr2[2] = d10;
                    }
                } else {
                    dArr6 = dArr2;
                    if (i < 8) {
                        d12 = ((dYFromLstar - (d6 * d10)) - (d8 * d9)) / d7;
                        if (isBounded(d12)) {
                            dArr2 = new double[]{d10, d12, d9};
                        } else {
                            dArr2 = new double[]{-1.0d, -1.0d, -1.0d};
                        }
                    } else {
                        d11 = ((dYFromLstar - (d6 * d9)) - (d7 * d10)) / d8;
                        if (isBounded(d11)) {
                            dArr2 = new double[]{d9, d10, d11};
                        } else {
                            dArr2 = new double[]{-1.0d, -1.0d, -1.0d};
                        }
                    }
                }
                if (dArr2[0] < 0.0d) {
                    dArr2 = dArr6;
                } else {
                    dHueOf3 = hueOf(dArr2);
                    if (!z) {
                        dArr = dArr2;
                        d4 = dHueOf3;
                        d5 = d4;
                        z = true;
                    } else {
                        if (z2) {
                        }
                        if (areInCyclicOrder(d4, dSanitizeDegreesDouble, dHueOf3)) {
                            d5 = dHueOf3;
                        } else {
                            dArr = dArr2;
                            d4 = dHueOf3;
                            dArr2 = dArr6;
                        }
                        z2 = false;
                    }
                }
            }
            double[][] dArr11 = {dArr, dArr2};
            dArr3 = dArr11[0];
            dHueOf = hueOf(dArr3);
            dArr4 = dArr11[1];
            while (i2 < 3) {
                if (dArr3[i2] == dArr4[i2]) {
                    if (dArr3[i2] < dArr4[i2]) {
                        iCeil = (int) Math.floor(trueDelinearized(dArr3[i2]) - 0.5d);
                        dFloor = Math.ceil(trueDelinearized(dArr4[i2]) - 0.5d);
                    } else {
                        iCeil = (int) Math.ceil(trueDelinearized(dArr3[i2]) - 0.5d);
                        dFloor = Math.floor(trueDelinearized(dArr4[i2]) - 0.5d);
                    }
                    i3 = (int) dFloor;
                    while (i4 < 8) {
                        iFloor = (int) Math.floor(((double) (iCeil + i3)) / 2.0d);
                        double d26 = CRITICAL_PLANES[iFloor];
                        double d27 = dArr3[i2];
                        double d28 = (d26 - d27) / (dArr4[i2] - d27);
                        dArr5 = new double[]{((dArr4[0] - dArr3[0]) * d28) + dArr3[0], ((dArr4[1] - dArr3[1]) * d28) + dArr3[1], ((dArr4[2] - dArr3[2]) * d28) + dArr3[2]};
                        dHueOf2 = hueOf(dArr5);
                        if (areInCyclicOrder(dHueOf, dSanitizeDegreesDouble, dHueOf2)) {
                            i3 = iFloor;
                            dArr4 = dArr5;
                        } else {
                            iCeil = iFloor;
                            dArr3 = dArr5;
                            dHueOf = dHueOf2;
                        }
                    }
                }
            }
            return ColorUtils.argbFromLinrgb(new double[]{(dArr3[0] + dArr4[0]) / 2.0d, (dArr3[1] + dArr4[1]) / 2.0d, (dArr3[2] + dArr4[2]) / 2.0d});
        }
    }

    public static double trueDelinearized(double d) {
        double d2 = d / 100.0d;
        return (d2 <= 0.0031308d ? d2 * 12.92d : (Math.pow(d2, 0.4166666666666667d) * 1.055d) - 0.055d) * 255.0d;
    }
}
