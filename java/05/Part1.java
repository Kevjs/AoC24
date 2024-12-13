import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Part1 {
    public static void main(String[] args) throws Exception {
        System.out.println("");
        Path filePath = Path.of("part_1.input");
        // Path filePath = Path.of("part_1_sample.input");
        List<String> lines = Files.readAllLines(filePath);

        Rules rules = new Rules();
        Updates updates = new Updates();

        Addable rn = rules;

        for (String line : lines) {
            if (line.isBlank()) {
                rn = updates;
                continue;
            }
            ;
            rn.add(line);
        }

        System.out.println(updates.correctUpdates(rules));
    }
}

interface Addable {
    public void add(String line);
}

class Updates implements Addable {
    List<Update> updates = new ArrayList<>();

    public void add(String line) {
        updates.add(new Update(line));
    }

    public int correctUpdates(Rules currentRules) {
        int total = 0;

        for (Update update : updates) {
            if (update.isCorrect(currentRules)) {
                total += update.getMiddle();
            }
        }

        return total;
    }
}

class Update {
    int[] nums;

    public Update(String input) {
        nums = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    public int getMiddle() {
        return nums[nums.length / 2];
    }

    public boolean isCorrect(Rules rules) {
        Set<Integer> currentSeen = new HashSet<>();
        for (int i = 0; i <  nums.length; i++) {
            if (rules.seeingValid(currentSeen, nums[i])) {
                currentSeen.add(nums[i]);
                continue;
            }
            return false;
        }
        return true;
    }
}

class Rules implements Addable {
    Map<Integer, AppearBefore> rules = new HashMap<>();

    public void add(String line) {
        int[] nums = Arrays.stream(line.split("\\|")).mapToInt(Integer::parseInt).toArray();
        if (nums.length != 2)
            return;
        this.add(nums[0], nums[1]);
    }

    public void add(int before, int after) {
        AppearBefore b = rules.get(before);
        if (b == null) {
            b = new AppearBefore(before);
        }
        b.add(after);
        rules.put(before, b);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Integer key : rules.keySet()) {
            builder.append(rules.get(key).toString() + "\n");
        }

        return builder.toString();
    }

    public boolean seeingValid(Set<Integer> prevSeen, int current) {
        AppearBefore numRule = rules.get(current);
        if (numRule == null)
            return true;

        for (Integer num : numRule.nums) {
            if (prevSeen.contains(num))
                return false;
        }

        return true;
    }
}

class AppearBefore {
    final int num;
    List<Integer> nums = new ArrayList<>();

    AppearBefore(int num) {
        this.num = num;
    }

    public String toString() {
        return num + " must appear before: " + nums.toString();
    }

    public void add(int num) {
        nums.add(num);
    }
}