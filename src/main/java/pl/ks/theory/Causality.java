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
public class Causality {
    int x;
    int y;

    @Actor
    public void actor1() {
        x = 1;
        y = 1;
    }

    @Actor
    public void actor2(II_Result r) {
        r.r1 = y;
        r.r2 = x;
    }
}
