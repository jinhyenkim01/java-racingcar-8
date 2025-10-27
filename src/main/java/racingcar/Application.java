package racingcar;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Application {
    static boolean containsInvalidCharacter(String str) {
        for (int i = 0; i < str.length();) {
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
        if (str.length() > 5) {
            return true;
        }
        return false;
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



    public static void main(String[] args) {
        String[] carNames = inputCarName();
    }
}