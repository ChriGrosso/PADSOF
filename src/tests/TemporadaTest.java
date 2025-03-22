package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.time.MonthDay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aeropuertos.Temporada;

class TemporadaTest {

private Temporada temporada;
    
    @BeforeEach
    void setUp() throws Exception {
        temporada = new Temporada(MonthDay.of(6, 1), LocalTime.of(6, 0), LocalTime.of(22, 0), MonthDay.of(8, 31));
    }

    @Test
    void testGetFechaInicioTemporada() {
        assertEquals(MonthDay.of(6, 1), temporada.getFechaInicioTemporada());
    }

    @Test
    void testGetApertura() {
        assertEquals(LocalTime.of(6, 0), temporada.getApertura());
    }

    @Test
    void testGetCierre() {
        assertEquals(LocalTime.of(22, 0), temporada.getCierre());
    }

    @Test
    void testGetFechaFinTemporada() {
        assertEquals(MonthDay.of(8, 31), temporada.getFechaFinTemporada());
    }

    @Test
    void testSetFechaInicioTemporada() {
        temporada.setFechaInicioTemporada(MonthDay.of(7, 1));
        assertEquals(MonthDay.of(7, 1), temporada.getFechaInicioTemporada());
    }

    @Test
    void testSetApertura() {
        temporada.setApertura(LocalTime.of(7, 0));
        assertEquals(LocalTime.of(7, 0), temporada.getApertura());
    }

    @Test
    void testSetCierre() {
        temporada.setCierre(LocalTime.of(23, 0));
        assertEquals(LocalTime.of(23, 0), temporada.getCierre());
    }

    @Test
    void testSetFechaFinTemporada() {
        temporada.setFechaFinTemporada(MonthDay.of(9, 30));
        assertEquals(MonthDay.of(9, 30), temporada.getFechaFinTemporada());
    }
}

