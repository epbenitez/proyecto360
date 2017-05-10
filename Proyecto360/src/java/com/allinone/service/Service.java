package com.allinone.service;

import com.allinone.persistence.dao.AbonoDao;
import com.allinone.persistence.dao.AbonoDatosPagoDao;
import com.allinone.persistence.dao.AbonoTipoDao;
import com.allinone.persistence.dao.AreaDao;
import com.allinone.persistence.dao.AreaHorariosDao;
import com.allinone.persistence.dao.AreaReglasReservacionDao;
import com.allinone.persistence.dao.AreaReservacionDao;
import com.allinone.persistence.dao.BitacoraDao;
import com.allinone.persistence.dao.CargoDao;
import com.allinone.persistence.dao.ConceptoDao;
import com.allinone.persistence.dao.CondominioDao;
import com.allinone.persistence.dao.ConfiguracionCargaMasivaDao;
import com.allinone.persistence.dao.ConfiguracionDao;
import com.allinone.persistence.dao.ConfiguracionEnvioCorreosDao;
import com.allinone.persistence.dao.CuestionarioDao;
import com.allinone.persistence.dao.CuestionarioPreguntaRespuestaDao;
import com.allinone.persistence.dao.CuestionarioPreguntaTipoDao;
import com.allinone.persistence.dao.CuestionarioPreguntasDao;
import com.allinone.persistence.dao.CuestionarioRespuestasDao;
import com.allinone.persistence.dao.CuestionarioRespuestasUsuarioDao;
import com.allinone.persistence.dao.CuestionarioSeccionDao;
import com.allinone.persistence.dao.DaoBase;
import com.allinone.persistence.dao.DepartamentoContactoDao;
import com.allinone.persistence.dao.DepartamentoDao;
import com.allinone.persistence.dao.DepartamentoUsuarioDao;
import com.allinone.persistence.dao.DocumentosDao;
import com.allinone.persistence.dao.EntidadFederativaDao;
import com.allinone.persistence.dao.EquipoDao;
import com.allinone.persistence.dao.EquipoMantenimientoDao;
import com.allinone.persistence.dao.EquipoMantenimientoEstatusDao;
import com.allinone.persistence.dao.EquipoMantenimientoFrecuenciaDao;
import com.allinone.persistence.dao.EquipoMantenimientoTipoDao;
import com.allinone.persistence.dao.EquipoSistemaDao;
import com.allinone.persistence.dao.EquipoSubsistemaDao;
import com.allinone.persistence.dao.EquipoTipoDao;
import com.allinone.persistence.dao.MenuDao;
import com.allinone.persistence.dao.NotificacionDao;
import com.allinone.persistence.dao.NotificacionDocumentoDao;
import com.allinone.persistence.dao.NotificacionEstatusDao;
import com.allinone.persistence.dao.NotificacionGrupoDao;
import com.allinone.persistence.dao.NotificacionGrupoDepartamentoDao;
import com.allinone.persistence.dao.NotificacionLogDao;
import com.allinone.persistence.dao.NotificacionesGruposDao;
import com.allinone.persistence.dao.PaisDao;
import com.allinone.persistence.dao.PersonalAdministrativoDao;
import com.allinone.persistence.dao.ProyectoDao;
import com.allinone.persistence.dao.ProyectoTareaDao;
import com.allinone.persistence.dao.RelacionGeograficaDao;
import com.allinone.persistence.dao.RelacionMenuRolesCondominioDao;
import com.allinone.persistence.dao.RelacionMenuRolesDao;
import com.allinone.persistence.dao.RolDao;
import com.allinone.persistence.dao.SolicitudDao;
import com.allinone.persistence.dao.SolicitudHistorialDao;
import com.allinone.persistence.dao.SolicitudesAreaDao;
import com.allinone.persistence.dao.SolicitudesCategoriaDao;
import com.allinone.persistence.dao.SolicitudesEstadoDao;
import com.allinone.persistence.dao.SolicitudesPermisosDao;
import com.allinone.persistence.dao.SolicitudesTipoAreaDao;
import com.allinone.persistence.dao.SolicitudesTipoDao;
import com.allinone.persistence.dao.SolicitudesTipoInmuebleDao;
import com.allinone.persistence.dao.TipoDepartamentoDao;
import com.allinone.persistence.dao.TorreDao;
import com.allinone.persistence.dao.UsuarioCondominioDao;
import com.allinone.persistence.dao.UsuarioDao;
import com.allinone.persistence.dao.UsuarioPrivilegioDao;
import com.allinone.persistence.model.Banco;
import com.allinone.persistence.model.LocalidadColonia;
import com.allinone.persistence.dao.SolicitudesUmbralesDao;
import com.allinone.persistence.dao.VWSolicitudesUmbralesResponsablesDao;
import com.allinone.persistence.dao.VwProyectoSiguienteTareaDao;
import com.allinone.persistence.model.ProyectoCategoria;
import com.allinone.persistence.model.ProyectoCuota;
import com.allinone.persistence.model.ProyectoEstatus;
import com.allinone.persistence.model.ProyectoPrioridad;
import com.allinone.persistence.model.ProyectoTipo;

