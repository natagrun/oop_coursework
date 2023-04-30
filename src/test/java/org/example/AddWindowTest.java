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
    void check_nums() {
        AddWindow s = new AddWindow();
        assertFalse(s.check_nums("Введите имя"));
        assertFalse(s.check_nums("3459a"));
        assertTrue(s.check_nums("987654567"));
    }
}