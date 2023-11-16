package com.example.passwordvalidation.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
class PasswordValidationServiceTest {

    private PasswordService passwordService;

    @BeforeEach
    void setup(){
        passwordService = new PasswordService();
    }

    //@Test
    @ParameterizedTest
    @MethodSource("testCasesOfOneCapitalLetter")
    void the_password_should_be_at_least_one_capital_letter(String password, Boolean expectedResult) {
        //GIVE
//        PasswordService passwordService = new PasswordService();
        //WHEN
        Boolean testResult = passwordService.hasOneCapitalLetter(password);
        //THEN
        Assertions.assertEquals(expectedResult, testResult);
    }

    private static Stream<Object[]> testCasesOfOneCapitalLetter(){
        return Stream.of(
                new Object[]{"mipassworD",true},
                new Object[]{"mipaSsword",true},
                new Object[]{"passwordconerror",false}
        );
    }


    //@Test
    @ParameterizedTest
    @MethodSource("testCasesOfOneLowerCase")
    void the_password_should_be_at_least_one_lowercase(String password, Boolean expectedResult) {
        //GIVE
//        PasswordService passwordService=new PasswordService();
        //WHEN
        Boolean testResult = passwordService.hasOneLowerCase(password);
        //THEN
        Assertions.assertEquals(expectedResult, testResult);
    }

    private static Stream<Object[]> testCasesOfOneLowerCase(){
        return Stream.of(
                new Object[]{"MIpASSWORD",true},
                new Object[]{"mIPASSWORD",true},
                new Object[]{"MIpASSWORd",true},
                new Object[]{"mIpASSWORd",true},
                new Object[]{"MIPASSWORD",false}
        );
    }

    //@Test
    @ParameterizedTest
    @MethodSource("testCasesOfSixCharactersAsMinium")
    void the_password_should_be_six_characters_as_a_minium(String password, Boolean expectedResult)  {
        //GIVE
//        PasswordService passwordService=new PasswordService();
        //WHEN
        Boolean testResult=passwordService.hasSixCharactesAsMinium(password);
        //THEN
        Assertions.assertEquals(expectedResult, testResult);
    }

    private static Stream<Object[]> testCasesOfSixCharactersAsMinium(){
        return Stream.of(
                new Object[]{"123456",true},
                new Object[]{"123",false},
                new Object[]{"1",false},
                new Object[]{"",false},
                new Object[]{"1324567",true}
        );
    }

    //@Test
    @ParameterizedTest
    @MethodSource("testCasesOfTwelveCharacters")
    void the_password_should_be_a_maximum_of_twelve_characters(String password, Boolean expectedResult)  {
        //GIVE
//        PasswordService passwordService=new PasswordService();
        //WHEN
        Boolean testResult=passwordService.hasMaximumTwelveCharacters(password);
        //THEN
        Assertions.assertEquals(expectedResult, testResult);
    }

    private static Stream<Object[]> testCasesOfTwelveCharacters(){
        return Stream.of(
                new Object[]{"123456789012",true},
                new Object[]{"123456789012345",false},
                new Object[]{"123456",true},
                new Object[]{"1111111111111111",false},
                new Object[]{"111",true}
        );
    }

    //@Test
    @ParameterizedTest
    @MethodSource("testCasesOfSpecialCharacter")
    void the_password_should_be_at_least_one_special_character(String password, Boolean expectedResult)  {
        //GIVE
//        PasswordService passwordService=new PasswordService();
        //WHEN
        Boolean testResult=passwordService.hasOneSpecialCharacter(password);
        //THEN
        Assertions.assertEquals(expectedResult, testResult);
    }

    private static Stream<Object[]> testCasesOfSpecialCharacter(){
        return Stream.of(
                new Object[]{"@",true},
                new Object[]{"hola_/",true},
                new Object[]{"mipassword",false},
                new Object[]{"mIpASSWORd",false},
                new Object[]{"MIPASSWORD",false}
        );
    }

    //@Test
    @ParameterizedTest
    @MethodSource("testCasesOfOneDigit")
    void the_password_should_be_at_least_one_digit(String password, Boolean expectedResult)  {
        //GIVE
//        PasswordService passwordService=new PasswordService();
        //WHEN
        Boolean testResult=passwordService.hasOneDigit(password);
        //THEN
        Assertions.assertEquals(expectedResult, testResult);
    }

    private static Stream<Object[]> testCasesOfOneDigit(){
        return Stream.of(
                new Object[]{"mipassword1",true},
                new Object[]{"1",true},
                new Object[]{"m1password",true},
                new Object[]{"mipassword",false},
                new Object[]{"MIPASSWORD",false}
        );
    }

    //@Test
    @ParameterizedTest
    @MethodSource("testCasesOfOCheckAllValidations")
    void check_all_validations(String password, Boolean expectedResult)  {
        //GIVE
//        PasswordService passwordService=new PasswordService();
        //WHEN
        Boolean testResult=passwordService.validatePassword(password);
        //THEN
        Assertions.assertEquals(expectedResult,testResult);
    }

    private static Stream<Object[]> testCasesOfOCheckAllValidations(){
        return Stream.of(
                new Object[]{"M1Passw@rd",true},
                new Object[]{"mip@s",false}
        );
    }
}