public class Service {

    private BitacoraDao bitacoraDao;
    private UsuarioDao usuarioDao;
    private RelacionMenuRolesDao relacionMenuRolesDao;
    private RelacionGeograficaDao relacionGeograficaDao;
    private EntidadFederativaDao entidadFederativaDao;
    private PaisDao paisDao;
    private RolDao rolDao;
    private MenuDao menuDao;
    //--------------------------------------------------------------------------
    // CONFIGURACION DEL SISTEMA
    private ConfiguracionDao configuracionDao;
    private UsuarioPrivilegioDao usuarioPrivilegioDao;

    private DaoBase<LocalidadColonia, Long> localidadColoniaDao;
    //--------------------------------------------------------------------------
    //CUESTIONARIOS
    private CuestionarioDao cuestionarioDao;
    private CuestionarioPreguntaRespuestaDao cuestionarioPreguntaRespuestaDao;
    private CuestionarioPreguntaTipoDao cuestionarioPreguntaTipoDao;
    private CuestionarioPreguntasDao cuestionarioPreguntasDao;
    private CuestionarioRespuestasDao cuestionarioRespuestasDao;
    private CuestionarioRespuestasUsuarioDao cuestionarioRespuestasUsuarioDao;
    private CuestionarioSeccionDao cuestionarioSeccionDao;
    private PersonalAdministrativoDao personalAdministrativoDao;
    private DocumentosDao documentosDao;

    //--------------------------------------------------------------------------
    //ESTADO DE CUENTA
    private CondominioDao condominioDao;
    private DepartamentoDao departamentoDao;
    private DepartamentoUsuarioDao departamentoUsuarioDao;
    private TipoDepartamentoDao tipoDepartamentoDao;
    private TorreDao torreDao;

    private AbonoDao abonoDao;
    private CargoDao cargoDao;
    private ConceptoDao conceptoDao;
    private ConfiguracionCargaMasivaDao configuracionCargaMasivaDao;

    //PAGOS
    private AbonoDatosPagoDao abonoDatosPagoDao;
    private AbonoTipoDao abonoTipoDao;
    private DaoBase<Banco, Long> bancoDao;

    //SOLICITUDES
    private SolicitudesTipoDao solicitudesTipoDao;
    private SolicitudesEstadoDao solicitudesEstadoDao;
    private UsuarioCondominioDao usuarioCondominioDao;
    private SolicitudDao solicitudDao;
    private SolicitudHistorialDao solicitudHistorialDao;
    private SolicitudesUmbralesDao solicitudesUmbralesDao;
    private SolicitudesPermisosDao solicitudesPermisosDao;
    private VWSolicitudesUmbralesResponsablesDao vwSolicitudesUmbralesResponsablesDao;
    private SolicitudesAreaDao solicitudesAreaDao;
    private SolicitudesTipoAreaDao solicitudesTipoAreaDao;
    private SolicitudesCategoriaDao solicitudesCategoriaDao;
    private SolicitudesTipoInmuebleDao solicitudesTipoInmuebleDao;

