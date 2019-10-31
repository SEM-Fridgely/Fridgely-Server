package com.sem.fridgely.http;

import com.sem.fridgely.http.interfaces.ShoppingListInterface;
import com.sem.fridgely.manager.ShoppingListManager;
import com.sem.fridgely.models.ShoppingList;
import mocks.ShoppingListMock;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShoppingLIstInterface extends JerseyTest {

    ShoppingListManager slmMock = null;

    @Override
    protected Application configure() {
        return new ResourceConfig(ShoppingListInterface.class);
    }

    @Before
    public void setup() {
        // setup Shopping List Manager mock object
        slmMock = mock(ShoppingListManager.class);
        when(slmMock.getById(anyString())).thenAnswer(invocation -> new ShoppingList(ShoppingListMock.getShopplingList()));
    }

    @Test
    public void testRoot() {
        Response response = target("/shoppingList").request().get();
        String hello = response.readEntity(String.class);
        assertEquals("hello this is shopping list page", hello);
        response.close();
    }

    @Test
    public void testGetById() {
        Response response = target("/shoppingList/sl003").request().get();
//        JSONObject actual = response.readEntity(JSONObject.class); // could not deserialize error
        assertEquals(200, response.getStatus());
        response.close();
    }

    @Test
    public void testGetByUser() {
        Response response = target("/shoppingList/user/u123/all").request().get();
        assertEquals(200, response.getStatus());
        response.close();
    }

//    @Test
//    public void testDeleteById() {
//        Response response = target("/shoppingLists/sl003").request().delete();
//        String actual = response.readEntity(String.class); // could not deserialize error
//        assertEquals(200, response.getStatus());
//        response.close();
//    }
}
