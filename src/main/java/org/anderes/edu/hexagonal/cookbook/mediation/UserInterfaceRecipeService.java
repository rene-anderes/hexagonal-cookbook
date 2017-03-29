package org.anderes.edu.hexagonal.cookbook.mediation;

import org.anderes.edu.hexagonal.cookbook.domain.RecipeDomainObject;
import org.anderes.edu.hexagonal.cookbook.port.UserInterfacePort;

public interface UserInterfaceRecipeService {

    /**
     * Anfrage für eine Rezept mit der entsprechenden ID
     * 
     * @param id
     *            ID des gewünschten rezepts
     * @param userInterfacePort
     *            UI-Port
     * @see UserInterfacePort#showRecipe(RecipeDomainObject)
     * @throws CookbookException,
     *             wenn das Rezept mit dieser ID nicht existiert
     */
    void getRecipeById(String id, final UserInterfacePort userInterfacePort);

    /**
     * Aktualisiert ein bestehendes Rezept
     * 
     * @param recipe
     *            Rezept mit aktuellen Daten
     * 
     * @throws CookbookException,
     *             wenn das Rezept nicht existiert oder die Daten nicht korrekt sind
     */
    void setRecipe(final RecipeDomainObject recipe);

    /**
     * Speichert ein neues Rezept
     * 
     * @param recipe
     *            Neues Rezept
     * 
     * @throws CookbookException,
     *             wenn das Rezept bereits existiert oder die Daten nicht korrekt sind
     */
    void addRecipe(final RecipeDomainObject recipe);

    /**
     * Anfrage für eine Liste von ID's/Rezepttitel
     * 
     * @param userInterfacePort
     *            UI-Port
     * @see UserInterfacePort#showRecipeOverview(java.util.Map)
     */
    void getRecipeOverview(final UserInterfacePort userInterfacePort);

}
