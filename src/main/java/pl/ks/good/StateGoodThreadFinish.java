package pl.ks.good;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.III_Result;

@JCStressTest
@Outcome(id = "0, 0, 0", expect = Expect.FORBIDDEN, desc = "How that happened?")
@Outcome(id = "1, 0, 0", expect = Expect.FORBIDDEN, desc = "How that happened?")
@Outcome(id = "1, 2, 0", expect = Expect.FORBIDDEN, desc = "How that happened?")
@Outcome(id = "1, 2, 3", expect = Expect.ACCEPTABLE, desc = "Consistent state")
@State
public class StateGoodThreadFinish {
    private ValueHolder valueHolder;

    @Actor
    public void actor1(III_Result result) {
        try {
            Thread thread = new Thread(() -> {
                this.valueHolder = new ValueHolder();
            });
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        result.r1 = valueHolder.a;
        result.r2 = valueHolder.b;
        result.r3 = valueHolder.c;
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
