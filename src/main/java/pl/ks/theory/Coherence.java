package pl.ks.theory;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;

@JCStressTest
@Outcome(id = "0, 0", expect = Expect.ACCEPTABLE, desc = "Consistent state")
@Outcome(id = "1, 1", expect = Expect.ACCEPTABLE, desc = "Consistent state")
@Outcome(id = "0, 1", expect = Expect.ACCEPTABLE, desc = "Consistent state")
@Outcome(id = "1, 0", expect = Expect.ACCEPTABLE_INTERESTING, desc = "How that happened?")
@State
public class Coherence {
    private final ValueHolder h1 = new ValueHolder();
    private final ValueHolder h2 = h1;

    @Actor
    public void actor1() {
        h1.a = 1;
    }

    @Actor
    public void actor2(II_Result r) {
        ValueHolder h1 = this.h1;
        ValueHolder h2 = this.h2;

        h1.doNothingLetCompilerCheckForNullHere();
        h2.doNothingLetCompilerCheckForNullHere();

        r.r1 = h1.a;
        r.r2 = h2.a;
    }

    static class ValueHolder {
        int a;

        void doNothingLetCompilerCheckForNullHere() {
            hashCode();
        }
    }
}