    private RelacionMenuRolesCondominioDao relacionMenuRolesCondominioDao;

    private AreaDao areaDao;
    private AreaHorariosDao areaHorariosDao;
    private AreaReglasReservacionDao areaReglasReservacionDao;
    private AreaReservacionDao areaReservacionDao;
    
    private EquipoDao equipoDao;
    private EquipoMantenimientoEstatusDao equipoMantenimientoEstatusDao;
    private EquipoMantenimientoFrecuenciaDao equipoMantenimientoFrecuenciaDao;
    private EquipoMantenimientoDao equipoMantenimientoDao;
    private EquipoMantenimientoTipoDao equipoMantenimientoTipoDao;
    private EquipoSistemaDao equipoSistemaDao;
    private EquipoSubsistemaDao equipoSubsistemaDao;
    private EquipoTipoDao equipoTipoDao;
    
    private ConfiguracionEnvioCorreosDao configuracionEnvioCorreosDao;
    private DepartamentoContactoDao departamentoContactoDao;
    
    private ProyectoDao proyectoDao;
    private ProyectoTareaDao proyectoTareaDao;
    private DaoBase<ProyectoCategoria, Long> proyectoCategoriaDao;
    private DaoBase<ProyectoCuota, Long> proyectoCuotaDao;
    private DaoBase<ProyectoEstatus, Long> proyectoEstatusDao;
    private DaoBase<ProyectoPrioridad, Long> proyectoPrioridadDao;
    private DaoBase<ProyectoTipo, Long> proyectoTipoDao;
    private VwProyectoSiguienteTareaDao vwProyectoSiguienteTareaDao;
    
    private NotificacionGrupoDao notificacionGrupoDao;
    private NotificacionGrupoDepartamentoDao notificacionGrupoDepartamentoDao;
    private NotificacionDao notificacionDao;
    private NotificacionEstatusDao notificacionEstatusDao; 
    private NotificacionDocumentoDao notificacionDocumentoDao;
    private NotificacionesGruposDao notificacionesGruposDao;
    private NotificacionLogDao notificacionLogDao;

    public BitacoraDao getBitacoraDao() {
        return bitacoraDao;
    }

    public void setBitacoraDao(BitacoraDao bitacoraDao) {
        this.bitacoraDao = bitacoraDao;
    }

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public RelacionMenuRolesDao getRelacionMenuRolesDao() {
        return relacionMenuRolesDao;
    }

    public void setRelacionMenuRolesDao(RelacionMenuRolesDao relacionMenuRolesDao) {
        this.relacionMenuRolesDao = relacionMenuRolesDao;
    }

    public RelacionGeograficaDao getRelacionGeograficaDao() {
        return relacionGeograficaDao;
    }

    public void setRelacionGeograficaDao(RelacionGeograficaDao relacionGeograficaDao) {
        this.relacionGeograficaDao = relacionGeograficaDao;
    }

    public EntidadFederativaDao getEntidadFederativaDao() {
        return entidadFederativaDao;
    }

    public void setEntidadFederativaDao(EntidadFederativaDao entidadFederativaDao) {
        this.entidadFederativaDao = entidadFederativaDao;
    }

    public PaisDao getPaisDao() {
        return paisDao;
    }

    public void setPaisDao(PaisDao paisDao) {
        this.paisDao = paisDao;
    }

    public RolDao getRolDao() {
        return rolDao;
    }

    public void setRolDao(RolDao rolDao) {
        this.rolDao = rolDao;
    }

    public MenuDao getMenuDao() {
        return menuDao;
    }

