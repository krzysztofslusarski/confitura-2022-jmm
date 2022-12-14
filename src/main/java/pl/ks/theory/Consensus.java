package pl.ks.theory;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;

@JCStressTest
@Outcome(id = "0, 0", expect = Expect.ACCEPTABLE_INTERESTING, desc = "How that happened?")
@Outcome(id = "0, 1", expect = Expect.ACCEPTABLE, desc = "Consistent state")
@Outcome(id = "1, 0", expect = Expect.ACCEPTABLE, desc = "Consistent state")
@Outcome(id = "1, 1", expect = Expect.ACCEPTABLE, desc = "Consistent state")
@State
public class Consensus {
    private int x;
    private int y;

    @Actor
    public void actor1(II_Result r) {
        x = 1;
        r.r1 = y;
    }

    @Actor
    public void actor2(II_Result r) {
        y = 1;
        r.r2 = x;
    }
}
