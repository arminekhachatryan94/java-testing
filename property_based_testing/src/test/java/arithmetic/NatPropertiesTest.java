package arithmetic;

import com.pholser.junit.quickcheck.generator.Size;
import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.junit.Assume.assumeTrue;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

@RunWith(JUnitQuickcheck.class)
public class NatPropertiesTest {
    // a == a
    @Property
    public void numberEqualsSelf(@From(NatGenerator.class) @Size(max = 10) final Nat a) {
        assertEquals(a, a);
    }

    // a + b == b + a
    @Property
    public void additionCommutativeProperty(@From(NatGenerator.class) @Size(max = 10) final Nat a, @From(NatGenerator.class) @Size(max = 10) final Nat b) {
        assertEquals(a.add(b), b.add(a));
    }

    // (a + b) + c == a + (b + c)
    @Property
    public void additionAssociativeProperty(
        @From(NatGenerator.class) @Size(max = 10) final Nat a,
        @From(NatGenerator.class) @Size(max = 10) final Nat b,
        @From(NatGenerator.class) @Size(max = 10) final Nat c
    ) {
        assertEquals((a.add(b)).add(c), a.add(b.add(c)));
    }

    // { (a + a + ...) } b times == a * b
    // { (b + b + ...) } a times == b * a
    @Property
    public void sumOfNumberWithItselfNTimesEqualsNumberMultipliedByN(
        @From(NatGenerator.class) @Size(min = 1, max = 10) final Nat a,
        @From(NatGenerator.class) @Size(min = 1, max = 10) final Nat b
    ) {
        int count_a = (int)(a.toString().chars().filter(ch -> ch == 'S').count());
        int count_b = (int)(b.toString().chars().filter(ch -> ch == 'S').count());

        Nat a_copy = a;
        Nat b_copy = b;

        for( int i = 1; i < count_b; i++ ){
            a_copy = a_copy.add(a);
        }

        for( int i = 1; i < count_a; i++ ){
            b_copy = b_copy.add(b);
        }

        assertEquals(a_copy, a.multiply(b));
        assertEquals(b_copy, b.multiply(a));
    }

    // TODO: write your properties below
    // Put a comment before them explaining what sort of property you're testing
}
