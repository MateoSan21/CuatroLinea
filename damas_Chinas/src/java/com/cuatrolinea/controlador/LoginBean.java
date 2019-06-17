/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuatrolinea.controlador;
import com.cuatrolinea.controlador.util.FacesUtils;
import com.cuatrolinea.controlador.util.JsfUtil;
import com.cuatrolinea.modelo.Usuario;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;

import javax.inject.Named;


/**
 *
 * @author carloaiza
 */
@Named(value = "loginBean")
@ViewScoped
public class LoginBean implements Serializable{

    private Usuario usuario;
    @EJB   
    private UsuarioFacade usuarioFacade;   

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }
    
    @PostConstruct
    private void inicializar()
    {
        usuario= new Usuario();
    }
    
    public String ingresar()
    {       
    
        Usuario usuarioEncontrado=usuarioFacade.find(usuario.getCorreo());
        if(usuarioEncontrado != null)
        {
            if(usuario.getContrasena().compareTo(usuarioEncontrado.getContrasena())==0)
            {
                ControladorCuatroLinea contDamas= (ControladorCuatroLinea) FacesUtils.getManagedBean("controladorDamasChinas");
                contDamas.setUsuario(usuarioEncontrado);
                if(usuarioEncontrado.getTipoUsuario().getCodigo()==1)
                {
                    return "ingresar";
                }    
                else
                {
                    return "jugar";
                }
                
                
                
            }
            JsfUtil.addErrorMessage("Contraseña errada");
        }
        else
        {
            JsfUtil.addErrorMessage("El correo ingresado no existe");
        }
        return null;
    }
}