    public void setMenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    public ConfiguracionDao getConfiguracionDao() {
        return configuracionDao;
    }

    public void setConfiguracionDao(ConfiguracionDao configuracionDao) {
        this.configuracionDao = configuracionDao;
    }

    public UsuarioPrivilegioDao getUsuarioPrivilegioDao() {
        return usuarioPrivilegioDao;
    }

    public void setUsuarioPrivilegioDao(UsuarioPrivilegioDao usuarioPrivilegioDao) {
        this.usuarioPrivilegioDao = usuarioPrivilegioDao;
    }

    public CuestionarioDao getCuestionarioDao() {
        return cuestionarioDao;
    }

    public void setCuestionarioDao(CuestionarioDao cuestionarioDao) {
        this.cuestionarioDao = cuestionarioDao;
    }

    public CuestionarioPreguntaRespuestaDao getCuestionarioPreguntaRespuestaDao() {
        return cuestionarioPreguntaRespuestaDao;
    }

    public void setCuestionarioPreguntaRespuestaDao(CuestionarioPreguntaRespuestaDao cuestionarioPreguntaRespuestaDao) {
        this.cuestionarioPreguntaRespuestaDao = cuestionarioPreguntaRespuestaDao;
    }

    public CuestionarioPreguntaTipoDao getCuestionarioPreguntaTipoDao() {
        return cuestionarioPreguntaTipoDao;
    }

    public void setCuestionarioPreguntaTipoDao(CuestionarioPreguntaTipoDao cuestionarioPreguntaTipoDao) {
        this.cuestionarioPreguntaTipoDao = cuestionarioPreguntaTipoDao;
    }

    public CuestionarioPreguntasDao getCuestionarioPreguntasDao() {
        return cuestionarioPreguntasDao;
    }

    public void setCuestionarioPreguntasDao(CuestionarioPreguntasDao cuestionarioPreguntasDao) {
        this.cuestionarioPreguntasDao = cuestionarioPreguntasDao;
    }

    public CuestionarioRespuestasDao getCuestionarioRespuestasDao() {
        return cuestionarioRespuestasDao;
    }

    public void setCuestionarioRespuestasDao(CuestionarioRespuestasDao cuestionarioRespuestasDao) {
        this.cuestionarioRespuestasDao = cuestionarioRespuestasDao;
    }

    public CuestionarioRespuestasUsuarioDao getCuestionarioRespuestasUsuarioDao() {
        return cuestionarioRespuestasUsuarioDao;
    }

    public void setCuestionarioRespuestasUsuarioDao(CuestionarioRespuestasUsuarioDao cuestionarioRespuestasUsuarioDao) {
        this.cuestionarioRespuestasUsuarioDao = cuestionarioRespuestasUsuarioDao;
    }

    public CuestionarioSeccionDao getCuestionarioSeccionDao() {
        return cuestionarioSeccionDao;
    }

    public void setCuestionarioSeccionDao(CuestionarioSeccionDao cuestionarioSeccionDao) {
        this.cuestionarioSeccionDao = cuestionarioSeccionDao;
    }

    public PersonalAdministrativoDao getPersonalAdministrativoDao() {
        return personalAdministrativoDao;
    }

    public void setPersonalAdministrativoDao(PersonalAdministrativoDao personalAdministrativoDao) {
        this.personalAdministrativoDao = personalAdministrativoDao;
    }

    public DocumentosDao getDocumentosDao() {
        return documentosDao;
    }

    public void setDocumentosDao(DocumentosDao documentosDao) {
        this.documentosDao = documentosDao;
    }

    public DaoBase<LocalidadColonia, Long> getLocalidadColoniaDao() {
        return localidadColoniaDao;
    }

    public void setLocalidadColoniaDao(DaoBase<LocalidadColonia, Long> localidadColoniaDao) {
        this.localidadColoniaDao = localidadColoniaDao;
    }

    public CondominioDao getCondominioDao() {
        return condominioDao;
    }

