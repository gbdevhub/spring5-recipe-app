package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by deinf.escalda on 20/05/2021.
 */
@RunWith(SpringRunner.class)
public class IndexControllerTest {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @Mock
    Recipe receita1;

    @Mock
    Recipe receita2;

    @Test
    public void getIndexPageTest() {
        IndexController indexController = new IndexController(recipeService);

        //Dado que
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(receita1);
        recipes.add(receita2);
        Mockito.when(recipeService.getRecipes()).thenReturn(recipes);
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //Quando
        String resultado = indexController.getIndexPage(model);

        //Entao
        Assert.assertEquals("index", resultado);
        Mockito.verify(recipeService, Mockito.times(1)).getRecipes();
        Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("recipes"), argumentCaptor.capture());

        Set<Recipe> setInController = argumentCaptor.getValue();
        Assert.assertEquals(2, setInController.size());
    }
}
