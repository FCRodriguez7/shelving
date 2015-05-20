package edu.yale.sml.view;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import edu.yale.sml.model.Admin;
import edu.yale.sml.persistence.AdminDAO;
import edu.yale.sml.persistence.AdminHibernateDAO;
import edu.yale.sml.servlet.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@RequestScoped
public class AdminView {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<Admin> adminAsList = new ArrayList<Admin>();
    private String adminCode = "";
    private String editor = "";
    private String netid = "";
    private List<String> permissionTypes = new ArrayList<String>();
    final AdminDAO adminDAO = new AdminHibernateDAO();

    @PostConstruct
    public void initialize() {
        // TODO add dao lookup for persmissions
        permissionTypes.add("Admin");
        permissionTypes.add("Student");

        try {
            adminAsList = adminDAO.findAll(Admin.class);
        } catch (Throwable e) {
            logger.error("Error", e);
        }
    }

    public void addInfo(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Changes Saved."));
    }

    public List findAll() {
        initialize();
        return adminAsList;
    }

    public List<Admin> getAdminAsList() {
        return adminAsList;
    }

    public String getAdminCode() {
        return adminCode;
    }

    public String getEditor() {
        return editor;
    }

    public String getNetid() {
        return netid;
    }

    public List<String> getPermissionTypes() {
        return permissionTypes;
    }

    public void remove(Admin adminCatalog) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Deleted!"));
        AdminDAO adminDAO = new AdminHibernateDAO();

        try {
            adminDAO.delete(adminCatalog);
            adminAsList.remove(adminCatalog);
        } catch (Throwable e) {
            logger.error("Error", e);
        }
    }

    public void saveAll() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.NETID) != null) {
            editor = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.NETID).toString();
        }

        Admin adminCatalog = new Admin(netid, editor, adminCode);

        try {
            adminDAO.save(adminCatalog);
            initialize();
        } catch (Throwable e) {
            logger.error("Error", e);
        }
    }

    public void setAdminAsList(List<Admin> adminAsList) {
        this.adminAsList = adminAsList;
    }

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public void setNetid(String netid) {
        this.netid = netid;
    }

    public void setPermissionTypes(List<String> permissionTypes) {
        this.permissionTypes = permissionTypes;
    }
}