    public void setCondominioDao(CondominioDao condominioDao) {
        this.condominioDao = condominioDao;
    }

    public DepartamentoDao getDepartamentoDao() {
        return departamentoDao;
    }

    public void setDepartamentoDao(DepartamentoDao departamentoDao) {
        this.departamentoDao = departamentoDao;
    }

    public DepartamentoUsuarioDao getDepartamentoUsuarioDao() {
        return departamentoUsuarioDao;
    }

    public void setDepartamentoUsuarioDao(DepartamentoUsuarioDao departamentoUsuarioDao) {
        this.departamentoUsuarioDao = departamentoUsuarioDao;
    }

    public TipoDepartamentoDao getTipoDepartamentoDao() {
        return tipoDepartamentoDao;
    }

    public void setTipoDepartamentoDao(TipoDepartamentoDao tipoDepartamentoDao) {
        this.tipoDepartamentoDao = tipoDepartamentoDao;
    }

    public TorreDao getTorreDao() {
        return torreDao;
    }

    public void setTorreDao(TorreDao torreDao) {
        this.torreDao = torreDao;
    }

    public AbonoDao getAbonoDao() {
        return abonoDao;
    }

    public void setAbonoDao(AbonoDao abonoDao) {
        this.abonoDao = abonoDao;
    }

    public CargoDao getCargoDao() {
        return cargoDao;
    }

    public void setCargoDao(CargoDao cargoDao) {
        this.cargoDao = cargoDao;
    }

    public ConceptoDao getConceptoDao() {
        return conceptoDao;
    }

    public void setConceptoDao(ConceptoDao conceptoDao) {
        this.conceptoDao = conceptoDao;
    }

    public ConfiguracionCargaMasivaDao getConfiguracionCargaMasivaDao() {
        return configuracionCargaMasivaDao;
    }

    public void setConfiguracionCargaMasivaDao(ConfiguracionCargaMasivaDao configuracionCargaMasivaDao) {
        this.configuracionCargaMasivaDao = configuracionCargaMasivaDao;
    }

    public AbonoDatosPagoDao getAbonoDatosPagoDao() {
        return abonoDatosPagoDao;
    }

    public void setAbonoDatosPagoDao(AbonoDatosPagoDao abonoDatosPagoDao) {
        this.abonoDatosPagoDao = abonoDatosPagoDao;
    }

    public AbonoTipoDao getAbonoTipoDao() {
        return abonoTipoDao;
    }

    public void setAbonoTipoDao(AbonoTipoDao abonoTipoDao) {
        this.abonoTipoDao = abonoTipoDao;
    }

    public DaoBase<Banco, Long> getBancoDao() {
        return bancoDao;
    }

    public void setBancoDao(DaoBase<Banco, Long> bancoDao) {
        this.bancoDao = bancoDao;
    }

    public SolicitudesTipoDao getSolicitudesTipoDao() {
        return solicitudesTipoDao;
    }

    public void setSolicitudesTipoDao(SolicitudesTipoDao solicitudesTipoDao) {
        this.solicitudesTipoDao = solicitudesTipoDao;
    }

    public SolicitudesEstadoDao getSolicitudesEstadoDao() {
        return solicitudesEstadoDao;
    }

    public void setSolicitudesEstadoDao(SolicitudesEstadoDao solicitudesEstadoDao) {
        this.solicitudesEstadoDao = solicitudesEstadoDao;
    }

    public UsuarioCondominioDao getUsuarioCondominioDao() {
        return usuarioCondominioDao;
    }

    public void setUsuarioCondominioDao(UsuarioCondominioDao usuarioCondominioDao) {
        this.usuarioCondominioDao = usuarioCondominioDao;
    }

    public SolicitudDao getSolicitudDao() {
        return solicitudDao;
    }

    public void setSolicitudDao(SolicitudDao solicitudDao) {
        this.solicitudDao = solicitudDao;
    }

