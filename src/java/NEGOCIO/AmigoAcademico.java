/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NEGOCIO;

import DAO.AmigoJpaController;
import DAO.Conexion;
import DAO.HorarioJpaController;
import DTO.Amigo;
import DTO.Horario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author estudiante
 */
public class AmigoAcademico {

    Conexion con = Conexion.getConexion();
    AmigoJpaController amigoDAO = new AmigoJpaController(con.getBd());
    List<Amigo> amigos = amigoDAO.findAmigoEntities();
    HorarioJpaController horarioDAO = new HorarioJpaController(con.getBd());
    List<Horario> horarios = horarioDAO.findHorarioEntities();

    //esta mierda esta mal
    public boolean registrarAmigo(int codigo) {
        Amigo amigo = new Amigo();
        amigo.setCodigo(codigo);
        amigo = buscarAmigo(amigo);
        if (amigo != null) {
            return false;
        } else {
            Amigo a = new Amigo();
            a.setCodigo(codigo);
            try {
                amigoDAO.create(a);
                return true;
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return false;
    }

    public boolean registrarHorario(int codigo, int dia, int jornada) {
        Amigo m = new Amigo();
        m = findAmigo(codigo);
        if (m == null) {
            return false;
        } else {
            Horario h = new Horario();
            h.setIdHorario((int) (Math.random()*100) +100);
            h.setCodigo(m);
            h.setDia(dia);
            h.setJornada(jornada);
            try {
                horarioDAO.create(h);
                return true;
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return false;
    }

    private Amigo buscarAmigo(Amigo x) {
        for (Amigo m : amigos) {
            if (m.equals(x)) {
                return m;
            }
        }
        return null;
    }

    private Amigo findAmigo(int codigo) {
        return amigoDAO.findAmigo(codigo);
    }
    
    public ArrayList buscarHorarioCodigo(int codigo){
        ArrayList<Horario> resultado = new ArrayList<>();
        Amigo a = new Amigo();
        a = findAmigo(codigo);
        for(Horario h: horarios){
            if(h.getCodigo().equals(a)){
                resultado.add(h);
            }
        }
        return resultado;
    }
}
