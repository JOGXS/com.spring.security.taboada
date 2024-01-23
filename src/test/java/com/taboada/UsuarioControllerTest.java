package com.taboada;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.taboada.controller.UsuarioController;
import com.taboada.model.Usuario;
import com.taboada.repository.UsuarioRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearUsuario() {
        // Datos de ejemplo para el usuario
        Usuario usuario = new Usuario("nombreUsuario", "contrasena", "correo@example.com");

        // Simular el comportamiento del repositorio al guardar el usuario
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);


        // Llamamos al método a probar
        String result = usuarioController.crearUsuario(usuario);
        
        // Verificamos que el método redirige a la página adecuada después de guardar
        assertEquals("redirect:/usuarios/lista", result);

        // Verificamos que el método llama al método save del repositorio con el usuario
        verify(usuarioRepository, times(1)).save(usuario);
    }
}