    public SolicitudHistorialDao getSolicitudHistorialDao() {
        return solicitudHistorialDao;
    }

    public void setSolicitudHistorialDao(SolicitudHistorialDao solicitudHistorialDao) {
        this.solicitudHistorialDao = solicitudHistorialDao;
    }

    public SolicitudesUmbralesDao getSolicitudesUmbralesDao() {
        return solicitudesUmbralesDao;
    }

    public void setSolicitudesUmbralesDao(SolicitudesUmbralesDao solicitudesUmbralesDao) {
        this.solicitudesUmbralesDao = solicitudesUmbralesDao;
    }

    public SolicitudesPermisosDao getSolicitudesPermisosDao() {
        return solicitudesPermisosDao;
    }

    public void setSolicitudesPermisosDao(SolicitudesPermisosDao solicitudesPermisosDao) {
        this.solicitudesPermisosDao = solicitudesPermisosDao;
    }

    public VWSolicitudesUmbralesResponsablesDao getVwSolicitudesUmbralesResponsablesDao() {
        return vwSolicitudesUmbralesResponsablesDao;
    }

    public void setVwSolicitudesUmbralesResponsablesDao(VWSolicitudesUmbralesResponsablesDao vwSolicitudesUmbralesResponsablesDao) {
        this.vwSolicitudesUmbralesResponsablesDao = vwSolicitudesUmbralesResponsablesDao;
    }

    public SolicitudesAreaDao getSolicitudesAreaDao() {
        return solicitudesAreaDao;
    }

    public void setSolicitudesAreaDao(SolicitudesAreaDao solicitudesAreaDao) {
        this.solicitudesAreaDao = solicitudesAreaDao;
    }

    public SolicitudesTipoAreaDao getSolicitudesTipoAreaDao() {
        return solicitudesTipoAreaDao;
    }

    public void setSolicitudesTipoAreaDao(SolicitudesTipoAreaDao solicitudesTipoAreaDao) {
        this.solicitudesTipoAreaDao = solicitudesTipoAreaDao;
    }

    public SolicitudesCategoriaDao getSolicitudesCategoriaDao() {
        return solicitudesCategoriaDao;
    }

    public void setSolicitudesCategoriaDao(SolicitudesCategoriaDao solicitudesCategoriaDao) {
        this.solicitudesCategoriaDao = solicitudesCategoriaDao;
    }

    public RelacionMenuRolesCondominioDao getRelacionMenuRolesCondominioDao() {
        return relacionMenuRolesCondominioDao;
    }

    public void setRelacionMenuRolesCondominioDao(RelacionMenuRolesCondominioDao relacionMenuRolesCondominioDao) {
        this.relacionMenuRolesCondominioDao = relacionMenuRolesCondominioDao;
    }

    public AreaDao getAreaDao() {
        return areaDao;
    }

    public void setAreaDao(AreaDao areaDao) {
        this.areaDao = areaDao;
    }

    public AreaHorariosDao getAreaHorariosDao() {
        return areaHorariosDao;
    }

    public void setAreaHorariosDao(AreaHorariosDao areaHorariosDao) {
        this.areaHorariosDao = areaHorariosDao;
    }

    public AreaReglasReservacionDao getAreaReglasReservacionDao() {
        return areaReglasReservacionDao;
    }

    public void setAreaReglasReservacionDao(AreaReglasReservacionDao areaReglasReservacionDao) {
        this.areaReglasReservacionDao = areaReglasReservacionDao;
    }

    public AreaReservacionDao getAreaReservacionDao() {
        return areaReservacionDao;
    }

    public void setAreaReservacionDao(AreaReservacionDao areaReservacionDao) {
        this.areaReservacionDao = areaReservacionDao;
    }

    public EquipoDao getEquipoDao() {
        return equipoDao;
    }

    public void setEquipoDao(EquipoDao equipoDao) {
        this.equipoDao = equipoDao;
    }

