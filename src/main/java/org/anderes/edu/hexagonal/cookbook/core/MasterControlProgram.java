package org.anderes.edu.hexagonal.cookbook.core;

import org.anderes.edu.hexagonal.cookbook.core.CookbookConfig;
import org.anderes.edu.hexagonal.cookbook.mediation.UserInterfaceRecipeService;
import org.anderes.edu.hexagonal.cookbook.port.RepositoryPort;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MasterControlProgram {

    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private static MasterControlProgram instance;
    private RepositoryPort repositoryPort;
    private final AnnotationConfigApplicationContext ctx;

    public MasterControlProgram() {
        super();
        ctx = new AnnotationConfigApplicationContext();
        ctx.register(CookbookConfig.class);
        ctx.refresh();
        logger.info("Master Control Program initialized");
    }
    
    public static MasterControlProgram getInstance() {
        if (instance == null) {
            instance = new MasterControlProgram();
        }
        return instance;
    }
    
    public void registerRepositoryPort(final RepositoryPort port) {
        Validate.notNull(port);
        this.repositoryPort = port;
        logger.info("Register new RepositoryPort: " + port.getVersion());
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
