package com.tecnoupsa.ecopass_betav10;

public class constantes {
    public final static String URL_WEB_SERVICE = "https://desolate-falls-65337.herokuapp.com/";
    //USUARIO
    public final static String URL_OBTENERUSUARIOPORID = URL_WEB_SERVICE + "Consultas_Usuario/Get_porIDPOST.php";
    //ESTADO USUARIO
    public final static String URL_OBTENERESTADO = URL_WEB_SERVICE + "Consultas_EstadoUsuario/Get_porIDPOST.php";
    public final static String URL_ACTUALIZARESTADO = URL_WEB_SERVICE + "Consultas_EstadoUsuario/Update_porIDPOST.php";
    //VIAJES ACTIVOS
    public final static String URL_OBTENERVIAJESACTIVOS = URL_WEB_SERVICE + "Consultas_ViajesActivos/Get_porIDUsuarioPOST.php";
    public final static String URL_ELIMINARVIAJESACTIVOS = URL_WEB_SERVICE + "Consultas_ViajesActivos/Delete_porIDUsuarioPOST.php";
    //PARADAS
    public final static String URL_OBTENERPARADAS= URL_WEB_SERVICE + "Consultas_UbicacionParadas/Get_porIDPOST.php";
}
