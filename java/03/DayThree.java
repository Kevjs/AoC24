import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

class DayThree {
    public static void main(String[] args) {
        ArrayList<Mul> allMuls = getMuls();
        System.out.println("Found " + allMuls.size() + " multiplications");
        int total = allMuls.stream().mapToInt(Mul::calc).sum();
        System.out.println("Total: " + total);
    }

    public static ArrayList<Mul> getMuls() {
        ArrayList<Mul> result = new ArrayList<>();

        try {
            File input = new File("raw_input2");
            Scanner scan = new Scanner(input);

            while (scan.hasNextLine()) {
                char[] line = scan.nextLine().toCharArray();
                int i = 0, len = line.length;
                System.out.println("Length of line is: " + len);
                while (i < len) {
                    Resp check = Sequence.matches(line, i);
                    if (check == null) {
                        i++;
                        continue;
                    }

                    if (check.mul.isPresent()) {
                        result.add(check.mul.get());
                    }

                    i += check.index;
                }

            }

            scan.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

        return result;
    }
}

class Mul {
    public final int first;
    public final int second;

    public Mul(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public int calc() {
        return first * second;
    }
}

class Sequence {
    private static boolean enabled = true;

    public static Resp matches(char[] in, int i) {
        Resp r;
        if (enabled) {
            r = MulSequence.doesItMatch(in, i);
            if (r != null) {
                return r;
            }

            r = DoDontSequence.matchesDont(in, i);
            if (r != null)
                enabled = false;
            return r;
        }

        r = DoDontSequence.matchesDo(in, i);
        if (r != null)
            enabled = true;
        return r;
    }
}

class DoDontSequence {
    private static char[] doSequence = new char[] {
            'd',
            'o',
            '(',
            ')',
    };

    private static char[] dontSequence = new char[] {
            'd',
            'o',
            'n',
            '\'',
            't',
            '(',
            ')',
    };

    public static Resp matchesDo(char[] in, int i) {
        Resp r = new Resp();

        int z = 0;

        while (z < doSequence.length) {
            if (in[i + z] != doSequence[z++])
                return null;
        }

        r.index = z;

        return r;
    }

    public static Resp matchesDont(char[] in, int i) {
        Resp r = new Resp();

        int z = 0;

        while (z < doSequence.length) {
            if (in[i + z] != dontSequence[z++])
                return null;
        }

        r.index = z;

        return r;
    }

}

class MulSequence {
    private static char[] sequence = new char[] {
            'm',
            'u',
            'l',
            '(',
            ',',
            ')',
    };

    public static Resp doesItMatch(char[] in, int i) {
        int z = 0;

        Resp resp = new Resp();

        while (z <= 3) {
            if (in[i + z] != sequence[z++])
                return null;
        }

        if (in[i + z] < '0' || in[i + z] > '9') {
            return null;
        }
        int first_1 = z;
        z++;

        while (in[i + z] >= '0' && in[i + z] <= '9') {
            z++;
        }
        int first_2 = z - 1;

        if (in[i + (z++)] != sequence[4])
            return null;

        if (in[i + z] < '0' || in[i + z] > '9') {
            return null;
        }
        int second_1 = z;
        z++;

        while (in[i + z] >= '0' && in[i + z] <= '9') {
            z++;
        }
        int second_2 = z - 1;

        if (in[i + (z++)] != sequence[5])
            return null;

        String first = "";
        while (first_1 <= first_2) {
            first += in[i + (first_1++)];
        }
        String second = "";
        while (second_1 <= second_2) {
            second += in[i + (second_1++)];
        }

        resp.mul = Optional.of(new Mul(Integer.parseInt(first), Integer.parseInt(second)));
        resp.index = z;

        return resp;
    }
}

class Resp {
    public Optional<Mul> mul = Optional.empty();
    public int index;

    public Resp() {
    };
}