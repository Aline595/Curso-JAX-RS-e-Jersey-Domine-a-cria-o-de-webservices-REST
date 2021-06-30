package br.com.alura.loja;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import junit.framework.Assert;

public class ClienteTest {
	
	private HttpServer server;
	private Client client;
	private WebTarget target;
	
	@Before //antes de 
	public void startaServidor() {
		server = Servidor.inicializaServidor();
	    //ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja");
	    //URI uri = URI.create("http://localhost:8080/");
	    //server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
		this.client = ClientBuilder.newClient();
		this.target = client.target("http://localhost:8080/");
	}
 
	@After //Depois de cada teste executar
    public void mataServidor() {
        server.stop();
    }
	
   /* @Test // Define que é uma classe de teste
    public void testaQueAConexaoComOServidorFunciona() {
        Client client = ClientBuilder.newClient(); // Cria cliente para realizar a conexão
        WebTarget target = client.target("http://www.mocky.io"); // Define a URL base
        String conteudo = target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(String.class); // Define o subdiretorio no site, get faz requisisão 
        //System.out.println(conteudo); // Exibe xml(dados da página)
        Assert.assertTrue(conteudo.contains("<rua>Rua Vergueiro 3185")); // Define trecho que precisa ter no xml para estar correto 
    }*/

	/*@Test
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
       // ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja");
       // URI uri = URI.create("http://localhost:8080/");
       // HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");
        String conteudo = target.path("/carrinhos").request().get(String.class);
        Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
        Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());

        //server.stop();
    }*/
	
	
	@Test
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");
        String conteudo = target.path("/carrinhos/1").request().get(String.class);
        Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
        Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
    }
	
	@Test
    public void testaQueSuportaNovosCarrinhos() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");
        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");
        String xml = carrinho.toXML();

        Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);

        Response response = target.path("/carrinhos").request().post(entity);
        Assert.assertEquals(201, response.getStatus());
        //Assert.assertEquals("<status>sucesso</status>", response.readEntity(String.class));
        String location = response.getHeaderString("Location");
        String conteudo = client.target(location).request().get(String.class);
        Assert.assertTrue(conteudo.contains("Microfone"));
        
    }

}