    public EquipoMantenimientoEstatusDao getEquipoMantenimientoEstatusDao() {
        return equipoMantenimientoEstatusDao;
    }

    public void setEquipoMantenimientoEstatusDao(EquipoMantenimientoEstatusDao equipoMantenimientoEstatusDao) {
        this.equipoMantenimientoEstatusDao = equipoMantenimientoEstatusDao;
    }

    public EquipoMantenimientoFrecuenciaDao getEquipoMantenimientoFrecuenciaDao() {
        return equipoMantenimientoFrecuenciaDao;
    }

    public void setEquipoMantenimientoFrecuenciaDao(EquipoMantenimientoFrecuenciaDao equipoMantenimientoFrecuenciaDao) {
        this.equipoMantenimientoFrecuenciaDao = equipoMantenimientoFrecuenciaDao;
    }

    public EquipoMantenimientoDao getEquipoMantenimientoDao() {
        return equipoMantenimientoDao;
    }

    public void setEquipoMantenimientoDao(EquipoMantenimientoDao equipoMantenimientoDao) {
        this.equipoMantenimientoDao = equipoMantenimientoDao;
    }

    public EquipoMantenimientoTipoDao getEquipoMantenimientoTipoDao() {
        return equipoMantenimientoTipoDao;
    }

    public void setEquipoMantenimientoTipoDao(EquipoMantenimientoTipoDao equipoMantenimientoTipoDao) {
        this.equipoMantenimientoTipoDao = equipoMantenimientoTipoDao;
    }

    public EquipoSistemaDao getEquipoSistemaDao() {
        return equipoSistemaDao;
    }

    public void setEquipoSistemaDao(EquipoSistemaDao equipoSistemaDao) {
        this.equipoSistemaDao = equipoSistemaDao;
    }

    public EquipoSubsistemaDao getEquipoSubsistemaDao() {
        return equipoSubsistemaDao;
    }

    public void setEquipoSubsistemaDao(EquipoSubsistemaDao equipoSubsistemaDao) {
        this.equipoSubsistemaDao = equipoSubsistemaDao;
    }

    public EquipoTipoDao getEquipoTipoDao() {
        return equipoTipoDao;
    }

    public void setEquipoTipoDao(EquipoTipoDao equipoTipoDao) {
        this.equipoTipoDao = equipoTipoDao;
    }

    public ConfiguracionEnvioCorreosDao getConfiguracionEnvioCorreosDao() {
        return configuracionEnvioCorreosDao;
    }

    public void setConfiguracionEnvioCorreosDao(ConfiguracionEnvioCorreosDao configuracionEnvioCorreosDao) {
        this.configuracionEnvioCorreosDao = configuracionEnvioCorreosDao;
    }

    public DepartamentoContactoDao getDepartamentoContactoDao() {
        return departamentoContactoDao;
    }

    public void setDepartamentoContactoDao(DepartamentoContactoDao departamentoContactoDao) {
        this.departamentoContactoDao = departamentoContactoDao;
    }

    public ProyectoDao getProyectoDao() {
        return proyectoDao;
    }

    public void setProyectoDao(ProyectoDao proyectoDao) {
        this.proyectoDao = proyectoDao;
    }

    public ProyectoTareaDao getProyectoTareaDao() {
        return proyectoTareaDao;
    }

    public void setProyectoTareaDao(ProyectoTareaDao proyectoTareaDao) {
        this.proyectoTareaDao = proyectoTareaDao;
    }

    public DaoBase<ProyectoCategoria, Long> getProyectoCategoriaDao() {
        return proyectoCategoriaDao;
    }

    public void setProyectoCategoriaDao(DaoBase<ProyectoCategoria, Long> proyectoCategoriaDao) {
        this.proyectoCategoriaDao = proyectoCategoriaDao;
    }

    public DaoBase<ProyectoCuota, Long> getProyectoCuotaDao() {
        return proyectoCuotaDao;
    }

