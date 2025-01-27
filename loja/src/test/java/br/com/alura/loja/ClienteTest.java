package br.com.alura.loja;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.Projeto;
import junit.framework.Assert;

public class ClienteTest {
	
	private HttpServer server;
	private Client client;
	private WebTarget target;
	
	@Before //antes de cada teste
	public void startaServidor() {
		server = Servidor.inicializaServidor();
	    //ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja");
	    //URI uri = URI.create("http://localhost:8080/");
	    //server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
		ClientConfig config = new ClientConfig();
		config.register(new LoggingFilter());
		this.client = ClientBuilder.newClient(config);
		this.target = client.target("http://localhost:8080/");
	}
 
	@After //Depois de cada teste executar
    public void mataServidor() {
        server.stop();
    }
	
  /* @Test // Define que � uma classe de teste
    public void testaQueAConexaoComOServidorFunciona() {
        Client client = ClientBuilder.newClient(); // Cria cliente para realizar a conex�o
        WebTarget target = client.target("http://www.mocky.io"); // Define a URL base
        String conteudo = target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(String.class); // Define o subdiretorio no site, get faz requisis�o 
        //System.out.println(conteudo); // Exibe xml(dados da p�gina)
        Assert.assertTrue(conteudo.contains("<rua>Rua Vergueiro 3185")); // Define trecho que precisa ter no xml para estar correto 
    }

	@Test
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
    }
	
	*/
	@Test
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");
        Carrinho carrinho = target.path("/carrinhos/1").request().get(Carrinho.class);
        //Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
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
        //String xml = carrinho.toXML();
        //Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
        Entity<Carrinho> entity = Entity.entity(carrinho, MediaType.APPLICATION_XML);
        
        Response response = target.path("/carrinhos").request().post(entity);
        Assert.assertEquals(201, response.getStatus());
        //Assert.assertEquals("<status>sucesso</status>", response.readEntity(String.class));
        String location = response.getHeaderString("Location");
        Carrinho carrinhoCarregado = client.target(location).request().get(Carrinho.class);
        Assert.assertEquals("Microfone", carrinhoCarregado.getProdutos().get(0).getNome());
        
    }
	//patch: altera parte do objeto apenas
	
    @Test
    public void testaQueBuscarUmCarrinhoTrasUmCarrinho() {
        WebTarget target = client.target("http://localhost:8080");
        Carrinho carrinho = target.path("/carrinhos/1").request().get(Carrinho.class);
        Assert.assertEquals("Rua Vergueiro 3185, 8 andar",carrinho.getRua());
    }

    @Test
    public void testaQueAConexaoComOServidorFuncionaNoPathDeProjetos() {
        WebTarget target = client.target("http://localhost:8080");
        Projeto projeto = target.path("/projetos/1").request().get(Projeto.class);
        Assert.assertEquals(1L, projeto.getId(),0);

    }
    
    @Test
    public void testaQueSuportaNovosCarrinhos1(){
        WebTarget target = client.target("http://localhost:8080");
        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");

        Entity<Carrinho> entity = Entity.entity(carrinho, MediaType.APPLICATION_XML);
        Response response = target.path("/carrinhos").request().post(entity);
        Assert.assertEquals(201, response.getStatus());
    }

}