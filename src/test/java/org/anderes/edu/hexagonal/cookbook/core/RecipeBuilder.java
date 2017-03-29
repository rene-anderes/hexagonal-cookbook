package org.anderes.edu.hexagonal.cookbook.core;

import java.time.LocalDateTime;

import org.anderes.edu.hexagonal.cookbook.domain.IngredientDomainObject;
import org.anderes.edu.hexagonal.cookbook.domain.NutritiveValueDomanObject;
import org.anderes.edu.hexagonal.cookbook.domain.RecipeDomainObject;

import static java.time.Month.*;

public abstract class RecipeBuilder {
    
    public static RecipeDomainObject createRecipe() {
        final RecipeDomainObject recipe = new RecipeDomainObject();
        recipe.setTitle("Curry-Spinat mit Mandel-Kartoffeln")
            .setPreamble("Herrlich gebratene Kartoffeln serviert auf frischem Blattspinat verfeinert mit Currypulver.")
            .setNoOfPeople("2")
            .setPreparation("Die Kartoffeln ungeschält in einer Pfanne mit Wasser bedecken und 12–15 Minuten weich kochen. Inzwischen den Spinat waschen. Tropfnass in eine Pfanne geben, leicht salzen, aufkochen und 2–3 Minuten zusammenfallen lassen. In ein Sieb abschütten, kalt abschrecken und mit einer Kelle gut ausdrücken. Die Pfanne beiseite stellen. Die Zwiebel und die Knoblauchzehen schälen und fein hacken. Den Peperoncino längs halbieren, entkernen und klein würfeln. Die Kartoffeln in ein Sieb abschütten und kalt abschrecken. Die Kartoffeln schälen. In der Spinatpfanne die Butter schmelzen. Zwiebel, Knoblauch und Peperoncino darin andünsten. Den Curry darüberstäuben und kurz mitdünsten. Den Weisswein und die Bouillon beifügen und auf grossem Feuer fast vollständig einkochen lassen. Zuletzt den Spinat und den Rahm beifügen und alles nochmals 2–3 Minuten lebhaft kochen lassen. Den Curry-Spinat mit Salz abschmecken. Gleichzeitig in einer beschichteten Bratpfanne die Bratbutter erhitzen. Die Kartoffeln darin 6–7 Minuten braten. Inzwischen die Petersilie fein hacken. Die Mandelblättchen mit einem grossen Messer grob zerkleinern. Petersilie, Mandelblättchen und Butter zu den Kartoffeln geben, alles mischen, leicht salzen und noch 2–3 Minuten fertigbraten. Mit dem Curry-Spinat auf Tellern anrichten. ")
            .setHint("Selbstverständlich kann man das Gericht auch mit Tiefkühlspinat zubereiten; in diesem Fall benötigt man etwa 300 g gefrorenes Gemüse. Entweder lässt man ihn vor der Zubereitung an- oder auftauen, sodass man ihn ausdrücken und grob schneiden kann, oder man gibt ihn noch gefroren mit etwa 1 dl Wasser in eine Pfanne und lässt ihn zugedeckt etwa 10 Minuten garen. Dann in ein Sieb abschütten und nach Rezept weiterfahren.")
            .setNutritiveValue(new NutritiveValueDomanObject(572, 18, 37, 35))
            .setRating(4)
            .setAddingDate(LocalDateTime.of(2017, MARCH, 27, 16, 00))
            .setEditingDate(LocalDateTime.of(2017, MARCH, 27, 16, 32))
            .addIngredient(new IngredientDomainObject("400 g", "Kartoffeln", "klein festkochend"))
            .addIngredient(new IngredientDomainObject("500 g", "Blattspinat frisch", "siehe Rezept-Tipp"))
            .addIngredient(new IngredientDomainObject("Salz"))
            .addIngredient(new IngredientDomainObject("1", "Zwiebel", "mittelgross"))
            .addIngredient(new IngredientDomainObject("2", "Knoblauchzehen"))
            .addIngredient(new IngredientDomainObject("1", "Peperoncino"))
            .addIngredient(new IngredientDomainObject("Butter"))
            .addIngredient(new IngredientDomainObject("1 Esslöffel", "Currypulver"))
            .addIngredient(new IngredientDomainObject("0.5 dl", "Weisswein"))
            .addIngredient(new IngredientDomainObject("0.5 dl", "Gemüseboullion"))
            .addIngredient(new IngredientDomainObject("0.5 dl", "Rahm"))
            .addIngredient(new IngredientDomainObject("1 Esslöffel", "Bratbutter"))
            .addIngredient(new IngredientDomainObject("0.5 Bund", "Peterli", "glattblättrig"))
            .addIngredient(new IngredientDomainObject("50 g", "Mandelblättchen"));
        return recipe;
    }

}

