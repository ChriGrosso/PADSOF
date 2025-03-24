package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import notificaciones.Notificacion;
import usuarios.Gestor;
import usuarios.Usuario;

class NotificacionTest {

	private Notificacion notificacion;
    
    @BeforeEach
    void setUp() throws Exception {
    	Usuario user = new Gestor("01020304A", "Gestor", "password");
        notificacion = new Notificacion("Hola, este es un mensaje de prueba", user);
    }

    @Test
    void testGetMensaje() {
        assertEquals("Hola, este es un mensaje de prueba", notificacion.getMensaje());
    }

    @Test
    void testGetFechaEmision() throws InterruptedException {
    	Thread.sleep(1000);
    	assertTrue(notificacion.getFechaEmision().isBefore(LocalDateTime.now()));
    }

    @Test
    void testGetEmisor() {
        assertEquals("01020304A", notificacion.getEmisor().getDni());
    }

    @Test
    void testGetLeida() {
        assertFalse(notificacion.getLeida());
    }

    @Test
    void testSetLeida() {
        notificacion.setLeida(true);
        assertTrue(notificacion.getLeida());
    }
}
