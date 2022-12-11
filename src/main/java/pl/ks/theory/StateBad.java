package pl.ks.theory;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.IIII_Result;

@JCStressTest
@Outcome(id = "0, 0, 0, 0", expect = Expect.ACCEPTABLE, desc = "Consistent state")
@Outcome(id = "1, 2, 3, 4", expect = Expect.ACCEPTABLE, desc = "Consistent state")
@Outcome(expect = Expect.ACCEPTABLE_INTERESTING, desc = "How that happened?")
@State
public class StateBad {
    private int start = 1;
    private ValueHolder valueHolder;

    @Actor
    public void actor1() {
        valueHolder = new ValueHolder(start);
    }

    @Actor
    public void actor2(IIII_Result result) {
        ValueHolder valueHolder = this.valueHolder;
        if (valueHolder != null) {
            result.r1 = valueHolder.a;
            result.r2 = valueHolder.b;
            result.r3 = valueHolder.c;
            result.r4 = valueHolder.d;
        }
    }

    static class ValueHolder {
        int a, b, c, d;

        public ValueHolder(int start) {
            a = start;
            b = a + 1;
            c = b + 1;
            d = c + 1;
        }
    }
}
