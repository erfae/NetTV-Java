package androidx.constraintlayout.core;

/* JADX INFO: loaded from: classes.dex */
public class Cache {
    public Pools.Pool<ArrayRow> optimizedArrayRowPool = new Pools.SimplePool();
    public Pools.Pool<ArrayRow> arrayRowPool = new Pools.SimplePool();
    public Pools.Pool<SolverVariable> solverVariablePool = new Pools.SimplePool();
    public SolverVariable[] mIndexedVariables = new SolverVariable[32];
}
