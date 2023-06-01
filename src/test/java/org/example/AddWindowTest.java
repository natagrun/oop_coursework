package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static org.junit.jupiter.api.Assertions.*;

class AddWindowTest {

    @BeforeClass // Фиксируем начало тестирования
    public static void allTestsStarted() {
        System.out.println("Начало тестирования");
    }

    @AfterClass // Фиксируем конец тестирования
    public static void allTestsFinished() {
        System.out.println("Конец тестирования");
    }

    @Before // Фиксируем запуск теста
    public void testStarted() {
        System.out.println("Запуск теста");
    }

    @After // Фиксируем завершение теста
    public void testFinished() {
        System.out.println("Завершение теста");
    }

    @Test
    void check_nums1() {
        AddObject s = new AddObject(null);
        assertFalse(s.checkString("Введите имя"));
    }
    @Test
    void check_nums2() {
        AddObject s = new AddObject(null);
        assertFalse(s.checkString("3459a"));
    }

    @Test
    void check_nums3() {
        AddObject s = new AddObject(null);
        assertTrue(s.checkString("3459"));
    }

    @Test
    void check_nulls1() {
        AddObject s = new AddObject(null);
        assertTrue(s.checkNULL("3459"));
    }

    @Test
    void check_nulls2() {
        AddObject s = new AddObject(null);
        assertFalse(s.checkNULL(""));
    }
}