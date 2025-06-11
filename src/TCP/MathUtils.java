package TCP;

public class MathUtils {
    public static boolean areCoprime(int a, int b) {
        return gcd(a, b) == 1;
    }

    private static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }


}
