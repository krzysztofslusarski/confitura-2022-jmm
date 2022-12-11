package pl.ks.good;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.III_Result;

@JCStressTest
@Outcome(id = "0, 0, 0", expect = Expect.ACCEPTABLE, desc = "Consistent state")
@Outcome(id = "1, 0, 0", expect = Expect.FORBIDDEN, desc = "How that happened?")
@Outcome(id = "1, 2, 0", expect = Expect.FORBIDDEN, desc = "How that happened?")
@Outcome(id = "1, 2, 3", expect = Expect.ACCEPTABLE, desc = "Consistent state")
@State
public class StateGoodVolatile {
    private volatile ValueHolder valueHolder;

    @Actor
    public void actor1() {
        this.valueHolder = new ValueHolder();
    }

    @Actor
    public void actor2(III_Result result) {
        ValueHolder valueHolder = this.valueHolder;
        if (valueHolder != null) {
            result.r1 = valueHolder.a;
            result.r2 = valueHolder.b;
            result.r3 = valueHolder.c;
        }
    }

    static class ValueHolder {
        int a, b, c;

        public ValueHolder() {
            a = 1;
            b = 2;
            c = 3;
        }
    }
}
