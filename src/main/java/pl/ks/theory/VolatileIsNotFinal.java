package pl.ks.theory;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.L_Result;

import java.util.concurrent.atomic.AtomicLong;

@JCStressTest
@Outcome(id = "-1", expect = Expect.ACCEPTABLE, desc = "Consistent state")
@Outcome(id = "1", expect = Expect.ACCEPTABLE, desc = "Consistent state")
@Outcome(id = "0", expect = Expect.ACCEPTABLE_INTERESTING, desc = "How that happened?")
@State
/**
 * Doesn't work on X86, test it on AArch64. Works on AWS Graviton.
 */
public class VolatileIsNotFinal {
    private AtomicLong i;

    @Actor
    public void actor1() {
        i = new AtomicLong(1);
    }

    @Actor
    public void actor2(L_Result r) {
        AtomicLong local = i;
        if (local == null) {
            r.r1 = -1;
        } else {
            r.r1 = i.get();
        }
    }
}
