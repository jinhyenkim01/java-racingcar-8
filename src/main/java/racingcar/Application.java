package racingcar;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Application {
    static boolean containsInvalidCharacter(String str) {
        for (int i = 0; i < str.length(); ) {
            int charPointer = str.codePointAt(i);
            if (Character.isWhitespace(charPointer)) {
                return true;
            }
            if (Character.isISOControl(charPointer)) {
                return true;
            }
            i = i + Character.charCount(charPointer);
        }
        return false;
    }

    static boolean invalidCarNameLength(String str) {
        if (str.isEmpty()) {
            return true;
        }
        return str.length() > 5;
    }

    static String[] inputCarName() {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        String carNameInput = Console.readLine();
        String[] carNames = carNameInput.split(",");
        for (int i = 0; i < carNames.length; i++) {
            if (containsInvalidCharacter(carNames[i])) {
                throw new IllegalArgumentException("자동차 이름에 사용 불가능한 문자가 있습니다. 화이트스페이스 문자 및 ISO 제어 문자는 사용 불가능합니다.");
            }
            if (invalidCarNameLength(carNames[i])) {
                throw new IllegalArgumentException("자동차 이름은 최소 1자 이상, 최대 5자 이하여야 합니다.");
            }
        }
        return carNames;
    }

    static int inputNoOfRaces() {
        System.out.println("시도할 횟수는 몇 회인가요?");
        String attemptNumberInput = Console.readLine();
        int attemptNumber;
        try {
            attemptNumber = Integer.parseInt(attemptNumberInput);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("시도 횟수는 자연수여야 합니다.");
        }
        if (attemptNumber < 1) {
            throw new IllegalArgumentException("시도 횟수는 자연수여야 합니다.");
        }
        return attemptNumber;
    }

    static int determineAdvanceDistance() {
        int randomInt = Randoms.pickNumberInRange(0, 9);
        if (randomInt > 3) {
            return 1;
        } else {
            return 0;
        }
    }

    static int[] simulateOneMovementCycle(int[] travelDistance) {
        for (int i = 0; i < travelDistance.length; i++) {
            travelDistance[i] = travelDistance[i] + determineAdvanceDistance();
        }
        return travelDistance;
    }

    static void printCurrentSituation(String[] carNames, int[] travelDistance) {
        System.out.println("실행 결과");
        for (int i = 0; i < travelDistance.length; i++) {
            System.out.print(carNames[i] + " : ");
            for (int j = 0; j < travelDistance[i]; j++) {
                System.out.print("-");
            }
            System.out.println();
        }
        System.out.println();
    }

    static List<String> determineWinners(String[] carNames, int[] travelDistance, List<String> winners) {
        int maxDistance = -1;
        for (int i = 0; i < travelDistance.length; i++) {
            if (travelDistance[i] > maxDistance) {
                winners.clear();
                winners.add(carNames[i]);
                maxDistance = travelDistance[i];
            } else if (travelDistance[i] == maxDistance) {
                winners.add(carNames[i]);
            }
        }
        return winners;
    }

    static void printWinners(List<String> winners) {
        System.out.print("최종 우승자 : ");
        Iterator<String> iterator = winners.iterator();
        while (iterator.hasNext()) {
            String winner = iterator.next();
            System.out.print(winner);
            if (iterator.hasNext()) {
                System.out.print(", ");
            }
        }
    }

    public static void main(String[] args) {
        String[] carNames = inputCarName();
        int noOfRaces = inputNoOfRaces();
        int[] travelDistance = new int[carNames.length];
        List<String> winners = new ArrayList<>();

        for (int i = 0; i < noOfRaces; i++) {
            travelDistance = simulateOneMovementCycle(travelDistance);
            printCurrentSituation(carNames, travelDistance);
        }

        winners = determineWinners(carNames, travelDistance, winners);
        printWinners(winners);
    }
}
