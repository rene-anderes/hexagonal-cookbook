package org.anderes.edu.hexagonal.cookbook.domain;

import org.anderes.edu.hexagonal.cookbook.domain.CookbookConfig;
import org.anderes.edu.hexagonal.cookbook.mediation.UserInterfaceRecipeService;
import org.anderes.edu.hexagonal.cookbook.port.RepositoryPort;
import org.apache.commons.lang3.Validate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MasterControlProgramm {

    private static MasterControlProgramm instance;
    private RepositoryPort repositoryPort;
    private final AnnotationConfigApplicationContext ctx;

    public MasterControlProgramm() {
        super();
        ctx = new AnnotationConfigApplicationContext();
        ctx.register(CookbookConfig.class);
        ctx.refresh();
    }
    
    public static MasterControlProgramm getInstance() {
        if (instance == null) {
            instance = new MasterControlProgramm();
        }
        return instance;
    }
    
    public void registerRepositoryPort(final RepositoryPort port) {
        Validate.notNull(port);
        this.repositoryPort = port;
    }
    
    RepositoryPort getRepositoryPort() {
        Validate.notNull(repositoryPort, "Es wurde keine '%s' registriert!", RepositoryPort.class);
        return repositoryPort;
    }
    
    public UserInterfaceRecipeService getUserInterfaceRecipeService() {
        return ctx.getBean(UserInterfaceRecipeService.class);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        ctx.close();
    }
}
