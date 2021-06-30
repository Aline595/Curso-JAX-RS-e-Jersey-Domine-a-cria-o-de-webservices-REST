package br.com.alura.loja;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import junit.framework.Assert;

public class ClienteTest {
	
	private HttpServer server;
	
	@Before //antes de 
	public void startaServidor() {
		server = Servidor.inicializaServidor();
	    //ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja");
	    //URI uri = URI.create("http://localhost:8080/");
	    //server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
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

}