    public void setProyectoCuotaDao(DaoBase<ProyectoCuota, Long> proyectoCuotaDao) {
        this.proyectoCuotaDao = proyectoCuotaDao;
    }

    public DaoBase<ProyectoEstatus, Long> getProyectoEstatusDao() {
        return proyectoEstatusDao;
    }

    public void setProyectoEstatusDao(DaoBase<ProyectoEstatus, Long> proyectoEstatusDao) {
        this.proyectoEstatusDao = proyectoEstatusDao;
    }

    public DaoBase<ProyectoPrioridad, Long> getProyectoPrioridadDao() {
        return proyectoPrioridadDao;
    }

    public void setProyectoPrioridadDao(DaoBase<ProyectoPrioridad, Long> proyectoPrioridadDao) {
        this.proyectoPrioridadDao = proyectoPrioridadDao;
    }

    public DaoBase<ProyectoTipo, Long> getProyectoTipoDao() {
        return proyectoTipoDao;
    }

    public void setProyectoTipoDao(DaoBase<ProyectoTipo, Long> proyectoTipoDao) {
        this.proyectoTipoDao = proyectoTipoDao;
    }

    public VwProyectoSiguienteTareaDao getVwProyectoSiguienteTareaDao() {
        return vwProyectoSiguienteTareaDao;
    }

    public void setVwProyectoSiguienteTareaDao(VwProyectoSiguienteTareaDao vwProyectoSiguienteTareaDao) {
        this.vwProyectoSiguienteTareaDao = vwProyectoSiguienteTareaDao;
    }

    public NotificacionGrupoDao getNotificacionGrupoDao() {
        return notificacionGrupoDao;
    }

    public void setNotificacionGrupoDao(NotificacionGrupoDao notificacionGrupoDao) {
        this.notificacionGrupoDao = notificacionGrupoDao;
    }

    public NotificacionGrupoDepartamentoDao getNotificacionGrupoDepartamentoDao() {
        return notificacionGrupoDepartamentoDao;
    }

    public void setNotificacionGrupoDepartamentoDao(NotificacionGrupoDepartamentoDao notificacionGrupoDepartamentoDao) {
        this.notificacionGrupoDepartamentoDao = notificacionGrupoDepartamentoDao;
    }

    public NotificacionDao getNotificacionDao() {
        return notificacionDao;
    }

    public void setNotificacionDao(NotificacionDao notificacionDao) {
        this.notificacionDao = notificacionDao;
    }

    public NotificacionEstatusDao getNotificacionEstatusDao() {
        return notificacionEstatusDao;
    }

    public void setNotificacionEstatusDao(NotificacionEstatusDao notificacionEstatusDao) {
        this.notificacionEstatusDao = notificacionEstatusDao;
    }

    public NotificacionDocumentoDao getNotificacionDocumentoDao() {
        return notificacionDocumentoDao;
    }

    public void setNotificacionDocumentoDao(NotificacionDocumentoDao notificacionDocumentoDao) {
        this.notificacionDocumentoDao = notificacionDocumentoDao;
    }

    public NotificacionesGruposDao getNotificacionesGruposDao() {
        return notificacionesGruposDao;
    }

    public void setNotificacionesGruposDao(NotificacionesGruposDao notificacionesGruposDao) {
        this.notificacionesGruposDao = notificacionesGruposDao;
    }

    public NotificacionLogDao getNotificacionLogDao() {
        return notificacionLogDao;
    }

    public void setNotificacionLogDao(NotificacionLogDao notificacionLogDao) {
        this.notificacionLogDao = notificacionLogDao;
    }

    public SolicitudesTipoInmuebleDao getSolicitudesTipoInmuebleDao() {
        return solicitudesTipoInmuebleDao;
    }

    public void setSolicitudesTipoInmuebleDao(SolicitudesTipoInmuebleDao solicitudesTipoInmuebleDao) {
        this.solicitudesTipoInmuebleDao = solicitudesTipoInmuebleDao;
    }

}
