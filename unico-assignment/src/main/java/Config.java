import java.util.Set;

@javax.ws.rs.ApplicationPath("resources")
public class Config extends javax.ws.rs.core.Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> restClasses = new java.util.HashSet<>();
        addRestResourceClasses(restClasses);
        return restClasses;
    }
 
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(unico.web.RestWebService.class);
    }

}
