package com.owr.tools.formula.parser.step01;

import com.owr.tools.formula.parser.validate.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CharElementTest {

    private static ToCharElementStep s;

    @BeforeAll
    public static void init() {
        s = new ToCharElementStep();
    }

    @Test
    public void validDataTest() {
        s.validateBefore("5");
        s.validateBefore("a");
        s.validateBefore(" a / b ");

        s.validateBefore(" \ta / b ");
    }

    @Test
    public void noDataFailTest() {
        Assertions.assertThrows(ValidationException.class, () -> s.validateBefore(null));
        Assertions.assertThrows(ValidationException.class, () -> s.validateBefore(""));
        Assertions.assertThrows(ValidationException.class, () -> s.validateBefore(" "));
        Assertions.assertThrows(ValidationException.class, () -> s.validateBefore("\n"));
        Assertions.assertThrows(ValidationException.class, () -> s.validateBefore("\t"));
        Assertions.assertThrows(ValidationException.class, () -> s.validateBefore(" \n\t\n"));
    }

    @Test
    public void notValidSpacesOrMissingOperatorFailTest() {
        Assertions.assertThrows(ValidationException.class, () -> s.validateBefore(" A B "));
        Assertions.assertThrows(ValidationException.class, () -> s.validateBefore(" A 1 "));
        Assertions.assertThrows(ValidationException.class, () -> s.validateBefore(" 2 C "));
        Assertions.assertThrows(ValidationException.class, () -> s.validateBefore(" 2 C A B"));
        Assertions.assertThrows(ValidationException.class, () -> s.validateBefore("+2 +C A -B"));

        Assertions.assertThrows(ValidationException.class, () -> s.validateBefore("2\tC\tA\tB"));
        Assertions.assertThrows(ValidationException.class, () -> s.validateBefore("2\nC\nA\nB"));

    }

}
